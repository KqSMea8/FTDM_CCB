package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;

/**
 * Created by terry on 2017/9/12.
 */
public interface IOrderCheck {

    /**
     * 检查订单是否重复提交
     * @param order_no
     * @return
     * @throws BusinessException
     */
    public boolean checkOrder(String order_no)throws BusinessException;

    /**
     * 检查订单状态是否可继续执行业务
     * @param transTransreq
     * @return
     * @throws BusinessException
     */
    public boolean checkOrderStatus(TransTransreq transTransreq)throws BusinessException;
}
