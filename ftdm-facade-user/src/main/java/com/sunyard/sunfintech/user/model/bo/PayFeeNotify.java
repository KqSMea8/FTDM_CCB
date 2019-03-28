package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/5.
 */

public class PayFeeNotify extends BaseResponse {
    /**
     * 平台客户编号（出资人）
     */
    @NotBlank
    private String platcust;
    /**
     * 产品编号，如果非缴费到平台必填
     */

    private String prod_id;
    /**
     * 缴费金额
     */
    @NotNull
    private BigDecimal trans_amt;
    /**
     * 订单信息
     */
    private String order_info;
    private String mall_no;


    public String getMall_no() {
        return mall_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public String getOrder_info() {
        return order_info;
    }

    public void setOrder_info(String order_info) {
        this.order_info = order_info;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }
}
