package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.util.List;

/**
 * Created by terry on 2018/2/3.
 */
public interface ITransferService {

    /**
     * 批量转账（统一事务）
     * @param baseRequest
     * @param eaccAccountamtlists
     * @throws BusinessException
     */
    public void transfer(BaseRequest baseRequest, List<EaccAccountamtlist> eaccAccountamtlists)throws BusinessException;

    /**
     * 单笔转账
     * @param baseRequest
     * @param eaccAccountamtlists
     * @throws BusinessException
     */
    public void transfer(BaseRequest baseRequest, EaccAccountamtlist eaccAccountamtlists)throws BusinessException;
}
