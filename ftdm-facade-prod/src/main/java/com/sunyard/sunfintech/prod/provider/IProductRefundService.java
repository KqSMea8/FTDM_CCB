package com.sunyard.sunfintech.prod.provider;

import java.util.List;

import com.sunyard.sunfintech.account.model.bo.BatchPayQueryResponseDataDetail;
import com.sunyard.sunfintech.core.base.BaseMessage;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.ProdCompensationInfo;
import com.sunyard.sunfintech.dao.entity.ProdCompensationRepay;
import com.sunyard.sunfintech.prod.model.bo.*;


public interface IProductRefundService {

	/**
	 * 借款人还款计划更新
	 * @author pengzy
	 * @param prodUpdateRepaymentPlanRequest
	 * 2017年5月13日
	 *
	 */
	public ProdUpdateRepaymentPlanResponse updateRefundPlan(ProdUpdateRepaymentPlanRequest prodUpdateRepaymentPlanRequest) throws BusinessException;

	/**
	 * 标的批量还款
	 * @author Terry
	 * @param prodBatchRepayRequest
	 * @return
	 * @throws BusinessException 抛出的异常
	 */
	public List<ProdBusinessData> batchRefund(ProdBatchRepayOldRequest prodBatchRepayRequest)throws BusinessException;

	/**
	 * 标的批量还款(一借一代)
	 * @author Terry
	 * @param prodBatchRepayRequest
	 * @return
	 * @throws BusinessException 抛出的异常
	 */
	public boolean batchRefundAsyn(ProdBatchRepayRequest prodBatchRepayRequest)throws BusinessException;

	/**
	 * 合成批量电子账户转账数据
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	public boolean getBatchEaccountTransData(String key,Long maxNum,Long maxThread,Long needThreadNum)throws BusinessException;

	/**
	 * 执行批量提现
	 * @return
	 * @throws BusinessException
	 */
	public boolean executeBatchRefund(List<BatchPayQueryResponseDataDetail> detailList,String trans_serial)throws BusinessException;

	/**
	 * 处理失败的还款
	 * @param trans_serial
	 * @return
	 * @throws BusinessException
	 */
	public boolean executeFailedRefund(String trans_serial,String plat_no)throws BusinessException;

	/**
	 * 手动启动意外关闭的job
	 * @param trans_serial
	 * @param mall_no
	 * @return
	 * @throws BusinessException
	 */
	public boolean startClosedFundQueryJob(String trans_serial,String mall_no)throws BusinessException;

	/**
	 * 执行还款
	 * @param custRepay
	 * @throws BusinessException
	 */
	public void toRefund(BatchCustRepayOld custRepay) throws BusinessException;
	boolean addCompensationInfo(ProdCompensationInfo compensationInfo ) throws BusinessException;
	boolean addCompensationRepay (ProdCompensationRepay prodCompensationRepay) throws BusinessException;
}
