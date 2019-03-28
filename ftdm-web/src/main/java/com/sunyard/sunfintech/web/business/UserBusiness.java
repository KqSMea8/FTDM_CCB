package com.sunyard.sunfintech.web.business;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.FirstCertRequest;
import com.sunyard.sunfintech.account.model.bo.FirstCertResponse;
import com.sunyard.sunfintech.account.model.bo.QueryFirstCertRequest;
import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.AuthStatus;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.BeanUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.EaccUserauth;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyRequest;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyResponseDetail;
import com.sunyard.sunfintech.thirdparty.model.ContractConfirmRequest;
import com.sunyard.sunfintech.thirdparty.model.ContractStatusReponse;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.model.bo.BatchRepayAsynRequest;
import com.sunyard.sunfintech.user.model.bo.OrderData;
import com.sunyard.sunfintech.user.model.bo.SwitchAccountRequest;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayAsynRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.CompensateRepayRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.SubstituteRepayRequestOld;
import com.sunyard.sunfintech.user.provider.*;
import com.sunyard.sunfintech.web.model.vo.account.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PengZY
 *
 */
@Service("userBusiness")
public class UserBusiness {
	//dd
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private IUserBorrowerService userBorrowerService;

	@Autowired
	private IUserBindCardService userBindCardService;

	@Autowired
	private IUserAccountService userAccountService;
	@Autowired
	private IUserAccountExtService  userAccountExtService;
	@Autowired
	private IProdSearchService prodSearchService;
	@Autowired
	private IUserTransferService userTransferService;

	@Autowired
	private IRwWithdrawOptionService rwWithdrawOptionService;

	@Resource(name = "notifyBusiness")
	private NotifyBusiness notifyBusiness;
	/**
	 * 缴费
	 *
	 * @param payFeeRequest
	 * @return
	 */
 public 	BaseResponse payFee(PayFeeRequest  payFeeRequest) throws BusinessException{

	return userTransferService.payFee(payFeeRequest);
 }

	/**
	 * 授权缴费
	 *
	 * @param authPayFeeRequest
	 * @return
	 */
	public	AuthPayFeeResponse authPayFee(AuthPayFeeRequest authPayFeeRequest)  throws BusinessException{

		return userTransferService.authPayFee(authPayFeeRequest);
	}



	/**
	 * 取消缴费
	 *
	 * @param cancelPayFeeRequest
	 * @return
	 */
	public	BaseResponse cancelPayFee(CancelPayFeeRequest cancelPayFeeRequest)  throws BusinessException{
		return userTransferService.cancelPayFee(cancelPayFeeRequest);
	}
	/**
     * 代偿还款
     * @author PengZY
     * @param substituteRepayRequest 代偿还款请求参数
     * @return SubstituteRepayResponse 代偿还款响应参数
     */
	public SubstituteRepayResponse substituteRepay(SubstituteRepayRequest substituteRepayRequest) throws BusinessException{

		SubstituteRepayResponse substituteRepayResponse = new SubstituteRepayResponse();
		substituteRepayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
    	try {
    		if(StringUtils.isNotBlank(substituteRepayRequest.getTrans_pwd()) && StringUtils.isNotBlank(substituteRepayRequest.getRandom_key())){
    			substituteRepayResponse = userBorrowerService.substituteRepay(substituteRepayRequest);
			}
    	}catch (BusinessException e){
			OrderData orderData = new OrderData();
			orderData.setOrder_status(OrderStatus.FAIL.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
			substituteRepayResponse.setOrderData(orderData);
			substituteRepayResponse.setRecode(e.getBaseResponse().getRecode());
			substituteRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
    	}
        return substituteRepayResponse;
    }

	/**
	 * 授权代偿还款
	 * @author PengZY
	 * @param substituteRepayRequest 代偿还款请求参数
	 * @return SubstituteRepayResponse 代偿还款响应参数
	 */
	public SubstituteRepayResponse authSubstituteRepay(SubstituteRepayRequest substituteRepayRequest) throws BusinessException{

		SubstituteRepayResponse substituteRepayResponse = new SubstituteRepayResponse();
		substituteRepayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		try {
			substituteRepayResponse = userBorrowerService.substituteRepay(substituteRepayRequest);
		}catch (BusinessException e){
			OrderData orderData = new OrderData();
			orderData.setOrder_status(OrderStatus.FAIL.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
			substituteRepayResponse.setOrderData(orderData);
			substituteRepayResponse.setRecode(e.getBaseResponse().getRecode());
			substituteRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return substituteRepayResponse;
	}
	
	/**
     * 借款人还款代偿
     * @author PengZY
     * @param compensateRepayRequest 借款人还款代偿 还款请求参数
     * @return CompensateRepayResponse 借款人还款代偿 还款响应参数
     */
    public CompensateRepayResponse compensateRepay(CompensateRepayRequest compensateRepayRequest){
		CompensateRepayResponse compensateRepayResponse = new CompensateRepayResponse();
		compensateRepayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		try {
        	compensateRepayResponse = userBorrowerService.compensateRepay(compensateRepayRequest);
		}catch (BusinessException e){
			compensateRepayResponse.setRecode(BusinessMsg.DUBBO_TIMEOUT);
			compensateRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DUBBO_TIMEOUT));
			compensateRepayResponse.setOrder_no(compensateRepayRequest.getOrder_no());
			compensateRepayResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
		}
        return compensateRepayResponse;
    }

	/**
	 * 四要素开户申请
	 * @param applyRequest
	 * @return
	 */
	public BaseResponse apply4ElementsRegister(Apply4ElementsRegisterRequest applyRequest){
    	BaseResponse baseResponse=new BaseResponse();
    	try{
    		userBindCardService.apply4ElementsRegister(applyRequest);
			baseResponse.setRecode(BusinessMsg.SUCCESS);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}catch (Exception e){
    		logger.error(String.format("【四要素开户申请】异常|origin_order_no:%s|trans_serial:%s|error:",
			applyRequest.getOrigin_order_no(),applyRequest.getTrans_serial()),e);
    		if(e instanceof BusinessException){
    			baseResponse=((BusinessException)e).getBaseResponse();
			}else{
    			//dubbo异常
				baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
			}
		}
		baseResponse.setOrder_no(applyRequest.getOrigin_order_no());
		return baseResponse;

	}

	/**
	 * 四要素开户确认
	 * @param confirmRequest
	 * @return
	 */
	public BatchRegisterReturnData confirm4ElementsRegister(Confirm4ElementsRegisterRequest confirmRequest){
		BatchRegisterReturnData batchRegisterReturnData=new BatchRegisterReturnData();
		if(StringUtils.isBlank(confirmRequest.getOrigin_order_no())){
			batchRegisterReturnData.setOrder_no(confirmRequest.getOrder_no());
			batchRegisterReturnData.setOrigin_order_no(confirmRequest.getOrder_no());
		}else{
			batchRegisterReturnData.setOrder_no(confirmRequest.getOrigin_order_no());
			batchRegisterReturnData.setOrigin_order_no(confirmRequest.getOrigin_order_no());
		}
		try{
			batchRegisterReturnData=userBindCardService.confirm4ElementsRegister(confirmRequest);
			batchRegisterReturnData.setRecode(BusinessMsg.SUCCESS);
			batchRegisterReturnData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			if(StringUtils.isNotBlank(confirmRequest.getOrigin_order_no())){
				batchRegisterReturnData.setPlatcust(StringUtils.hidePlatcust(batchRegisterReturnData.getPlatcust()));
			}
		}catch (Exception e){
			logger.error(String.format("【四要素开户确认】异常|origin_order_no:%s|error:",
					confirmRequest.getOrigin_order_no()),e);
			if(e instanceof BusinessException){
				batchRegisterReturnData.setRecode(((BusinessException) e).getBaseResponse().getRecode());
				batchRegisterReturnData.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
			}else{
				//dubbo异常
				batchRegisterReturnData.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
				batchRegisterReturnData.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
			}
		}
		return batchRegisterReturnData;

	}

	/**
	 * 三要素开户申请
	 * @param applyRequest
	 * @return
	 */
	public BatchRegisterReturnData apply3ElementsRegister(Apply3ElementsRegisterRequest applyRequest){
		BatchRegisterReturnData batchRegisterReturnData=new BatchRegisterReturnData();
		if(StringUtils.isBlank(applyRequest.getOrigin_order_no())){
			batchRegisterReturnData.setOrder_no(applyRequest.getOrder_no());
			batchRegisterReturnData.setOrigin_order_no(applyRequest.getOrigin_order_no());
		}else{
			batchRegisterReturnData.setOrder_no(applyRequest.getOrigin_order_no());
			batchRegisterReturnData.setOrigin_order_no(applyRequest.getOrigin_order_no());
		}
		try{
			batchRegisterReturnData=userBindCardService.apply3ElementsRegister(applyRequest);
			batchRegisterReturnData.setRecode(BusinessMsg.SUCCESS);
			batchRegisterReturnData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			if(StringUtils.isNotBlank(applyRequest.getOrigin_order_no())){
				batchRegisterReturnData.setPlatcust(StringUtils.hidePlatcust(batchRegisterReturnData.getPlatcust()));
			}
		}catch (Exception e){
			logger.error(String.format("【三要素开户确认】异常|origin_order_no:%s|error:",
					applyRequest.getOrigin_order_no()),e);
			if(e instanceof BusinessException){
				batchRegisterReturnData.setRecode(((BusinessException) e).getBaseResponse().getRecode());
				batchRegisterReturnData.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
			}else{
				//dubbo异常
				batchRegisterReturnData.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
				batchRegisterReturnData.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
			}
		}
		return batchRegisterReturnData;

	}

	/**
	 * 企业开户申请
	 * @param applyRequest
	 * @return
	 */
	public BatchRegisterReturnData applyOrgRegister(ApplyOrgRegisterRequest applyRequest){
		BatchRegisterReturnData batchRegisterReturnData=new BatchRegisterReturnData();
		if(StringUtils.isNotBlank(applyRequest.getOrder_no())){
			batchRegisterReturnData.setOrder_no(applyRequest.getOrder_no());
			batchRegisterReturnData.setOrigin_order_no(applyRequest.getOrder_no());
		}else{
			batchRegisterReturnData.setOrder_no(applyRequest.getOrigin_order_no());
			batchRegisterReturnData.setOrigin_order_no(applyRequest.getOrigin_order_no());
		}
		try{
			batchRegisterReturnData=userBindCardService.applyOrgRegister(applyRequest);
			batchRegisterReturnData.setRecode(BusinessMsg.SUCCESS);
			batchRegisterReturnData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			/*if(StringUtils.isNotBlank(applyRequest.getOrder_no())){
				batchRegisterReturnData.setPlatcust(StringUtils.hidePlatcust(batchRegisterReturnData.getPlatcust()));
			}*/
		}catch (Exception e){
			logger.error(String.format("【企业开户申请】异常|origin_order_no:%s|error:",
					applyRequest.getOrigin_order_no()),e);
			if(e instanceof BusinessException){
				batchRegisterReturnData.setRecode(((BusinessException) e).getBaseResponse().getRecode());
				batchRegisterReturnData.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
			}else{
				//dubbo异常
				batchRegisterReturnData.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
				batchRegisterReturnData.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
			}
		}
		return batchRegisterReturnData;

	}

	/**
	 * 境外开户
	 * @param applyRequests
	 * @return
	 */
	public BaseResponse applyOrgRegisterOpening(ApplyOrgRegisterOpeningRequest applyRequests){
		BaseResponse applyOrgRegisterOpeningRequest=new BaseResponse();
		try {
			applyOrgRegisterOpeningRequest=userBindCardService.applyOrgRegisterOpening(applyRequests);
			applyOrgRegisterOpeningRequest.setOrder_no(applyRequests.getOrder_no());
			applyOrgRegisterOpeningRequest.setOrder_status(FlowStatus.INPROCESS.getCode());
			applyOrgRegisterOpeningRequest.setRecode(BusinessMsg.SUCCESS);
			applyOrgRegisterOpeningRequest.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}catch (Exception e){
			logger.error(String.format("【境外开户异常】异常|origin_order_no:%s|error:",applyRequests.getOrder_no(),e));
			throw e;
		}

		return applyOrgRegisterOpeningRequest ;
	}


	/**
	 * 企业开户审核
	 * @param confirmRequest
	 * @return
	 */
	public BaseResponse confirmOrgRegister(ConfirmOrgRegisterRequest confirmRequest){
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setOrder_no(confirmRequest.getOrigin_order_no());
		try{
			baseResponse=userBindCardService.confirmOrgRegister(confirmRequest);
//			baseResponse.setRecode(BusinessMsg.SUCCESS);
//			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}catch (Exception e){
			logger.error(String.format("【企业开户审核】异常|origin_order_no:%s|error:",
					confirmRequest.getOrigin_order_no()),e);
			if(e instanceof BusinessException){
				baseResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
				baseResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
			}else{
				//dubbo异常
				baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
			}
		}
		return baseResponse;

	}

	/**
	 * 绑卡核验
	 * @param checkCardRequest
	 * @return
	 */
	public BaseResponse checkCard(CheckCardRequest checkCardRequest){
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setOrder_no(checkCardRequest.getOrder_no());
		try{
			baseResponse=userBindCardService.checkCard(checkCardRequest);
//			baseResponse.setRecode(BusinessMsg.SUCCESS);
//			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}catch (Exception e){
			logger.error(String.format("【绑卡核验】异常|order_no:%s|error:",
					checkCardRequest.getOrder_no()),e);
			if(e instanceof BusinessException){
				baseResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
				baseResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
			}else{
				//dubbo异常
				baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
			}
		}
		return baseResponse;

	}

	/**
	 * 设置交易密码
	 * @param setNewPasswordRequest
	 * @return
	 */
	public BaseResponse setNewPassword(SetNewPasswordRequest setNewPasswordRequest){
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setOrder_no(setNewPasswordRequest.getOrder_no());
		try{
			baseResponse=userAccountService.setNewPassword(setNewPasswordRequest);
//			baseResponse.setRecode(BusinessMsg.SUCCESS);
//			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}catch (Exception e){
			logger.error(String.format("【设置交易密码】异常|order_no:%s|error:",
					setNewPasswordRequest.getOrder_no()),e);
			if(e instanceof BusinessException){
				baseResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
				baseResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
			}else{
				//dubbo异常
				baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
			}
		}
		return baseResponse;

	}

	/**
	 * 電子賬戶開戶
	 * @author dany
	 * @param platplatcustRegisterRequest 请求参数
	 * @return platplatcustRegisterResponse
	 */
	public PlatplatcustRegisterResponse registerEaccount(PlatplatcustRegisterRequest platplatcustRegisterRequest)throws BusinessException {
		PlatplatcustRegisterResponse platplatcustRegisterResponse = new PlatplatcustRegisterResponse();
		PlatplatcustRegisterResponseData platplatcustRegisterResponseData=new PlatplatcustRegisterResponseData();
		//copy传参对象
		try{
			logger.info("电子账户开户--going");
			platplatcustRegisterResponseData = userAccountService.openEaccount(platplatcustRegisterRequest);
			if(platplatcustRegisterResponseData==null){
				platplatcustRegisterResponse.setRecode(BusinessMsg.FAIL);
				platplatcustRegisterResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
			}else{
				logger.info("电子账户开户返回platcust:"+platplatcustRegisterResponseData.getPlatcust());
				logger.info("电子账户开户返回json:"+JSON.toJSONString(platplatcustRegisterResponseData, GlobalConfig.serializerFeature));
				platplatcustRegisterResponse.setRecode(BusinessMsg.SUCCESS);
				platplatcustRegisterResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				platplatcustRegisterResponse.setPlatplatcustRegisterResponseData(platplatcustRegisterResponseData);
				platplatcustRegisterResponse.setSign("sunyard");
			}
			platplatcustRegisterResponse.setOrder_no(platplatcustRegisterRequest.getOrder_no());
			logger.info("电子账户开户操作成功");
		} catch (BusinessException e) {
			logger.error("电子账户开户操作出现错误 ，错误信息：" + e);
			platplatcustRegisterResponse.setOrder_no(platplatcustRegisterRequest.getOrder_no());
			platplatcustRegisterResponse.setRecode(e.getBaseResponse().getRecode());
			platplatcustRegisterResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return platplatcustRegisterResponse;
	}

	public List<BaseErrorData> getErrorDataList(BusinessException e) {
		BaseErrorData baseErrorData = new BaseErrorData();
		baseErrorData.setDetail_no(e.getBaseResponse().getOrder_no());
		baseErrorData.setError_no(e.getBaseResponse().getRecode());
		baseErrorData.setError_info(e.getBaseResponse().getRemsg());
		List<BaseErrorData> errorDataList = new ArrayList<BaseErrorData>();
		errorDataList.add(baseErrorData);
		return errorDataList;
	}


    /**
     * 短验绑卡申请
     * @author terry
     * @param applyBindCardRequest
     * @return
     * @throws BusinessException
     */
	public boolean applyBindCard(ApplyBindCardRequest applyBindCardRequest)throws BusinessException{
		ApplyBindCardRequestBo applyBindCardRequestBo=new ApplyBindCardRequestBo();
		BeanUtils.copyProperties(applyBindCardRequest,applyBindCardRequestBo);
		userBindCardService.bingCardByMsg(applyBindCardRequestBo);
		return true;
	}

	/**
	 * 短验绑卡确认
	 * wubin
	 * @param confirmBindCardRequest
	 * @return
	 */
	public ConfirmBindCardResponse confirmBindCard(ConfirmBindCardRequest confirmBindCardRequest){
		ConfirmBindCardResponse confirmBindCardResponse = new ConfirmBindCardResponse();
		ConfirmBindCardBo confirmBindCardBo = new ConfirmBindCardBo();
		try {
			BeanUtils.copyProperties(confirmBindCardRequest,confirmBindCardBo);
			String plcust = userBindCardService.confirmBindCard(confirmBindCardBo);
			confirmBindCardResponse.setPlatcust(plcust);
			confirmBindCardResponse.setRecode(BusinessMsg.SUCCESS);
			confirmBindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		}catch (BusinessException e){
			confirmBindCardResponse.setRecode(e.getErrorCode());
			confirmBindCardResponse.setRemsg(e.getErrorMsg());
		}
		confirmBindCardResponse.setOrder_no(confirmBindCardBo.getOrder_no());
		confirmBindCardResponse.setSign("");
		return confirmBindCardResponse;
	}

	/**
	 * 客户信息变更
	 * @author pengzy
	 * @param changeAccountInfoRequest
	 * @throws BusinessException
	 */
	public ChangeAccountInfoResponse changeAccountInfo(ChangeAccountInfoRequest changeAccountInfoRequest) throws BusinessException{

		ChangeAccountInfoResponse changeAccountInfoResponse = new ChangeAccountInfoResponse();
		changeAccountInfoResponse.setTrans_date(DateUtils.getyyyyMMddDate());
    	try {
    		changeAccountInfoResponse = userAccountService.changeAccountInfo(changeAccountInfoRequest);
    	}catch (BusinessException e){
			changeAccountInfoResponse.setRecode(e.getBaseResponse().getRecode());
			changeAccountInfoResponse.setRemsg(e.getBaseResponse().getRemsg());
    	}
        return changeAccountInfoResponse;
    }

	/**
	 * 借款人还款申请
	 * @author pengzi
	 * 2018年01月07日
	 */
	public BatchInvestNoSynResponse batchRepayAsyn(BatchRepayAsynRequest batchRepayRequest){
		BatchInvestNoSynResponse batchRepayResponse = new BatchInvestNoSynResponse();
		batchRepayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		try {
			userBorrowerService.batchRepayAsyn(batchRepayRequest);
			batchRepayResponse.setRecode(BusinessMsg.SUCCESS);
			batchRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			batchRepayResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
			batchRepayResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
		} catch (BusinessException e) {
			batchRepayResponse.setRecode(e.getBaseResponse().getRecode());
			batchRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
			batchRepayResponse.setOrder_status(OrderStatus.FAIL.getCode());
			batchRepayResponse.setOrder_info(OrderStatus.FAIL.getMsg());
		}
		return batchRepayResponse;
	}

	/**
	 * 解绑
	 * @author terry
	 * @param unBindCardRequest
	 * @return
	 */
	public UnBindCardReturn unbindCard(UnBindCardRequest unBindCardRequest) throws BusinessException
	{
		return userBindCardService.bathUnbindCard(unBindCardRequest,unBindCardRequest.getData_detail());
	}

	/**
	 * 批量解绑
	 * @author Bob
	 */
	public UnBindCardReturn unbindCardForMultiCards(UnBindCardForMultiCardsRequest unBindCardForMultiCardsRequest) throws BusinessException
	{
		return userBindCardService.batchUnbindCardForMultiCards(unBindCardForMultiCardsRequest,
				unBindCardForMultiCardsRequest.getData_detail());
	}

	/**
	 * 批量换卡（先绑卡再解绑）
	 * @author Raoyulu
	 * @param batchChangeCardRequest
	 * @return
	 * @throws BusinessException
	 */
	public BatchChangeCardReturn batchChangeCard(BatchChangeCardRequest batchChangeCardRequest) throws BusinessException{
		return userBindCardService.batchChangeCard(batchChangeCardRequest,
				batchChangeCardRequest.getBatchChangeCardRequestDetailList());
	}

	/**
	 * 无短验绑卡，企业绑卡
	 * @param bindCardRequest
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean orgBindCard(BindCardRequest bindCardRequest) throws BusinessException{
		return userBindCardService.orgBindCard(bindCardRequest);
	}

	/**
	 * 无短验绑卡
	 * @param bindCardRequest
	 * @return
	 * @throws BusinessException
	 */
	public boolean bindCard(BindCardRequest bindCardRequest) throws BusinessException{
		return userBindCardService.bindCardByNoMsg(bindCardRequest);
	}

	/**
	 *三要素绑卡（仅支持个人信用卡，绑定卡仅用于提现）
	 * @param personBindCardRequest
	 * @return
	 * @throws BusinessException
	 */
	public boolean personCreditBindCard(PersonBindCardRequest personBindCardRequest) throws BusinessException{
		return userBindCardService.personBindCard(personBindCardRequest);
	}
	/**
	 * 電子賬戶開戶for存量用户
	 * @author dany
	 * @param platplatcustRegisterRequest 请求参数
	 * @return platplatcustRegisterResponse
	 */
	public PlatplatcustRegisterResponse registerEaccountForRegularSubscribers(PlatplatcustRegisterForRegularSubscribersRequest platplatcustRegisterRequest)throws BusinessException {
		PlatplatcustRegisterResponse platplatcustRegisterResponse = new PlatplatcustRegisterResponse();
		PlatplatcustRegisterResponseData platplatcustRegisterResponseData=new PlatplatcustRegisterResponseData();
		//copy传参对象
		PlatplatcustRegisterRequest platplatcustRegisterCopy = new PlatplatcustRegisterRequest();
		BeanUtils.copyProperties(platplatcustRegisterRequest,platplatcustRegisterCopy);
		try{
			logger.info("电子账户开户--going");
			platplatcustRegisterResponseData = userAccountService.openEaccountForRegularSubscribers(platplatcustRegisterCopy);
			if(platplatcustRegisterResponseData==null){
				platplatcustRegisterResponse.setRecode(BusinessMsg.FAIL);
				platplatcustRegisterResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
			}else{
				logger.info("电子账户开户返回platcust:"+platplatcustRegisterResponseData.getPlatcust());
				logger.info("电子账户开户返回json:"+JSON.toJSONString(platplatcustRegisterResponseData, GlobalConfig.serializerFeature));
				platplatcustRegisterResponse.setRecode(BusinessMsg.SUCCESS);
				platplatcustRegisterResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				platplatcustRegisterResponse.setPlatplatcustRegisterResponseData(platplatcustRegisterResponseData);
				platplatcustRegisterResponse.setSign("sunyard");
			}
			platplatcustRegisterResponse.setOrder_no(platplatcustRegisterRequest.getOrder_no());
			logger.info("电子账户开户操作成功");
		} catch (BusinessException e) {
			logger.error("电子账户开户操作出现错误 ，错误信息：" + e);
			platplatcustRegisterResponse.setOrder_no(platplatcustRegisterRequest.getOrder_no());
			platplatcustRegisterResponse.setRecode(e.getBaseResponse().getRecode());
			platplatcustRegisterResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return platplatcustRegisterResponse;
	}

    public FirstCertResponse firstCert(FirstCertRequest firstCertRequest) {
		FirstCertResponse firstCertResponse=new FirstCertResponse();
		try {
			logger.info("开始首笔认证");
			firstCertResponse = userAccountService.firstCert(firstCertRequest);
			logger.info("首笔认证成功");
		} catch (BusinessException e) {
			logger.error("首笔认证出现错误 ，错误信息：" + e);
			firstCertResponse.setRecode(e.getBaseResponse().getRecode());
			firstCertResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return firstCertResponse;
    }

	/**
	 * 批量开户(四要素绑卡)
	 * @author yaojp
	 * @param batchRegisterRequest 四要素验证请求参数
	 * @return BatchRegisterResponse
	 */
	public BatchRegisterResponse batchRegister(BatchRegisterRequest batchRegisterRequest)throws BusinessException {
		BatchRegisterResponse batchRegisterResponse = new BatchRegisterResponse();
		List<BatchRegisterRequestDetail> batchRegisterRequestDetailList = batchRegisterRequest.getDataObject();
		BatchRegisterReturnOldData batchRegisterReturnData = null;
		if (batchRegisterRequest.getTotal_num() != null
				&& batchRegisterRequest.getTotal_num() == batchRegisterRequestDetailList.size()) {
			try {
				logger.info("开始批量(四要素)绑卡");
				batchRegisterReturnData = userAccountService.batchRegister(batchRegisterRequest,
						batchRegisterRequestDetailList);
				batchRegisterResponse.setRecode(BusinessMsg.SUCCESS);
				batchRegisterResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				batchRegisterResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
				batchRegisterResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
				batchRegisterResponse.setPlat_no(batchRegisterRequest.getMer_no());
				batchRegisterResponse.setOrder_no(batchRegisterRequest.getOrder_no());
				batchRegisterResponse.setFinish_datetime(DateUtils.todayStr());
				batchRegisterResponse.setTotal_num(batchRegisterRequest.getTotal_num() + "");
				batchRegisterResponse.setSuccess_num(batchRegisterReturnData.getSuccess_data_detail().size() + "");
				batchRegisterResponse.setSuccess_data_detail(batchRegisterReturnData.getSuccess_data_detail());
				batchRegisterResponse.setError_data_detail(batchRegisterReturnData.getError_data_detail());
				logger.info("批量(四要素)绑卡操作成功");
			} catch (BusinessException e) {
				logger.error("批量(四要素)绑卡操作出现错误 ，错误信息：" + e);
				batchRegisterResponse.setError_data_detail(getErrorDataList(e));
				batchRegisterResponse.setRecode(e.getBaseResponse().getRecode());
				batchRegisterResponse.setRemsg(e.getBaseResponse().getRemsg());
			}
		} else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
			baseResponse.setRemsg("总数量total_num和data数组长度不一致");
			baseResponse.setOrder_no(batchRegisterRequest.getOrder_no());
			throw new BusinessException(baseResponse);
		}
		return batchRegisterResponse;
	}

	/**
	 * 批量开户（实名认证）
	 * @author yaojp
	 * @param batchAuthenticationRequest 批量开户验证请求参数
	 * @return BatchAuthenticationResponse
	 */
	public BatchAuthenticationResponse batchAuthentication(BatchAuthenticationRequest batchAuthenticationRequest)throws BusinessException {
		BatchAuthenticationResponse batchAuthenticationResponse = new BatchAuthenticationResponse();
		List<BatchAuthenticationRequestDetail> dataObject = batchAuthenticationRequest.getDataObject();
		BatchAuthenticationReturnData batchAuthenticationReturnData = null;
		if (batchAuthenticationRequest.getTotal_num() != null
				&& batchAuthenticationRequest.getTotal_num() == dataObject.size()) {
			try {
				logger.info("开始批量开户（实名认证）");
				batchAuthenticationReturnData = userAccountService.batchAuthentication(batchAuthenticationRequest,
						dataObject);
				batchAuthenticationResponse.setRecode(BusinessMsg.SUCCESS);
				batchAuthenticationResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				batchAuthenticationResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
				batchAuthenticationResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
				batchAuthenticationResponse.setPlat_no(batchAuthenticationRequest.getMer_no());
				batchAuthenticationResponse.setOrder_no(batchAuthenticationRequest.getOrder_no());
				batchAuthenticationResponse.setFinish_datetime(DateUtils.todayStr());
				batchAuthenticationResponse.setTotal_num(batchAuthenticationRequest.getTotal_num() + "");
				batchAuthenticationResponse.setSuccess_num(batchAuthenticationReturnData.getSuccess_data_detail().size()+"");
				batchAuthenticationResponse.setSuccess_data_detail(batchAuthenticationReturnData.getSuccess_data_detail());
				batchAuthenticationResponse.setError_data_detail(batchAuthenticationReturnData.getError_data_detail());
				logger.info("批量开户（实名认证）操作成功");
			} catch (BusinessException e) {
				logger.error("批量开户（实名认证）操作出现错误 ，错误信息：" + e);
				batchAuthenticationResponse.setError_data_detail(getErrorDataList(e));
				batchAuthenticationResponse.setRecode(e.getBaseResponse().getRecode());
				batchAuthenticationResponse.setRemsg(e.getBaseResponse().getRemsg());
			}
		} else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
			throw new BusinessException(baseResponse);
		}
		return batchAuthenticationResponse;
	}

	public BaseResponse queryfirstCert(QueryFirstCertRequest queryFirstCertRequest) {
//TODO yanglei		BaseResponse baseResponse=new BaseResponse();
//		try {
//			baseResponse=userAccountService.queryfirstCert(queryFirstCertRequest);
//			String responseMsg=baseResponse.getOrder_no();
//			String[] params = null;
//			if(null!=responseMsg && !"".equals(responseMsg)){
//				params=responseMsg.split("\\|");
//
//				OrderData orderData = new OrderData();
//				orderData.setQuery_id(queryFirstCertRequest.getMer_no());
//				orderData.setOrder_no(queryFirstCertRequest.getOrder_no());
//				if (BusinessMsg.SUCCESS.equals(params[0])) {
//					//发送借款人线下还款异步通知
//					String url = queryFirstCertRequest.getNotify_url();//平台地址
//					OfferPlatAsynRequest offerPlatAsynRequest = new OfferPlatAsynRequest();
//					offerPlatAsynRequest.setPlat_no(queryFirstCertRequest.getMer_no());
//					offerPlatAsynRequest.setAmt(new BigDecimal(params[1]));
//					offerPlatAsynRequest.setOrder_no(queryFirstCertRequest.getOrder_no());
//					offerPlatAsynRequest.setMall_no(queryFirstCertRequest.getMall_no());
//
//					List<PlatcustListDetail> platcustList = new ArrayList<>();
//					PlatcustListDetail platcustListDetail=new PlatcustListDetail();
//					platcustListDetail.setAmt(new BigDecimal(params[1]));
//					platcustListDetail.setPlatcust(params[2]);
//					platcustList.add(platcustListDetail);
//
//					offerPlatAsynRequest.setData_detail(platcustList);
//					if ("2".equals(baseResponse.getOrder_no())) {//处理中
//						orderData.setOrder_status(OrderStatus.PROCESSING.getCode());
//					}
//					if ("3".equals(baseResponse.getOrder_no())) {//交易成功
//						orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
//						offerPlatAsynRequest.setCode("1");
//						offerPlatAsynRequest.setMsg(OrderStatus.SUCCESS.getMsg());
//						String chargeAsynRqString = JSON.toJSONString(offerPlatAsynRequest, GlobalConfig.serializerFeature);
//						logger.info("【准备发送首笔认证划款异步通知】=============url:" + url + ",json:" + chargeAsynRqString);
//						notifyBusiness.nofity(url, chargeAsynRqString);
//					} else if ("7".equals(baseResponse.getOrder_no())) {//交易失败
//						orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
//						offerPlatAsynRequest.setCode("2");
//						String withDrawAsynRqString = JSON.toJSONString(offerPlatAsynRequest, GlobalConfig.serializerFeature);
//						logger.info("【准备发送首笔认证划款异步通知】=============url:" + url + ",json:" + withDrawAsynRqString);
//						notifyBusiness.nofity(url, withDrawAsynRqString);
//					}
//				}
//			}
//
//		}catch (BusinessException e) {
//			logger.error("查询首笔认证出现错误 ，错误信息：" , e);
//			baseResponse.setRecode(e.getBaseResponse().getRecode());
//			baseResponse.setRemsg(e.getBaseResponse().getRemsg());
//		}
//		return baseResponse;
		return null;
	}

	public BorrowerSubAccountResponse sub_account(BorrowerSubAccountRequest borrowerSubAccountRequest)throws BusinessException{
		BorrowerSubAccountResponse borrowerSubAccountResponse=new BorrowerSubAccountResponse();
		try {
			borrowerSubAccountResponse = userBorrowerService.sub_account(borrowerSubAccountRequest);
			logger.info("----------------------融资人分账-----------------------成功");
		}catch (BusinessException e){
			/*OrderData orderData = new OrderData();
			orderData.setQuery_id("");
			orderData.setOrder_status(OrderStatus.FAIL.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
			borrowerSubAccountResponse.setOrderData(orderData);*/
			borrowerSubAccountResponse.setRecode(e.getBaseResponse().getRecode());
			borrowerSubAccountResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return borrowerSubAccountResponse;
	}

	public BaseResponse changeCardByMsg(ChangeCardByMsgConfirm changeCardByMsgConfirm) throws BusinessException{
		return userBindCardService.changeCardByMsg(changeCardByMsgConfirm);
	}

	public BaseResponse changeCardByMsgReq(ChangeCardByMsgRequest changeCardByMsgRequest) throws BusinessException{
		return userBindCardService.changeCardByMsgReq(changeCardByMsgRequest);
	}

	public String fourElementsRegister(RegisterRequest4 registerRequest4) throws BusinessException{
		return userAccountService.fourElementsRegister(registerRequest4);
	}

	public String threeElementsRegister(RegisterRequest3 registerRequest3) throws BusinessException {
		return userAccountService.threeElementsRegister(registerRequest3);
	}

	public BaseResponse modifyMobile(ChangePreMobileRequest changePreMobileRequest) throws BusinessException {
		return userAccountService.modifyMobile(changePreMobileRequest);
	}

	public BaseResponse applyAuthOpera(ApplyAuthOperaRequest applyAuthOperaRequest) throws BusinessException{
		return userAccountService.applyAuthOpera(applyAuthOperaRequest);
	}

	public BaseResponse confirmAuthOpera(ConfirmAuthOperaRequest confirmAuthOperaRequest) {
		return userAccountService.confirmAuthOpera(confirmAuthOperaRequest);
	}
	public BaseResponse cancelAuthOpera(CancelAuthOperaRequest cancelAuthOperaRequest) {
		return userAccountService.cancelAuthOpera(cancelAuthOperaRequest);
	}
	public BaseResponse switchAccount(SwitchAccountRequest switchAccountRequest) {
		return userAccountService.switchAccount(switchAccountRequest);
	}

	public String companyRegister(CompanyRegisterRequest companyRegisterRequest){
		return userAccountService.companyRegister(companyRegisterRequest);
	}

    public SetPwdRequest setPassword(SetPwdRequest setPwdRequest) {
		return userAccountService.setPassword(setPwdRequest);
    }

    public ModifyPwdRequest modifyPassword(ModifyPwdRequest modifyPwdRequest) {
		return userAccountService.modifyPassword(modifyPwdRequest);
    }

	public void getCode4SetPassword(VcodeRequest vcodeRequest) throws Exception {
		userBindCardService.getCode4Password(vcodeRequest);
	}

	public void getCode4Company(VcodeRequest vcodeRequest) throws Exception {
		userBindCardService.getCode4Company(vcodeRequest);
	}

	public BaseResponse freezeAccount(FreezeAccountRequest freezeAccountRequest) throws BusinessException{
		return userAccountService.freezeAccount(freezeAccountRequest);
	}

	public BaseResponse unregAccount(UnregAccountRequest unregAccountRequest) throws BusinessException{
		return userAccountService.unregAccount(unregAccountRequest);
	}

	/**
	 * @author Zz
	 *2017年5月18日
	 *借款人批量还款  同步老接口
	 */
	public  BatchRepayResponse batchRepayOld(BatchRepayRequestOld batchRepayRequestOld){

		BatchRepayResponse batchRepayResponse = new BatchRepayResponse();

		batchRepayResponse=userBorrowerService.batchRepayOld(batchRepayRequestOld);

		return batchRepayResponse;
	}

	public BatchInvestNoSynResponse batchRepayAsynOld(BatchRepayAsynRequestOld batchRepayRequest){

		BatchInvestNoSynResponse batchRepayResponse = new BatchInvestNoSynResponse();

		try {
			userBorrowerService.batchRepayAsynOld(batchRepayRequest);
			batchRepayResponse.setRecode(BusinessMsg.SUCCESS);
			batchRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			batchRepayResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
			batchRepayResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
		} catch (BusinessException e) {
			batchRepayResponse.setRecode(e.getBaseResponse().getRecode());
			batchRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
			batchRepayResponse.setOrder_status(OrderStatus.FAIL.getCode());
			batchRepayResponse.setOrder_info(OrderStatus.FAIL.getMsg());
		}
		batchRepayResponse.setOrder_no(batchRepayRequest.getOrder_no());
		return batchRepayResponse;
	}

	/**
	 * 签约申请
	 */
	public ContractApplyResponseDetail contractApp(ContractApplyRequest contractApplyRequest)throws BusinessException{
		ContractApplyResponseDetail contractApplyResponseDetail=new ContractApplyResponseDetail();

		try{
			contractApplyResponseDetail=userBorrowerService.contractApp(contractApplyRequest);

		}catch (BusinessException e){
			contractApplyResponseDetail.setRecode(e.getBaseResponse().getRecode());
			contractApplyResponseDetail.setRemsg(e.getBaseResponse().getRemsg());
			contractApplyResponseDetail.setOrder_status(OrderStatus.FAIL.getCode());
			contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
		}

		return  contractApplyResponseDetail;
	}

	/**
	 * 签约确认
	 */
	public ContractStatusReponse contractConfirm(ContractConfirmRequest contractConfirmRequest)throws  BusinessException{
		ContractStatusReponse baseResponse=new ContractStatusReponse();

		try {
			baseResponse=userBorrowerService.contractConfirm(contractConfirmRequest);
		}catch (BusinessException e){
			baseResponse.setRecode(e.getBaseResponse().getRecode());
			baseResponse.setRemsg(e.getBaseResponse().getRemsg());
			baseResponse.setOrder_status(e.getBaseResponse().getOrder_status());
			baseResponse.setOrder_no(contractConfirmRequest.getOrder_no());
		}

		return baseResponse;
	}

	/**
	 * 签约查询
	 */
	public ContractApplyResponseDetail contractStatus(ContractApplyRequest contractApplyRequest)throws BusinessException{
		ContractApplyResponseDetail contractApplyResponseDetail=new ContractApplyResponseDetail();

		try{
			contractApplyResponseDetail=userBorrowerService.contractStatus(contractApplyRequest);
		}catch (BusinessException e){
			contractApplyResponseDetail.setRecode(e.getBaseResponse().getRecode());
			contractApplyResponseDetail.setRemsg(e.getBaseResponse().getRemsg());
			contractApplyResponseDetail.setOrder_status(OrderStatus.FAIL.getCode());
			contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
		}

		return  contractApplyResponseDetail;
	}



	/**
	 * 标的代偿（委托）还款  老接口
	 * @author PengZY
	 * @param substituteRepayRequest 标的代偿（委托）还款请求参数
	 * @return SubstituteRepayResponse 标的代偿（委托）还款响应参数
	 */
	public SubstituteRepayResponse substituteRepayOld(SubstituteRepayRequestOld substituteRepayRequest) throws BusinessException{

		SubstituteRepayResponse substituteRepayResponse = new SubstituteRepayResponse();

		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setSign(substituteRepayRequest.getSign());
		baseRequest.setOrder_no(substituteRepayRequest.getOrder_no());
		baseRequest.setPartner_trans_date(substituteRepayRequest.getPartner_trans_date());
		baseRequest.setPartner_trans_time(substituteRepayRequest.getPartner_trans_time());
		baseRequest.setVersion(substituteRepayRequest.getVersion());
		baseRequest.setMall_no(substituteRepayRequest.getMall_no());
		baseRequest.setMall_name(substituteRepayRequest.getMall_name());
		baseRequest.setMer_no(substituteRepayRequest.getMer_no());
		baseRequest.setMer_name(substituteRepayRequest.getMer_name());
		baseRequest.setRemark(substituteRepayRequest.getRemark());

		try {

			substituteRepayResponse = userBorrowerService.substituteRepayOld(baseRequest, substituteRepayRequest);

		}catch (BusinessException e){
			OrderData orderData = new OrderData();
			orderData.setQuery_id("");
			orderData.setOrder_no(substituteRepayRequest.getOrder_no());
			orderData.setOrder_status(OrderStatus.FAIL.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
			substituteRepayResponse.setOrderData(orderData);
			substituteRepayResponse.setRecode(e.getBaseResponse().getRecode());
			substituteRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		return substituteRepayResponse;
	}


	/**
	 * 借款人还款代偿（委托）
	 * @author PengZY
	 * @param compensateRepayRequest 标的代偿（委托）还款请求参数
	 * @return CompensateRepayResponse 标的代偿（委托）还款响应参数
	 */
	public CompensateRepayResponse compensateRepayOld(CompensateRepayRequestOld compensateRepayRequest){
		CompensateRepayResponse compensateRepayResponse = new CompensateRepayResponse();
		try {

			compensateRepayResponse = userBorrowerService.compensateRepayOld(compensateRepayRequest);

			logger.info("----------------------借款人还款代偿（委托）-----------------------成功");

		}catch (BusinessException e){
			OrderData orderData = new OrderData();
			orderData.setQuery_id("");
			orderData.setOrder_no(compensateRepayRequest.getOrder_no());
			orderData.setOrder_status(OrderStatus.FAIL.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
			compensateRepayResponse.setRecode(e.getBaseResponse().getRecode());
			compensateRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
			compensateRepayResponse.setOrderData(orderData);
		}
		return compensateRepayResponse;
	}

	public BaseResponse withdrawSendAsyn(Map<String, Object> map) {
		logger.info("================进入提现代发异步业务方法==============参数："+JSON.toJSON(map));
		return rwWithdrawOptionService.withdrawSendAsyn(map);
	}
}
