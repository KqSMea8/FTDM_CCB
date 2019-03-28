package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.util.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by PengZY on 2017/11/2.
 */
public class BatchTransferToPersonNotifyData implements Serializable {

    private String order_no;
    private String recode;
    private String remsg;
    private String order_status;
    private String order_info;
    private String platcust;
    private BigDecimal amount;
    private String trans_date;

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRecode() {
        return recode;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public String getRemsg() {
        return remsg;
    }

    public void setRemsg(String remsg) {
        this.remsg = remsg;
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

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
