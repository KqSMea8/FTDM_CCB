package com.sunyard.sunfintech.billcheck.provider;

import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.dao.entity.ClearResult;

/**
 * Created by KouKi on 2018/1/29.
 */
public interface IReconciliationService {

    public BaseResponse getAccountFile(ClearResult clearResult);

}
