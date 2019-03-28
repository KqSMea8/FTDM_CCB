package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.Platlimit;
import com.sunyard.sunfintech.user.model.bo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wubin on 2017/5/23.
 */
public interface IPlatformOptionService {

    public boolean checkCauseType(String plat_no, String cause_type)throws BusinessException;

    public Platlimit getPlatLimit(String mall_no, String mer_no)throws BusinessException;

    /**
     *  平台转帐个人
     * @param transferToPersonRequest
     * @return Boolean true：平台转帐个人成功
     * @throws BusinessException 平台转帐个人失败，抛异常
     */
    public BaseResponse transferToPerson(TransferToPersonRequest transferToPersonRequest)throws BusinessException;

    /**
     *  （批量）平台转帐个人
     * @param batchTransferToPersonRequest
     * @return BatchTransferToPersonResponse 平台转帐个人返回数据
     * @throws BusinessException 平台转帐个人失败，抛异常
     */
    public BatchTransferToPersonResponse batchTransferToPerson(BatchTransferToPersonRequest batchTransferToPersonRequest)throws BusinessException;

    /**
     *  个人转帐平台
     * @param transferToPersonRequest
     * @return Boolean true：个人转帐平台
     * @throws BusinessException 个人转帐平台失败，抛异常
     */
    public BaseResponse transferFromPerson(TransferToPersonRequest transferToPersonRequest)throws BusinessException;

    /**
     *  平台不同账户转账
     * @param transferToPlatformRequest
     * @return Boolean true：平台不同账户转账成功
     * @throws BusinessException 平台不同账户转账失败，抛异常
     */
    public TransferToPlatformResponse transferToPlatform(TransferToPlatformRequest transferToPlatformRequest)throws BusinessException;

    /**
     * 平台充值
     * @param chargeRequest
     * @throws BusinessException
     */
    public ChargeResponse charge(ChargeRequest chargeRequest)throws BusinessException;

    /**
     * 平台充值异步
     * @param map
     * @throws BusinessException
     */
    public Map<String,Object> platChargeAsyn(Map<String, Object> map)throws BusinessException;
    /**
     * 平台提现
     * @param withdrawRequest
     * @throws BusinessException
     */
    public WithdrawResponse withdraw(WithdrawRequest withdrawRequest)throws BusinessException;

    /**
     * 平台异步提现
     * @param map
     * @return
     * @throws BusinessException
     */
    public Map<String,Object> platWithdrawAsyn(Map<String, Object> map)throws BusinessException;

    /**
     * 下载指定账户流水单据
     * @param platcust
     * @param startDate
     * @param endDate
     * @return
     * @throws BusinessException
     */
  //  public List<EaccAccountamtlist> downAccountList(String platcust, String startDate, String endDate)throws BusinessException;
    /**
     *  平台转个人撤销
     * @param transferToPersonBackRequest
     * @throws BusinessException
     */
    public BaseResponse rollback_plat2person(TransferToPersonBackRequest transferToPersonBackRequest);

    /**
     *  资金冻结解冻
     * @param freezeFundRequest
     * @throws BusinessException
     */
    public FreezeFundResponse freezeFund(FreezeFundRequest freezeFundRequest);

}