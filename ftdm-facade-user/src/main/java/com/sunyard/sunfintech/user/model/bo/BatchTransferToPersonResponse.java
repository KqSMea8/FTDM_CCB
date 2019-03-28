package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * Created by PengZY on 2017/9/21.
 */
public class BatchTransferToPersonResponse extends BaseResponse {

    //订单状态
    private String order_status;
    //订单处理信息
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
}
