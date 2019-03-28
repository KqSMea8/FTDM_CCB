package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdRepaymentlist implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 
     */
    private String platcust;

    /**
     * 还款期别
     */
    private Integer repay_num;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 计划还款本金
     */
    private BigDecimal repay_amount;

    /**
     * 计划还款利息
     */
    private BigDecimal repay_val;

    /**
     * 计划还款金额
     */
    private BigDecimal repay_amt;

    /**
     * 计划还款日期
     */
    private Date repay_date;

    /**
     * 实际还款本金
     */
    private BigDecimal real_repay_amount;

    /**
     * 实际还款利息
     */
    private BigDecimal real_repay_val;

    /**
     * 实际还款金额
     */
    private BigDecimal real_repay_amt;

    /**
     * 实际还款日期
     */
    private Date real_repay_date;

    /**
     * 还款状态
     */
    private String status;

    /**
     * 是否垫付
     */
    private String if_advance;

    /**
     * 体验金
     */
    private BigDecimal real_experience_amt;

    /**
     * 加息金
     */
    private BigDecimal real_rates_amt;

    /**
     * 应扣手续费
     */
    private BigDecimal real_repay_fee;

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
     * prod_repaymentlist
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
     * 平台编号
     * @return plat_no 平台编号
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 平台编号
     * @param plat_no 平台编号
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
     * 还款期别
     * @return repay_num 还款期别
     */
    public Integer getRepay_num() {
        return repay_num;
    }

    /**
     * 还款期别
     * @param repay_num 还款期别
     */
    public void setRepay_num(Integer repay_num) {
        this.repay_num = repay_num;
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
     * 计划还款本金
     * @return repay_amount 计划还款本金
     */
    public BigDecimal getRepay_amount() {
        return repay_amount;
    }

    /**
     * 计划还款本金
     * @param repay_amount 计划还款本金
     */
    public void setRepay_amount(BigDecimal repay_amount) {
        this.repay_amount = repay_amount;
    }

    /**
     * 计划还款利息
     * @return repay_val 计划还款利息
     */
    public BigDecimal getRepay_val() {
        return repay_val;
    }

    /**
     * 计划还款利息
     * @param repay_val 计划还款利息
     */
    public void setRepay_val(BigDecimal repay_val) {
        this.repay_val = repay_val;
    }

    /**
     * 计划还款金额
     * @return repay_amt 计划还款金额
     */
    public BigDecimal getRepay_amt() {
        return repay_amt;
    }

    /**
     * 计划还款金额
     * @param repay_amt 计划还款金额
     */
    public void setRepay_amt(BigDecimal repay_amt) {
        this.repay_amt = repay_amt;
    }

    /**
     * 计划还款日期
     * @return repay_date 计划还款日期
     */
    public Date getRepay_date() {
        return repay_date;
    }

    /**
     * 计划还款日期
     * @param repay_date 计划还款日期
     */
    public void setRepay_date(Date repay_date) {
        this.repay_date = repay_date;
    }

    /**
     * 实际还款本金
     * @return real_repay_amount 实际还款本金
     */
    public BigDecimal getReal_repay_amount() {
        return real_repay_amount;
    }

    /**
     * 实际还款本金
     * @param real_repay_amount 实际还款本金
     */
    public void setReal_repay_amount(BigDecimal real_repay_amount) {
        this.real_repay_amount = real_repay_amount;
    }

    /**
     * 实际还款利息
     * @return real_repay_val 实际还款利息
     */
    public BigDecimal getReal_repay_val() {
        return real_repay_val;
    }

    /**
     * 实际还款利息
     * @param real_repay_val 实际还款利息
     */
    public void setReal_repay_val(BigDecimal real_repay_val) {
        this.real_repay_val = real_repay_val;
    }

    /**
     * 实际还款金额
     * @return real_repay_amt 实际还款金额
     */
    public BigDecimal getReal_repay_amt() {
        return real_repay_amt;
    }

    /**
     * 实际还款金额
     * @param real_repay_amt 实际还款金额
     */
    public void setReal_repay_amt(BigDecimal real_repay_amt) {
        this.real_repay_amt = real_repay_amt;
    }

    /**
     * 实际还款日期
     * @return real_repay_date 实际还款日期
     */
    public Date getReal_repay_date() {
        return real_repay_date;
    }

    /**
     * 实际还款日期
     * @param real_repay_date 实际还款日期
     */
    public void setReal_repay_date(Date real_repay_date) {
        this.real_repay_date = real_repay_date;
    }

    /**
     * 还款状态
     * @return status 还款状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 还款状态
     * @param status 还款状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 是否垫付
     * @return if_advance 是否垫付
     */
    public String getIf_advance() {
        return if_advance;
    }

    /**
     * 是否垫付
     * @param if_advance 是否垫付
     */
    public void setIf_advance(String if_advance) {
        this.if_advance = if_advance == null ? null : if_advance.trim();
    }

    /**
     * 体验金
     * @return real_experience_amt 体验金
     */
    public BigDecimal getReal_experience_amt() {
        return real_experience_amt;
    }

    /**
     * 体验金
     * @param real_experience_amt 体验金
     */
    public void setReal_experience_amt(BigDecimal real_experience_amt) {
        this.real_experience_amt = real_experience_amt;
    }

    /**
     * 加息金
     * @return real_rates_amt 加息金
     */
    public BigDecimal getReal_rates_amt() {
        return real_rates_amt;
    }

    /**
     * 加息金
     * @param real_rates_amt 加息金
     */
    public void setReal_rates_amt(BigDecimal real_rates_amt) {
        this.real_rates_amt = real_rates_amt;
    }

    /**
     * 应扣手续费
     * @return real_repay_fee 应扣手续费
     */
    public BigDecimal getReal_repay_fee() {
        return real_repay_fee;
    }

    /**
     * 应扣手续费
     * @param real_repay_fee 应扣手续费
     */
    public void setReal_repay_fee(BigDecimal real_repay_fee) {
        this.real_repay_fee = real_repay_fee;
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
        ProdRepaymentlist other = (ProdRepaymentlist) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getRepay_num() == null ? other.getRepay_num() == null : this.getRepay_num().equals(other.getRepay_num()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getRepay_amount() == null ? other.getRepay_amount() == null : this.getRepay_amount().equals(other.getRepay_amount()))
            && (this.getRepay_val() == null ? other.getRepay_val() == null : this.getRepay_val().equals(other.getRepay_val()))
            && (this.getRepay_amt() == null ? other.getRepay_amt() == null : this.getRepay_amt().equals(other.getRepay_amt()))
            && (this.getRepay_date() == null ? other.getRepay_date() == null : this.getRepay_date().equals(other.getRepay_date()))
            && (this.getReal_repay_amount() == null ? other.getReal_repay_amount() == null : this.getReal_repay_amount().equals(other.getReal_repay_amount()))
            && (this.getReal_repay_val() == null ? other.getReal_repay_val() == null : this.getReal_repay_val().equals(other.getReal_repay_val()))
            && (this.getReal_repay_amt() == null ? other.getReal_repay_amt() == null : this.getReal_repay_amt().equals(other.getReal_repay_amt()))
            && (this.getReal_repay_date() == null ? other.getReal_repay_date() == null : this.getReal_repay_date().equals(other.getReal_repay_date()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIf_advance() == null ? other.getIf_advance() == null : this.getIf_advance().equals(other.getIf_advance()))
            && (this.getReal_experience_amt() == null ? other.getReal_experience_amt() == null : this.getReal_experience_amt().equals(other.getReal_experience_amt()))
            && (this.getReal_rates_amt() == null ? other.getReal_rates_amt() == null : this.getReal_rates_amt().equals(other.getReal_rates_amt()))
            && (this.getReal_repay_fee() == null ? other.getReal_repay_fee() == null : this.getReal_repay_fee().equals(other.getReal_repay_fee()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()));
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
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getRepay_num() == null) ? 0 : getRepay_num().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getRepay_amount() == null) ? 0 : getRepay_amount().hashCode());
        result = prime * result + ((getRepay_val() == null) ? 0 : getRepay_val().hashCode());
        result = prime * result + ((getRepay_amt() == null) ? 0 : getRepay_amt().hashCode());
        result = prime * result + ((getRepay_date() == null) ? 0 : getRepay_date().hashCode());
        result = prime * result + ((getReal_repay_amount() == null) ? 0 : getReal_repay_amount().hashCode());
        result = prime * result + ((getReal_repay_val() == null) ? 0 : getReal_repay_val().hashCode());
        result = prime * result + ((getReal_repay_amt() == null) ? 0 : getReal_repay_amt().hashCode());
        result = prime * result + ((getReal_repay_date() == null) ? 0 : getReal_repay_date().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIf_advance() == null) ? 0 : getIf_advance().hashCode());
        result = prime * result + ((getReal_experience_amt() == null) ? 0 : getReal_experience_amt().hashCode());
        result = prime * result + ((getReal_rates_amt() == null) ? 0 : getReal_rates_amt().hashCode());
        result = prime * result + ((getReal_repay_fee() == null) ? 0 : getReal_repay_fee().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
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
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", repay_num=").append(repay_num);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", repay_amount=").append(repay_amount);
        sb.append(", repay_val=").append(repay_val);
        sb.append(", repay_amt=").append(repay_amt);
        sb.append(", repay_date=").append(repay_date);
        sb.append(", real_repay_amount=").append(real_repay_amount);
        sb.append(", real_repay_val=").append(real_repay_val);
        sb.append(", real_repay_amt=").append(real_repay_amt);
        sb.append(", real_repay_date=").append(real_repay_date);
        sb.append(", status=").append(status);
        sb.append(", if_advance=").append(if_advance);
        sb.append(", real_experience_amt=").append(real_experience_amt);
        sb.append(", real_rates_amt=").append(real_rates_amt);
        sb.append(", real_repay_fee=").append(real_repay_fee);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}