package com.sunyard.sunfintech.search.provider;

import com.sunyard.sunfintech.account.model.bo.AccountBalanceDetailRequest;
import com.sunyard.sunfintech.account.model.bo.AccountBalanceDetailResponse;
import com.sunyard.sunfintech.account.model.bo.FundChangeRequest;
import com.sunyard.sunfintech.account.model.bo.FundChangeResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.search.model.*;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyRequest;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyResponseDetail;
import com.sunyard.sunfintech.thirdparty.model.WithholdRequest;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponseDetail;
import com.sunyard.sunfintech.user.model.bo.*;

import java.util.Date;
import java.util.List;

/**
 * Created by my on 2018/7/20.
 */
public interface ISearchAllQueryService {
    public SearchAccountBalanceResponse queryAccounBalance(SearchAccountBalanceRequest accountBalanceRequest)throws BusinessException;

    public List<AccountSubjectInfo> queryAccountlist(String plat_no, String platcust)throws BusinessException;

    public SearchRepayDetailResponse queryRepayDetail(SearchRepayDetailRequest repayDetailRequest)throws BusinessException;

    public SearchProductInvestmentDetailResponse queryInvestmentDeail(SearchProductInvestmentDetailRequest investmentDetailRequest)throws BusinessException;

    /**
     * 查询标的信息
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @return ProdProductInfo
     * @throws BusinessException
     */
    public ProdProductinfo queryProdInfo(String plat_no, String prod_id)throws BusinessException;

    /**
     * 查询标的信息
     * @param productInfoRequest
     * @return
     * @throws BusinessException
     */
    public SearchProductInfoResponse queryProductInfo(SearchProductInfoRequest productInfoRequest)throws BusinessException;

    public SearchAccountBalanceDetailResponse queryAccountBalanceDetail(SearchAccountBalanceDetailRequest accountBalanceDetailRequest) throws BusinessException;

    /**
     *借款人募集账户(标的账户)余额查询
     * @author RaoYL
     * @version 20180118
     */
    public SearchProductBalanceResponse queryProductBalance(SearchProductBalanceRequest productBalanceRequest)throws BusinessException;

    /**
     * 订单状态查询
     */
    public TransTransreq queryOrderStatus(SearchOrderStatusRequest orderStatusRequest) throws BusinessException;

    public RwRecharge queryChargeStatus(ChargeStatus chargeStatus) throws BusinessException;

    /**
     * 退票补单查询
     */
    public RwRecode QueryRrfund(SearchRefundRequest refundRequest) throws BusinessException;

    /**
     * 平台对公账户余额查询
     */
    public SearchCompanyAccountBalanceResponseDetail queryCompanyAccountBalance(SearchCompanyAccountBalanceRequest companyAccountBalanceRequest) throws BusinessException;

    public AccountInfoResponse queryAccountInfo(EaccountInfoRequest eaccountInfoRequest) throws BusinessException;

    /**
     * 查询授权信息
     * @param platcust
     * @param plat_no
     * @param mall_no
     * @return
     * @throws BusinessException
     */
    public List<EaccUserauth> queryEaccUserAuth(String platcust,String plat_no,String mall_no,String... status)throws BusinessException;

    /**
     * 资金变动明细查询
     * @param fundChangeRequest
     * @return
     * @throws BusinessException
     */
    public FundChangeResponse queryFundChange(FundChangeRequest fundChangeRequest)throws BusinessException;

    public AccountSubjectInfo queryAccount(String plat_no, String platcust, String subject, String sub_subject)throws BusinessException;

    /**
     * 代扣查询
     */
    public WithholdResponseDetail queryWithhold(WithholdRequest withholdRequest)throws BusinessException;

    /**
     * @version 20180307
     * @author RaoYL
     * 客户账务往来明细查询--调E支付(晋商银行调用)
     */
    public List<PlatAccountDetailResponseList> queryAccountDetail(PlatAccountDetailRequest accountingDetailRequest)throws BusinessException;

    /**
     * 签约查询
     */

    public ContractApplyResponseDetail contractStatus(ContractApplyRequest contractApplyRequest)throws BusinessException;


}
