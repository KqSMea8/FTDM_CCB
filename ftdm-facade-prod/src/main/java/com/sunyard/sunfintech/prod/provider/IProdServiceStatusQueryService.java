package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;

import java.util.List;

/**
 * Created by terry on 2018/1/30.
 */
public interface IProdServiceStatusQueryService {

    //查询下一级模块（Account）订单状态，由schedule调用
    public List<TransTransreq> autoQueryAccountStatus(int limit)throws BusinessException;

    public void doProcessOrders(TransTransreq transTransreq)throws BusinessException;

    public boolean transCodeRouter(TransTransreq transTransreq)throws BusinessException;
}
