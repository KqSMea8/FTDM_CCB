package com.sunyard.sunfintech.prod.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountServiceStatusQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.custdao.mapper.CustTransTransreqMapper;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.prod.provider.IProdServiceStatusQueryService;
import com.sunyard.sunfintech.pub.provider.IOrderCheck;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.user.provider.IUserTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2018/1/30.
 */
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = IProdServiceStatusQueryService.class)
@Service
public class ProdServiceStatusQueryService extends BaseServiceSimple implements IProdServiceStatusQueryService {

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private IAccountServiceStatusQueryService accountServiceStatusQueryService;

    @Autowired
    private ProdMQOptionService prodMQOptionService;
    @Autowired
    private CustTransTransreqMapper custTransTransreqMapper;

    @Autowired
    private IOrderCheck orderCheck;

    private    List<TransTransreq>  queryProdProcessing(int limit) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
        map.put("limit", limit);
        map.put("service_name", "prod");
        String[] transCodes = {TransConsts.BUY_CODE, TransConsts.TRANSFERDEBT_CODE, TransConsts.PRODREPAY_CODE};
        map.put("trans_codes", transCodes);
        List<TransTransreq> transTransreqs = custTransTransreqMapper.queryProdProcessing(map);
        return transTransreqs;
    }
    @Override
    public List<TransTransreq> autoQueryAccountStatus(int limit) throws BusinessException {
        logger.info(String.format("【Prod订单状态主动查询】开始查询|time:%s,limit:%s", DateUtils.getTime(), limit));
        //查询最近2小时前到24小时提交过来的处理中的还款
//        TransTransreqExample transTransreqExample=new TransTransreqExample();
//        TransTransreqExample.Criteria transTransreqCriteria=transTransreqExample.createCriteria();
//        transTransreqCriteria.andCreate_timeBetween(DateUtils.getYesterday(),DateUtils.addHour(DateUtils.getNow(),-2));
//        transTransreqCriteria.andStatusEqualTo(FlowStatus.INPROCESS.getCode());
//        transTransreqCriteria.andEnabledEqualTo(Constants.ENABLED);

        try {
            List<TransTransreq> processOrders = queryProdProcessing(limit);
            // List<TransTransreq> processOrders=transTransreqMapper.selectByExample(transTransreqExample);
//            for (TransTransreq transTransreq : processOrders) {
//                logger.info("【Prod订单状态主动查询】向account查询订单状态|参数：" + JSON.toJSONString(transTransreq));
//                TransTransreq accountTransTransreq = accountServiceStatusQueryService.queryMyTransSerialStatus(
//                        transTransreq.getOrder_no(), transTransreq.getTrans_serial(), transTransreq.getPlat_no());
//                logger.info("【Prod订单状态主动查询】查询到account订单状态|参数：" + JSON.toJSONString(accountTransTransreq));
//
//                if (accountTransTransreq != null) {
//                    logger.info(String.format("【Prod订单状态主动查询】订单已为终态，做补单处理|trans_serial:%s|order_no=%s",
//                            accountTransTransreq.getTrans_serial(), accountTransTransreq.getOrder_no()));
//                    accountTransTransreq.setReturn_msg("job发起查询置状态位");
//                    transCodeRouter(accountTransTransreq);
//                } else {
//                    transTransreq.setStatus(OrderStatus.FAIL.getCode());
//                    transTransreq.setReturn_code(BusinessMsg.FAIL);
//                    transTransreq.setReturn_msg("account中找不到订单，job置超时处理");
//                }
//            }
            return processOrders;
        } catch (Exception e) {
            logger.error("【autoQueryAccountStatus】异常", e);
        }
        return null;
    }

    @Override
    public void doProcessOrders(TransTransreq transTransreq) throws BusinessException {
        logger.info("【Prod订单状态主动查询】向account查询订单状态|参数：" + JSON.toJSONString(transTransreq));
        TransTransreq accountTransTransreq = accountServiceStatusQueryService.queryMyTransSerialStatus(
                transTransreq.getOrder_no(), transTransreq.getTrans_serial(), transTransreq.getPlat_no());
        logger.info("【Prod订单状态主动查询】查询到account订单状态|参数：" + JSON.toJSONString(accountTransTransreq));

        if (accountTransTransreq != null){
            if(FlowStatus.SUCCESS.getCode().equals(accountTransTransreq.getStatus()) ||
                        FlowStatus.FAIL.getCode().equals(accountTransTransreq.getStatus())) {
                logger.info(String.format("【Prod订单状态主动查询】订单已为终态，做补单处理|trans_serial:%s|order_no=%s",
                        accountTransTransreq.getTrans_serial(), accountTransTransreq.getOrder_no()));
                transCodeRouter(accountTransTransreq);
            }
        } else {
            transTransreq.setStatus(OrderStatus.FAIL.getCode());
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg("account中找不到订单，job置超时处理");
            transCodeRouter(transTransreq);
        }
    }

    @Override
    public boolean transCodeRouter(TransTransreq transTransreq) throws BusinessException {
        //检查当前订单当前状态，判断是否可以继续执行或者回滚转账
        Boolean orderCheckStatus=orderCheck.checkOrderStatus(transTransreq);
        if(!orderCheckStatus){
            return true;
        }
        String trans_code=transTransreq.getTrans_code();
        logger.info(String.format("【Prod异步通知接收】处理消息|trans_code:%s|params:%s",trans_code, JSON.toJSONString(transTransreq)));
        switch (trans_code){
            case TransConsts.BUY_CODE:{
                logger.info(String.format("【Prod异步通知接收】接收到投资消息|trans_serial:%s|status:%s|return_code:%s|return_msg:%s",
                        transTransreq.getTrans_serial(),transTransreq.getStatus(),transTransreq.getReturn_code(),transTransreq.getReturn_msg()));
                //TODO 投资补单
                prodMQOptionService.investAsyncCallBack(transTransreq);
                break;
            }
            case TransConsts.TRANSFERDEBT_CODE:{
                logger.info("【Prod异步通知接收】接收到债转消息|trans_serial:%s|status:%s|return_code:%s|return_msg:%",
                        transTransreq.getTrans_serial(),transTransreq.getStatus(),transTransreq.getReturn_code(),transTransreq.getReturn_msg());
                prodMQOptionService.finishTransProd(transTransreq);
                break;
            }
            case TransConsts.PRODREPAY_CODE:{
                logger.info("【Prod异步通知接收】接收到标的还款消息|trans_serial:%s|status:%s|return_code:%s|return_msg:%",
                        transTransreq.getTrans_serial(),transTransreq.getStatus(),transTransreq.getReturn_code(),transTransreq.getReturn_msg());
                prodMQOptionService.finishProdRefund(transTransreq);
                break;
            }
            default:{
                return false;
            }
        }
        return true;
    }
}
