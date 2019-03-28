package com.sunyard.sunfintech.user.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountServiceStatusQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.custdao.mapper.CustTransTransreqMapper;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.pub.provider.IOrderCheck;
import com.sunyard.sunfintech.user.provider.IUserServiceStatusQueryService;
import com.sunyard.sunfintech.user.service.callback.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2018/2/2.
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = IUserServiceStatusQueryService.class)
public class UserServiceStatusQueryService extends BaseServiceSimple implements IUserServiceStatusQueryService {

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private IAccountServiceStatusQueryService accountServiceStatusQueryService;

    @Autowired
    private CustTransTransreqMapper custTransTransreqMapper;

    @Autowired
    private IOrderCheck orderCheck;

    @Override
    public List<TransTransreq> autoQueryAccountStatus(Integer limit) throws BusinessException {
        logger.info(String.format("【User订单状态主动查询】开始查询|time:%s", DateUtils.getTime()));
        //查询最近2小时前到24小时提交过来的处理中的还款
//        TransTransreqExample transTransreqExample=new TransTransreqExample();
//        TransTransreqExample.Criteria transTransreqCriteria=transTransreqExample.createCriteria();
//        transTransreqCriteria.andCreate_timeBetween(DateUtils.getYesterday(),DateUtils.addHour(DateUtils.getNow(),-2));
//        transTransreqCriteria.andStatusEqualTo(FlowStatus.INPROCESS.getCode());
//        transTransreqCriteria.andEnabledEqualTo(Constants.ENABLED);
//        List<TransTransreq> processOrders=transTransreqMapper.selectByExample(transTransreqExample);
        Map<String, Object> map = new HashMap<>();
        map.put("limit", limit);
        map.put("service_name", serviceName);
        String[] transCodes = {TransConsts.PLAT_TO_ACCOUNT_CODE, TransConsts.PAY_FEE_CODE,
                TransConsts.AUTH_PAY_FEE_CODE,TransConsts.CANCEL_PAY_FEE_CODE,TransConsts.BORROWREPAY_CODE};
        map.put("trans_codes", transCodes);
        List<TransTransreq> processOrders = custTransTransreqMapper.queryProdProcessing(map);
        return processOrders;
    }

    @Override
    public void doProcessOrders(TransTransreq transTransreq) throws BusinessException {
            logger.info("【User订单状态主动查询】向account查询订单状态|参数："+ JSON.toJSONString(transTransreq));
            TransTransreq accountTransTransreq=accountServiceStatusQueryService.queryMyTransSerialStatus(
                    transTransreq.getOrder_no(),transTransreq.getTrans_serial(),transTransreq.getPlat_no());
            logger.info("【User订单状态主动查询】查询到account订单状态|参数："+JSON.toJSONString(accountTransTransreq));
            if(accountTransTransreq!=null){
                logger.info(String.format("【User订单状态主动查询】订单已为终态，做补单处理|trans_serial:%s|order_no=%s",
                        transTransreq.getTrans_serial(),transTransreq.getOrder_no()));
                if(FlowStatus.SUCCESS.getCode().equals(accountTransTransreq.getStatus())
                        || FlowStatus.FAIL.getCode().equals(accountTransTransreq.getStatus())){
                    callBack(accountTransTransreq);
                }
            } else {
                transTransreq.setStatus(OrderStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg("account中找不到订单，job置超时处理");
                callBack(transTransreq);
            }
    }


    public boolean callBack(TransTransreq transTransreq) {
        //检查当前订单当前状态，判断是否可以继续执行或者回滚转账
        Boolean orderCheckStatus=orderCheck.checkOrderStatus(transTransreq);
        if(!orderCheckStatus){
            return true;
        }

        if (errorMsg!=null&&errorMsg.containsKey(transTransreq.getTrans_code())) {
            TransRouter transRouter = errorMsg.get(transTransreq.getTrans_code());
            logger.info(String.format("【User异步通知接收】处理消息|trans_code:%s|params:%s", transTransreq.getTrans_code(), JSON.toJSONString(transTransreq)));
            transRouter.onCallBack(transTransreq);
            logger.info(String.format("【User异步通知接收】处理消息|trans_code:%s|params:%s,处理成功！", transTransreq.getTrans_code(), JSON.toJSONString(transTransreq)));
        } else {
            logger.info("【User异步通知接收】未匹配到trans_code，丢弃消息|trans_code:%s|order_no:%s|trans_serial:%s",
                    transTransreq.getTrans_code(), transTransreq.getOrder_no(), transTransreq.getTrans_serial());
            return false;
        }
        return true;
    }

    private static final Map<String, TransRouter> errorMsg = new HashMap<String, TransRouter>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;
        {
            put(TransConsts.PLAT_TO_ACCOUNT_CODE, SpringContextHolder.getBean(PlatToAccountService.class));
            put(TransConsts.PAY_FEE_CODE, SpringContextHolder.getBean(PayfeeService.class));
            put(TransConsts.AUTH_PAY_FEE_CODE, SpringContextHolder.getBean(AuthPayFeeService.class));
            put(TransConsts.CANCEL_PAY_FEE_CODE, SpringContextHolder.getBean(CancelPayFeeService.class));
            put(TransConsts.BORROWREPAY_CODE, SpringContextHolder.getBean(BorrowRepayService.class));
        }
    };
}
