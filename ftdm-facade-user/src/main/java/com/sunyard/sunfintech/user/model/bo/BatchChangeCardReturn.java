package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class BatchChangeCardReturn implements Serializable{

    private List<BatchChangeCardSuccessData> successDataList;

    private List<BatchChangeCardErrorData> errorDataList;

    public List<BatchChangeCardSuccessData> getSuccessDataList() {
        return successDataList;
    }

    public void setSuccessDataList(List<BatchChangeCardSuccessData> successDataList) {
        this.successDataList = successDataList;
    }

    public List<BatchChangeCardErrorData> getErrorDataList() {
        return errorDataList;
    }

    public void setErrorDataList(List<BatchChangeCardErrorData> errorDataList) {
        this.errorDataList = errorDataList;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
