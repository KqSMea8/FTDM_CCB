package com.sunyard.sunfintech.account.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.modulepublic.TransReqService;
import com.sunyard.sunfintech.account.provider.IAccountServiceStatusQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.mapper.CustTransTransreqMapper;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2018/2/2.
 */

@Service(interfaceClass = IAccountServiceStatusQueryService.class)
@CacheConfig(cacheNames = "accountServiceStatusQueryService")
@org.springframework.stereotype.Service
public class AccountServiceStatusQueryService extends BaseServiceSimple implements IAccountServiceStatusQueryService {

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private CustTransTransreqMapper custTransTransreqMapper;

    @Autowired
    private TransReqService transReqService;

    @Override
    public TransTransreq queryMyTransSerialStatus(String order_no, String trans_serial, String plat_no) throws BusinessException {
        logger.info(String.format("【Account订单状态查询】参数|order_no:%s|trans_serial:%s|plat_no:%s",order_no,trans_serial,plat_no));
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();
        if (StringUtils.isNotBlank(trans_serial)) {
            criteria.andTrans_serialEqualTo(trans_serial);
        }
        if (StringUtils.isNotBlank(order_no)) {
            criteria.andOrder_noEqualTo(order_no);
        }
        criteria.andService_nameEqualTo(serviceName);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<TransTransreq> transTransreqList=transTransreqMapper.selectByExample(transTransreqExample);
        if(transTransreqList.size()<=0){
            logger.info(String.format("【Account订单状态查询】数据库中查无此订单，查询redis|order_no:%s|trans_serial:%s|plat_no:%s",
                    order_no,trans_serial,plat_no));
            String key=Constants.TRANSREQ_ORDER_KEY+serviceName+":"+order_no;
            Object transObj=CacheUtil.getCache().get(key);
            if(transObj==null){
                logger.info(String.format("【Account订单状态查询】redis中查无此订单|order_no:%s|trans_serial:%s|plat_no:%s",
                        order_no,trans_serial,plat_no));
                return null;
            }
            return JSON.parseObject((String)transObj,TransTransreq.class);
        }
        return transTransreqList.get(0);
    }

    @Override
    public List<TransTransreq> queryMyProcessingOrder(Integer limit) throws BusinessException {
        //查询超过5个小时处理中的订单
        Map<String,Object> params=new HashMap<>();
        params.put("start_time", DateUtils.getYesterday());
        params.put("end_time",DateUtils.addHour(DateUtils.getNow(),-5));
        params.put("service_name",serviceName);
        params.put("limit",limit);
        return custTransTransreqMapper.queryProcessOrders(params);
    }

    @Override
    public void doProcessingOrder(TransTransreq transTransreq) throws BusinessException {
        transTransreq.setStatus(FlowStatus.FAIL.getCode());
        transTransreq.setReturn_code(BusinessMsg.FAIL);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
        transReqService.insert(transTransreq);
    }
}
