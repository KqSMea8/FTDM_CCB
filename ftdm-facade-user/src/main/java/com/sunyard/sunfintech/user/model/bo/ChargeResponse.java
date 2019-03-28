package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 平台充值 --响应model--
 * Created by wubin on 2017/5/5.
 */
public class ChargeResponse extends BaseResponse {

    /**
     * 返回业务数据
     */
    private String data;

    @JSONField(serialize = false)
    private OrderData orderData;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public OrderData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderData chargeReData) {
        this.orderData = chargeReData;
        if(chargeReData!=null){
            this.data = JSON.toJSONString(chargeReData, GlobalConfig.serializerFeature);
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
