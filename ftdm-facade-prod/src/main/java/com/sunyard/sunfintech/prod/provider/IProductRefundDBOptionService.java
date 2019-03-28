package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.ProdRepaymentlist;
import com.sunyard.sunfintech.dao.entity.ProdShareList;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepayOld;

import java.math.BigDecimal;

/**
 * Created by terry on 2017/7/13.
 */
public interface IProductRefundDBOptionService {

    /**
     * 标的还款数据库操作
     * @param prodProductinfo
     * @param platcust
     * @param selectOne
     * @param repaymentlist
     * @throws BusinessException
     */
    public void doRefundDBOption(ProdProductinfo prodProductinfo, BigDecimal real_repay_amount, String platcust, ProdShareList selectOne, ProdRepaymentlist repaymentlist)throws BusinessException;

    /**
     * 查询还款记录
     * @param trans_serial
     * @throws BusinessException
     */
    public ProdRepaymentlist queryProdRepaymentlist(String trans_serial)throws BusinessException;

    /**
     * 删除还款记录
     * @param prodRepaymentlist
     * @return
     * @throws BusinessException
     */
    public boolean delProdRepaymentlist(ProdRepaymentlist prodRepaymentlist)throws BusinessException;

    /**
     * 还款回滚
     * @param transTransreq
     * @throws BusinessException
     */
    public void backProdRefund(TransTransreq transTransreq)throws BusinessException;
}
