package com.sunyard.sunfintech.account.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class FundChangeResponseDetail implements Serializable {
    //平台编号
    private String plat_no;

    //平台客户编号
    private String platcust;

    //交易时间
    private String trans_time;

    //交易日期
    private String trans_date;

    //交易金额
    private String amt;

    //资金变动类型
    private String amt_type;

    private String trans_name;

    private String oppo_platcust;

    private String remark;

    private String order_no;

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public String getOppo_platcust() {
        return oppo_platcust;
    }

    public void setOppo_platcust(String oppo_platcust) {
        this.oppo_platcust = oppo_platcust;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getAmt_type() {
        return amt_type;
    }

    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type;
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
