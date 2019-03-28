package com.sunyard.sunfintech.account.provider;

import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

/**
 * @author heroy
 * @version 2018/1/18
 */
public interface IAccountCompensationService {
    //冲正，如果之前成功的反向操作
    boolean negativeCompensate(EaccAccountamtlist eaccAccountamtlist);

    //补偿
    boolean compensate(EaccAccountamtlist eaccAccountamtlist);
}
