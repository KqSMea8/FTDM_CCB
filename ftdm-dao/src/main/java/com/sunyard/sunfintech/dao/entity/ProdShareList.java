package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdShareList implements Serializable {
    /**
     * 
     */
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

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 份额id
     * @return trans_serial 份额id
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 份额id
     * @param trans_serial 份额id
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
    }

    /**
     * 
     * @return trans_code 
     */
    public String getTrans_code() {
        return trans_code;
    }

    /**
     * 
     * @param trans_code 
     */
    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code == null ? null : trans_code.trim();
    }

    /**
     * 
     * @return plat_no 
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 
     * @param plat_no 
     */
    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no == null ? null : plat_no.trim();
    }

    /**
     * 
     * @return platcust 
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 
     * @param platcust 
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 产品编号
     * @return prod_id 产品编号
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * 产品编号
     * @param prod_id 产品编号
     */
    public void setProd_id(String prod_id) {
        this.prod_id = prod_id == null ? null : prod_id.trim();
    }

    /**
     * 交易日期
     * @return trans_date 交易日期
     */
    public String getTrans_date() {
        return trans_date;
    }

    /**
     * 交易日期
     * @param trans_date 交易日期
     */
    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date == null ? null : trans_date.trim();
    }

    /**
     * 交易时间
     * @return trans_time 交易时间
     */
    public String getTrans_time() {
        return trans_time;
    }

    /**
     * 交易时间
     * @param trans_time 交易时间
     */
    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time == null ? null : trans_time.trim();
    }

    /**
     * 份额
     * @return vol 份额
     */
    public BigDecimal getVol() {
        return vol;
    }

    /**
     * 份额
     * @param vol 份额
     */
    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

    /**
     * 类型(0-增加 1-减少)
     * @return amt_type 类型(0-增加 1-减少)
     */
    public String getAmt_type() {
        return amt_type;
    }

    /**
     * 类型(0-增加 1-减少)
     * @param amt_type 类型(0-增加 1-减少)
     */
    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type == null ? null : amt_type.trim();
    }

    /**
     * 利息
     * @return in_interest 利息
     */
    public BigDecimal getIn_interest() {
        return in_interest;
    }

    /**
     * 利息
     * @param in_interest 利息
     */
    public void setIn_interest(BigDecimal in_interest) {
        this.in_interest = in_interest;
    }

    /**
     * 状态
     * @return status 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * @return enabled 
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled 
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    /**
     * 
     * @return remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 
     * @return create_by 
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 
     * @param create_by 
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_by 
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 
     * @param update_by 
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 扩展字段1
     * @return ext1 扩展字段1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 扩展字段1
     * @param ext1 扩展字段1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 扩展字段2
     * @return ext2 扩展字段2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 扩展字段2
     * @param ext2 扩展字段2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * 自费金额
     * @return self_amt 自费金额
     */
    public BigDecimal getSelf_amt() {
        return self_amt;
    }

    /**
     * 自费金额
     * @param self_amt 自费金额
     */
    public void setSelf_amt(BigDecimal self_amt) {
        this.self_amt = self_amt;
    }

    /**
     * 抵用券金额
     * @return coupon_amt 抵用券金额
     */
    public BigDecimal getCoupon_amt() {
        return coupon_amt;
    }

    /**
     * 抵用券金额
     * @param coupon_amt 抵用券金额
     */
    public void setCoupon_amt(BigDecimal coupon_amt) {
        this.coupon_amt = coupon_amt;
    }

    /**
     * 体验金金额
     * @return experience_amt 体验金金额
     */
    public BigDecimal getExperience_amt() {
        return experience_amt;
    }

    /**
     * 体验金金额
     * @param experience_amt 体验金金额
     */
    public void setExperience_amt(BigDecimal experience_amt) {
        this.experience_amt = experience_amt;
    }

    /**
     * 手续费金额
     * @return payout_amt 手续费金额
     */
    public BigDecimal getPayout_amt() {
        return payout_amt;
    }

    /**
     * 手续费金额
     * @param payout_amt 手续费金额
     */
    public void setPayout_amt(BigDecimal payout_amt) {
        this.payout_amt = payout_amt;
    }

    /**
     * 投资优先级
     * @return self_priority 投资优先级
     */
    public String getSelf_priority() {
        return self_priority;
    }

    /**
     * 投资优先级
     * @param self_priority 投资优先级
     */
    public void setSelf_priority(String self_priority) {
        this.self_priority = self_priority == null ? null : self_priority.trim();
    }

    /**
     * 
     * @return fee_priority 
     */
    public String getFee_priority() {
        return fee_priority;
    }

    /**
     * 
     * @param fee_priority 
     */
    public void setFee_priority(String fee_priority) {
        this.fee_priority = fee_priority == null ? null : fee_priority.trim();
    }

    /**
     * 加息券
     * @return interest 加息券
     */
    public BigDecimal getInterest() {
        return interest;
    }

    /**
     * 加息券
     * @param interest 加息券
     */
    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProdShareList other = (ProdShareList) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getVol() == null ? other.getVol() == null : this.getVol().equals(other.getVol()))
            && (this.getAmt_type() == null ? other.getAmt_type() == null : this.getAmt_type().equals(other.getAmt_type()))
            && (this.getIn_interest() == null ? other.getIn_interest() == null : this.getIn_interest().equals(other.getIn_interest()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getSelf_amt() == null ? other.getSelf_amt() == null : this.getSelf_amt().equals(other.getSelf_amt()))
            && (this.getCoupon_amt() == null ? other.getCoupon_amt() == null : this.getCoupon_amt().equals(other.getCoupon_amt()))
            && (this.getExperience_amt() == null ? other.getExperience_amt() == null : this.getExperience_amt().equals(other.getExperience_amt()))
            && (this.getPayout_amt() == null ? other.getPayout_amt() == null : this.getPayout_amt().equals(other.getPayout_amt()))
            && (this.getSelf_priority() == null ? other.getSelf_priority() == null : this.getSelf_priority().equals(other.getSelf_priority()))
            && (this.getFee_priority() == null ? other.getFee_priority() == null : this.getFee_priority().equals(other.getFee_priority()))
            && (this.getInterest() == null ? other.getInterest() == null : this.getInterest().equals(other.getInterest()));
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getVol() == null) ? 0 : getVol().hashCode());
        result = prime * result + ((getAmt_type() == null) ? 0 : getAmt_type().hashCode());
        result = prime * result + ((getIn_interest() == null) ? 0 : getIn_interest().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getSelf_amt() == null) ? 0 : getSelf_amt().hashCode());
        result = prime * result + ((getCoupon_amt() == null) ? 0 : getCoupon_amt().hashCode());
        result = prime * result + ((getExperience_amt() == null) ? 0 : getExperience_amt().hashCode());
        result = prime * result + ((getPayout_amt() == null) ? 0 : getPayout_amt().hashCode());
        result = prime * result + ((getSelf_priority() == null) ? 0 : getSelf_priority().hashCode());
        result = prime * result + ((getFee_priority() == null) ? 0 : getFee_priority().hashCode());
        result = prime * result + ((getInterest() == null) ? 0 : getInterest().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", vol=").append(vol);
        sb.append(", amt_type=").append(amt_type);
        sb.append(", in_interest=").append(in_interest);
        sb.append(", status=").append(status);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", self_amt=").append(self_amt);
        sb.append(", coupon_amt=").append(coupon_amt);
        sb.append(", experience_amt=").append(experience_amt);
        sb.append(", payout_amt=").append(payout_amt);
        sb.append(", self_priority=").append(self_priority);
        sb.append(", fee_priority=").append(fee_priority);
        sb.append(", interest=").append(interest);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}