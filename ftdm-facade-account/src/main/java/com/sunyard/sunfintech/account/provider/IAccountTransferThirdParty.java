package com.sunyard.sunfintech.account.provider;

import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;

import java.util.Map;

/**
 * Created by terry on 2017/7/4.
 */
public interface IAccountTransferThirdParty {

    /**
     * 代付
     * @param params
     * @param mall_no
     * @return
     * @throws BusinessException
     */
    public EpayAgentPayRealResponse eaccTransfer(Map<String,Object> params, String mall_no)throws BusinessException;

    /**
     * 批量代付
     * @param params
     * @param mall_no
     * @return
     * @throws BusinessException
     */
    public BatchPayResponse batchPay(Map<String,Object> params, String mall_no)throws BusinessException;

    /**
     * 批量代扣
     * @param params
     * @param mall_no
     * @return
     * @throws BusinessException
     */
    public BatchPayResponse batchCollection(Map<String,Object> params, String mall_no)throws BusinessException;

    /**
     * 批量代付查询
     * @param params
     * @param mall_no
     * @return
     * @throws BusinessException
     */
    public BatchPayQueryResponse batchPayQuery(Map<String,Object> params, String mall_no)throws BusinessException;

    /**
     * 单笔订单查询
     * @param params
     * @param mall_no
     * @return
     * @throws BusinessException
     */
    public QueryPayStatusResponse queryPayStatusQuery(Map<String,Object> params, String mall_no)throws BusinessException;

    /**
     * 添加查询到队列
     * @param queryPayStatus
     * @throws BusinessException
     */
    public void addQueryToQueue(QueryPayStatus queryPayStatus)throws BusinessException;

    /**
     * 添加冲正到队列
     * @param bankReverse
     * @throws BusinessException
     */
    public void addReverseToQueue(BankReverse bankReverse)throws BusinessException;

    public BankReverseResponse bankReverse(Map<String,Object> params, String mall_no)throws BusinessException;

    public BaseHttpResponse doPost(Map<String, Object> params, String URL, String seriNo) throws BusinessException;
}
