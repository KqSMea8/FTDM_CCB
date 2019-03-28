package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EaccFinancinfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 融资账户
     */
    private String platcust;

    /**
     * 申请日期
     */
    private String reg_date;

    /**
     * 申请时间
     */
    private String reg_time;

    /**
     * 融资金额
     */
    private BigDecimal financ_amt;

    /**
     * 周期单位
     */
    private String cycle_unit;

    /**
     * 周期
     */
    private Integer cycle;

    /**
     * 融资利率
     */
    private BigDecimal financ_int;

    /**
     * 还款方式
     */
    private String repay_type;

    /**
     * 用款日期
     */
    private String use_date;

    /**
     * financ_purpose
     */
    private String financ_purpose;

    /**
     * 银行编号
     */
    private String bank_code;

    /**
     * 收款行
     */
    private String open_branch;

    /**
     * 收款账户
     */
    private String withdraw_account;

    /**
     * 状态(废)
     */
    private String status;

    /**
     * 收款户名
     */
    private String payee_name;

    /**
     * 账户类型
     */
    private String account_type;

    /**
     * 融资手续费
     */
    private BigDecimal fee_int;

    /**
     * 删除标记
     */
    private String enabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建用户
     */
    private String create_by;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新用户
     */
    private String update_by;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     *
     */
    private String trustee_platcust;

    /**
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * eacc_financinfo
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
     * 融资账户
     * @return platcust 融资账户
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 融资账户
     * @param platcust 融资账户
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 申请日期
     * @return reg_date 申请日期
     */
    public String getReg_date() {
        return reg_date;
    }

    /**
     * 申请日期
     * @param reg_date 申请日期
     */
    public void setReg_date(String reg_date) {
        this.reg_date = reg_date == null ? null : reg_date.trim();
    }

    /**
     * 申请时间
     * @return reg_time 申请时间
     */
    public String getReg_time() {
        return reg_time;
    }

    /**
     * 申请时间
     * @param reg_time 申请时间
     */
    public void setReg_time(String reg_time) {
        this.reg_time = reg_time == null ? null : reg_time.trim();
    }

    /**
     * 融资金额
     * @return financ_amt 融资金额
     */
    public BigDecimal getFinanc_amt() {
        return financ_amt;
    }

    /**
     * 融资金额
     * @param financ_amt 融资金额
     */
    public void setFinanc_amt(BigDecimal financ_amt) {
        this.financ_amt = financ_amt;
    }

    /**
     * 周期单位
     * @return cycle_unit 周期单位
     */
    public String getCycle_unit() {
        return cycle_unit;
    }

    /**
     * 周期单位
     * @param cycle_unit 周期单位
     */
    public void setCycle_unit(String cycle_unit) {
        this.cycle_unit = cycle_unit == null ? null : cycle_unit.trim();
    }

    /**
     * 周期
     * @return cycle 周期
     */
    public Integer getCycle() {
        return cycle;
    }

    /**
     * 周期
     * @param cycle 周期
     */
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    /**
     * 融资利率
     * @return financ_int 融资利率
     */
    public BigDecimal getFinanc_int() {
        return financ_int;
    }

    /**
     * 融资利率
     * @param financ_int 融资利率
     */
    public void setFinanc_int(BigDecimal financ_int) {
        this.financ_int = financ_int;
    }

    /**
     * 还款方式
     * @return repay_type 还款方式
     */
    public String getRepay_type() {
        return repay_type;
    }

    /**
     * 还款方式
     * @param repay_type 还款方式
     */
    public void setRepay_type(String repay_type) {
        this.repay_type = repay_type == null ? null : repay_type.trim();
    }

    /**
     * 用款日期
     * @return use_date 用款日期
     */
    public String getUse_date() {
        return use_date;
    }

    /**
     * 用款日期
     * @param use_date 用款日期
     */
    public void setUse_date(String use_date) {
        this.use_date = use_date == null ? null : use_date.trim();
    }

    /**
     * financ_purpose
     * @return financ_purpose financ_purpose
     */
    public String getFinanc_purpose() {
        return financ_purpose;
    }

    /**
     * financ_purpose
     * @param financ_purpose financ_purpose
     */
    public void setFinanc_purpose(String financ_purpose) {
        this.financ_purpose = financ_purpose == null ? null : financ_purpose.trim();
    }

    /**
     * 银行编号
     * @return bank_code 银行编号
     */
    public String getBank_code() {
        return bank_code;
    }

    /**
     * 银行编号
     * @param bank_code 银行编号
     */
    public void setBank_code(String bank_code) {
        this.bank_code = bank_code == null ? null : bank_code.trim();
    }

    /**
     * 收款行
     * @return open_branch 收款行
     */
    public String getOpen_branch() {
        return open_branch;
    }

    /**
     * 收款行
     * @param open_branch 收款行
     */
    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch == null ? null : open_branch.trim();
    }

    /**
     * 收款账户
     * @return withdraw_account 收款账户
     */
    public String getWithdraw_account() {
        return withdraw_account;
    }

    /**
     * 收款账户
     * @param withdraw_account 收款账户
     */
    public void setWithdraw_account(String withdraw_account) {
        this.withdraw_account = withdraw_account == null ? null : withdraw_account.trim();
    }

    /**
     * 状态(废)
     * @return status 状态(废)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态(废)
     * @param status 状态(废)
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 收款户名
     * @return payee_name 收款户名
     */
    public String getPayee_name() {
        return payee_name;
    }

    /**
     * 收款户名
     * @param payee_name 收款户名
     */
    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name == null ? null : payee_name.trim();
    }

    /**
     * 账户类型
     * @return account_type 账户类型
     */
    public String getAccount_type() {
        return account_type;
    }

    /**
     * 账户类型
     * @param account_type 账户类型
     */
    public void setAccount_type(String account_type) {
        this.account_type = account_type == null ? null : account_type.trim();
    }

    /**
     * 融资手续费
     * @return fee_int 融资手续费
     */
    public BigDecimal getFee_int() {
        return fee_int;
    }

    /**
     * 融资手续费
     * @param fee_int 融资手续费
     */
    public void setFee_int(BigDecimal fee_int) {
        this.fee_int = fee_int;
    }

    /**
     * 删除标记
     * @return enabled 删除标记
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 删除标记
     * @param enabled 删除标记
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 创建用户
     * @return create_by 创建用户
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 创建用户
     * @param create_by 创建用户
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间
     * @param create_time 创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 更新用户
     * @return update_by 更新用户
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 更新用户
     * @param update_by 更新用户
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     *
     * @return trustee_platcust
     */
    public String getTrustee_platcust() {
        return trustee_platcust;
    }

    /**
     *
     * @param trustee_platcust
     */
    public void setTrustee_platcust(String trustee_platcust) {
        this.trustee_platcust = trustee_platcust == null ? null : trustee_platcust.trim();
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
     * @mbggenerated 2017-09-15
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
        EaccFinancinfo other = (EaccFinancinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getReg_date() == null ? other.getReg_date() == null : this.getReg_date().equals(other.getReg_date()))
            && (this.getReg_time() == null ? other.getReg_time() == null : this.getReg_time().equals(other.getReg_time()))
            && (this.getFinanc_amt() == null ? other.getFinanc_amt() == null : this.getFinanc_amt().equals(other.getFinanc_amt()))
            && (this.getCycle_unit() == null ? other.getCycle_unit() == null : this.getCycle_unit().equals(other.getCycle_unit()))
            && (this.getCycle() == null ? other.getCycle() == null : this.getCycle().equals(other.getCycle()))
            && (this.getFinanc_int() == null ? other.getFinanc_int() == null : this.getFinanc_int().equals(other.getFinanc_int()))
            && (this.getRepay_type() == null ? other.getRepay_type() == null : this.getRepay_type().equals(other.getRepay_type()))
            && (this.getUse_date() == null ? other.getUse_date() == null : this.getUse_date().equals(other.getUse_date()))
            && (this.getFinanc_purpose() == null ? other.getFinanc_purpose() == null : this.getFinanc_purpose().equals(other.getFinanc_purpose()))
            && (this.getBank_code() == null ? other.getBank_code() == null : this.getBank_code().equals(other.getBank_code()))
            && (this.getOpen_branch() == null ? other.getOpen_branch() == null : this.getOpen_branch().equals(other.getOpen_branch()))
            && (this.getWithdraw_account() == null ? other.getWithdraw_account() == null : this.getWithdraw_account().equals(other.getWithdraw_account()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPayee_name() == null ? other.getPayee_name() == null : this.getPayee_name().equals(other.getPayee_name()))
            && (this.getAccount_type() == null ? other.getAccount_type() == null : this.getAccount_type().equals(other.getAccount_type()))
            && (this.getFee_int() == null ? other.getFee_int() == null : this.getFee_int().equals(other.getFee_int()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getTrustee_platcust() == null ? other.getTrustee_platcust() == null : this.getTrustee_platcust().equals(other.getTrustee_platcust()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()));
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getReg_date() == null) ? 0 : getReg_date().hashCode());
        result = prime * result + ((getReg_time() == null) ? 0 : getReg_time().hashCode());
        result = prime * result + ((getFinanc_amt() == null) ? 0 : getFinanc_amt().hashCode());
        result = prime * result + ((getCycle_unit() == null) ? 0 : getCycle_unit().hashCode());
        result = prime * result + ((getCycle() == null) ? 0 : getCycle().hashCode());
        result = prime * result + ((getFinanc_int() == null) ? 0 : getFinanc_int().hashCode());
        result = prime * result + ((getRepay_type() == null) ? 0 : getRepay_type().hashCode());
        result = prime * result + ((getUse_date() == null) ? 0 : getUse_date().hashCode());
        result = prime * result + ((getFinanc_purpose() == null) ? 0 : getFinanc_purpose().hashCode());
        result = prime * result + ((getBank_code() == null) ? 0 : getBank_code().hashCode());
        result = prime * result + ((getOpen_branch() == null) ? 0 : getOpen_branch().hashCode());
        result = prime * result + ((getWithdraw_account() == null) ? 0 : getWithdraw_account().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPayee_name() == null) ? 0 : getPayee_name().hashCode());
        result = prime * result + ((getAccount_type() == null) ? 0 : getAccount_type().hashCode());
        result = prime * result + ((getFee_int() == null) ? 0 : getFee_int().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getTrustee_platcust() == null) ? 0 : getTrustee_platcust().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", platcust=").append(platcust);
        sb.append(", reg_date=").append(reg_date);
        sb.append(", reg_time=").append(reg_time);
        sb.append(", financ_amt=").append(financ_amt);
        sb.append(", cycle_unit=").append(cycle_unit);
        sb.append(", cycle=").append(cycle);
        sb.append(", financ_int=").append(financ_int);
        sb.append(", repay_type=").append(repay_type);
        sb.append(", use_date=").append(use_date);
        sb.append(", financ_purpose=").append(financ_purpose);
        sb.append(", bank_code=").append(bank_code);
        sb.append(", open_branch=").append(open_branch);
        sb.append(", withdraw_account=").append(withdraw_account);
        sb.append(", status=").append(status);
        sb.append(", payee_name=").append(payee_name);
        sb.append(", account_type=").append(account_type);
        sb.append(", fee_int=").append(fee_int);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", trustee_platcust=").append(trustee_platcust);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}