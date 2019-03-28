package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.user.model.bo.BatchRepayRequest;
import com.sunyard.sunfintech.user.model.bo.BatchRepayRequestDetail;
import com.sunyard.sunfintech.user.model.bo.BatchRepaySuccessData;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestDetailOld;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestOld;

/**
 * Created by PengZY on 2017/7/10.
 */
public interface IBatchRepayExtService {

    public BatchRepaySuccessData onePatchRepay(BatchRepayRequestOld batchRepayRequest, BatchRepayRequestDetailOld detail) throws BusinessException;

}
