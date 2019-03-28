package com.sunyard.sunfintech.account.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.util.List;

/**
 * Created by terry on 2017/9/13.
 */
public interface IAccountTransferAsynService {

    /**
     * 单笔转账
     * @param eaccAccountamtlist
     * @return
     * @throws BusinessException
     */
    public Boolean transfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;

    /**
     * 批量转账
     * @param eaccAccountamtlists
     * @return
     * @throws BusinessException
     */
    public Integer batchTransfer(List<EaccAccountamtlist> eaccAccountamtlists)throws BusinessException;
}
