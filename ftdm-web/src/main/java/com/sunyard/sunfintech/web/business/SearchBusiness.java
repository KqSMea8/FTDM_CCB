package com.sunyard.sunfintech.web.business;

import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.RwRecode;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.model.bo.ProductInfoRequest;
import com.sunyard.sunfintech.prod.model.bo.ProductInfoResponse;
import com.sunyard.sunfintech.prod.model.bo.ProductInvestmentDetailRequest;
import com.sunyard.sunfintech.prod.model.bo.ProductInvestmentDetailResponse;
import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import com.sunyard.sunfintech.thirdparty.model.WithholdRequest;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponseDetail;
import com.sunyard.sunfintech.user.model.bo.AccountInfoResponse;
import com.sunyard.sunfintech.user.model.bo.ChargeStatus;
import com.sunyard.sunfintech.user.model.bo.CompanyAccountBalanceResponse;
import com.sunyard.sunfintech.user.model.bo.EaccountInfoRequest;
import com.sunyard.sunfintech.user.model.bo.RefundResponse;
import com.sunyard.sunfintech.user.model.bo.RefundResponseDetail;
import com.sunyard.sunfintech.user.modelold.bo.AccountDetailTran;
import com.sunyard.sunfintech.user.modelold.bo.AccountingDetailBo;
import com.sunyard.sunfintech.user.modelold.bo.AccountingDetailRequest;
import com.sunyard.sunfintech.user.modelold.bo.AccountingDetailResponse;
import com.sunyard.sunfintech.user.provider.*;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.web.model.vo.search.*;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Service("searchBusiness")
public class SearchBusiness {
	@Autowired
	private IUserTransService userTransService;

	@Autowired
	private IProdRISearchService prodRISearchService;

	@Autowired
	private IAccountSearchService accountSearchService;

	@Autowired
    private IThirdpartySearchService thirdpartySearchService;

	@Autowired
	private IAccountQueryService accountQueryService;

	@Autowired
	private IProdSearchService prodSearchService;

	@Autowired
	private IUserSearchService userSearchService;

	@Autowired
	private IUserAccountService userAccountService;


	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(getClass());

	/**
	 * 还款明细查询
	 * @author RaoYL
	 * @param repayDetailRequest
	 * @return repayDetailResponse
	 */
    public RepayDetailResponse queryRepayDeail(RepayDetailRequest repayDetailRequest) {
		RepayDetailResponse repayDetailResponse = new RepayDetailResponse();
		try {
			repayDetailResponse = prodRISearchService.queryRepayDetail(repayDetailRequest );
		} catch (BusinessException e) {
			BaseResponse baseResponse=new BaseResponse();
			baseResponse.setRecode(e.getBaseResponse().getRecode());
			baseResponse.setRemsg(e.getBaseResponse().getRemsg());
			throw new BusinessException(baseResponse);
		}
		return repayDetailResponse;
	}

	/**
	 * 根据订单资金流水查询
	 * @param eaccountPordListRequest
	 * @return
	 */
	public EaccountProdResponseData queryEaccDetail(EaccountPordListRequest eaccountPordListRequest){
		EaccountProdResponseData eaccountProdListResponse =new EaccountProdResponseData();
		try {
			eaccountProdListResponse=accountQueryService.queryEaccAccoun(eaccountPordListRequest);
		}catch (BusinessException e){
			BaseResponse baseResponse=new BaseResponse();
			baseResponse.setRecode(e.getBaseResponse().getRecode());
			baseResponse.setRemsg(e.getBaseResponse().getRemsg());
			throw new BusinessException(baseResponse);
		}
		return eaccountProdListResponse;
	}

	/**
	 * 根据订单份额流水查询
	 * @param baseRequest
	 * @return
	 */
	public EaccountProdResponseToData queryEaccToDetail(BaseRequest baseRequest){
		EaccountProdResponseToData eaccountProdResponseToData=new EaccountProdResponseToData();
		try {
			eaccountProdResponseToData=accountQueryService.queryEaccToAccoun(baseRequest);
		}catch (BusinessException e) {
			BaseResponse baseResponse1=new BaseResponse();
			baseResponse1.setRecode(e.getBaseResponse().getRecode());
			baseResponse1.setRemsg(e.getBaseResponse().getRemsg());
			throw new BusinessException(baseResponse1);
		}
		return eaccountProdResponseToData;
	}


	/**
	 * 账户余额查询
	 * @author RaoYL
	 * @param accountBalanceRequest
	 * @return accountBalanceResponse
	 */
	public AccountBalanceResponse accountBalance(AccountBalanceRequest accountBalanceRequest){
		AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
		accountBalanceResponse.setOrder_no(accountBalanceRequest.getOrder_no());
		try{
			accountBalanceResponse= accountQueryService.queryAccounBalance(accountBalanceRequest);
		} catch (BusinessException e){
			accountBalanceResponse.setRecode(e.getBaseResponse().getRecode());
			accountBalanceResponse.setRemsg(e.getBaseResponse().getRemsg());
		}

		return  accountBalanceResponse;
	}


	/**
	 * 批量代扣订单查询
	 */
	public WithholdResponseDetail withholdStatusDeail(WithholdRequest withholdRequest){
		WithholdResponseDetail withholdResponseDetail=new WithholdResponseDetail();
		withholdResponseDetail.setOrder_no(withholdRequest.getOrder_no());
		try{
			withholdResponseDetail=userSearchService.queryWithhold(withholdRequest);
			if (withholdResponseDetail!=null){
				withholdResponseDetail.setRecode(BusinessMsg.SUCCESS);
				withholdResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			}
		}catch (BusinessException e){
			withholdResponseDetail.setRemsg(e.getBaseResponse().getRemsg());
			withholdResponseDetail.setRecode(e.getBaseResponse().getRecode());
		}
		return withholdResponseDetail;
	}

	/**
	 * 资金变动明细查询
	 * @author RaoYL
	 * @param fundChangeRequest
	 * @return fundChangeResponse
	 */
	public FundChangeResponse queryFundChange(FundChangeRequest fundChangeRequest){
		FundChangeResponse fundChangeResponse= new FundChangeResponse();
		try{
			fundChangeResponse=accountQueryService.queryFundChange(fundChangeRequest);
		} catch (BusinessException e){
			fundChangeResponse.setRecode(e.getBaseResponse().getRecode());
			fundChangeResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return  fundChangeResponse;
	}

	/**
     * 标的投资明细查询
     * @author Lid
     * @param productInvestmentDetailRequest
     * @return
     */
	public ProductInvestmentDetailResponse queryInvestmentDeail(ProductInvestmentDetailRequest productInvestmentDetailRequest) {
		ProductInvestmentDetailResponse detailResponse = new ProductInvestmentDetailResponse();
		try {
			detailResponse = prodSearchService.queryInvestmentDeail(productInvestmentDetailRequest);
		}  catch (BusinessException e) {
			detailResponse.setRecode(e.getBaseResponse().getRecode());
			detailResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return detailResponse;
	}

	/**
	 * 标的信息查询
	 * @param productInfoRequest
	 * @return productInfoResponse
	 */
	public ProductInfoResponse queryProdInfo(ProductInfoRequest productInfoRequest) {
        ProductInfoResponse productInfoResponse = new ProductInfoResponse();
        try {

			productInfoResponse = prodSearchService.queryProductInfo(productInfoRequest);

        } catch (BusinessException e) {

			productInfoResponse.setRecode(e.getBaseResponse().getRecode());
			productInfoResponse.setRemsg(e.getBaseResponse().getRemsg());

        }
		return productInfoResponse;
    }

	/**
	 * 订单状态查询
	 * @author bob
	 */
	public OrderStatusResponse queryOrderStatus(OrderStatusRequest orderStatusRequest){
		logger.info("【订单状态查询】order_no:"+orderStatusRequest.getOrder_no());
		OrderStatusResponse orderStatusResponse=new OrderStatusResponse();
		orderStatusResponse.setOrder_no(orderStatusRequest.getOrder_no());
		OrderStatusResponseDetail orderStatusResponseDetails = new OrderStatusResponseDetail();
		orderStatusResponseDetails.setQuery_order_no(orderStatusRequest.getQuery_order_no());
		orderStatusResponseDetails.setPlat_no(orderStatusRequest.getMer_no());
		try {
			TransTransreq transTransReq = userSearchService.queryOrderStatus(orderStatusRequest);
			//判断是否有数据
			if(null==transTransReq) {
                orderStatusResponse.setData_detail(orderStatusResponseDetails);
				orderStatusResponse.setRecode(BusinessMsg.ORDER_NOEXISTENT);
				orderStatusResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORDER_NOEXISTENT));
				return  orderStatusResponse;
			}
			if (null != transTransReq) {
				orderStatusResponseDetails.setStatus(transTransReq.getStatus());
				orderStatusResponseDetails.setReturn_msg(transTransReq.getReturn_msg());
				orderStatusResponseDetails.setReturn_code(transTransReq.getReturn_code());
			}
			orderStatusResponseDetails.setTrans_serial(transTransReq.getTrans_serial());
            orderStatusResponse.setData_detail(orderStatusResponseDetails);
            orderStatusResponse.setRecode(BusinessMsg.SUCCESS);
            orderStatusResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			orderStatusResponse.setData_detail(orderStatusResponseDetails);
			orderStatusResponse.setRemsg(e.getBaseResponse().getRemsg());
			orderStatusResponse.setRecode(e.getBaseResponse().getRecode());
		}
		return  orderStatusResponse;
	}

	/**
	 * 充值订单状态查询
	 * @author dingjy
	 * @param chargeStatusRequest
	 * @return ChargeStatusResponse
	 */
	public ChargeStatusResponse queryChargeStatus(ChargeStatusRequest chargeStatusRequest){
		logger.info("【充值订单状态查询】order_no"+chargeStatusRequest.getOrder_no()+"开始查询，原充值订单号："+chargeStatusRequest.getOriginal_serial_no());
		ChargeStatusResponseDetail chargeStatusResponseDetail=new ChargeStatusResponseDetail();
		ChargeStatusResponse chargeStatusResponse= new ChargeStatusResponse();
		chargeStatusResponse.setOrder_no(chargeStatusRequest.getOrder_no());
		chargeStatusResponseDetail.setQuery_order_no(chargeStatusRequest.getOriginal_serial_no());
		chargeStatusResponseDetail.setPlat_no(chargeStatusRequest.getMer_no());
		try {
			ChargeStatus chargeStatus=new ChargeStatus();
			BeanUtils.copyProperties(chargeStatusRequest,chargeStatus);
			logger.info("【充值订单状态查询】order_no："+chargeStatusRequest.getOrder_no()+"开始调用第三方支付查询，原充值订单号："+chargeStatusRequest.getOriginal_serial_no());
			RwRecharge rwRecharge=userSearchService.queryChargeStatus(chargeStatus);
			logger.info("【充值订单状态查询】order_no："+chargeStatusRequest.getOrder_no()+"调用第三方支付查询结束，原充值订单号："+chargeStatusRequest.getOriginal_serial_no());
			if (null != rwRecharge) {
				chargeStatusResponseDetail.setTrans_serial(rwRecharge.getTrans_serial());
				//如果金额不对
                logger.info("【充值订单状态查询】order_no："+chargeStatusRequest.getOrder_no()+"第三方支付返回正常，原充值订单号："+chargeStatusRequest.getOriginal_serial_no());
                chargeStatusResponseDetail.setStatus(rwRecharge.getStatus());
                chargeStatusResponseDetail.setReturn_msg(rwRecharge.getReturn_msg());
                chargeStatusResponseDetail.setReturn_code(rwRecharge.getReturn_code());
                chargeStatusResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                chargeStatusResponse.setRecode(BusinessMsg.SUCCESS);
            }else {
				logger.info("【充值订单状态查询】order_no："+chargeStatusRequest.getOrder_no()+"第三方支付返回无此原充值订单号，原充值订单号："+chargeStatusRequest.getOriginal_serial_no());
				chargeStatusResponse.setRecode(BusinessMsg.ORDER_NOEXISTENT);
				chargeStatusResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORDER_NOEXISTENT));
			}

            chargeStatusResponse.setData_detail(chargeStatusResponseDetail);
		} catch (Exception e) {
			chargeStatusResponse.setData_detail(chargeStatusResponseDetail);
			if(e instanceof BusinessException) {
				BusinessException businessException=(BusinessException) e;
				logger.error("【充值订单状态查询】order_no："+chargeStatusRequest.getOrder_no()+"内部异常，原充值订单号："+chargeStatusRequest.getOriginal_serial_no()+e.getMessage());
				chargeStatusResponse.setRecode(businessException.getBaseResponse().getRecode());
				chargeStatusResponse.setRemsg(businessException.getBaseResponse().getRemsg());
			}else {
				logger.error("【充值订单状态查询】order_no"+chargeStatusRequest.getOrder_no()+"未知异常，原充值订单号："+chargeStatusRequest.getOriginal_serial_no()+e.getMessage());
				chargeStatusResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
				chargeStatusResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE)+"充值订单状态查询未知异常");
			}
		}
		logger.info("【充值订单状态查询】order_no"+chargeStatusRequest.getOrder_no()+"查询结束，原充值订单号："+chargeStatusRequest.getOriginal_serial_no());
		return chargeStatusResponse;
	}

    /**
     * 退票补单查询
	 * @author dingjy
     * @return RefundResponse
     */
	public RefundResponse queryRwRecode(RefundRequest refundRequest){
        RwRecode rwRecode= new RwRecode();
        RefundResponse refundResponse=new RefundResponse();
		RefundResponseDetail refundResponseDetail=new RefundResponseDetail();
		refundResponseDetail.setOrder_no(refundRequest.getQuery_order_no());
        try {
            rwRecode=userSearchService.QueryRrfund(refundRequest);
        } catch (BusinessException e) {
            refundResponse.setRecode(e.getBaseResponse().getRecode());
            refundResponse.setRemsg(e.getBaseResponse().getRemsg());
            return refundResponse;
        }

        if(rwRecode!=null) {
			refundResponseDetail.setAmt(rwRecode.getAmt());
			refundResponseDetail.setType(rwRecode.getType());
			refundResponse.setData_detail(refundResponseDetail);
			refundResponse.setRecode(BusinessMsg.SUCCESS);
			refundResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}else {
			refundResponse.setRecode(BusinessMsg.ORDER_NOEXISTENT);
			refundResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORDER_NOEXISTENT));
		}
        return refundResponse;
    }

	/**
	 * 电子账户信息查询
	 * @author PengZY
	 * @param eaccountInfoRequest 电子账户信息查询  验证请求参数
	 * @return EaccountInfoResponse
	 */
	public EaccountInfoResponse eaccountInfo(EaccountInfoRequest eaccountInfoRequest){

		EaccountInfoResponse eaccountInfoResponse = new EaccountInfoResponse();

		try{

			eaccountInfoResponse = accountSearchService.queryEaccountInfo(eaccountInfoRequest);

		} catch (BusinessException e) {

			eaccountInfoResponse.setRecode(e.getBaseResponse().getRecode());
			eaccountInfoResponse.setRemsg(e.getBaseResponse().getRemsg());

		}
		return eaccountInfoResponse;
	}
//
	/**
	 * 电子账户余额查询
	 * @author PengZY
	 * @param eaccountBalanceRequest 电子账户余额查询  验证请求参数
	 * @return EaccountBalanceResponse
	 */
	public EaccountBalanceResponse eaccountBalance(EaccountBalanceRequest eaccountBalanceRequest){

		EaccountBalanceResponse eaccountBalanceResponse = new EaccountBalanceResponse();
		eaccountBalanceResponse.setOrder_no(eaccountBalanceRequest.getOrder_no());
		try {

			eaccountBalanceResponse = accountSearchService.queryEaccountInfoBalance(eaccountBalanceRequest);

		}catch (BusinessException e){

			eaccountBalanceResponse.setRecode(e.getBaseResponse().getRecode());
			eaccountBalanceResponse.setRemsg(e.getBaseResponse().getRemsg());

		}
		return eaccountBalanceResponse;

	}
//
	/**
     * 账户余额明细查询
	 * @author RaoYL
     * @param accountBalanceDetailRequest
     * @return accountBalanceDetailResponse
     */
	public AccountBalanceDetailResponse queryAccountBalanceDetail(AccountBalanceDetailRequest accountBalanceDetailRequest){
		AccountBalanceDetailResponse accountBalanceaDetail = new AccountBalanceDetailResponse();
		try {
			accountBalanceaDetail = accountQueryService.queryAccountBalanceDetail(accountBalanceDetailRequest);
		} catch (BusinessException e) {
			accountBalanceaDetail.setRecode(e.getBaseResponse().getRecode());
			accountBalanceaDetail.setRemsg(e.getBaseResponse().getRemsg());
        }
		return accountBalanceaDetail;
	}
	/**
	 *借款人募集账户(标的账户)余额查询
	 * @author RaoYL
	 * @param productBalanceRequest 借款人募集账户(标的账户)余额查询  验证请求参数
	 * @return ProductBalanceResponse
	 */
	public ProductBalanceResponse queryProductbalance(ProductBalanceRequest productBalanceRequest){
		ProductBalanceResponse productBalanceResponse=new ProductBalanceResponse();
		try{
			productBalanceResponse = prodSearchService.queryProductBalance(productBalanceRequest);
		} catch (BusinessException e){
			productBalanceResponse.setRecode(e.getBaseResponse().getRecode());
			productBalanceResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return productBalanceResponse;

	}

	/**
	 * 查询电子账户信息
	 * @param realEaccountRequest
	 * @return QueryPlatBalanceRes
	 */
	public RealEaccountResponse queryEaccount(RealEaccountRequest realEaccountRequest){
		RealEaccountResponse eaccountResponse = new RealEaccountResponse();

		try {
			eaccountResponse=accountSearchService.queryEaccount(realEaccountRequest);
		} catch (BusinessException e) {
			eaccountResponse.setRecode(e.getBaseResponse().getRecode ());
			eaccountResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return eaccountResponse;
	}

//	/**
//	 * 客户账务往来明细查询
//	 */
//	public AccountingDetailResponse queryAccountingDetail(AccountingDetailRequest accountingDetailRequest){
//		AccountingDetailResponse accountingDetailResponse = new AccountingDetailResponse();
//
//		try {
//			accountingDetailResponse=thirdpartySearchService.queryAccountingDetail(accountingDetailRequest);
//		} catch (BusinessException e) {
//			accountingDetailResponse.setRecode(e.getBaseResponse().getRecode ());
//			accountingDetailResponse.setRemsg(e.getBaseResponse().getRemsg());
//		}
//		accountingDetailResponse.setOrder_no(accountingDetailRequest.getOrder_no());
//		accountingDetailResponse.setSign("");
//		return accountingDetailResponse;
//	}

	/**
	 * 真实电子账户余额查询
	 */
	public RealEaccountBalanceResponseDetail queryRealEaccountBalance(RealEaccountBalance realEaccountBalance){
		RealEaccountBalanceResponseDetail realEaccountBalanceResponseDetail = new RealEaccountBalanceResponseDetail();
		try {
			realEaccountBalanceResponseDetail = accountSearchService.queryRealEaccountBalance(realEaccountBalance);
			realEaccountBalanceResponseDetail.setRecode(BusinessMsg.SUCCESS);
			realEaccountBalanceResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}catch (BusinessException e){
			realEaccountBalanceResponseDetail.setRecode(e.getBaseResponse().getRecode ());
			realEaccountBalanceResponseDetail.setRemsg(e.getBaseResponse().getRemsg());
		}
		return realEaccountBalanceResponseDetail;
	}

	/**
	 * 平台对公账户余额查询
	 * @param companyAccountBalanceRequest
	 * @return CompanyAccountBalanceResponse
	 */
	public CompanyAccountBalanceResponse queryCompanyAccountBalance(CompanyAccountBalanceRequest companyAccountBalanceRequest){
		CompanyAccountBalanceResponse companyAccountBalanceResponse = new CompanyAccountBalanceResponse();
		companyAccountBalanceResponse.setOrder_no(companyAccountBalanceRequest.getOrder_no());
		try {
			CompanyAccountBalanceResponseDetail dataDetail = userSearchService.queryCompanyAccountBalance(companyAccountBalanceRequest);
			companyAccountBalanceResponse.setData_detail(dataDetail);
			companyAccountBalanceResponse.setRecode(BusinessMsg.SUCCESS);
			companyAccountBalanceResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			companyAccountBalanceResponse.setRecode(e.getBaseResponse().getRecode());
			companyAccountBalanceResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return companyAccountBalanceResponse;
	}

	/**
	 * 平台对公账户余额查询（老接口）
	 * @param companyAccBalanceData
	 * @return QueryPlatBalanceRes
	 */
	public PlatBalanceResponse queryCompanyAccBalanceOld(CompanyAccBalanceData companyAccBalanceData){
		PlatBalanceResponse platBalanceResponse=new PlatBalanceResponse();
		platBalanceResponse.setOrder_no(companyAccBalanceData.getOrder_no());
		try {
			CompanyAccountDetailData companyAccountDetailData = userSearchService.queryAccountByPublic(companyAccBalanceData);
			platBalanceResponse.setData_detail(companyAccountDetailData);
			platBalanceResponse.setRecode(BusinessMsg.SUCCESS);
			platBalanceResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			platBalanceResponse.setRecode(e.getBaseResponse().getRecode());
			platBalanceResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return platBalanceResponse;
	}

//	/**
//	 * 客户账务往来明细查询--核心
//	 */
//	public AccountingDetailHxResponse queryAccountingHxDetail(AccountingDetailHxRequest accountingDetailHxRequest){
//		AccountingDetailHxResponse accountingDetailHxResponse = new AccountingDetailHxResponse();
//		try {
//			accountingDetailHxResponse =thirdpartySearchService.queryAccountingDetail(accountingDetailHxRequest);
//		} catch (BusinessException e) {
//			accountingDetailHxResponse.setRecode(e.getBaseResponse().getRecode ());
//			accountingDetailHxResponse.setRemsg(e.getBaseResponse().getRemsg());
//		}
//		accountingDetailHxResponse.setOrder_no(accountingDetailHxRequest.getOrder_no());
//		return accountingDetailHxResponse;
//	}

	/**
	 * 平台真实账务往来明细查询--调核心（带附言)
	 */
	public AccountingDetailHxNoteResponse queryAccountingHxDetailNote(AccountingDetailHxNoteRequest accountingDetailHxNoteRequest){
		AccountingDetailHxNoteResponse accountingDetailHxNoteResponse = new AccountingDetailHxNoteResponse();
		try {
			accountingDetailHxNoteResponse = userSearchService.queryAccountingDetailNote(accountingDetailHxNoteRequest);
			accountingDetailHxNoteResponse.setRecode(BusinessMsg.SUCCESS);
			accountingDetailHxNoteResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			accountingDetailHxNoteResponse.setRecode(e.getBaseResponse().getRecode ());
			accountingDetailHxNoteResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		accountingDetailHxNoteResponse.setOrder_no(accountingDetailHxNoteRequest.getOrder_no());
		return accountingDetailHxNoteResponse;
	}

	/**
	 * 客户账务往来明细查询--E支付(老查询接口)
	 */
	public AccountingDetailResponse queryAccountingDetailOld(AccountingDetailRequest accountingDetailRequest){
		AccountingDetailResponse accountingDetailResponse = new AccountingDetailResponse();
		AccountingDetailBo accountingDetailBo = new AccountingDetailBo();
		BeanUtils.copyProperties(accountingDetailRequest, accountingDetailBo);
		try {
			List<AccountDetailTran> accountDetailTrans =userSearchService.queryAccountDetailOld(accountingDetailRequest);
			accountingDetailResponse.setData_detail(accountDetailTrans);
			accountingDetailResponse.setRecode(BusinessMsg.SUCCESS);
			accountingDetailResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			accountingDetailResponse.setRecode(e.getBaseResponse().getRecode ());
			accountingDetailResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		accountingDetailResponse.setOrder_no(accountingDetailRequest.getOrder_no());
		return accountingDetailResponse;
	}

	/**
	 * @version 20180307
	 * @author RaoYL
	 * 客户账务往来明细查询--调E支付(晋商银行调用)
	 */
	public PlatAccountDetailResponse queryAccountingDetail(PlatAccountDetailRequest accountingDetailRequest){
		PlatAccountDetailResponse accountingDetailResponse = new PlatAccountDetailResponse();
		try {
			List<PlatAccountDetailResponseList> accountDetailList = userSearchService.queryAccountDetail(accountingDetailRequest);
			accountingDetailResponse.setData_detail(accountDetailList);
			accountingDetailResponse.setRecode(BusinessMsg.SUCCESS);
			accountingDetailResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			accountingDetailResponse.setRecode(e.getBaseResponse().getRecode ());
			accountingDetailResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		accountingDetailResponse.setOrder_no(accountingDetailRequest.getOrder_no());
		return accountingDetailResponse;
	}

	/**
	 * 账户信息查询
	 * @author RaoYL
	 * @param eaccountInfoRequest 平台客户子账户信息查询  验证请求参数
	 * @return EaccountInfoResponse
	 */
	public AccountInfoResponse accountInfo(EaccountInfoRequest eaccountInfoRequest){
		AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
		try{
			accountInfoResponse = accountSearchService.queryAccountInfo(eaccountInfoRequest);
		} catch (BusinessException e) {
			accountInfoResponse.setRecode(e.getBaseResponse().getRecode());
			accountInfoResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return accountInfoResponse;
	}

    public AuthInfoResponse queryAuthInfo(AuthInfoRequest authInfoRequest) {
		return userAccountService.queryAuthInfo(authInfoRequest);
    }
}
