package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdBorrowerRealrepay extends ProdBorrowerRealrepayKey implements Serializable {
    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 还款期别
     */
    private Integer repay_num;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 计划还款日期
     */
    private String repay_date;

    /**
     * 计划还款金额
     */
    private BigDecimal repay_amt;

    /**
     * 计划还款手续费
     */
    private BigDecimal repay_fee;

    /**
     * 实际还款金额
     */
    private BigDecimal real_repay_amt;

    /**
     * 实际还款手续费
     */
    private BigDecimal real_repay_fee;

    /**
     * 借款人编号
     */
    private String platcust;

    /**
     * 状态
     */
    private String status;

    /**
     * 交易日期
     */
    private String trans_date;

    /**
     * 交易时间
     */
    private String trans_time;

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
     * prod_borrower_realrepay
     */
    private static final long serialVersionUID = 1L;

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
     * 计划还款日期
     * @return repay_date 计划还款日期
     */
    public String getRepay_date() {
        return repay_date;
    }

    /**
     * 计划还款日期
     * @param repay_date 计划还款日期
     */
    public void setRepay_date(String repay_date) {
        this.repay_date = repay_date == null ? null : repay_date.trim();
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
     * 计划还款手续费
     * @return repay_fee 计划还款手续费
     */
    public BigDecimal getRepay_fee() {
        return repay_fee;
    }

    /**
     * 计划还款手续费
     * @param repay_fee 计划还款手续费
     */
    public void setRepay_fee(BigDecimal repay_fee) {
        this.repay_fee = repay_fee;
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
     * 实际还款手续费
     * @return real_repay_fee 实际还款手续费
     */
    public BigDecimal getReal_repay_fee() {
        return real_repay_fee;
    }

    /**
     * 实际还款手续费
     * @param real_repay_fee 实际还款手续费
     */
    public void setReal_repay_fee(BigDecimal real_repay_fee) {
        this.real_repay_fee = real_repay_fee;
    }

    /**
     * 借款人编号
     * @return platcust 借款人编号
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 借款人编号
     * @param platcust 借款人编号
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
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
        ProdBorrowerRealrepay other = (ProdBorrowerRealrepay) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBorrower_id() == null ? other.getBorrower_id() == null : this.getBorrower_id().equals(other.getBorrower_id()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getRepay_num() == null ? other.getRepay_num() == null : this.getRepay_num().equals(other.getRepay_num()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getRepay_date() == null ? other.getRepay_date() == null : this.getRepay_date().equals(other.getRepay_date()))
            && (this.getRepay_amt() == null ? other.getRepay_amt() == null : this.getRepay_amt().equals(other.getRepay_amt()))
            && (this.getRepay_fee() == null ? other.getRepay_fee() == null : this.getRepay_fee().equals(other.getRepay_fee()))
            && (this.getReal_repay_amt() == null ? other.getReal_repay_amt() == null : this.getReal_repay_amt().equals(other.getReal_repay_amt()))
            && (this.getReal_repay_fee() == null ? other.getReal_repay_fee() == null : this.getReal_repay_fee().equals(other.getReal_repay_fee()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
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
        result = prime * result + ((getBorrower_id() == null) ? 0 : getBorrower_id().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getRepay_num() == null) ? 0 : getRepay_num().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getRepay_date() == null) ? 0 : getRepay_date().hashCode());
        result = prime * result + ((getRepay_amt() == null) ? 0 : getRepay_amt().hashCode());
        result = prime * result + ((getRepay_fee() == null) ? 0 : getRepay_fee().hashCode());
        result = prime * result + ((getReal_repay_amt() == null) ? 0 : getReal_repay_amt().hashCode());
        result = prime * result + ((getReal_repay_fee() == null) ? 0 : getReal_repay_fee().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
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
        sb.append(", plat_no=").append(plat_no);
        sb.append(", repay_num=").append(repay_num);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", repay_date=").append(repay_date);
        sb.append(", repay_amt=").append(repay_amt);
        sb.append(", repay_fee=").append(repay_fee);
        sb.append(", real_repay_amt=").append(real_repay_amt);
        sb.append(", real_repay_fee=").append(real_repay_fee);
        sb.append(", platcust=").append(platcust);
        sb.append(", status=").append(status);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
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