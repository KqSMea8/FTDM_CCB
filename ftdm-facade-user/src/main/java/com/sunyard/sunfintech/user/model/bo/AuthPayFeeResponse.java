package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


/**
 * 授权缴费  --响应model--
 * Created by  wyc
 */
public class AuthPayFeeResponse extends BaseResponse {

    private String data;

    @JSONField(serialize=false)
    private List<AuthPayFeeResponseDetail  > authPayFeeResponseDetails;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



    public void setOrderData(List<AuthPayFeeResponseDetail  > authPayFeeResponseDetails) {
        this.authPayFeeResponseDetails = authPayFeeResponseDetails;
        if(authPayFeeResponseDetails!=null){
            this.data = JSON.toJSONString(authPayFeeResponseDetails, GlobalConfig.serializerFeature);
        }
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
