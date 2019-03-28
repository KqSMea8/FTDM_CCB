package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 借款人还款代偿（委托） --响应model--
 * Created by wubin on 2017/5/3.
 */
public class CompensateRepayResponse extends BaseResponse{

    /**
     * 返回业务数据
     */
    private String data;

    @JSONField(serialize=false)
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

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
        if(orderData!=null){
            this.data = JSON.toJSONString(orderData, GlobalConfig.serializerFeature);
        }
    }

    @Override
    public String toString() {
        return "CompensateRepayResponse{" +
                "data='" + data + '\'' +
                ", orderData=" + orderData +
                '}';
    }
}
