package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepayOld;
import com.sunyard.sunfintech.prod.model.bo.ProdRepayData;
import com.sunyard.sunfintech.prod.model.mq.ProdRefundData;

/**
 * Created by terry on 2017/7/13.
 */
public interface IProductRefundExtService {

    /**
     * 标的还款非电子账户还款(一借多贷用老接口)
     * @param custRepay
     * @param transTransreq
     * @param subject
     * @return
     * @throws BusinessException
     */
    public boolean refundToAccount(BatchCustRepayOld custRepay, TransTransreq transTransreq, String subject)throws BusinessException;

    /**
     * 标的还款电子账户还款
     * @param custRepay
     * @param transTransreq
     * @return
     * @throws BusinessException
     */
    public boolean refundToEaccount(BatchCustRepayOld custRepay, TransTransreq transTransreq)throws BusinessException;

    /**
     * 标的还款异步还款
     * @param custRepay
     * @param transTransreq
     * @param prodProductinfo
     * @return
     * @throws BusinessException
     */
    public boolean refundForAsyn(BatchCustRepay custRepay, TransTransreq transTransreq, ProdProductinfo prodProductinfo)throws BusinessException;
}
