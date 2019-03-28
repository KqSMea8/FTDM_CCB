package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClearAccountError implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String plat_no;

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
     * sql异常次数
     */
    private Integer error_fail_times;

    /**
     * 并发异常次数
     */
    private Integer concurrence_fail_times;

    /**
     * 失败类型
     */
    private String fail_type;

    /**
     * 清算时间
     */
    private String clear_date;

    /**
     * 渠道
     */
    private String pay_code;

    /**
     * 可用
     */
    private BigDecimal n_balance;

    /**
     * 冻结
     */
    private BigDecimal f_balance;

    /**
     * 
     */
    private BigDecimal last_n_balance;

    /**
     * 
     */
    private BigDecimal last_f_balance;

    /**
     * 暂时无用
     */
    private String status;

    /**
     * 保留
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
     * clear_account_error
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
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
     * sql异常次数
     * @return error_fail_times sql异常次数
     */
    public Integer getError_fail_times() {
        return error_fail_times;
    }

    /**
     * sql异常次数
     * @param error_fail_times sql异常次数
     */
    public void setError_fail_times(Integer error_fail_times) {
        this.error_fail_times = error_fail_times;
    }

    /**
     * 并发异常次数
     * @return concurrence_fail_times 并发异常次数
     */
    public Integer getConcurrence_fail_times() {
        return concurrence_fail_times;
    }

    /**
     * 并发异常次数
     * @param concurrence_fail_times 并发异常次数
     */
    public void setConcurrence_fail_times(Integer concurrence_fail_times) {
        this.concurrence_fail_times = concurrence_fail_times;
    }

    /**
     * 失败类型
     * @return fail_type 失败类型
     */
    public String getFail_type() {
        return fail_type;
    }

    /**
     * 失败类型
     * @param fail_type 失败类型
     */
    public void setFail_type(String fail_type) {
        this.fail_type = fail_type == null ? null : fail_type.trim();
    }

    /**
     * 清算时间
     * @return clear_date 清算时间
     */
    public String getClear_date() {
        return clear_date;
    }

    /**
     * 清算时间
     * @param clear_date 清算时间
     */
    public void setClear_date(String clear_date) {
        this.clear_date = clear_date == null ? null : clear_date.trim();
    }

    /**
     * 渠道
     * @return pay_code 渠道
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 渠道
     * @param pay_code 渠道
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
    }

    /**
     * 可用
     * @return n_balance 可用
     */
    public BigDecimal getN_balance() {
        return n_balance;
    }

    /**
     * 可用
     * @param n_balance 可用
     */
    public void setN_balance(BigDecimal n_balance) {
        this.n_balance = n_balance;
    }

    /**
     * 冻结
     * @return f_balance 冻结
     */
    public BigDecimal getF_balance() {
        return f_balance;
    }

    /**
     * 冻结
     * @param f_balance 冻结
     */
    public void setF_balance(BigDecimal f_balance) {
        this.f_balance = f_balance;
    }

    /**
     * 
     * @return last_n_balance 
     */
    public BigDecimal getLast_n_balance() {
        return last_n_balance;
    }

    /**
     * 
     * @param last_n_balance 
     */
    public void setLast_n_balance(BigDecimal last_n_balance) {
        this.last_n_balance = last_n_balance;
    }

    /**
     * 
     * @return last_f_balance 
     */
    public BigDecimal getLast_f_balance() {
        return last_f_balance;
    }

    /**
     * 
     * @param last_f_balance 
     */
    public void setLast_f_balance(BigDecimal last_f_balance) {
        this.last_f_balance = last_f_balance;
    }

    /**
     * 暂时无用
     * @return status 暂时无用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 暂时无用
     * @param status 暂时无用
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 保留
     * @return remark 保留
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 保留
     * @param remark 保留
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
     * @mbggenerated 2017-11-01
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
        ClearAccountError other = (ClearAccountError) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getSub_subject() == null ? other.getSub_subject() == null : this.getSub_subject().equals(other.getSub_subject()))
            && (this.getError_fail_times() == null ? other.getError_fail_times() == null : this.getError_fail_times().equals(other.getError_fail_times()))
            && (this.getConcurrence_fail_times() == null ? other.getConcurrence_fail_times() == null : this.getConcurrence_fail_times().equals(other.getConcurrence_fail_times()))
            && (this.getFail_type() == null ? other.getFail_type() == null : this.getFail_type().equals(other.getFail_type()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getN_balance() == null ? other.getN_balance() == null : this.getN_balance().equals(other.getN_balance()))
            && (this.getF_balance() == null ? other.getF_balance() == null : this.getF_balance().equals(other.getF_balance()))
            && (this.getLast_n_balance() == null ? other.getLast_n_balance() == null : this.getLast_n_balance().equals(other.getLast_n_balance()))
            && (this.getLast_f_balance() == null ? other.getLast_f_balance() == null : this.getLast_f_balance().equals(other.getLast_f_balance()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2017-11-01
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getSub_subject() == null) ? 0 : getSub_subject().hashCode());
        result = prime * result + ((getError_fail_times() == null) ? 0 : getError_fail_times().hashCode());
        result = prime * result + ((getConcurrence_fail_times() == null) ? 0 : getConcurrence_fail_times().hashCode());
        result = prime * result + ((getFail_type() == null) ? 0 : getFail_type().hashCode());
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getN_balance() == null) ? 0 : getN_balance().hashCode());
        result = prime * result + ((getF_balance() == null) ? 0 : getF_balance().hashCode());
        result = prime * result + ((getLast_n_balance() == null) ? 0 : getLast_n_balance().hashCode());
        result = prime * result + ((getLast_f_balance() == null) ? 0 : getLast_f_balance().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-11-01
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", subject=").append(subject);
        sb.append(", sub_subject=").append(sub_subject);
        sb.append(", error_fail_times=").append(error_fail_times);
        sb.append(", concurrence_fail_times=").append(concurrence_fail_times);
        sb.append(", fail_type=").append(fail_type);
        sb.append(", clear_date=").append(clear_date);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", n_balance=").append(n_balance);
        sb.append(", f_balance=").append(f_balance);
        sb.append(", last_n_balance=").append(last_n_balance);
        sb.append(", last_f_balance=").append(last_f_balance);
        sb.append(", status=").append(status);
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