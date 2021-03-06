package com.sunyard.sunfintech.account.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by my on 2018/5/3.
 */
public class EaccountProdResponseData extends BaseResponse {
    private String data;//资金
    @JSONField(serialize = false)
    private List<EaccountProdListResponse> data_detail;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<EaccountProdListResponse> getData_detail() {
        return data_detail;
    }

    public void setData_detail(List<EaccountProdListResponse> data_detail) {
        this.data_detail = data_detail;
        setData(JSON.toJSONString(data_detail, GlobalConfig.serializerFeature));
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
