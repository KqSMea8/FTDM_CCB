package com.sunyard.sunfintech.billcheck.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

/**
 * Created by terry on 2018/3/24.
 */
public interface IAccountTransferService {

    /**
     * 添加交易流水
     * @author PengZY
     * @param eaccAccountamtlist
     */
    public void addTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;
}
