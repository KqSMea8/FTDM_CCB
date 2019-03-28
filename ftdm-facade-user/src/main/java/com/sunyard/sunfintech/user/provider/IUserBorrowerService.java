package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyRequest;
import com.sunyard.sunfintech.thirdparty.model.ContractApplyResponseDetail;
import com.sunyard.sunfintech.thirdparty.model.ContractConfirmRequest;
import com.sunyard.sunfintech.thirdparty.model.ContractStatusReponse;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayAsynRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.CompensateRepayRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.SubstituteRepayRequestOld;

import java.util.List;


public interface IUserBorrowerService {

	/**
	 * 借款人批量还款  同步老接口
	 * @author PZY
     * @param batchRepayRequest
     * @return 
     * @throws BusinessException 抛出的异常
	 */
	public BatchRepayResponse batchRepayOld(BatchRepayRequestOld batchRepayRequest)throws BusinessException;

	/**
	 * 借款人批量还款  异步老接口
	 * @author PZY
	 * @param batchRepayRequest
	 * @return
	 * @throws BusinessException 抛出的异常
	 */
	public boolean batchRepayAsynOld(BatchRepayAsynRequestOld batchRepayRequest)throws BusinessException;


	/**
	 * 借款人还款申请
	 * @author PZY
	 * @param batchRepayRequest
	 * @return
	 * @throws BusinessException 抛出的异常
	 */
	public boolean batchRepayAsyn(BatchRepayAsynRequest batchRepayRequest)throws BusinessException;


	/**
     * 代偿还款
     * @author PengZY
     * @param substituteRepayRequest
     * @return  SubstituteRepayResponse
     * @throws BusinessException 抛出的异常
     */
    public SubstituteRepayResponse substituteRepay(SubstituteRepayRequest substituteRepayRequest) throws BusinessException;

	/**
	 * 标的代偿（委托）还款  老接口
	 * @author PengZY
	 * @param baserequest
	 * @param substituteRepayRequest
	 * @return  SubstituteRepayResponse
	 * @throws BusinessException 抛出的异常
	 */
	public SubstituteRepayResponse substituteRepayOld(BaseRequest baserequest, SubstituteRepayRequestOld substituteRepayRequest) throws BusinessException;

	/**
	 * 签约申请
	 */
	public ContractApplyResponseDetail contractApp(ContractApplyRequest contractApplyRequest)throws BusinessException;

	/**
	 * 签约确认
	 */

	public ContractStatusReponse contractConfirm(ContractConfirmRequest contractConfirmRequest)throws  BusinessException;

	/**
	 * 签约查询
	 */

	public ContractApplyResponseDetail contractStatus(ContractApplyRequest contractApplyRequest)throws BusinessException;


	/**
     * 借款人还款代偿（委托）
     * @author PengZY
     * @param compensateRepayRequest
     * @return  CompensateRepayResponse
     * @throws BusinessException 抛出的异常
     */
    public CompensateRepayResponse compensateRepay(CompensateRepayRequest compensateRepayRequest) throws BusinessException;

	/**
	 * 借款人还款代偿（委托）  老接口
	 * @author PengZY
	 * @param compensateRepayRequest
	 * @return  CompensateRepayResponse
	 * @throws BusinessException 抛出的异常
	 */
	public CompensateRepayResponse compensateRepayOld(CompensateRepayRequestOld compensateRepayRequest) throws BusinessException;

    /*
     * 融资人分账
     * @author wuml
     */
	public BorrowerSubAccountResponse sub_account(BorrowerSubAccountRequest borrowerSubAccountRequest) throws BusinessException;


	/**
	 * 查询长时间处理中的借款人还款代偿，标的代偿还款
	 * @throws BusinessException
	 */
	public List<TransTransreq> queryAllProcessOrder()throws BusinessException;

	/**
	 * 补偿借款人还款代偿，标的代偿还款
	 * @throws BusinessException
	 */
	public void doAllProcessOrder(TransTransreq transTransreq)throws BusinessException;
}
