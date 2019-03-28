package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 批量换卡（先绑卡再解绑）请求参数
 * @author Raoyulu
 * @version 2017/9/18
 */
public class BatchChangeCardRequest extends BaseRequest{

    @NotBlank
    private String data;

    private List<BatchChangeCardRequestDetail> batchChangeCardRequestDetailList;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        if(StringUtils.isNoneBlank(data)){
            batchChangeCardRequestDetailList = JSON.parseArray(data,BatchChangeCardRequestDetail.class);
        }
    }

    public List<BatchChangeCardRequestDetail> getBatchChangeCardRequestDetailList() {
        return batchChangeCardRequestDetailList;
    }

    public void setBatchChangeCardRequestDetailList(List<BatchChangeCardRequestDetail> batchChangeCardRequestDetailList) {
        this.batchChangeCardRequestDetailList = batchChangeCardRequestDetailList;

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
