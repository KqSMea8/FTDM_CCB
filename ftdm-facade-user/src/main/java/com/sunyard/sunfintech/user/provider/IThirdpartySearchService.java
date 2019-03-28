package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.RwRecode;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.user.model.bo.*;

public interface IThirdpartySearchService {
    /**
     * 订单状态查询
     */
    //public TransTransreq QueryOrderStatus(OrderStatusRequire orderStatusRequire) throws BusinessException;

    /**
     * 充值订单状态查询
     */
    //public RwRecharge queryChargeStatus(ChargeStatus chargeStatus) throws BusinessException;

    /**
     * 退票补单查询
     */
   // public RwRecode QueryRrfund(RefundRequest refundRequest) throws BusinessException;

    /**
     * 平台对公账户余额查询
     */
    //public QueryPlatBalanceRes QueryCompanyAccountBalance(CompanyAccountBalanceData companyAccountBalanceData) throws BusinessException;

    /**
     * 客户账务往来明细查询
     */
//    public AccountingDetailHxResponse queryAccountingDetail(BaseRequest baseRequest) throws BusinessException;

    /**
     * 客户账务往来明细查询（带附言）
     */
//    public AccountingDetailHxNoteResponse queryAccountingDetailNote(AccountingDetailHxNoteRequest accountingDetailHxNoteRequest) throws BusinessException;

    /**
     * pzy
     * 真是电子账户余额查询
     */
    //TODO 杨磊 public RealEaccountBalanceResponseDetail queryRealEaccountBalance(RealEaccountBalance realEaccountBalance) throws BusinessException;

}
