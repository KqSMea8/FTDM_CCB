package com.sunyard.sunfintech.web.controller;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.annotation.CheckPassword;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.HttpClientUtils;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.web.business.PaymentBusiness;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author Zz
 * @return2017年5月4日
 */
@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {

	@Resource(name = "paymentBusiness")
	private PaymentBusiness paymentBusiness;

	/**
	 *  网关充值请求
	 * @author wml
	 * @param getewayPayRequest
	 * @return GetewayPayResponse
	 * @throws IOException 
	 */
	@RequestMapping("/gateway_pay")
	@Sign(checkPassword= CheckPassword.YES)
	@Log(method = "getewayPay")
	public GetewayPayResponse getewayPay(HttpServletRequest httpServletRequest, GetewayPayRequest getewayPayRequest) throws IOException{
		validate(getewayPayRequest);
		GetewayPayResponse getewaypayresponse =paymentBusiness.gatewayRechargeRequest(getewayPayRequest);
		getewaypayresponse.setOrder_no(getewayPayRequest.getOrder_no());
		return getewaypayresponse;
	}
	/**
	 *  网关充值请求  不验密老接口
	 * @author wml
	 * @param getewayPayRequest
	 * @return GetewayPayResponse
	 * @throws IOException
	 */
	@RequestMapping("/gateway_pay_old")
	@Sign
	@Log(method = "getewayPayOld")
	public GetewayPayResponse getewayPayOld(HttpServletRequest httpServletRequest, GetewayPayRequest getewayPayRequest) throws IOException{
		validate(getewayPayRequest);
		GetewayPayResponse getewaypayresponse =paymentBusiness.gatewayRechargeRequest(getewayPayRequest);
		getewaypayresponse.setOrder_no(getewayPayRequest.getOrder_no());
		return getewaypayresponse;
	}

	/**
	 * 充值回调通知（快捷网关通用）
	 * @author ZZ
	 * @param httpServletRequest
	 * @return NotityPaymentResponse
	 */
	@RequestMapping("/notity_payment")
	@Log(method = "notityPayment")
	public String notityPayment(HttpServletRequest httpServletRequest){
		logger.info("========开始【充值异步回调】=======");
        //获取参数
        StringBuilder stringBuilder = HttpClientUtils.baseHttpResponseToString(httpServletRequest);
        Boolean result = false;
        logger.info("========【充值异步回调】收到回调参数=======" + stringBuilder.toString() + "=============================");
        NotityPaymentRequest notityPaymentRequest = JSON.parseObject(stringBuilder.toString(), NotityPaymentRequest.class);
        if (null == notityPaymentRequest) {
            logger.info("========【充值异步回调】对象为空=============================");
            return "fail";
        }
        logger.info("========【充值异步回调】准备异步通知平台=======" + JSON.toJSONString(notityPaymentRequest));
        try {
            result = paymentBusiness.notifyCharge(notityPaymentRequest);
            logger.info("========【充值异步回调】结果=======" + result);
            if (result) {
                return "success";
            }
        } catch (Exception e) {
            logger.error("【充值异步回调】异常",e);
            return "fail";
        }
        return "fail";
	}

	/**
	 *  快捷充值请求
	 * @author ZZ
	 * @param applyQuickPayRequest
	 * @return ApplyQuickPayResponse
	 */
	@RequestMapping("/apply_quick_pay")
	@Sign
	@Log(method = "applyQuickPay")
	public ApplyQuickPayResponse applyQuickPay(HttpServletRequest httpServletRequest, ApplyQuickPayRequest applyQuickPayRequest){

		validate(applyQuickPayRequest);

		ApplyQuickPayResponse applyQuickPayResponse =new ApplyQuickPayResponse();
		applyQuickPayResponse=paymentBusiness.quickPayApply(applyQuickPayRequest);
		applyQuickPayResponse.setOrder_no(applyQuickPayRequest.getOrder_no());
		applyQuickPayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		return applyQuickPayResponse;
	}


	 /**
	  快捷充值确认重写
	  @author Raoyulu
	  @version 2017/11/9
	  @param confirmQuickPayRequset
	  @return ConfirmQuickPayResponse
	  */
	//@RequestMapping("/confirm_quick_pay_rewrite")
	@RequestMapping("/confirm_quick_pay")
	@Sign(checkPassword= CheckPassword.YES)
	@Log(method = "confirmQuickPayRewrite")
	public ConfirmQuickPayResponse confirmQuickPayRewrite(HttpServletRequest httpServletRequest,ConfirmQuickPayRequest confirmQuickPayRequset){
		logger.info("【快捷充值确认】=========请求参数："+confirmQuickPayRequset.toString());
		ConfirmQuickPayResponse confirmQuickPayResponse = new ConfirmQuickPayResponse();
		validate(confirmQuickPayRequset);
		confirmQuickPayResponse = paymentBusiness.confirmQuickPay(confirmQuickPayRequset);
		confirmQuickPayResponse.setOrder_no(confirmQuickPayRequset.getOrder_no());
		confirmQuickPayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		logger.info("【快捷充值确认】=========响应参数："+confirmQuickPayResponse.toString());
		return confirmQuickPayResponse;
	}


	/**
	 快捷充值确认老接口
	 @author Raoyulu
	 @version 2017/11/9
	 @param confirmQuickPayRequsetOld
	 @return ConfirmQuickPayResponse
	 */
	@RequestMapping("/confirm_quick_pay_old")
	@Sign
	@Log(method = "confirmQuickPayRewriteOld")
	public ConfirmQuickPayResponse confirmQuickPayRewriteOld(HttpServletRequest httpServletRequest,ConfirmQuickPayRequestOld confirmQuickPayRequsetOld){
		logger.info("【快捷充值确认】=========请求参数："+confirmQuickPayRequsetOld.toString());
		ConfirmQuickPayResponse confirmQuickPayResponse =new ConfirmQuickPayResponse();
		validate(confirmQuickPayRequsetOld);
		ConfirmQuickPayRequest confirmQuickPayRequest=new ConfirmQuickPayRequest();
		BeanUtils.copyProperties(confirmQuickPayRequsetOld,confirmQuickPayRequest);
		confirmQuickPayResponse = paymentBusiness.confirmQuickPay(confirmQuickPayRequest);
		confirmQuickPayResponse.setOrder_no(confirmQuickPayRequsetOld.getOrder_no());
		confirmQuickPayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		logger.info("【快捷充值确认】=========响应参数："+confirmQuickPayResponse.toString());
		return confirmQuickPayResponse;
	}

	/*//**
	 * 快捷充值请求----商城
	 * @author ZZ
	 * @param applyQuickPayMallRequest
	 * @return ApplyQuickPayMallResponse
	 *//*
	@RequestMapping("/apply_quick_pay_mall")
	@Sign
	public BaseResponse applyQuickPayMall(HttpServletRequest httpServletRequest, ApplyQuickPayMallRequest applyQuickPayMallRequest){

		validate(applyQuickPayMallRequest);

		ApplyQuickPayMallResponse applyQuickPayMallResponse =new ApplyQuickPayMallResponse();
		applyQuickPayMallResponse.setOrder_no(applyQuickPayMallRequest.getOrder_no());
		return applyQuickPayMallResponse;
	}


	*//**
	 * 商城充值通知
	 * @author ZZ
	 * @param notifyMallPayRequest
	 * @return NotifyMallPayResponse
	 *//*
	@RequestMapping("/notify_mall_pay")
	public BaseResponse notifyMallPay(HttpServletRequest httpServletRequest, NotifyMallPayRequest notifyMallPayRequest){

		validate(notifyMallPayRequest);
		NotifyMallPayResponse  notifyMallPayResponse =new NotifyMallPayResponse();
		notifyMallPayResponse.setOrder_no(notifyMallPayRequest.getOrder_no());
		return notifyMallPayResponse;
	}

	*//**
	 * 借款人线下还款
	 * @author bobguo
	 * @param repayOffLineRequest
	 * @return RepayOffLineResponse
	 */
	@RequestMapping("/repay_off_line")
	@Sign
	@Log(method = "repayOffLine")
	public BaseResponse repayOffLine(HttpServletRequest httpServletRequest, RepayOffLineRequest repayOffLineRequest){
		validate(repayOffLineRequest);
		OfferChargeResponse offerChargeResponse = paymentBusiness.repayOffLine(repayOffLineRequest);
		offerChargeResponse.setOrder_no(repayOffLineRequest.getOrder_no());
		//和文档同步响应参数一致
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setOrder_no(repayOffLineRequest.getOrder_no());
		baseResponse.setRemsg(offerChargeResponse.getRemsg());
		baseResponse.setRecode(offerChargeResponse.getRecode());
		return baseResponse;
		//return offerChargeResponse;
	}

	/*//**
	 *  投资人线下充值
	 * @author ZZ
	 * @param chargeOffLineRequest
	 * @return ChargeOffLineResponse
	 *//*
	@RequestMapping("/charge_off_line")
	@Sign
	public BaseResponse chargeOffLine(HttpServletRequest httpServletRequest, ChargeOffLineRequest chargeOffLineRequest){
		validate(chargeOffLineRequest);
		BaseResponse baseResponse = paymentBusiness.chargeOffLine(chargeOffLineRequest);
		baseResponse.setOrder_no(chargeOffLineRequest.getOrder_no());
		return baseResponse;
	}

	*/

	/**
	 *  批量提现
	 * @author pzy
	 * @param batchWithdrawRequest
	 * @return BatchWithdrawResponse
	 */
	@RequestMapping("/batch_withdraw")
	@Sign
	@Log(method = "batchWithdraw",batchDataName = "data")
	public BatchWithdrawResponse batchWithdraw(HttpServletRequest httpServletRequest, BatchWithdrawRequest batchWithdrawRequest){
		logger.info("【批量提现】-->开始-->order_no:"+batchWithdrawRequest.getOrder_no());
		Long start = System.currentTimeMillis();
		validate(batchWithdrawRequest);
		BatchWithdrawResponse batchWithdrawResponse = paymentBusiness.batchWithdraw(batchWithdrawRequest);
		batchWithdrawResponse.setOrder_no(batchWithdrawRequest.getOrder_no());
		Long end = System.currentTimeMillis();
		logger.info("【批量提现】-->结束-->order_no:"+batchWithdrawRequest.getOrder_no()+"-->耗时:"+(end-start));
		return batchWithdrawResponse;
	}

	/**
	 *  提现申请
	 * @author pzy
	 * @param withdrawApplicationRequest
	 * @return BatchWithdrawResponse
	 */
	@RequestMapping("/withdraw_application")
	@Sign(checkPassword = CheckPassword.YES)
	@Log(method = "withdrawApplication")
	public BatchWithdrawResponse withdrawApplication(HttpServletRequest httpServletRequest, WithdrawApplicationRequest withdrawApplicationRequest){
		logger.info("【批量提现申请】-->开始-->order_no:"+withdrawApplicationRequest.getOrder_no());
		Long start = System.currentTimeMillis();
		validate(withdrawApplicationRequest);
		withdrawApplicationRequest.setIs_advance(withdrawApplicationRequest.getIs_advance());
		withdrawApplicationRequest.setFee_amt(withdrawApplicationRequest.getFee_amt());
		withdrawApplicationRequest.setWithdraw_type(withdrawApplicationRequest.getWithdraw_type());
		withdrawApplicationRequest.setClient_property(withdrawApplicationRequest.getClient_property());
		withdrawApplicationRequest.setCard_type(withdrawApplicationRequest.getCard_type());
		BatchWithdrawResponse withdrawResponse = paymentBusiness.withdrawApplication(withdrawApplicationRequest);
		Long end = System.currentTimeMillis();
		logger.info("【批量提现申请】-->结束-->order_no:"+withdrawApplicationRequest.getOrder_no()+"-->耗时:"+(end-start));
		return withdrawResponse;
	}

	/**
	 *  提现确认
	 * @author pzy
	 * @param withdrawAffirmRequest
	 * @return BatchWithdrawResponse
	 */
	@RequestMapping("/withdraw_affirm")
	@Sign
	@Log(method = "withdrawAffirm",batchDataName = "data")
	public BatchWithdrawResponse withdrawAffirm(HttpServletRequest httpServletRequest, WithdrawAffirmRequest withdrawAffirmRequest){
		logger.info("【批量提现确认】-->开始-->order_no:"+withdrawAffirmRequest.getOrder_no());
		validate(withdrawAffirmRequest);
		Long start = System.currentTimeMillis();
		BatchWithdrawResponse withdrawResponse = paymentBusiness.withdrawAffirm(withdrawAffirmRequest);
		Long end = System.currentTimeMillis();
		logger.info("【批量提现确认】-->结束-->order_no:"+withdrawAffirmRequest.getOrder_no()+"-->耗时:"+(end-start));
		return withdrawResponse;
	}



    /**
     * Ccb短信接口
     * @param httpServletRequest
     * @param ccbSendMsgReq
     * @return
     *//*
	@RequestMapping("/ccb_send_msg")
	public BaseResponse CcbSendMeg(HttpServletRequest httpServletRequest, CcbSendMsgReq ccbSendMsgReq){
		validate(ccbSendMsgReq);
		BaseResponse baseResponse = paymentBusiness.ccbSendMsg(ccbSendMsgReq);
		baseResponse.setOrder_no(ccbSendMsgReq.getOrder_no());
        return baseResponse;
	}

	*//**
	 *  代扣充值
	 * @author bob
	 */
	@RequestMapping("/collection")
	@Sign
	@Log(method = "collection")
	public CollectionResponse collection(HttpServletRequest httpServletRequest, CollectionRequest collectionRequest){
		validate(collectionRequest);
		return paymentBusiness.collection(collectionRequest);
	}

	/*
	/  批量代扣
	 */
	@RequestMapping("/batch_collection")
	@Sign
	@Log(method = "batch_collection")
	public BatchCollectionResponse batch_collection(HttpServletRequest httpServletRequest, BatchCollectionRequest collectionRequest){
		logger.info("批量代扣"+JSON.toJSON(collectionRequest));
		validate(collectionRequest);
		return paymentBusiness.batchcollection(collectionRequest);
	}

	/**
	 *  协议支付申请
	 * @author ZZ
	 * @param applyQuickPayRequest
	 * @return ApplyQuickPayResponse
	 */
	@RequestMapping("/agr_pay_apply")
	@Sign
	@Log(method = "agrPayApply")
	public ApplyQuickPayResponse agrPayApply(HttpServletRequest httpServletRequest, ApplyQuickPayRequest applyQuickPayRequest){

		validate(applyQuickPayRequest);

		ApplyQuickPayResponse applyQuickPayResponse =new ApplyQuickPayResponse();
		applyQuickPayResponse=paymentBusiness.agrPayApply(applyQuickPayRequest);
		applyQuickPayResponse.setOrder_no(applyQuickPayRequest.getOrder_no());
		applyQuickPayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		return applyQuickPayResponse;
	}


	/**
	 协议支付确认
	 */
	@RequestMapping("/agr_pay_confirm")
	@Sign(checkPassword= CheckPassword.YES)
	@Log(method = "agrPayConfirm")
	public ConfirmQuickPayResponse agrPayConfirm(HttpServletRequest httpServletRequest,ConfirmQuickPayRequest confirmQuickPayRequset){
		logger.info("【协议支付确认】=========请求参数："+confirmQuickPayRequset.toString());
		ConfirmQuickPayResponse confirmQuickPayResponse = new ConfirmQuickPayResponse();
		validate(confirmQuickPayRequset);
		confirmQuickPayResponse = paymentBusiness.agrPayConfirm(confirmQuickPayRequset);
		confirmQuickPayResponse.setOrder_no(confirmQuickPayRequset.getOrder_no());
		confirmQuickPayResponse.setTrans_date(DateUtils.getyyyyMMddDate());
		logger.info("【协议支付确认】=========响应参数："+confirmQuickPayResponse.toString());
		return confirmQuickPayResponse;
	}

}
	
