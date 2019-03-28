package com.sunyard.sunfintech.web.controller;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.FirstCertRequest;
import com.sunyard.sunfintech.account.model.bo.FirstCertResponse;
import com.sunyard.sunfintech.account.model.bo.QueryFirstCertRequest;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.SystemCode;
import com.sunyard.sunfintech.core.annotation.CheckPassword;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.thirdparty.model.*;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.model.bo.SwitchAccountRequest;
import com.sunyard.sunfintech.web.business.AccountBusiness;
import com.sunyard.sunfintech.web.business.NotifyBusiness;
import com.sunyard.sunfintech.web.business.UserBusiness;
import com.sunyard.sunfintech.web.model.vo.account.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账户系统对外web接口
 *
 * @author heroy
 * @version 20170330
 */

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

	@Resource(name = "accountBusiness")
	private AccountBusiness business;

	@Resource(name = "userBusiness")
	private UserBusiness userBusiness;

	@Resource(name = "notifyBusiness")
	private NotifyBusiness notifyBusiness;

	/**
	 * 首笔认证（对公绑卡）
	 *
	 * @param httpServletRequest request请求参数
	 * @param firstCertRequest   首笔验证请求参数
	 * @return
	 * @author wuml
	 */
	@ApiOperation(value = "first_cert",notes = "首笔认证（对公绑卡）")
	@RequestMapping("/first_cert")
	@Sign
	@Log(method = "firstCert")
	public FirstCertResponse firstCert(HttpServletRequest httpServletRequest, FirstCertRequest firstCertRequest) {
		logger.info("请求参数:" + firstCertRequest);
		validate(firstCertRequest);
		FirstCertResponse firstCertResponse = new FirstCertResponse();
		try {
			firstCertResponse = userBusiness.firstCert(firstCertRequest);
		} catch (BusinessException e) {
			firstCertResponse.setRecode(e.getBaseResponse().getRecode());
			firstCertResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		firstCertResponse.setOrder_no(firstCertRequest.getOrder_no());

		return firstCertResponse;
	}

	/**
	 * 首笔认证查询
	 *
	 * @param httpServletRequest    request请求参数
	 * @param queryFirstCertRequest 首笔验证查询请求参数
	 * @return
	 * @author wuml
	 */

	@RequestMapping("/query_first_cert")
	@Sign
	@Log(method = "queryfirstcert")
	public BaseResponse queryfirstcert(HttpServletRequest httpServletRequest, QueryFirstCertRequest queryFirstCertRequest) {
		logger.info("请求参数:" + queryFirstCertRequest);
		validate(queryFirstCertRequest);
		BaseResponse baseResponse = new BaseResponse();

		try {
			baseResponse = userBusiness.queryfirstCert(queryFirstCertRequest);
		} catch (BusinessException e) {
			baseResponse.setRecode(e.getBaseResponse().getRecode());
			baseResponse.setRemsg(e.getBaseResponse().getRemsg());
		}

		baseResponse.setOrder_no(queryFirstCertRequest.getOrder_no());
		return baseResponse;
	}


	/**
	 * 批量开户(四要素绑卡)
	 * @author PengZY
	 * @param httpServletRequest request请求参数
	 * @param batchRegisterRequest 四要素验证请求参数
	 * @return BatchRegisterResponse
	 */
	@RequestMapping("/batch_register")
	@Sign
	@Log(method = "batchRegister",batchDataName = "data")
	public BatchRegisterResponse batchRegister(HttpServletRequest httpServletRequest, BatchRegisterRequest batchRegisterRequest){

		logger.info("请求参数:" + batchRegisterRequest);
		BatchRegisterResponse batchRegisterResponse = new BatchRegisterResponse();
		validate(batchRegisterRequest);
		try{
			batchRegisterResponse = userBusiness.batchRegister(batchRegisterRequest);
		}catch(BusinessException e){
			batchRegisterResponse.setRecode(e.getBaseResponse().getRecode());
			batchRegisterResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		batchRegisterResponse.setOrder_no(batchRegisterRequest.getOrder_no());
		return batchRegisterResponse;
	}

	/**
	 * 批量开户（实名认证）
	 * @author PengZY
	 * @param httpServletRequest request请求参数
	 * @param batchAuthenticationRequest 批量开户验证请求参数
	 * @return BatchAuthenticationResponse
	 */
	@RequestMapping("/batch_authentication")
	@Sign
	@Log(method = "batchAuthentication",batchDataName = "data")
	public BatchAuthenticationResponse batchAuthentication(HttpServletRequest httpServletRequest, BatchAuthenticationRequest batchAuthenticationRequest ){

		logger.info("请求参数:" + batchAuthenticationRequest.toString());
		BatchAuthenticationResponse batchRegisterResponse = new BatchAuthenticationResponse();
		validate(batchAuthenticationRequest);
		try{
			batchRegisterResponse = userBusiness.batchAuthentication(batchAuthenticationRequest);
		}catch(BusinessException e){
			batchRegisterResponse.setRecode(e.getBaseResponse().getRecode());
			batchRegisterResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		batchRegisterResponse.setOrder_no(batchAuthenticationRequest.getOrder_no());
		return batchRegisterResponse;

	}

	/**
	 * 電子賬戶开户
	 *
	 * @param httpServletRequest          request请求参数
	 * @param platplatcustRegisterRequest 電子賬戶开户验证请求参数
	 * @return BatchAuthenticationResponse
	 * @author dany
	 */
	@ApiOperation(value = "platcust_register",notes = "四要素开户确认")
	@RequestMapping("/platcust_register")
	@Sign
	@Log(method = "registerPlatcust")
	public PlatplatcustRegisterResponse registerPlatcust(HttpServletRequest httpServletRequest, final PlatplatcustRegisterRequest platplatcustRegisterRequest) {

		logger.info("请求参数:" + platplatcustRegisterRequest.toString());
		PlatplatcustRegisterResponse platplatcustregisterResponse = new PlatplatcustRegisterResponse();
		validate(platplatcustRegisterRequest);
		try {
			platplatcustregisterResponse = userBusiness.registerEaccount(platplatcustRegisterRequest);
		} catch (BusinessException e) {
			platplatcustregisterResponse.setOrder_no(platplatcustRegisterRequest.getOrder_no());
			platplatcustregisterResponse.setRecode(e.getBaseResponse().getRecode());
			platplatcustregisterResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		//System.out.print("lpojoj");
		platplatcustregisterResponse.setOrder_no(platplatcustRegisterRequest.getOrder_no());
		return platplatcustregisterResponse;

	}

	/**
	 * 四要素开户申请
	 * @param httpServletRequest
	 * @param applyRequest
	 * @return
	 */
	@ApiOperation(value = "apply_4elements_register",notes = "四要素开户申请")
	@PostMapping("/apply_4elements_register")
	@Sign
	@Log(method = "apply4ElementsRegister")
	public BaseResponse apply4ElementsRegister(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Apply4ElementsRegisterRequest applyRequest){
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		if(StringUtils.isBlank(applyRequest.getOrigin_order_no())){
			validate(applyRequest);
		}
		return userBusiness.apply4ElementsRegister(applyRequest);
	}

	/**
	 * 四要素开户确认
	 * @param httpServletRequest
	 * @param confirmRequest
	 * @return
	 */
	@ApiOperation(value = "confirm_4elements_register",notes = "四要素开户确认")
	@RequestMapping("/confirm_4elements_register")
	@Sign
	@Log(method = "confirm4ElementsRegister")
//	@CrossOrigin(origins = "*")
	public BatchRegisterReturnData confirm4ElementsRegister(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Confirm4ElementsRegisterRequest confirmRequest){
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("【四要素开户确认】请求参数："+JSON.toJSONString(confirmRequest));
		if(StringUtils.isNotBlank(confirmRequest.getOrder_no())){
			validate(confirmRequest);
		}else{
			if(StringUtils.isBlank(confirmRequest.getOrigin_order_no()) || StringUtils.isBlank(confirmRequest.getIdentifying_code())
					|| StringUtils.isBlank(confirmRequest.getTrans_pwd()) || StringUtils.isBlank(confirmRequest.getRandom_key())){
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
			}
			confirmRequest.setOrder_no(SeqUtil.getRadomNum());
		}
		return userBusiness.confirm4ElementsRegister(confirmRequest);
	}

	/**
	 * 四要素开户确认
	 * @param httpServletRequest
	 * @param confirmRequest
	 * @return
	 */
	@RequestMapping("/confirm_4elements_register_for_old_user_call_new_interface_hehehehe")
	@Sign
	@Log(method = "confirm4ElementsRegisterOld")
	public BatchRegisterReturnData confirm4ElementsRegisterOld(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Confirm4ElementsRegisterRequest confirmRequest){
		logger.info("【四要素开户确认-老用户调新接口】请求参数："+JSON.toJSONString(confirmRequest));
		if(StringUtils.isNotBlank(confirmRequest.getOrder_no())){
			validate(confirmRequest);
		}else{
			if(StringUtils.isBlank(confirmRequest.getOrigin_order_no()) || StringUtils.isBlank(confirmRequest.getIdentifying_code())
					|| StringUtils.isBlank(confirmRequest.getTrans_pwd()) || StringUtils.isBlank(confirmRequest.getRandom_key())){
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
			}
			confirmRequest.setOrder_no(SeqUtil.getRadomNum());
		}
		return userBusiness.confirm4ElementsRegister(confirmRequest);
	}

	/**
	 * 三要素开户申请
	 * @author RaoYL
	 * @param httpServletRequest
	 * @param applyRequest
	 * @return BatchRegisterReturnData
	 */
	@ApiOperation(value = "apply_3elements_register",notes = "三要素开户申请")
	@RequestMapping("/apply_3elements_register")
	@Sign
	@Log(method = "apply3ElementsRegister")
//	@CrossOrigin(origins = "*")
	public BatchRegisterReturnData apply3ElementsRegister(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Apply3ElementsRegisterRequest applyRequest){
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		if(StringUtils.isNotBlank(applyRequest.getOrder_no())){
			validate(applyRequest);
		}else{
			if(StringUtils.isBlank(applyRequest.getOrigin_order_no()) || StringUtils.isBlank(applyRequest.getTrans_serial())
					|| StringUtils.isBlank(applyRequest.getTrans_pwd()) || StringUtils.isBlank(applyRequest.getRandom_key())){
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) );
			}
			applyRequest.setOrder_no(SeqUtil.getRadomNum());
		}
		return userBusiness.apply3ElementsRegister(applyRequest);
	}

	/**
	 * 企业开户申请
	 * @param httpServletRequest
	 * @param applyRequest
	 * @return
	 */
	@ApiOperation(value = "apply_org_register",notes = "企业开户申请")
	@RequestMapping("/apply_org_register")
	@Sign
	@Log(method = "applyOrgRegister")
//	@CrossOrigin(origins = "*")
	public BatchRegisterReturnData applyOrgRegister(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ApplyOrgRegisterRequest applyRequest){
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		if(StringUtils.isNotBlank(applyRequest.getOrder_no())){
			validate(applyRequest);
		}else{
			if(StringUtils.isBlank(applyRequest.getOrigin_order_no()) || StringUtils.isBlank(applyRequest.getTrans_serial())
					|| StringUtils.isBlank(applyRequest.getTrans_pwd()) || StringUtils.isBlank(applyRequest.getRandom_key())
					|| StringUtils.isBlank(applyRequest.getIdentifying_code())){
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
			}
			applyRequest.setOrder_no(SeqUtil.getRadomNum());
		}
		return userBusiness.applyOrgRegister(applyRequest);
	}

	/**
	 * 境外开户申请
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param
	 * @return
	 */

	@RequestMapping("/apply_org_register_opening")
	@Sign
	@Log(method = "applyOrgRegisterOpening")
	public BaseResponse applyOrgRegisterOpening (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ApplyOrgRegisterOpeningRequest applyRequests) {
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		validate(applyRequests);
		return  userBusiness.applyOrgRegisterOpening(applyRequests);
	}

	/**
	 * 企业开户审核
	 * @param httpServletRequest
	 * @param confirmequest
	 * @return
	 */
	@ApiOperation(value = "confirm_org_register",notes = "企业开户审核")
	@RequestMapping("/confirm_org_register")
	@Sign
	@Log(method = "confirmOrgRegister")
	public BaseResponse confirmOrgRegister(HttpServletRequest httpServletRequest, ConfirmOrgRegisterRequest confirmequest){
//		validate(confirmequest);
		return userBusiness.confirmOrgRegister(confirmequest);
	}

	/**
	 * 绑卡核验
	 * @param httpServletRequest
	 * @param checkCardRequest
	 * @return
	 */
	@RequestMapping("/check_card")
	@Sign
//	@Log(method = "checkCard")
//	@CrossOrigin(origins = "*")
	public BaseResponse checkCard(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CheckCardRequest checkCardRequest){
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		validate(checkCardRequest);
		return userBusiness.checkCard(checkCardRequest);
	}

	/**
	 * 获取验证码(设置密码)
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param vcodeRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/get_code_4_set_password")
	@Sign
//	@Log(method = "getCode4SetPassword")
//	@CrossOrigin(origins = "*")
	public BaseResponse getCode4SetPassword(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse , VcodeRequest vcodeRequest) throws Exception {
		validate(vcodeRequest);
		userBusiness.getCode4SetPassword(vcodeRequest);
		return new BaseResponse(SystemCode.SUCCESS);
	}

	/**
	 * 获取验证码(企业开户)
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param vcodeRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/get_code_4_company")
	@Sign
//	@Log(method = "getCode4Company")
//	@CrossOrigin(origins = "*")
	public BaseResponse getCode4Company(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse , VcodeRequest vcodeRequest) throws Exception {
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		validate(vcodeRequest);
		userBusiness.getCode4Company(vcodeRequest);
		return new BaseResponse(SystemCode.SUCCESS);
	}

	/**
	 * 设置交易密码
	 * @param httpServletRequest
	 * @param setNewPasswordRequest
	 * @return
	 */
	@RequestMapping("/set_trade_password")
	@Sign
	@Log(method = "setNewPassword")
	public BaseResponse setNewPassword(HttpServletRequest httpServletRequest, SetNewPasswordRequest setNewPasswordRequest){
		if(StringUtils.isNotBlank(setNewPasswordRequest.getOrder_no())){
			validate(setNewPasswordRequest);
		}else{
			if(StringUtils.isBlank(setNewPasswordRequest.getOrigin_order_no()) || StringUtils.isBlank(setNewPasswordRequest.getTrans_serial()) || StringUtils.isBlank(setNewPasswordRequest.getIdentifying_code())){
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
			}
			setNewPasswordRequest.setOrder_no(SeqUtil.getRadomNum());
		}

		return userBusiness.setNewPassword(setNewPasswordRequest);
	}

	/**
	 * 四要素绑卡,企业绑卡（不对未绑卡及换卡用户调用）
	 * @param httpServletRequest request请求参数
	 * @param bindCardRequest    绑卡验证请求参数
	 * @return BindCardResponse
	 * @author PengZY
	 */
	@RequestMapping("/bind_card_old")
	@Sign
	@Log(method = "bindCardRequestOld")
	public BindCardResponse bindCardRequestOld(HttpServletRequest httpServletRequest, BindCardRequest bindCardRequest) {

		validate(bindCardRequest);
		BindCardResponse bindCardResponse = new BindCardResponse();
		try {
			userBusiness.bindCard(bindCardRequest);
			bindCardResponse.setRecode(BusinessMsg.SUCCESS);
			bindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			bindCardResponse.setRecode(e.getBaseResponse().getRecode());
			bindCardResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		bindCardResponse.setOrder_no(bindCardRequest.getOrder_no());
		bindCardResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		return bindCardResponse;

	}

	/**
	 * 四要素绑卡,企业绑卡（不对未绑卡及换卡用户调用）
	 * @param httpServletRequest request请求参数
	 * @param bindCardRequest    绑卡验证请求参数
	 * @return BindCardResponse
	 * @author PengZY
	 */
	@RequestMapping("/bind_card")
	@Sign
	@Log(method = "bindCardRequest")
	public BindCardResponse bindCardRequest(HttpServletRequest httpServletRequest, BindCardRequest bindCardRequest) {

		validate(bindCardRequest);
		BindCardResponse bindCardResponse = new BindCardResponse();
		try {
			userBusiness.orgBindCard(bindCardRequest);
			bindCardResponse.setRecode(BusinessMsg.SUCCESS);
			bindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			bindCardResponse.setRecode(e.getBaseResponse().getRecode());
			bindCardResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		bindCardResponse.setOrder_no(bindCardRequest.getOrder_no());
		bindCardResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		return bindCardResponse;

	}

	/**
	 * 三要素绑卡（仅支持个人信用卡，绑定卡仅用于提现）
	 * @author RaoYL
	 * @param httpServletRequest    request请求参数
	 * @param personBindCardRequest 绑卡请求参数
	 * @return PersonBindCardResponse
	 */
	@RequestMapping("/bind_credit_card")
	@Sign
	@Log(method = "personBindCard")
	public PersonBindCardResponse personBindCard(HttpServletRequest httpServletRequest,
												 PersonBindCardRequest personBindCardRequest) {
		validate(personBindCardRequest);
		PersonBindCardResponse personBindCardResponse = new PersonBindCardResponse();
		try {
			userBusiness.personCreditBindCard(personBindCardRequest);
			personBindCardResponse.setRecode(BusinessMsg.SUCCESS);
			personBindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			personBindCardResponse.setRecode(e.getBaseResponse().getRecode());
			personBindCardResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		personBindCardResponse.setOrder_no(personBindCardRequest.getOrder_no());
		return personBindCardResponse;
	}

	/**
	 * 批量换卡（解绑+换卡申请）——非统一事务
	 *
	 * @param httpServletRequest request请求参数
	 * @param unBindCardRequest  批量换卡验证请求参数
	 * @return UnBindCardResponse
	 * @author PengZY
	 * @update terry
	 */
	@RequestMapping("/unbind_cards_old")
	@Sign
	@Log(method = "unbindCardOld",batchDataName = "data")
	public UnBindCardResponse unbindCardOld(HttpServletRequest httpServletRequest, UnBindCardRequest unBindCardRequest) {
		UnBindCardResponse unBindCardResponse = new UnBindCardResponse();
		UnBindCardReturn unBindCardReturn = new UnBindCardReturn();
		validate(unBindCardRequest);
		try {
			List<UnBindCardRequestDetail> data_detail=unBindCardRequest.getData_detail();
			for(UnBindCardRequestDetail unBindCardRequestDetail:data_detail){
				unBindCardRequestDetail.setInterface_version(Constants.OLD_INTERFACE);
			}
			unBindCardReturn = userBusiness.unbindCard(unBindCardRequest);
			unBindCardResponse.setRecode(BusinessMsg.SUCCESS);
			unBindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			unBindCardResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
			unBindCardResponse.setOrder_info(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			unBindCardResponse.setRecode(e.getErrorCode());
			unBindCardResponse.setRemsg(e.getErrorMsg());
			unBindCardResponse.setOrder_status(FlowStatus.FAIL.getCode());
			unBindCardResponse.setOrder_info(BusinessMsg.getMsg(BusinessMsg.FAIL));
		}
		unBindCardResponse.setPlat_no(unBindCardRequest.getMer_no());
		unBindCardResponse.setOrder_no(unBindCardRequest.getOrder_no());
		unBindCardResponse.setFinish_datetime(DateUtils.todayStr());
		unBindCardResponse.setTotal_num(String.valueOf(unBindCardRequest.getData_detail().size()));
		unBindCardResponse.setSuccess_num(unBindCardReturn.getSuccessDataList() != null ? String.valueOf(unBindCardReturn.getSuccessDataList().size()) : "0");
		unBindCardResponse.setSuccess_data_detail(unBindCardReturn.getSuccessDataList());
		unBindCardResponse.setError_data_detail(unBindCardReturn.getErrorDataList());

		return unBindCardResponse;

	}

	/**
	 * 批量换卡（解绑+换卡申请）——非统一事务
	 *
	 * @param httpServletRequest request请求参数
	 * @param unBindCardRequest  批量换卡验证请求参数
	 * @return UnBindCardResponse
	 * @author PengZY
	 * @update terry
	 */
	@RequestMapping("/unbind_cards")
	@Sign
	@Log(method = "unbindCard",batchDataName = "data")
	public UnBindCardResponse unbindCard(HttpServletRequest httpServletRequest, UnBindCardRequest unBindCardRequest) {
		UnBindCardResponse unBindCardResponse = new UnBindCardResponse();
		UnBindCardReturn unBindCardReturn = new UnBindCardReturn();
		validate(unBindCardRequest);
		try {
			unBindCardReturn = userBusiness.unbindCard(unBindCardRequest);
			unBindCardResponse.setRecode(BusinessMsg.SUCCESS);
			unBindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			unBindCardResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
			unBindCardResponse.setOrder_info(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			unBindCardResponse.setRecode(e.getErrorCode());
			unBindCardResponse.setRemsg(e.getErrorMsg());
			unBindCardResponse.setOrder_status(FlowStatus.FAIL.getCode());
			unBindCardResponse.setOrder_info(BusinessMsg.getMsg(BusinessMsg.FAIL));
		}
		unBindCardResponse.setPlat_no(unBindCardRequest.getMer_no());
		unBindCardResponse.setOrder_no(unBindCardRequest.getOrder_no());
		unBindCardResponse.setFinish_datetime(DateUtils.todayStr());
		unBindCardResponse.setTotal_num(String.valueOf(unBindCardRequest.getData_detail().size()));
		unBindCardResponse.setSuccess_num(unBindCardReturn.getSuccessDataList() != null ? String.valueOf(unBindCardReturn.getSuccessDataList().size()) : "0");
		unBindCardResponse.setSuccess_data_detail(unBindCardReturn.getSuccessDataList());
		unBindCardResponse.setError_data_detail(unBindCardReturn.getErrorDataList());

		return unBindCardResponse;

	}

	/**
	 * 批量解绑——非统一事务
	 *
	 * @author Bob
	 */
//	@RequestMapping("/unbind_cards")
	@Sign
	@Log(method = "unbindCardForMultiCards")
	public UnBindCardResponse unbindCardForMultiCards(HttpServletRequest httpServletRequest, UnBindCardForMultiCardsRequest unBindCardForMultiCardsRequest) {
		UnBindCardResponse unBindCardResponse = new UnBindCardResponse();
		UnBindCardReturn unBindCardReturn = new UnBindCardReturn();
		validate(unBindCardForMultiCardsRequest);
		try {
			unBindCardReturn = userBusiness.unbindCardForMultiCards(unBindCardForMultiCardsRequest);
			unBindCardResponse.setRecode(BusinessMsg.SUCCESS);
			unBindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			unBindCardResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
			unBindCardResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
		} catch (BusinessException e) {
			unBindCardResponse.setRecode(e.getErrorCode());
			unBindCardResponse.setRemsg(e.getErrorMsg());
		}
		unBindCardResponse.setPlat_no(unBindCardForMultiCardsRequest.getMer_no());
		unBindCardResponse.setOrder_no(unBindCardForMultiCardsRequest.getOrder_no());
		unBindCardResponse.setFinish_datetime(DateUtils.todayStr());
		String total_num = String.valueOf(unBindCardForMultiCardsRequest.getData_detail().size());
		unBindCardResponse.setTotal_num(total_num);
		String success_num = unBindCardReturn.getSuccessDataList() != null ? String.valueOf(unBindCardReturn.getSuccessDataList().size()) : "0";
		unBindCardResponse.setSuccess_num(success_num);
		if (!total_num.equals(success_num)) {
			unBindCardResponse.setOrder_status(OrderStatus.FAIL.getCode());
			unBindCardResponse.setOrder_info(OrderStatus.FAIL.getMsg());
		}
		unBindCardResponse.setSuccess_data_detail(unBindCardReturn.getSuccessDataList());
		unBindCardResponse.setError_data_detail(unBindCardReturn.getErrorDataList());
		return unBindCardResponse;
	}

	/**
	 * 批量换卡（先绑卡再解绑）
	 *
	 * @param httpServletRequest
	 * @param batchChangeCardRequest
	 * @return
	 * @author Raoyulu
	 */
	@RequestMapping("/batch_change_card")
	@Sign
	@Log(method = "batchChangeCard",batchDataName = "data")
	public BatchChangeCardResponse batchChangeCard(HttpServletRequest httpServletRequest, BatchChangeCardRequest batchChangeCardRequest) {
		BatchChangeCardResponse batchChangeCardResponse = new BatchChangeCardResponse();
		BatchChangeCardReturn batchChangeCardReturn = new BatchChangeCardReturn();
		validate(batchChangeCardRequest);
		try {
			batchChangeCardReturn = userBusiness.batchChangeCard(batchChangeCardRequest);
			batchChangeCardResponse.setRecode(BusinessMsg.SUCCESS);
			batchChangeCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			batchChangeCardResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
			batchChangeCardResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
		} catch (BusinessException e) {
			batchChangeCardResponse.setRecode(e.getErrorCode());
			batchChangeCardResponse.setRemsg(e.getErrorMsg());
		}
		batchChangeCardResponse.setPlat_no(batchChangeCardRequest.getMer_no());
		batchChangeCardResponse.setOrder_no(batchChangeCardRequest.getOrder_no());
		batchChangeCardResponse.setFinish_datetime(DateUtils.todayStr());
		String total_num = String.valueOf(batchChangeCardRequest.getBatchChangeCardRequestDetailList().size());
		batchChangeCardResponse.setTotal_num(total_num);
		String success_num = batchChangeCardReturn.getSuccessDataList() != null ? String.valueOf(batchChangeCardReturn.getSuccessDataList().size()) : "0";
		batchChangeCardResponse.setSuccess_num(success_num);
		if (!total_num.equals(success_num)) {
			batchChangeCardResponse.setOrder_status(OrderStatus.FAIL.getCode());
			batchChangeCardResponse.setOrder_info(OrderStatus.FAIL.getMsg());
		}
		batchChangeCardResponse.setSuccessDataList(batchChangeCardReturn.getSuccessDataList());
		batchChangeCardResponse.setErrorDataList(batchChangeCardReturn.getErrorDataList());
		return batchChangeCardResponse;
	}

//	/**
//	 * 用户投融资账户转账
//	 *
//	 * @param httpServletRequest   request请求参数
//	 * @param switchAccountRequest 用户投融资账户转账验证请求参数
//	 * @return SwitchAccountResponse
//	 * @author PengZY
//	 * @update terry
//	 */
//	@RequestMapping("/switch_account")
//	@Sign
//	public SwitchAccountResponse switchAccount(HttpServletRequest httpServletRequest, SwitchAccountRequest switchAccountRequest) {
//		SwitchAccountResponse switchAccountResponse = new SwitchAccountResponse();
//		validate(switchAccountRequest);
//		try {
//			business.switchAccount(switchAccountRequest);
//			switchAccountResponse.setRecode(BusinessMsg.SUCCESS);
//			switchAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//		} catch (BusinessException e) {
//			switchAccountResponse.setRecode(e.getErrorCode());
//			switchAccountResponse.setRemsg(e.getErrorMsg());
//		}
//		switchAccountResponse.setOrder_no(switchAccountRequest.getOrder_no());
//		return switchAccountResponse;
//
//	}

	/**
	 * 短验绑卡（可包含开户）申请
	 *
	 * @param httpServletRequest   request请求参数
	 * @param applyBindCardRequest 短验绑卡（可包含开户）申请     验证请求参数
	 * @return ApplyBindCardResponse
	 * @author PengZY
	 * @update terry
	 */
	@RequestMapping("/apply_bind_card_old")
	@Sign
	@Log(method = "applyBindCardOld")
	public ApplyBindCardResponse applyBindCardOld(HttpServletRequest httpServletRequest, ApplyBindCardRequest applyBindCardRequest) {
		applyBindCardRequest.setPlatcust(Constants.OLD_INTERFACE);
		ApplyBindCardResponse applyBindCardResponse = new ApplyBindCardResponse();
		validate(applyBindCardRequest);
		try {
			userBusiness.applyBindCard(applyBindCardRequest);
			applyBindCardResponse.setRecode(BusinessMsg.SUCCESS);
			applyBindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			applyBindCardResponse.setRecode(e.getErrorCode());
			applyBindCardResponse.setRemsg(e.getErrorMsg());
		}
		applyBindCardResponse.setOrder_no(applyBindCardRequest.getOrder_no());
		return applyBindCardResponse;

	}

	/**
	 * 短验绑卡（可包含开户）申请
	 *
	 * @param httpServletRequest   request请求参数
	 * @param applyBindCardRequest 短验绑卡（可包含开户）申请     验证请求参数
	 * @return ApplyBindCardResponse
	 * @author PengZY
	 * @update terry
	 */
	@RequestMapping("/apply_bind_card")
	@Sign
	@Log(method = "applyBindCard")
	public ApplyBindCardResponse applyBindCard(HttpServletRequest httpServletRequest, ApplyBindCardRequest applyBindCardRequest) {
//		if(StringUtils.isBlank(applyBindCardRequest.getPlatcust())){
//			applyBindCardRequest.setPlatcust(Constants.OLD_INTERFACE);
//		}
		ApplyBindCardResponse applyBindCardResponse = new ApplyBindCardResponse();
		validate(applyBindCardRequest);
		try {
			userBusiness.applyBindCard(applyBindCardRequest);
			applyBindCardResponse.setRecode(BusinessMsg.SUCCESS);
			applyBindCardResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			applyBindCardResponse.setRecode(e.getErrorCode());
			applyBindCardResponse.setRemsg(e.getErrorMsg());
		}
		applyBindCardResponse.setOrder_no(applyBindCardRequest.getOrder_no());
		return applyBindCardResponse;

	}

	/**
	 * 短验绑卡（可包含开户）确认
	 *
	 * @param httpServletRequest     request请求参数
	 * @param confirmBindCardRequest 短验绑卡（可包含开户）确认   验证请求参数
	 * @return ConfirmBindCardResponse
	 * @author wubin
	 */
	@RequestMapping("/confirm_bind_card_old")
	@Sign
	@Log(method = "confirmBindCardOld")
	public ConfirmBindCardResponse confirmBindCardOld(HttpServletRequest httpServletRequest, ConfirmBindCardRequest confirmBindCardRequest) {
		confirmBindCardRequest.setPlatcust(Constants.OLD_INTERFACE);
		validate(confirmBindCardRequest);
		ConfirmBindCardResponse confirmBindCardResponse = userBusiness.confirmBindCard(confirmBindCardRequest);
		confirmBindCardResponse.setOrder_no(confirmBindCardRequest.getOrder_no());
		return confirmBindCardResponse;
	}

	/**
	 * 短验绑卡（可包含开户）确认
	 *
	 * @param httpServletRequest     request请求参数
	 * @param confirmBindCardRequest 短验绑卡（可包含开户）确认   验证请求参数
	 * @return ConfirmBindCardResponse
	 * @author wubin
	 */
	@RequestMapping("/confirm_bind_card")
	@Sign
	@Log(method = "confirmBindCard")
	public ConfirmBindCardResponse confirmBindCard(HttpServletRequest httpServletRequest, ConfirmBindCardRequest confirmBindCardRequest) {
//		if(StringUtils.isBlank(confirmBindCardRequest.getPlatcust())){
//			confirmBindCardRequest.setPlatcust(Constants.OLD_INTERFACE);
//		}
		validate(confirmBindCardRequest);
		ConfirmBindCardResponse confirmBindCardResponse = userBusiness.confirmBindCard(confirmBindCardRequest);
		confirmBindCardResponse.setOrder_no(confirmBindCardRequest.getOrder_no());
		return confirmBindCardResponse;
	}

	/**
	 * 客户信息变更
	 * @author PengZY
	 * @param httpServletRequest	request请求参数
	 * @param changeAccountInfoRequest
	 * @return ChangeAccountInfoResponse
	 */
	@RequestMapping("/change_account_info")
	@Sign
	@Log(method = "changeAccountInfo")
	public ChangeAccountInfoResponse changeAccountInfo(HttpServletRequest httpServletRequest, ChangeAccountInfoRequest changeAccountInfoRequest) {
		logger.info("【客户信息变更】-->开始-->order_no:"+changeAccountInfoRequest.getOrder_no());
		long start = System.currentTimeMillis();
		validate(changeAccountInfoRequest);
		ChangeAccountInfoResponse changeAccountInfoResponse = userBusiness.changeAccountInfo(changeAccountInfoRequest);
		long end = System.currentTimeMillis();
		logger.info("【客户信息变更】-->结束-->order_no:"+changeAccountInfoRequest.getOrder_no()+"-->耗时:"+(end-start));
		return changeAccountInfoResponse;

	}

	/**
	 * 子账户现金转电子账户
	 *
	 * @param httpServletRequest
	 * @param transferEaccountRequest
	 * @return
	 */
	@RequestMapping("/transfer_eaccount")
	@Sign
	@Log(method = "transferEaccount")
	public BaseResponse transferEaccount(HttpServletRequest httpServletRequest, TransferEaccountRequest transferEaccountRequest) {
		validate(transferEaccountRequest);
		BaseResponse baseResponse = new BaseResponse();
		try {
			business.transferEaccount(transferEaccountRequest);
			baseResponse.setRecode(BusinessMsg.SUCCESS);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		} catch (BusinessException e) {
			baseResponse.setRecode(e.getBaseResponse().getRecode());
			baseResponse.setRemsg(e.getBaseResponse().getRemsg());
		}
		baseResponse.setOrder_no(transferEaccountRequest.getOrder_no());
		return baseResponse;
	}

	/**
	 * 電子賬戶开户for存量用户
	 *
	 * @param httpServletRequest          request请求参数
	 * @param platplatcustRegisterRequest 電子賬戶开户验证请求参数
	 * @return BatchAuthenticationResponse
	 * @author dany
	 */
	@RequestMapping("/platcust_register_for_regular_subscribers")
	@Sign
	@Log(method = "registerPlatcustForRegularSubscribers")
	public PlatplatcustRegisterResponse registerPlatcustForRegularSubscribers(HttpServletRequest httpServletRequest, PlatplatcustRegisterForRegularSubscribersRequest platplatcustRegisterRequest) {

		logger.info("请求参数:" + platplatcustRegisterRequest.toString());
		PlatplatcustRegisterResponse platplatcustregisterResponse = new PlatplatcustRegisterResponse();
		validate(platplatcustRegisterRequest);
		platplatcustregisterResponse = userBusiness.registerEaccountForRegularSubscribers(platplatcustRegisterRequest);
		platplatcustregisterResponse.setOrder_no(platplatcustRegisterRequest.getOrder_no());
		return platplatcustregisterResponse;

	}

	/**
	 * 短验换卡申请
	 *
	 * @param httpServletRequest     request请求参数
	 * @param changeCardByMsgRequest 短验换卡验证请求参数
	 * @return UnBindCardResponse
	 * @author wuml
	 */
	@RequestMapping("/apply_change_card")
	@Sign
	@Log(method = "changeCardByMsgReq")
	public BaseResponse changeCardByMsgReq(HttpServletRequest httpServletRequest, ChangeCardByMsgRequest changeCardByMsgRequest) {
		validate(changeCardByMsgRequest);
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse = userBusiness.changeCardByMsgReq(changeCardByMsgRequest);
		} catch (BusinessException e) {
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}
		baseResponse.setOrder_no(changeCardByMsgRequest.getOrder_no());

		return baseResponse;
	}

	/**
	 * 短验换卡确认
	 *
	 * @param httpServletRequest     request请求参数
	 * @param changeCardByMsgConfirm 短验换卡验证请求参数
	 * @return UnBindCardResponse
	 * @author wuml
	 */
	@RequestMapping("/confirm_change_card")
	@Sign
	@Log(method = "changeCardByMsg")
	public BaseResponse changeCardByMsg(HttpServletRequest httpServletRequest, ChangeCardByMsgConfirm changeCardByMsgConfirm) {
		validate(changeCardByMsgConfirm);
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse = userBusiness.changeCardByMsg(changeCardByMsgConfirm);
		} catch (BusinessException e) {
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}
		baseResponse.setOrder_no(changeCardByMsgConfirm.getOrder_no());

		return baseResponse;
	}

	/**
	 * 预留手机号变更
	 * @param changePreMobileRequest
	 * @return
	 */
	@RequestMapping("/modify_mobile")
	@Sign
	@Log(method = "motdifyMobile")
	public BaseResponse motdifyMobile(HttpServletRequest httpServletRequest, ChangePreMobileRequest changePreMobileRequest){
		validate(changePreMobileRequest);
        BaseResponse baseResponse = new BaseResponse();
        try{
            baseResponse = userBusiness.modifyMobile(changePreMobileRequest);
        }catch (BusinessException e){
            baseResponse.setRecode(e.getErrorCode());
            baseResponse.setRemsg(e.getErrorMsg());
        }
        baseResponse.setOrder_no(changePreMobileRequest.getOrder_no());
        return baseResponse;
	}

	/**
	 * 授权申请
	 * @param applyAuthOperaRequest
	 * @return
	 */
	@RequestMapping("/apply_auth_opera")
	@Sign
	@Log(method = "applyAuthOpera")
	public BaseResponse applyAuthOpera(HttpServletRequest httpServletRequest, ApplyAuthOperaRequest applyAuthOperaRequest){
		validate(applyAuthOperaRequest);
		BaseResponse baseResponse = new BaseResponse();
		try{
			baseResponse = userBusiness.applyAuthOpera(applyAuthOperaRequest);
		}catch (BusinessException e){
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}
		baseResponse.setOrder_no(applyAuthOperaRequest.getOrder_no());
		return baseResponse;
	}


	/**
	 * 授权确认
	 * @param confirmAuthOperaRequest
	 * @return
	 */
	@RequestMapping("/confirm_auth_opera")
	@Sign(checkPassword= CheckPassword.YES)
	@Log(method = "confirmAuthOpera")
	public BaseResponse confirmAuthOpera(HttpServletRequest httpServletRequest, ConfirmAuthOperaRequest confirmAuthOperaRequest){
		validate(confirmAuthOperaRequest);
		BaseResponse baseResponse = new BaseResponse();
		try{
			baseResponse = userBusiness.confirmAuthOpera(confirmAuthOperaRequest);
		}catch (BusinessException e){
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}
		baseResponse.setOrder_no(confirmAuthOperaRequest.getOrder_no());
		return baseResponse;
	}
	/**
	 * 取消授权
	 * @param cancelAuthOperaRequest
	 * @return
	 */
	@RequestMapping("/cancel_auth_opera_no_pwd")
	@Sign
	@Log(method = "cancelAuthOpera")
	public BaseResponse cancelAuthOpera(HttpServletRequest httpServletRequest, CancelAuthOperaRequest cancelAuthOperaRequest) {
		validate(cancelAuthOperaRequest);
		return userBusiness.cancelAuthOpera(cancelAuthOperaRequest);
	}

	/**
	 * 取消授权 需要验密
	 * @param cancelAuthOperaRequest
	 * @return
	 */
	@RequestMapping("/cancel_auth_opera")
	@Sign(checkPassword= CheckPassword.YES)
	@Log(method = "cancelAuthOperaPwd")
	public BaseResponse cancelAuthOperaPwd(HttpServletRequest httpServletRequest, CancelAuthOperaRequest cancelAuthOperaRequest) {
		validate(cancelAuthOperaRequest);
		return userBusiness.cancelAuthOpera(cancelAuthOperaRequest);
	}

	/**
	 * 投融资转换
	 * @param httpServletRequest
	 * @param switchAccountRequest
	 * @return
	 */
	@RequestMapping("/switch_account")
	@Sign
	@Log(method = "switchAccount")
	public BaseResponse switchAccount(HttpServletRequest httpServletRequest, SwitchAccountRequest switchAccountRequest){
		validate(switchAccountRequest);
		BaseResponse baseResponse = new BaseResponse();
		try{
			baseResponse = userBusiness.switchAccount(switchAccountRequest);
		}catch (BusinessException e){
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}
		baseResponse.setOrder_no(switchAccountRequest.getOrder_no());
		return baseResponse;
	}
	/**
	 * 客户冻结/解冻
	 * @param httpServletRequest
	 * @param
	 * @return
	 */
	@RequestMapping("/freeze_account")
	@Sign
	@Log(method = "freezeAccount")
	public BaseResponse freezeAccount(HttpServletRequest httpServletRequest,FreezeAccountRequest freezeAccountRequest){
		validate(freezeAccountRequest);
		BaseResponse baseResponse = new BaseResponse();
		try{
			baseResponse = userBusiness.freezeAccount(freezeAccountRequest);
		}catch (BusinessException e){
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}
		baseResponse.setOrder_no(freezeAccountRequest.getOrder_no());
		return baseResponse;
	}

	/**
	 * 客户销户
	 * @param httpServletRequest
	 * @param
	 * @return
	 */
	@RequestMapping("/unreg_account")
	@Sign(checkPassword= CheckPassword.YES)
	@Log(method = "unregAccount")
	public BaseResponse unregAccount(HttpServletRequest httpServletRequest,UnregAccountRequest unregAccountRequest){
		validate(unregAccountRequest);
		BaseResponse baseResponse = new BaseResponse();
		try{
			baseResponse = userBusiness.unregAccount(unregAccountRequest);
		}catch (BusinessException e){
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}
		baseResponse.setOrder_no(unregAccountRequest.getOrder_no());
		return baseResponse;
	}

	/**
	 * 签约申请
	 */
	@RequestMapping("/contract_apply")
	@Sign
	@Log(method = "contractApp")
	public ContractApplyResponseDetail contractApp(HttpServletRequest httpServletRequest, ContractApplyRequest contractApplyRequest){
		validate(contractApplyRequest);
		ContractApplyResponseDetail contractApplyResponseDetail=new ContractApplyResponseDetail();
		try {
			contractApplyResponseDetail= userBusiness.contractApp(contractApplyRequest);
		}catch (BusinessException e){
			contractApplyResponseDetail.setRecode(e.getErrorCode());
			contractApplyResponseDetail.setRemsg(e.getErrorMsg());
		}
		return contractApplyResponseDetail;
	}


	/**
	 *  签约确认
	 */
	@RequestMapping("/contract_confirm")
	@Sign
	@Log(method = "contractConfirm")
	public ContractStatusReponse contractConfirm(HttpServletRequest httpServletRequest, ContractConfirmRequest contractConfirmRequest){
		validate(contractConfirmRequest);
		ContractStatusReponse baseResponse=new ContractStatusReponse();
		try {
			baseResponse=userBusiness.contractConfirm(contractConfirmRequest);
		}catch (BusinessException e){
			baseResponse.setRecode(e.getErrorCode());
			baseResponse.setRemsg(e.getErrorMsg());
		}

		return  baseResponse;
	}


	/**
	 * 签约查询
	 */
	@RequestMapping("/contract_status")
	@Sign
	@Log(method = "contractStatus")
	public ContractApplyResponseDetail contractStatus(HttpServletRequest httpServletRequest, ContractApplyRequest contractApplyRequest){
		validate(contractApplyRequest);
		ContractApplyResponseDetail contractApplyResponseDetail=new ContractApplyResponseDetail();
		try {
			contractApplyResponseDetail= userBusiness.contractStatus(contractApplyRequest);
		}catch (BusinessException e){
			contractApplyResponseDetail.setRecode(e.getErrorCode());
			contractApplyResponseDetail.setRemsg(e.getErrorMsg());
		}
		return contractApplyResponseDetail;
	}

}
