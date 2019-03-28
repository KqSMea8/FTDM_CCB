package com.sunyard.sunfintech.account.provider;


import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

/**
 * @author heroy
 * @version 2017/4/12
 */
public interface IAccountFrozenService {

    //冻结
    public Boolean freeze(EaccAccountamtlist expense)throws BusinessException;

    //解冻
    public Boolean unfreeze(EaccAccountamtlist income)throws BusinessException;

    public Boolean charge(EaccAccountamtlist income) throws BusinessException;
}
