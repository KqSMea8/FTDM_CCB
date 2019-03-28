package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

import java.math.BigDecimal;

/**
 * Created by terry on 2017/7/28.
 */
public class ProdBaseVestResponse extends BaseResponse {

    //订单状态
    private String order_status;

    //订单信息
    private String order_info;

    private String prod_id;

    private String platcust;

    private BigDecimal trans_amt;

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

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProdBaseVestResponse that = (ProdBaseVestResponse) o;

        if (order_status != null ? !order_status.equals(that.order_status) : that.order_status != null) return false;
        if (order_info != null ? !order_info.equals(that.order_info) : that.order_info != null) return false;
        if (prod_id != null ? !prod_id.equals(that.prod_id) : that.prod_id != null) return false;
        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        return trans_amt != null ? trans_amt.equals(that.trans_amt) : that.trans_amt == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (order_status != null ? order_status.hashCode() : 0);
        result = 31 * result + (order_info != null ? order_info.hashCode() : 0);
        result = 31 * result + (prod_id != null ? prod_id.hashCode() : 0);
        result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
        result = 31 * result + (trans_amt != null ? trans_amt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProdBaseVestResponse{" +
                "order_status='" + order_status + '\'' +
                ", order_info='" + order_info + '\'' +
                ", prod_id='" + prod_id + '\'' +
                ", platcust='" + platcust + '\'' +
                ", trans_amt=" + trans_amt +
                '}';
    }
}
