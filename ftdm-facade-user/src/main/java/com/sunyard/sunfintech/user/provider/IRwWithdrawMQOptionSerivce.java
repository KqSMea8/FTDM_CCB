package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.user.model.bo.BatchRwWithdrawMQEntity;
import com.sunyard.sunfintech.user.model.bo.RwWithdrawAffirmMQEntity;

/**
 * Created by PengZY on 2018/1/30.
 */
public interface IRwWithdrawMQOptionSerivce {

    public void addRwWithdrawAffirm(RwWithdrawAffirmMQEntity batchRwWithdrawMQEntity) throws BusinessException;

    public void addBatchRwWithdraw(BatchRwWithdrawMQEntity batchRwWithdrawMQEntity) throws BusinessException;

    public void doRwWithdrawAffirm(RwWithdrawAffirmMQEntity batchRwWithdrawMQEntity) throws BusinessException;

    public void doBatchRwWithdraw(BatchRwWithdrawMQEntity batchRwWithdrawMQEntity) throws BusinessException;

}
