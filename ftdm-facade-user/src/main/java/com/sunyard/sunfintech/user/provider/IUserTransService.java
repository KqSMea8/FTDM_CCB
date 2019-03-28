package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.user.model.bo.*;

import java.util.List;
import java.util.Map;


public interface IUserTransService {

	/**
	 * 快捷充值请求
	 *
	 * @return ApplyQuickPayResponse
	 * @throws BusinessException
	 * @author wml
	 */
	public ApplyQuickPayResponse applyQuickPay(ApplyQuickPayRequest applyQuickPayRequest) throws BusinessException;

	/**
	 * 快捷充值确认重写
	 * @param confirmQuickPayRequest
	 * @return ConfirmQuickPayResponse
	 * @author wml
	 */
	public ConfirmQuickPayResponse confirmQuickPay(ConfirmQuickPayRequest confirmQuickPayRequest) throws BusinessException;

	/**
	 * 网关充值请求接口
	 *
	 * @param getewayPayRequest
	 * @return GetewayPayResponse
	 */
	public GetewayPayResponse gatewayRechargeRequest(GetewayPayRequest getewayPayRequest) throws BusinessException;

	/**
	 * 快捷支付异步回调接口
	 *
	 * @param notityPaymentRequest
	 * @return
	 *
	 * @throws BusinessException
	 */
	public NotifyConfirmQPres notifyCharge(NotityPaymentRequest notityPaymentRequest) throws BusinessException;

	//代扣查询E支付
	public RwRecharge queryEpayStatus(RwRecharge rwRecharge) throws BusinessException;

	//代扣充值
    CollectionResponse collectionService(CollectionRequest collectionRequest) throws BusinessException;

    //借款人线下还款
	OfferChargeResponse repayOffLine(RepayOffLineRequest repayOffLineRequest);

	//借款人线下还款异步通知
	RepayOffLineNotifyResponse borrowRepayAsyn(Map<String, Object> map);

	List<TransTransreq> queryTransProcessing() throws BusinessException;

    ApplyQuickPayResponse agrPayApply(ApplyQuickPayRequest applyQuickPayRequest);

	ConfirmQuickPayResponse agrPayConfirm(ConfirmQuickPayRequest confirmQuickPayRequest);

    BatchCollectionResponse batchCollectionService(BatchCollectionRequest collectionRequest);
}
