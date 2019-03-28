package com.sunyard.sunfintech.web.model.vo.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.user.model.bo.PlatplatcustRegisterResponseData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by dany on 2017/6/1.
 */
public class PlatplatcustRegisterResponse extends BaseResponse{

    //返回業務數據
    private String data;

    //返回業務數據明細
    @JSONField(serialize = false)
    private PlatplatcustRegisterResponseData platplatcustRegisterResponseData;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public PlatplatcustRegisterResponseData getPlatplatcustRegisterResponseData() {
        return platplatcustRegisterResponseData;
    }

    public void setPlatplatcustRegisterResponseData(PlatplatcustRegisterResponseData platplatcustRegisterResponseData) {
        this.platplatcustRegisterResponseData = platplatcustRegisterResponseData;
        this.data = JSON.toJSONString(platplatcustRegisterResponseData, GlobalConfig.serializerFeature);
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
