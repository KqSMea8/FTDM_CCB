package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.RwRecode;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.thirdparty.model.WithholdRequest;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponseDetail;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.modelold.bo.AccountDetailTran;
import com.sunyard.sunfintech.user.modelold.bo.AccountingDetailRequest;

import java.util.List;

public interface IUserSearchService {
    /**
     * 订单状态查询
     */
    public TransTransreq queryOrderStatus(OrderStatusRequest orderStatusRequest) throws BusinessException;

    /**
     * 充值订单状态查询
     */
    public RwRecharge queryChargeStatus(ChargeStatus chargeStatus) throws BusinessException;


    /**
     * 平台对公账户余额查询
     */
    public CompanyAccountBalanceResponseDetail queryCompanyAccountBalance(CompanyAccountBalanceRequest companyAccountBalanceRequest) throws BusinessException;

    /**
     * 对公平台账户查询（老接口）
     * @throws BusinessException
     */
    public CompanyAccountDetailData queryAccountByPublic(CompanyAccBalanceData companyAccBalanceData)throws BusinessException;

    /**
     * 平台真实账务往来明细查询--调核心（带附言）
     */
    public AccountingDetailHxNoteResponse queryAccountingDetailNote(AccountingDetailHxNoteRequest accountingDetailHxNoteRequest) throws BusinessException;

    /**
     * 平台真实账户往来明细查询(老查询接口)
     * @throws BusinessException
     */
    public List<AccountDetailTran> queryAccountDetailOld(AccountingDetailRequest accountingDetailBo)throws BusinessException;

    /**
     * @version 20180307
     * @author RaoYL
     * 客户账务往来明细查询--调E支付(晋商银行调用)
     */
    public List<PlatAccountDetailResponseList> queryAccountDetail(PlatAccountDetailRequest accountingDetailRequest)throws BusinessException;

    /**
     * pzy
     * 真是电子账户余额查询
     */
    //TODO 杨磊 public RealEaccountBalanceResponseDetail queryRealEaccountBalance(RealEaccountBalance realEaccountBalance) throws BusinessException;

    /**
     * 退票补单查询
     */
    public RwRecode QueryRrfund(RefundRequest refundRequest) throws BusinessException;

    /**
     * 代扣查询
     */
    public WithholdResponseDetail queryWithhold(WithholdRequest withholdRequest)throws BusinessException;
}
