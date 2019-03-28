package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseErrorData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by terry on 2017/5/17.
 */
public class UnBindCardReturn implements Serializable {

    private List<UnBindCardSuccess> successDataList;

    private List<BaseErrorData> errorDataList;

    public List<UnBindCardSuccess> getSuccessDataList() {
        return successDataList;
    }

    public void setSuccessDataList(List<UnBindCardSuccess> successDataList) {
        this.successDataList = successDataList;
    }

    public List<BaseErrorData> getErrorDataList() {
        return errorDataList;
    }

    public void setErrorDataList(List<BaseErrorData> errorDataList) {
        this.errorDataList = errorDataList;
    }
}
