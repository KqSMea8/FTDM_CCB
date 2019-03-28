package com.sunyard.sunfintech.account.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;

import java.util.List;

/**
 * Created by terry on 2018/1/30.
 */
public interface IAccountServiceStatusQueryService {

    //查询当前模块订单状态，由其它模块调用
    public TransTransreq queryMyTransSerialStatus(String order_no, String trans_serial, String plat_no)throws BusinessException;

    public List<TransTransreq> queryMyProcessingOrder(Integer limit)throws BusinessException;

    public void doProcessingOrder(TransTransreq transTransreq)throws BusinessException;
}
