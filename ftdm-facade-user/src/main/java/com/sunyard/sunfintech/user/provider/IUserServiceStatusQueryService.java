package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;

import java.util.List;

/**
 * Created by terry on 2018/1/30.
 */
public interface IUserServiceStatusQueryService {

    //查询下一级模块（Account）订单状态，由schedule调用
    public List<TransTransreq> autoQueryAccountStatus(Integer limit)throws BusinessException;

    public void doProcessOrders(TransTransreq transTransreq)throws BusinessException;

    public boolean callBack(TransTransreq transTransreq);
}
