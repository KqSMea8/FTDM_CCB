package com.sunyard.sunfintech.web.model.vo.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.prod.model.bo.ProdBaseResponse;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestSuccessData;

import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestNoSynResponse extends BaseResponse {

    //订单状态
    private String order_status;

    //订单信息
    private String order_info;

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_info() {
        return order_info;
    }

    public void setOrder_info(String order_info) {
        this.order_info = order_info;
    }

    @Override
    public String toString() {
        return "ProdInvestNoSynResponse{" +
                "order_status='" + order_status + '\'' +
                ", order_info='" + order_info + '\'' +
                '}';
    }
}
