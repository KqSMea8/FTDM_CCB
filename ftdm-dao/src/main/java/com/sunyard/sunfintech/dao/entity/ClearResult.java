package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClearResult implements Serializable {
    /**
     * 
     */
    private Integer pid;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String pay_code;

    /**
     * 
     */
    private Date clear_date;

    /**
     * 
     */
    private BigDecimal recharge_sum_inside;

    /**
     * 
     */
    private BigDecimal recharge_sum_outside;

    /**
     * 
     */
    private Integer recharge_count_inside;

    /**
     * 
     */
    private Integer recharge_count_outside;

    /**
     * 
     */
    private BigDecimal withdrawals_sum_inside;

    /**
     * 
     */
    private BigDecimal withdrawals_sum_outside;

    /**
     * 
     */
    private Integer withdrawals_count_inside;

    /**
     * 
     */
    private Integer withdrawals_count_outside;

    /**
     * 
     */
    private Integer withdrawals_fail_count;

    /**
     * 
     */
    private BigDecimal withdrawals_fail_sum;

    /**
     * 
     */
    private Integer withdrawals_wait_count;

    /**
     * 
     */
    private BigDecimal withdrawals_wait_sum;

    /**
     * 0未对1已对2异常
     */
    private String clear_status;

    /**
     * 
     */
    private String clear_recordtime;

    /**
     * 0未清1已清
     */
    private String liquidation;

    /**
     * 
     */
    private String liquidation_recordtime;

    /**
     * 
     */
    private BigDecimal wait_clear_sum;

    /**
     * 结算金额（行内充值汇总）
     */
    private BigDecimal clear_sum;

    /**
     * 
     */
    private BigDecimal balance_sum;

    /**
     * 行内提现汇总
     */
    private BigDecimal bank_withdrawals;

    /**
     * clear_result
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return pid 
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 
     * @param pid 
     */
    public void setPid(Integer pid) {
        this.pid = pid;
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
     * @return pay_code 
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 
     * @param pay_code 
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
    }

    /**
     * 
     * @return clear_date 
     */
    public Date getClear_date() {
        return clear_date;
    }

    /**
     * 
     * @param clear_date 
     */
    public void setClear_date(Date clear_date) {
        this.clear_date = clear_date;
    }

    /**
     * 
     * @return recharge_sum_inside 
     */
    public BigDecimal getRecharge_sum_inside() {
        return recharge_sum_inside;
    }

    /**
     * 
     * @param recharge_sum_inside 
     */
    public void setRecharge_sum_inside(BigDecimal recharge_sum_inside) {
        this.recharge_sum_inside = recharge_sum_inside;
    }

    /**
     * 
     * @return recharge_sum_outside 
     */
    public BigDecimal getRecharge_sum_outside() {
        return recharge_sum_outside;
    }

    /**
     * 
     * @param recharge_sum_outside 
     */
    public void setRecharge_sum_outside(BigDecimal recharge_sum_outside) {
        this.recharge_sum_outside = recharge_sum_outside;
    }

    /**
     * 
     * @return recharge_count_inside 
     */
    public Integer getRecharge_count_inside() {
        return recharge_count_inside;
    }

    /**
     * 
     * @param recharge_count_inside 
     */
    public void setRecharge_count_inside(Integer recharge_count_inside) {
        this.recharge_count_inside = recharge_count_inside;
    }

    /**
     * 
     * @return recharge_count_outside 
     */
    public Integer getRecharge_count_outside() {
        return recharge_count_outside;
    }

    /**
     * 
     * @param recharge_count_outside 
     */
    public void setRecharge_count_outside(Integer recharge_count_outside) {
        this.recharge_count_outside = recharge_count_outside;
    }

    /**
     * 
     * @return withdrawals_sum_inside 
     */
    public BigDecimal getWithdrawals_sum_inside() {
        return withdrawals_sum_inside;
    }

    /**
     * 
     * @param withdrawals_sum_inside 
     */
    public void setWithdrawals_sum_inside(BigDecimal withdrawals_sum_inside) {
        this.withdrawals_sum_inside = withdrawals_sum_inside;
    }

    /**
     * 
     * @return withdrawals_sum_outside 
     */
    public BigDecimal getWithdrawals_sum_outside() {
        return withdrawals_sum_outside;
    }

    /**
     * 
     * @param withdrawals_sum_outside 
     */
    public void setWithdrawals_sum_outside(BigDecimal withdrawals_sum_outside) {
        this.withdrawals_sum_outside = withdrawals_sum_outside;
    }

    /**
     * 
     * @return withdrawals_count_inside 
     */
    public Integer getWithdrawals_count_inside() {
        return withdrawals_count_inside;
    }

    /**
     * 
     * @param withdrawals_count_inside 
     */
    public void setWithdrawals_count_inside(Integer withdrawals_count_inside) {
        this.withdrawals_count_inside = withdrawals_count_inside;
    }

    /**
     * 
     * @return withdrawals_count_outside 
     */
    public Integer getWithdrawals_count_outside() {
        return withdrawals_count_outside;
    }

    /**
     * 
     * @param withdrawals_count_outside 
     */
    public void setWithdrawals_count_outside(Integer withdrawals_count_outside) {
        this.withdrawals_count_outside = withdrawals_count_outside;
    }

    /**
     * 
     * @return withdrawals_fail_count 
     */
    public Integer getWithdrawals_fail_count() {
        return withdrawals_fail_count;
    }

    /**
     * 
     * @param withdrawals_fail_count 
     */
    public void setWithdrawals_fail_count(Integer withdrawals_fail_count) {
        this.withdrawals_fail_count = withdrawals_fail_count;
    }

    /**
     * 
     * @return withdrawals_fail_sum 
     */
    public BigDecimal getWithdrawals_fail_sum() {
        return withdrawals_fail_sum;
    }

    /**
     * 
     * @param withdrawals_fail_sum 
     */
    public void setWithdrawals_fail_sum(BigDecimal withdrawals_fail_sum) {
        this.withdrawals_fail_sum = withdrawals_fail_sum;
    }

    /**
     * 
     * @return withdrawals_wait_count 
     */
    public Integer getWithdrawals_wait_count() {
        return withdrawals_wait_count;
    }

    /**
     * 
     * @param withdrawals_wait_count 
     */
    public void setWithdrawals_wait_count(Integer withdrawals_wait_count) {
        this.withdrawals_wait_count = withdrawals_wait_count;
    }

    /**
     * 
     * @return withdrawals_wait_sum 
     */
    public BigDecimal getWithdrawals_wait_sum() {
        return withdrawals_wait_sum;
    }

    /**
     * 
     * @param withdrawals_wait_sum 
     */
    public void setWithdrawals_wait_sum(BigDecimal withdrawals_wait_sum) {
        this.withdrawals_wait_sum = withdrawals_wait_sum;
    }

    /**
     * 0未对1已对2异常
     * @return clear_status 0未对1已对2异常
     */
    public String getClear_status() {
        return clear_status;
    }

    /**
     * 0未对1已对2异常
     * @param clear_status 0未对1已对2异常
     */
    public void setClear_status(String clear_status) {
        this.clear_status = clear_status == null ? null : clear_status.trim();
    }

    /**
     * 
     * @return clear_recordtime 
     */
    public String getClear_recordtime() {
        return clear_recordtime;
    }

    /**
     * 
     * @param clear_recordtime 
     */
    public void setClear_recordtime(String clear_recordtime) {
        this.clear_recordtime = clear_recordtime == null ? null : clear_recordtime.trim();
    }

    /**
     * 0未清1已清
     * @return liquidation 0未清1已清
     */
    public String getLiquidation() {
        return liquidation;
    }

    /**
     * 0未清1已清
     * @param liquidation 0未清1已清
     */
    public void setLiquidation(String liquidation) {
        this.liquidation = liquidation == null ? null : liquidation.trim();
    }

    /**
     * 
     * @return liquidation_recordtime 
     */
    public String getLiquidation_recordtime() {
        return liquidation_recordtime;
    }

    /**
     * 
     * @param liquidation_recordtime 
     */
    public void setLiquidation_recordtime(String liquidation_recordtime) {
        this.liquidation_recordtime = liquidation_recordtime == null ? null : liquidation_recordtime.trim();
    }

    /**
     * 
     * @return wait_clear_sum 
     */
    public BigDecimal getWait_clear_sum() {
        return wait_clear_sum;
    }

    /**
     * 
     * @param wait_clear_sum 
     */
    public void setWait_clear_sum(BigDecimal wait_clear_sum) {
        this.wait_clear_sum = wait_clear_sum;
    }

    /**
     * 结算金额（行内充值汇总）
     * @return clear_sum 结算金额（行内充值汇总）
     */
    public BigDecimal getClear_sum() {
        return clear_sum;
    }

    /**
     * 结算金额（行内充值汇总）
     * @param clear_sum 结算金额（行内充值汇总）
     */
    public void setClear_sum(BigDecimal clear_sum) {
        this.clear_sum = clear_sum;
    }

    /**
     * 
     * @return balance_sum 
     */
    public BigDecimal getBalance_sum() {
        return balance_sum;
    }

    /**
     * 
     * @param balance_sum 
     */
    public void setBalance_sum(BigDecimal balance_sum) {
        this.balance_sum = balance_sum;
    }

    /**
     * 行内提现汇总
     * @return bank_withdrawals 行内提现汇总
     */
    public BigDecimal getBank_withdrawals() {
        return bank_withdrawals;
    }

    /**
     * 行内提现汇总
     * @param bank_withdrawals 行内提现汇总
     */
    public void setBank_withdrawals(BigDecimal bank_withdrawals) {
        this.bank_withdrawals = bank_withdrawals;
    }

    /**
     *
     * @mbggenerated 2017-06-14
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
        ClearResult other = (ClearResult) that;
        return (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getRecharge_sum_inside() == null ? other.getRecharge_sum_inside() == null : this.getRecharge_sum_inside().equals(other.getRecharge_sum_inside()))
            && (this.getRecharge_sum_outside() == null ? other.getRecharge_sum_outside() == null : this.getRecharge_sum_outside().equals(other.getRecharge_sum_outside()))
            && (this.getRecharge_count_inside() == null ? other.getRecharge_count_inside() == null : this.getRecharge_count_inside().equals(other.getRecharge_count_inside()))
            && (this.getRecharge_count_outside() == null ? other.getRecharge_count_outside() == null : this.getRecharge_count_outside().equals(other.getRecharge_count_outside()))
            && (this.getWithdrawals_sum_inside() == null ? other.getWithdrawals_sum_inside() == null : this.getWithdrawals_sum_inside().equals(other.getWithdrawals_sum_inside()))
            && (this.getWithdrawals_sum_outside() == null ? other.getWithdrawals_sum_outside() == null : this.getWithdrawals_sum_outside().equals(other.getWithdrawals_sum_outside()))
            && (this.getWithdrawals_count_inside() == null ? other.getWithdrawals_count_inside() == null : this.getWithdrawals_count_inside().equals(other.getWithdrawals_count_inside()))
            && (this.getWithdrawals_count_outside() == null ? other.getWithdrawals_count_outside() == null : this.getWithdrawals_count_outside().equals(other.getWithdrawals_count_outside()))
            && (this.getWithdrawals_fail_count() == null ? other.getWithdrawals_fail_count() == null : this.getWithdrawals_fail_count().equals(other.getWithdrawals_fail_count()))
            && (this.getWithdrawals_fail_sum() == null ? other.getWithdrawals_fail_sum() == null : this.getWithdrawals_fail_sum().equals(other.getWithdrawals_fail_sum()))
            && (this.getWithdrawals_wait_count() == null ? other.getWithdrawals_wait_count() == null : this.getWithdrawals_wait_count().equals(other.getWithdrawals_wait_count()))
            && (this.getWithdrawals_wait_sum() == null ? other.getWithdrawals_wait_sum() == null : this.getWithdrawals_wait_sum().equals(other.getWithdrawals_wait_sum()))
            && (this.getClear_status() == null ? other.getClear_status() == null : this.getClear_status().equals(other.getClear_status()))
            && (this.getClear_recordtime() == null ? other.getClear_recordtime() == null : this.getClear_recordtime().equals(other.getClear_recordtime()))
            && (this.getLiquidation() == null ? other.getLiquidation() == null : this.getLiquidation().equals(other.getLiquidation()))
            && (this.getLiquidation_recordtime() == null ? other.getLiquidation_recordtime() == null : this.getLiquidation_recordtime().equals(other.getLiquidation_recordtime()))
            && (this.getWait_clear_sum() == null ? other.getWait_clear_sum() == null : this.getWait_clear_sum().equals(other.getWait_clear_sum()))
            && (this.getClear_sum() == null ? other.getClear_sum() == null : this.getClear_sum().equals(other.getClear_sum()))
            && (this.getBalance_sum() == null ? other.getBalance_sum() == null : this.getBalance_sum().equals(other.getBalance_sum()))
            && (this.getBank_withdrawals() == null ? other.getBank_withdrawals() == null : this.getBank_withdrawals().equals(other.getBank_withdrawals()));
    }

    /**
     *
     * @mbggenerated 2017-06-14
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getRecharge_sum_inside() == null) ? 0 : getRecharge_sum_inside().hashCode());
        result = prime * result + ((getRecharge_sum_outside() == null) ? 0 : getRecharge_sum_outside().hashCode());
        result = prime * result + ((getRecharge_count_inside() == null) ? 0 : getRecharge_count_inside().hashCode());
        result = prime * result + ((getRecharge_count_outside() == null) ? 0 : getRecharge_count_outside().hashCode());
        result = prime * result + ((getWithdrawals_sum_inside() == null) ? 0 : getWithdrawals_sum_inside().hashCode());
        result = prime * result + ((getWithdrawals_sum_outside() == null) ? 0 : getWithdrawals_sum_outside().hashCode());
        result = prime * result + ((getWithdrawals_count_inside() == null) ? 0 : getWithdrawals_count_inside().hashCode());
        result = prime * result + ((getWithdrawals_count_outside() == null) ? 0 : getWithdrawals_count_outside().hashCode());
        result = prime * result + ((getWithdrawals_fail_count() == null) ? 0 : getWithdrawals_fail_count().hashCode());
        result = prime * result + ((getWithdrawals_fail_sum() == null) ? 0 : getWithdrawals_fail_sum().hashCode());
        result = prime * result + ((getWithdrawals_wait_count() == null) ? 0 : getWithdrawals_wait_count().hashCode());
        result = prime * result + ((getWithdrawals_wait_sum() == null) ? 0 : getWithdrawals_wait_sum().hashCode());
        result = prime * result + ((getClear_status() == null) ? 0 : getClear_status().hashCode());
        result = prime * result + ((getClear_recordtime() == null) ? 0 : getClear_recordtime().hashCode());
        result = prime * result + ((getLiquidation() == null) ? 0 : getLiquidation().hashCode());
        result = prime * result + ((getLiquidation_recordtime() == null) ? 0 : getLiquidation_recordtime().hashCode());
        result = prime * result + ((getWait_clear_sum() == null) ? 0 : getWait_clear_sum().hashCode());
        result = prime * result + ((getClear_sum() == null) ? 0 : getClear_sum().hashCode());
        result = prime * result + ((getBalance_sum() == null) ? 0 : getBalance_sum().hashCode());
        result = prime * result + ((getBank_withdrawals() == null) ? 0 : getBank_withdrawals().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-06-14
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pid=").append(pid);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", clear_date=").append(clear_date);
        sb.append(", recharge_sum_inside=").append(recharge_sum_inside);
        sb.append(", recharge_sum_outside=").append(recharge_sum_outside);
        sb.append(", recharge_count_inside=").append(recharge_count_inside);
        sb.append(", recharge_count_outside=").append(recharge_count_outside);
        sb.append(", withdrawals_sum_inside=").append(withdrawals_sum_inside);
        sb.append(", withdrawals_sum_outside=").append(withdrawals_sum_outside);
        sb.append(", withdrawals_count_inside=").append(withdrawals_count_inside);
        sb.append(", withdrawals_count_outside=").append(withdrawals_count_outside);
        sb.append(", withdrawals_fail_count=").append(withdrawals_fail_count);
        sb.append(", withdrawals_fail_sum=").append(withdrawals_fail_sum);
        sb.append(", withdrawals_wait_count=").append(withdrawals_wait_count);
        sb.append(", withdrawals_wait_sum=").append(withdrawals_wait_sum);
        sb.append(", clear_status=").append(clear_status);
        sb.append(", clear_recordtime=").append(clear_recordtime);
        sb.append(", liquidation=").append(liquidation);
        sb.append(", liquidation_recordtime=").append(liquidation_recordtime);
        sb.append(", wait_clear_sum=").append(wait_clear_sum);
        sb.append(", clear_sum=").append(clear_sum);
        sb.append(", balance_sum=").append(balance_sum);
        sb.append(", bank_withdrawals=").append(bank_withdrawals);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}