package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.user.model.bo.BatchTransferToPersonRequestDeatil;
import com.sunyard.sunfintech.user.model.bo.BatchTransferToPersonResponseDetail;

/**
 * Created by PengZY on 2017/9/27.
 */
public interface IBatchTransferToPersonService {

    //批量平台转个人
    public BatchTransferToPersonResponseDetail batchTransferToPerson(AccountSubjectInfo plataccountSubjectInfo, BaseRequest baseRequest, BatchTransferToPersonRequestDeatil batchTransferToPersonRequestDeatil, String notify_url) throws BusinessException;

}
