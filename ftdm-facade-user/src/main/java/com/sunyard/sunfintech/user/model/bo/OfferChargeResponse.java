package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class OfferChargeResponse extends BaseResponse {

    /**
     * 返回业务数据
     */
    @NotBlank
    private String data;

    @JSONField(serialize = false)
    private OrderData orderData;

    /**
     * 系统处理日期(yyyyMMddHHmmss)
     */
    @NotBlank
    private String process_date;

    public OrderData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
        if(orderData!=null){
            this.data = JSON.toJSONString(orderData, GlobalConfig.serializerFeature);
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
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
