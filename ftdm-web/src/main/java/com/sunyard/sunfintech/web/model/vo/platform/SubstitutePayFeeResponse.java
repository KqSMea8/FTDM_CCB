package com.sunyard.sunfintech.web.model.vo.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.user.model.bo.SubstitutePayFeeData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;


/**
 * 自有资金账户手续费扣款查询    --响应model--
 * Created by wubin on 2017/5/5.
 */
public class SubstitutePayFeeResponse extends BaseResponse {

    @NotBlank
    private String data;

    @JSONField(serialize = false)
    private List<SubstitutePayFeeData> substitutePayFeeData;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<SubstitutePayFeeData> getSubstitutePayFeeData() {
        return substitutePayFeeData;
    }

    public void setSubstitutePayFeeData(List<SubstitutePayFeeData> substitutePayFeeData) {
        this.substitutePayFeeData = substitutePayFeeData;
        if(substitutePayFeeData!=null){
            this.data = JSON.toJSONString(substitutePayFeeData, GlobalConfig.serializerFeature);
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
