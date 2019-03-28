package com.sunyard.sunfintech.web.model.vo.product;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.prod.model.bo.OrderData;


/**
 * @author wubin
 */
public class ProdEstablishOrAbortResponse extends BaseResponse{

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
        setData(JSON.toJSONString(orderData, GlobalConfig.serializerFeature));
    }
}
