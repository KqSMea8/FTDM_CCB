package com.sunyard.sunfintech.account.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.swing.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by my on 2018/5/3.
 */
public class EaccountProdResponseTo implements Serializable {

    private Integer id;

    /**
     * 份额id
     */
    private String trans_serial;

    /**
     *
     */
    private String trans_code;

    /**
     *
     */
    private String plat_no;

    /**
     *
     */
    private String platcust;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 交易日期
     */
    private String trans_date;

    /**
     * 交易时间
     */
    private String trans_time;

    /**
     * 份额
     */
    private BigDecimal vol;

    /**
     * 类型(0-增加 1-减少)
     */
    private String amt_type;

    /**
     * 利息
     */
    private BigDecimal in_interest;

    /**
     * 状态
     */
    private String status;

    /**
     *
     */
    private String enabled;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    private String create_by;

    /**
     *
     */
    private Date create_time;

    /**
     *
     */
    private String update_by;

    /**
     *
     */
    private Date update_time;

    /**
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * 自费金额
     */
    private BigDecimal self_amt;

    /**
     * 抵用券金额
     */
    private BigDecimal coupon_amt;

    /**
     * 体验金金额
     */
    private BigDecimal experience_amt;

    /**
     * 手续费金额
     */
    private BigDecimal payout_amt;

    /**
     * 投资优先级
     */
    private String self_priority;

    /**
     *
     */
    private String fee_priority;

    /**
     * 加息券
     */
    private BigDecimal interest;

    /**
     * prod_share_list
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrans_serial() {
        return trans_serial;
    }

    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
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

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public BigDecimal getVol() {
        return vol;
    }

    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

    public String getAmt_type() {
        return amt_type;
    }

    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type;
    }

    public BigDecimal getIn_interest() {
        return in_interest;
    }

    public void setIn_interest(BigDecimal in_interest) {
        this.in_interest = in_interest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public BigDecimal getSelf_amt() {
        return self_amt;
    }

    public void setSelf_amt(BigDecimal self_amt) {
        this.self_amt = self_amt;
    }

    public BigDecimal getCoupon_amt() {
        return coupon_amt;
    }

    public void setCoupon_amt(BigDecimal coupon_amt) {
        this.coupon_amt = coupon_amt;
    }

    public BigDecimal getExperience_amt() {
        return experience_amt;
    }

    public void setExperience_amt(BigDecimal experience_amt) {
        this.experience_amt = experience_amt;
    }

    public BigDecimal getPayout_amt() {
        return payout_amt;
    }

    public void setPayout_amt(BigDecimal payout_amt) {
        this.payout_amt = payout_amt;
    }

    public String getSelf_priority() {
        return self_priority;
    }

    public void setSelf_priority(String self_priority) {
        this.self_priority = self_priority;
    }

    public String getFee_priority() {
        return fee_priority;
    }

    public void setFee_priority(String fee_priority) {
        this.fee_priority = fee_priority;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
