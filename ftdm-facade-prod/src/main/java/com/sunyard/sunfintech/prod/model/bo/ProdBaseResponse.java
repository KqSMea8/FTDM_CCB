package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

import java.math.BigDecimal;

/**
 * Created by terry on 2017/7/28.
 */
public class ProdBaseResponse extends BaseResponse {

    //订单状态
    private String order_status;

    //订单信息
    private String order_info;

    private String prod_id;

    private String platcust;

    private BigDecimal amt;

    private String oppo_platcust;

    public String getOppo_platcust() {
        return oppo_platcust;
    }

    public void setOppo_platcust(String oppo_platcust) {
        this.oppo_platcust = oppo_platcust;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

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
        return "ProdBaseResponse{" +
                "order_status='" + order_status + '\'' +
                ", order_info='" + order_info + '\'' +
                ", prod_id='" + prod_id + '\'' +
                ", platcust='" + platcust + '\'' +
                ", amt=" + amt +
                ", oppo_platcust='" + oppo_platcust + '\'' +
                '}';
    }
}
