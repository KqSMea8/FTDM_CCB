package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseErrorData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by terry on 2017/5/14.
 */
public class ProdBatchTransferReturnData implements Serializable {

    private List<ProdBatchTransferSuccessData> successData;

    private List<BaseErrorData> errorData;

    public List<ProdBatchTransferSuccessData> getSuccessData() {
        return successData;
    }

    public void setSuccessData(List<ProdBatchTransferSuccessData> successData) {
        this.successData = successData;
    }

    public List<BaseErrorData> getErrorData() {
        return errorData;
    }

    public void setErrorData(List<BaseErrorData> errorData) {
        this.errorData = errorData;
    }
}
