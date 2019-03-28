package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PlatTransCust implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String mall_no;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String order_no;

    /**
     * 
     */
    private String trans_serial;

    /**
     * 
     */
    private String platcust;

    /**
     * 
     */
    private String subject;

    /**
     * 
     */
    private String sub_subject;

    /**
     * 
     */
    private String oppo_subject;

    /**
     * 
     */
    private String oppo_sub_subject;

    /**
     * 转让金额
     */
    private BigDecimal total_amt;

    /**
     * 转让剩余金额
     */
    private BigDecimal balance;

    /**
     * 
     */
    private String trans_date;

    /**
     * 
     */
    private String trans_time;

    /**
     * 
     */
    private String cause_type;

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
     * plat_trans_cust
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
     * 
     * @return mall_no 
     */
    public String getMall_no() {
        return mall_no;
    }

    /**
     * 
     * @param mall_no 
     */
    public void setMall_no(String mall_no) {
        this.mall_no = mall_no == null ? null : mall_no.trim();
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
     * @return order_no 
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 
     * @param order_no 
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 
     * @return trans_serial 
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 
     * @param trans_serial 
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
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
     * 
     * @return subject 
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject 
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * 
     * @return sub_subject 
     */
    public String getSub_subject() {
        return sub_subject;
    }

    /**
     * 
     * @param sub_subject 
     */
    public void setSub_subject(String sub_subject) {
        this.sub_subject = sub_subject == null ? null : sub_subject.trim();
    }

    /**
     * 
     * @return oppo_subject 
     */
    public String getOppo_subject() {
        return oppo_subject;
    }

    /**
     * 
     * @param oppo_subject 
     */
    public void setOppo_subject(String oppo_subject) {
        this.oppo_subject = oppo_subject == null ? null : oppo_subject.trim();
    }

    /**
     * 
     * @return oppo_sub_subject 
     */
    public String getOppo_sub_subject() {
        return oppo_sub_subject;
    }

    /**
     * 
     * @param oppo_sub_subject 
     */
    public void setOppo_sub_subject(String oppo_sub_subject) {
        this.oppo_sub_subject = oppo_sub_subject == null ? null : oppo_sub_subject.trim();
    }

    /**
     * 转让金额
     * @return total_amt 转让金额
     */
    public BigDecimal getTotal_amt() {
        return total_amt;
    }

    /**
     * 转让金额
     * @param total_amt 转让金额
     */
    public void setTotal_amt(BigDecimal total_amt) {
        this.total_amt = total_amt;
    }

    /**
     * 转让剩余金额
     * @return balance 转让剩余金额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 转让剩余金额
     * @param balance 转让剩余金额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 
     * @return trans_date 
     */
    public String getTrans_date() {
        return trans_date;
    }

    /**
     * 
     * @param trans_date 
     */
    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date == null ? null : trans_date.trim();
    }

    /**
     * 
     * @return trans_time 
     */
    public String getTrans_time() {
        return trans_time;
    }

    /**
     * 
     * @param trans_time 
     */
    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time == null ? null : trans_time.trim();
    }

    /**
     * 
     * @return cause_type 
     */
    public String getCause_type() {
        return cause_type;
    }

    /**
     * 
     * @param cause_type 
     */
    public void setCause_type(String cause_type) {
        this.cause_type = cause_type == null ? null : cause_type.trim();
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
     *
     * @mbggenerated 2018-01-09
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
        PlatTransCust other = (PlatTransCust) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getSub_subject() == null ? other.getSub_subject() == null : this.getSub_subject().equals(other.getSub_subject()))
            && (this.getOppo_subject() == null ? other.getOppo_subject() == null : this.getOppo_subject().equals(other.getOppo_subject()))
            && (this.getOppo_sub_subject() == null ? other.getOppo_sub_subject() == null : this.getOppo_sub_subject().equals(other.getOppo_sub_subject()))
            && (this.getTotal_amt() == null ? other.getTotal_amt() == null : this.getTotal_amt().equals(other.getTotal_amt()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getCause_type() == null ? other.getCause_type() == null : this.getCause_type().equals(other.getCause_type()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2018-01-09
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getSub_subject() == null) ? 0 : getSub_subject().hashCode());
        result = prime * result + ((getOppo_subject() == null) ? 0 : getOppo_subject().hashCode());
        result = prime * result + ((getOppo_sub_subject() == null) ? 0 : getOppo_sub_subject().hashCode());
        result = prime * result + ((getTotal_amt() == null) ? 0 : getTotal_amt().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getCause_type() == null) ? 0 : getCause_type().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-09
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mall_no=").append(mall_no);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", order_no=").append(order_no);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", platcust=").append(platcust);
        sb.append(", subject=").append(subject);
        sb.append(", sub_subject=").append(sub_subject);
        sb.append(", oppo_subject=").append(oppo_subject);
        sb.append(", oppo_sub_subject=").append(oppo_sub_subject);
        sb.append(", total_amt=").append(total_amt);
        sb.append(", balance=").append(balance);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", cause_type=").append(cause_type);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}