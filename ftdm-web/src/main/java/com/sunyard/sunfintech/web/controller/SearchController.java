package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.prod.model.bo.ProductInfoRequest;
import com.sunyard.sunfintech.prod.model.bo.ProductInfoResponse;
import com.sunyard.sunfintech.prod.model.bo.ProductInvestmentDetailRequest;
import com.sunyard.sunfintech.prod.model.bo.ProductInvestmentDetailResponse;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyRequest;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyResponseDetail;
import com.sunyard.sunfintech.thirdparty.model.WithholdRequest;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponseDetail;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.modelold.bo.AccountingDetailRequest;
import com.sunyard.sunfintech.user.modelold.bo.AccountingDetailResponse;
import com.sunyard.sunfintech.web.business.SearchBusiness;
import com.sunyard.sunfintech.web.model.vo.search.ChargeStatusRequest;
import com.sunyard.sunfintech.web.model.vo.search.ChargeStatusResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 账户系统对外web接口
 * @author heroy
 * @version 20170504
 */
@RestController
@RequestMapping("/search")
public class SearchController extends BaseController{

	@Resource(name = "searchBusiness")
    private SearchBusiness searchBusiness;

	/**
	 * 资金变动明细查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param fundChangeRequest 资金变动明细查询  验证请求参数
	 * @return FundChangeResponse
	 */
	@RequestMapping("/fund_change")
	@Sign
	public FundChangeResponse fundChange(HttpServletRequest httpServletRequest, FundChangeRequest fundChangeRequest){
		validate(fundChangeRequest);
		FundChangeResponse fundChangeResponse = searchBusiness.queryFundChange(fundChangeRequest);
		fundChangeResponse.setOrder_no(fundChangeRequest.getOrder_no());
		return fundChangeResponse;
	}

	/**
	 * 资金余额查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param accountBalanceRequest 资金余额查询  验证请求参数
	 * @return AccountBalanceResponse
	 */
	@RequestMapping("/account_balance")
	@Sign
	public AccountBalanceResponse accountBalance(HttpServletRequest httpServletRequest, AccountBalanceRequest accountBalanceRequest){
		validate(accountBalanceRequest);
		AccountBalanceResponse accountBalanceResponse = searchBusiness.accountBalance(accountBalanceRequest);
		accountBalanceResponse.setOrder_no(accountBalanceRequest.getOrder_no());
		return accountBalanceResponse;
	}



	/**
	 * 批量代扣查询
	 */

	@RequestMapping("/withhold_status")
	@Sign
	public WithholdResponseDetail withholdStatus(HttpServletRequest httpServletRequest, WithholdRequest withholdRequest){
		validate(withholdRequest);
		WithholdResponseDetail withholdResponse=new WithholdResponseDetail();
		withholdResponse=searchBusiness.withholdStatusDeail(withholdRequest);
		return withholdResponse;
	}

	/**
	 * 还款明细查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param repayDetailRequest 还款明细查询  验证请求参数
	 * @return RepayDetailResponse
	 */
	@RequestMapping("/repay_detail")
	@Sign
	public RepayDetailResponse repayDetail(HttpServletRequest httpServletRequest, RepayDetailRequest repayDetailRequest){
		validate(repayDetailRequest);
		RepayDetailResponse repayDetailResponse = searchBusiness.queryRepayDeail(repayDetailRequest);
		repayDetailResponse.setOrder_no(repayDetailRequest.getOrder_no());
		return repayDetailResponse;
	}

	/**
	 *  根据订单号查询资金流水
	 * @param httpServletRequest
	 * @param eaccountPordListRequest
	 * @return
	 */
	@RequestMapping("/query_amtlist_and_invest")
	@Sign
	public EaccountProdResponseData eaccprodDetail(HttpServletRequest httpServletRequest,EaccountPordListRequest eaccountPordListRequest){
		validate(eaccountPordListRequest);
		EaccountProdResponseData eaccountProdListResponse=null;
			 eaccountProdListResponse=searchBusiness.queryEaccDetail(eaccountPordListRequest);
		eaccountProdListResponse.setOrder_no(eaccountPordListRequest.getOrder_no());
		return eaccountProdListResponse;
	}

    /**
     * 根据订单号查询份额流水
     * @param httpServletRequest
     * @param baseRequest
     * @return
     */
    @RequestMapping("/query_amtlist_and_investto")
    @Sign
	public EaccountProdResponseToData eaccProdToDetail(HttpServletRequest httpServletRequest, BaseRequest baseRequest){
        validate(baseRequest);
        EaccountProdResponseToData eaccountProdResponseToData=null;
        eaccountProdResponseToData=searchBusiness.queryEaccToDetail(baseRequest);
        eaccountProdResponseToData.setOrder_no(baseRequest.getOrder_no());
        return eaccountProdResponseToData;
    }

	/**
	 * 标的投资明细查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param productInvestmentDetailRequest 标的投资明细查询 验证请求参数
	 * @return ProductInvestmentDetailResponse
	 */
	@RequestMapping("/product_investment_detail")
	@Sign
	public ProductInvestmentDetailResponse productInvestmentDetail(HttpServletRequest httpServletRequest, ProductInvestmentDetailRequest productInvestmentDetailRequest ){
		logger.info("【标的投资明细查询】=========参数校验");
		validate(productInvestmentDetailRequest);
		ProductInvestmentDetailResponse productInvestmentDetailResponse = searchBusiness.queryInvestmentDeail(productInvestmentDetailRequest);
		productInvestmentDetailResponse.setOrder_no(productInvestmentDetailRequest.getOrder_no());
		return productInvestmentDetailResponse;
	}

	/**
	 * 标的信息查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param productInfoRequest 标的信息查询 验证请求参数
	 * @return ProductInfoResponse
	 */
	@RequestMapping("/product_info")
	@Sign
	public ProductInfoResponse productInfo(HttpServletRequest httpServletRequest, ProductInfoRequest productInfoRequest){
		logger.info("【标的信息查询】===========参数验证");
		validate(productInfoRequest);
		ProductInfoResponse productInfoResponse = searchBusiness.queryProdInfo(productInfoRequest);
		productInfoResponse.setOrder_no(productInfoRequest.getOrder_no());
		return productInfoResponse;
	}

	/**
	 * 账户余额明细查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param accountBalanceDetailRequest 账户余额明细查询 验证请求参数
	 * @return AccountBalanceDetailResponse
	 */
	@RequestMapping("/account_balance_detail")
	@Sign
	public AccountBalanceDetailResponse accountBalanceDetail(HttpServletRequest httpServletRequest, AccountBalanceDetailRequest accountBalanceDetailRequest ){
		validate(accountBalanceDetailRequest);
		AccountBalanceDetailResponse accountBalanceDetailResponse = searchBusiness.queryAccountBalanceDetail(accountBalanceDetailRequest);
		accountBalanceDetailResponse.setOrder_no(accountBalanceDetailRequest.getOrder_no());
		return accountBalanceDetailResponse;
	}

	/**
	 * 借款人募集账户(标的账户)余额查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param productBalanceRequest 借款人募集账户(标的账户)余额查询  验证请求参数
	 * @return ProductBalanceResponse
	 */
	@RequestMapping("/product_balance")
	@Sign
	public ProductBalanceResponse productBalance(HttpServletRequest httpServletRequest, ProductBalanceRequest productBalanceRequest ){
		validate(productBalanceRequest);
		ProductBalanceResponse productBalanceResponse = searchBusiness.queryProductbalance(productBalanceRequest);
		productBalanceResponse.setOrder_no(productBalanceRequest.getOrder_no());
		return productBalanceResponse;
	}

	/**
	 * 订单状态查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @return OrderStatusResponse
	 */
	@RequestMapping("/order_status")
	@Sign
	public OrderStatusResponse orderStatus(HttpServletRequest httpServletRequest, OrderStatusRequest orderStatusRequest ){
    	validate(orderStatusRequest);
		return searchBusiness.queryOrderStatus(orderStatusRequest);
    }
	/**
	 * 充值订单状态查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param chargeStatusRequest 充值订单状态查询  验证请求参数
	 * @return ChargeStatusResponse
	 */
	@RequestMapping("/charge_status")
	@Sign
	public ChargeStatusResponse chargeStatus(HttpServletRequest httpServletRequest, ChargeStatusRequest chargeStatusRequest ){
		logger.info("【充值订单状态查询】=============参数校验");
    	validate(chargeStatusRequest);
		ChargeStatusResponse chargeStatusResponse = searchBusiness.queryChargeStatus(chargeStatusRequest);
		chargeStatusResponse.setOrder_no(chargeStatusRequest.getOrder_no());
    	return chargeStatusResponse;

	}

	/**
	 * 退票补单查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param refundRequest 退票补单查询  验证请求参数
	 * @return RefundResponse
	 */
	@RequestMapping("/refund")
	@Sign
	public RefundResponse refund(HttpServletRequest httpServletRequest,  RefundRequest refundRequest){
		//验证参数
		validate(refundRequest);
		RefundResponse refundResponse = new RefundResponse();
		//请求business
		refundResponse=searchBusiness.queryRwRecode(refundRequest);
		refundResponse.setOrder_no(refundRequest.getOrder_no());
		return refundResponse;
	}

	/**
	 * 电子账户信息查询
	 * @author PengZY
	 * @param httpServletRequest request请求参数
	 * @param eaccountInfoRequest 电子账户信息查询  验证请求参数
	 * @return EaccountInfoResponse
	 */
	@RequestMapping("/eaccount_info")
	@Sign
	public EaccountInfoResponse eaccountInfo(HttpServletRequest httpServletRequest, EaccountInfoRequest eaccountInfoRequest){

		validate(eaccountInfoRequest);

		EaccountInfoResponse eaccountInfoResponse = searchBusiness.eaccountInfo(eaccountInfoRequest);

		eaccountInfoResponse.setOrder_no(eaccountInfoRequest.getOrder_no());

		return eaccountInfoResponse;

	}

	/**
	 * 电子账户余额查询
	 * @author PengZY
	 * @param httpServletRequest request请求参数
	 * @param eaccountBalanceRequest 电子账户余额查询  验证请求参数
	 * @return EaccountBalanceResponse
	 */
	@RequestMapping("/eaccount_balance")
	@Sign
	public EaccountBalanceResponse eaccountBalance(HttpServletRequest httpServletRequest,  EaccountBalanceRequest eaccountBalanceRequest){

		validate(eaccountBalanceRequest);

		EaccountBalanceResponse eaccountBalanceResponse = searchBusiness.eaccountBalance(eaccountBalanceRequest);

		eaccountBalanceResponse.setOrder_no(eaccountBalanceRequest.getOrder_no());

		return eaccountBalanceResponse;

	}

	/**
	 * 查询电子账户信息
	 * @author Lid
	 * @param realEaccountRequest
	 * @return RealEaccountResponse
	 */
	@RequestMapping("/real_eaccount")
	@Sign
	public RealEaccountResponse queryEaccount(HttpServletRequest httpServletRequest, RealEaccountRequest realEaccountRequest){

		validate(realEaccountRequest);

		RealEaccountResponse eaccountInfoResponse = searchBusiness.queryEaccount(realEaccountRequest);

		eaccountInfoResponse.setOrder_no(realEaccountRequest.getOrder_no());

		return eaccountInfoResponse;

	}



	/**
	 * 平台对公账户余额查询
	 * @author RaoYL
	 * @param httpServletRequest request请求参数
	 * @param companyAccountBalanceRequest 平台对公账户余额查询  验证请求参数
	 * @return CompanyAccountBalanceResponse
	 */
	@RequestMapping("/company_account_balance")
	@Sign
	public CompanyAccountBalanceResponse companyAccountBalance(HttpServletRequest httpServletRequest, CompanyAccountBalanceRequest companyAccountBalanceRequest){
		logger.info("【平台对公账户余额查询】==========参数校验");
		validate(companyAccountBalanceRequest);
		CompanyAccountBalanceResponse companyAccountBalanceResponse = searchBusiness.queryCompanyAccountBalance(companyAccountBalanceRequest);
		companyAccountBalanceResponse.setOrder_no(companyAccountBalanceRequest.getOrder_no());
		return companyAccountBalanceResponse;
	}

	@RequestMapping("/company_account_balance_old")
	@Sign
	public PlatBalanceResponse companyAccountBalanceOld(HttpServletRequest httpServletRequest, CompanyAccBalanceData companyAccBalanceData){

		validate(companyAccBalanceData);

		PlatBalanceResponse platBalanceResponse = searchBusiness.queryCompanyAccBalanceOld(companyAccBalanceData);

		platBalanceResponse.setOrder_no(companyAccBalanceData.getOrder_no());

		return platBalanceResponse;
	}

//	/**
//	 * 客户账务往来明细查询--调核心
//	 */
//	@RequestMapping("/accounting_hx_detail")
//	@Sign
//	public AccountingDetailHxResponse queryAccountingHxDetail(HttpServletRequest httpServletRequest, AccountingDetailHxRequest accountingDetailHxRequest){
//
//		validate(accountingDetailHxRequest);
//
//		AccountingDetailHxResponse accountingDetailHxResponse = searchBusiness.queryAccountingHxDetail(accountingDetailHxRequest);
//
//		accountingDetailHxResponse.setOrder_no(accountingDetailHxRequest.getOrder_no());
//
//		return accountingDetailHxResponse;
//	}

	/**
	 * @version 20180307
	 * @author RaoYL
	 * 平台真实账务往来明细查询--调核心（带附言）
	 */
	@RequestMapping("/accounting_hx_detail_note")
	@Sign
	public AccountingDetailHxNoteResponse queryAccountingHxDetailNote(HttpServletRequest httpServletRequest, AccountingDetailHxNoteRequest accountingDetailHxNoteRequest){
		logger.info("【平台真实账务往来明细查询(带附言)】===========参数校验");
		validate(accountingDetailHxNoteRequest);
		AccountingDetailHxNoteResponse accountingDetailHxNoteResponse = searchBusiness.queryAccountingHxDetailNote(accountingDetailHxNoteRequest);
		accountingDetailHxNoteResponse.setOrder_no(accountingDetailHxNoteRequest.getOrder_no());
		return accountingDetailHxNoteResponse;
	}

	/**
	 * @version 20180307
	 * @author RaoYL
	 * 客户账务往来明细查询--调E支付(老查询接口)
	 */
	@RequestMapping("/accounting_detail_old")
	@Sign
	public AccountingDetailResponse queryAccountingDetailOld(HttpServletRequest httpServletRequest, AccountingDetailRequest accountingDetailRequest){
		logger.info("【平台真实账务往来明细查询】===========参数校验");
		validate(accountingDetailRequest);
		AccountingDetailResponse accountingDetailResponse = searchBusiness.queryAccountingDetailOld(accountingDetailRequest);
		accountingDetailResponse.setOrder_no(accountingDetailRequest.getOrder_no());
		return accountingDetailResponse;
	}

	/**
	 * @version 20180307
	 * @author RaoYL
	 * 客户账务往来明细查询--调E支付(晋商银行调用)
	 */
	@RequestMapping("/accounting_detail")
	@Sign
	public PlatAccountDetailResponse queryAccountingDetail(HttpServletRequest httpServletRequest, PlatAccountDetailRequest accountingDetailRequest){
		logger.info("【平台真实账务往来明细查询】===========参数校验");
		validate(accountingDetailRequest);
		PlatAccountDetailResponse accountingDetailResponse = searchBusiness.queryAccountingDetail(accountingDetailRequest);
		accountingDetailResponse.setOrder_no(accountingDetailRequest.getOrder_no());
		return accountingDetailResponse;
	}

	/**
	 * 真实电子账户余额信息
	 * @author pzy
	 * @param realEaccountBalance
	 * @return RealEaccountResponse
	 */
	@RequestMapping("/real_eaccount_balance")
	@Sign
	public RealEaccountBalanceResponseDetail realEaccountBalance(HttpServletRequest httpServletRequest, RealEaccountBalance realEaccountBalance){
		validate(realEaccountBalance);
		RealEaccountBalanceResponseDetail realEaccountBalanceResponseDetail = searchBusiness.queryRealEaccountBalance(realEaccountBalance);
		return realEaccountBalanceResponseDetail;

	}

	/**
	 * 账户信息查询
	 * @author RaoYL
	 * @version 20180125
	 */
	@RequestMapping("/account_info")
	@Sign
	public AccountInfoResponse accountInfo(HttpServletRequest httpServletRequest, EaccountInfoRequest eaccountInfoRequest){
		validate(eaccountInfoRequest);
		AccountInfoResponse accountInfoResponse = searchBusiness.accountInfo(eaccountInfoRequest);
		accountInfoResponse.setOrder_no(eaccountInfoRequest.getOrder_no());
		return accountInfoResponse;
	}

	@RequestMapping("/auth_info")
	@Sign
	public AuthInfoResponse authInfo(HttpServletRequest httpServletRequest, AuthInfoRequest authInfoRequest){
		validate(authInfoRequest);
		AuthInfoResponse authInfoResponse = searchBusiness.queryAuthInfo(authInfoRequest);
		authInfoResponse.setOrder_no(authInfoRequest.getOrder_no());
		return authInfoResponse;
	}

}
