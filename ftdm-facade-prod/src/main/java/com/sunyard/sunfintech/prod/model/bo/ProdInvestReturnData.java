package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by terry on 2017/5/10.
 */
public class ProdInvestReturnData extends BaseModel {

    List<ProdInvestSuccessData> successData;

    List<BaseErrorData> errorData;

    public List<ProdInvestSuccessData> getSuccessData() {
        return successData;
    }

    public void setSuccessData(List<ProdInvestSuccessData> successData) {
        this.successData = successData;
    }

    public List<BaseErrorData> getErrorData() {
        return errorData;
    }

    public void setErrorData(List<BaseErrorData> errorData) {
        this.errorData = errorData;
    }

    @Override
    public String toString() {
        return "ProdInvestReturnData{" +
                "successData=" + successData +
                ", errorData=" + errorData +
                '}';
    }
}
