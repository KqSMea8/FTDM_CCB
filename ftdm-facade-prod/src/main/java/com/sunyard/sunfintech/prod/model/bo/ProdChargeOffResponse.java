package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.prod.model.bo.ProdBusinessData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdChargeOffResponse extends BaseResponse {

    private String data;
    @JSONField(serialize=false)
    private ProdBusinessData data_obj;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ProdBusinessData getData_obj() {
        return data_obj;
    }

    public void setData_obj(ProdBusinessData data_obj) {
        this.data_obj = data_obj;
        setData(JSON.toJSONString(data_obj, GlobalConfig.serializerFeature));
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
