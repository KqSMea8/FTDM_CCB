package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClearResultSummary implements Serializable {
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
    private Date clear_date;

    /**
     * 充值汇总
     */
    private BigDecimal recharge_sum_inside;

    /**
     * 三方充值汇总
     */
    private BigDecimal recharge_sum_outside;

    /**
     * 充值笔数
     */
    private Integer recharge_count_inside;

    /**
     * 三方充值笔数
     */
    private Integer recharge_count_outside;

    /**
     * 提现汇总
     */
    private BigDecimal withdrawals_sum_inside;

    /**
     * 三方提现汇总
     */
    private BigDecimal withdrawals_sum_outside;

    /**
     * 平台提现笔数
     */
    private Integer withdrawals_count_inside;

    /**
     * 三方提现笔数
     */
    private Integer withdrawals_count_outside;

    /**
     * 行内充值数量
     */
    private Integer recharge_count_plat;

    /**
     * 行内充值金额
     */
    private BigDecimal recharge_sum_plat;

    /**
     * 行内提现笔数
     */
    private Integer withdrawals_count_plat;

    /**
     * 行内提现总额
     */
    private BigDecimal withdrawals_sum_plat;

    /**
     * 0未清1已清
     */
    private String liquidation;

    /**
     * 清算时间
     */
    private String liquidation_recordtime;

    /**
     * 系统内可用现金
     */
    private BigDecimal balance_sum;

    /**
     * 银行账户余额
     */
    private BigDecimal balance_bank;

    /**
     * 账面余额
     */
    private BigDecimal book_balance;

    /**
     * clear_result_summary
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
     * 充值汇总
     * @return recharge_sum_inside 充值汇总
     */
    public BigDecimal getRecharge_sum_inside() {
        return recharge_sum_inside;
    }

    /**
     * 充值汇总
     * @param recharge_sum_inside 充值汇总
     */
    public void setRecharge_sum_inside(BigDecimal recharge_sum_inside) {
        this.recharge_sum_inside = recharge_sum_inside;
    }

    /**
     * 三方充值汇总
     * @return recharge_sum_outside 三方充值汇总
     */
    public BigDecimal getRecharge_sum_outside() {
        return recharge_sum_outside;
    }

    /**
     * 三方充值汇总
     * @param recharge_sum_outside 三方充值汇总
     */
    public void setRecharge_sum_outside(BigDecimal recharge_sum_outside) {
        this.recharge_sum_outside = recharge_sum_outside;
    }

    /**
     * 充值笔数
     * @return recharge_count_inside 充值笔数
     */
    public Integer getRecharge_count_inside() {
        return recharge_count_inside;
    }

    /**
     * 充值笔数
     * @param recharge_count_inside 充值笔数
     */
    public void setRecharge_count_inside(Integer recharge_count_inside) {
        this.recharge_count_inside = recharge_count_inside;
    }

    /**
     * 三方充值笔数
     * @return recharge_count_outside 三方充值笔数
     */
    public Integer getRecharge_count_outside() {
        return recharge_count_outside;
    }

    /**
     * 三方充值笔数
     * @param recharge_count_outside 三方充值笔数
     */
    public void setRecharge_count_outside(Integer recharge_count_outside) {
        this.recharge_count_outside = recharge_count_outside;
    }

    /**
     * 提现汇总
     * @return withdrawals_sum_inside 提现汇总
     */
    public BigDecimal getWithdrawals_sum_inside() {
        return withdrawals_sum_inside;
    }

    /**
     * 提现汇总
     * @param withdrawals_sum_inside 提现汇总
     */
    public void setWithdrawals_sum_inside(BigDecimal withdrawals_sum_inside) {
        this.withdrawals_sum_inside = withdrawals_sum_inside;
    }

    /**
     * 三方提现汇总
     * @return withdrawals_sum_outside 三方提现汇总
     */
    public BigDecimal getWithdrawals_sum_outside() {
        return withdrawals_sum_outside;
    }

    /**
     * 三方提现汇总
     * @param withdrawals_sum_outside 三方提现汇总
     */
    public void setWithdrawals_sum_outside(BigDecimal withdrawals_sum_outside) {
        this.withdrawals_sum_outside = withdrawals_sum_outside;
    }

    /**
     * 平台提现笔数
     * @return withdrawals_count_inside 平台提现笔数
     */
    public Integer getWithdrawals_count_inside() {
        return withdrawals_count_inside;
    }

    /**
     * 平台提现笔数
     * @param withdrawals_count_inside 平台提现笔数
     */
    public void setWithdrawals_count_inside(Integer withdrawals_count_inside) {
        this.withdrawals_count_inside = withdrawals_count_inside;
    }

    /**
     * 三方提现笔数
     * @return withdrawals_count_outside 三方提现笔数
     */
    public Integer getWithdrawals_count_outside() {
        return withdrawals_count_outside;
    }

    /**
     * 三方提现笔数
     * @param withdrawals_count_outside 三方提现笔数
     */
    public void setWithdrawals_count_outside(Integer withdrawals_count_outside) {
        this.withdrawals_count_outside = withdrawals_count_outside;
    }

    /**
     * 行内充值数量
     * @return recharge_count_plat 行内充值数量
     */
    public Integer getRecharge_count_plat() {
        return recharge_count_plat;
    }

    /**
     * 行内充值数量
     * @param recharge_count_plat 行内充值数量
     */
    public void setRecharge_count_plat(Integer recharge_count_plat) {
        this.recharge_count_plat = recharge_count_plat;
    }

    /**
     * 行内充值金额
     * @return recharge_sum_plat 行内充值金额
     */
    public BigDecimal getRecharge_sum_plat() {
        return recharge_sum_plat;
    }

    /**
     * 行内充值金额
     * @param recharge_sum_plat 行内充值金额
     */
    public void setRecharge_sum_plat(BigDecimal recharge_sum_plat) {
        this.recharge_sum_plat = recharge_sum_plat;
    }

    /**
     * 行内提现笔数
     * @return withdrawals_count_plat 行内提现笔数
     */
    public Integer getWithdrawals_count_plat() {
        return withdrawals_count_plat;
    }

    /**
     * 行内提现笔数
     * @param withdrawals_count_plat 行内提现笔数
     */
    public void setWithdrawals_count_plat(Integer withdrawals_count_plat) {
        this.withdrawals_count_plat = withdrawals_count_plat;
    }

    /**
     * 行内提现总额
     * @return withdrawals_sum_plat 行内提现总额
     */
    public BigDecimal getWithdrawals_sum_plat() {
        return withdrawals_sum_plat;
    }

    /**
     * 行内提现总额
     * @param withdrawals_sum_plat 行内提现总额
     */
    public void setWithdrawals_sum_plat(BigDecimal withdrawals_sum_plat) {
        this.withdrawals_sum_plat = withdrawals_sum_plat;
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
     * 清算时间
     * @return liquidation_recordtime 清算时间
     */
    public String getLiquidation_recordtime() {
        return liquidation_recordtime;
    }

    /**
     * 清算时间
     * @param liquidation_recordtime 清算时间
     */
    public void setLiquidation_recordtime(String liquidation_recordtime) {
        this.liquidation_recordtime = liquidation_recordtime == null ? null : liquidation_recordtime.trim();
    }

    /**
     * 系统内可用现金
     * @return balance_sum 系统内可用现金
     */
    public BigDecimal getBalance_sum() {
        return balance_sum;
    }

    /**
     * 系统内可用现金
     * @param balance_sum 系统内可用现金
     */
    public void setBalance_sum(BigDecimal balance_sum) {
        this.balance_sum = balance_sum;
    }

    /**
     * 银行账户余额
     * @return balance_bank 银行账户余额
     */
    public BigDecimal getBalance_bank() {
        return balance_bank;
    }

    /**
     * 银行账户余额
     * @param balance_bank 银行账户余额
     */
    public void setBalance_bank(BigDecimal balance_bank) {
        this.balance_bank = balance_bank;
    }

    /**
     * 账面余额
     * @return book_balance 账面余额
     */
    public BigDecimal getBook_balance() {
        return book_balance;
    }

    /**
     * 账面余额
     * @param book_balance 账面余额
     */
    public void setBook_balance(BigDecimal book_balance) {
        this.book_balance = book_balance;
    }

    /**
     *
     * @mbggenerated 2017-06-19
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
        ClearResultSummary other = (ClearResultSummary) that;
        return (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getRecharge_sum_inside() == null ? other.getRecharge_sum_inside() == null : this.getRecharge_sum_inside().equals(other.getRecharge_sum_inside()))
            && (this.getRecharge_sum_outside() == null ? other.getRecharge_sum_outside() == null : this.getRecharge_sum_outside().equals(other.getRecharge_sum_outside()))
            && (this.getRecharge_count_inside() == null ? other.getRecharge_count_inside() == null : this.getRecharge_count_inside().equals(other.getRecharge_count_inside()))
            && (this.getRecharge_count_outside() == null ? other.getRecharge_count_outside() == null : this.getRecharge_count_outside().equals(other.getRecharge_count_outside()))
            && (this.getWithdrawals_sum_inside() == null ? other.getWithdrawals_sum_inside() == null : this.getWithdrawals_sum_inside().equals(other.getWithdrawals_sum_inside()))
            && (this.getWithdrawals_sum_outside() == null ? other.getWithdrawals_sum_outside() == null : this.getWithdrawals_sum_outside().equals(other.getWithdrawals_sum_outside()))
            && (this.getWithdrawals_count_inside() == null ? other.getWithdrawals_count_inside() == null : this.getWithdrawals_count_inside().equals(other.getWithdrawals_count_inside()))
            && (this.getWithdrawals_count_outside() == null ? other.getWithdrawals_count_outside() == null : this.getWithdrawals_count_outside().equals(other.getWithdrawals_count_outside()))
            && (this.getRecharge_count_plat() == null ? other.getRecharge_count_plat() == null : this.getRecharge_count_plat().equals(other.getRecharge_count_plat()))
            && (this.getRecharge_sum_plat() == null ? other.getRecharge_sum_plat() == null : this.getRecharge_sum_plat().equals(other.getRecharge_sum_plat()))
            && (this.getWithdrawals_count_plat() == null ? other.getWithdrawals_count_plat() == null : this.getWithdrawals_count_plat().equals(other.getWithdrawals_count_plat()))
            && (this.getWithdrawals_sum_plat() == null ? other.getWithdrawals_sum_plat() == null : this.getWithdrawals_sum_plat().equals(other.getWithdrawals_sum_plat()))
            && (this.getLiquidation() == null ? other.getLiquidation() == null : this.getLiquidation().equals(other.getLiquidation()))
            && (this.getLiquidation_recordtime() == null ? other.getLiquidation_recordtime() == null : this.getLiquidation_recordtime().equals(other.getLiquidation_recordtime()))
            && (this.getBalance_sum() == null ? other.getBalance_sum() == null : this.getBalance_sum().equals(other.getBalance_sum()))
            && (this.getBalance_bank() == null ? other.getBalance_bank() == null : this.getBalance_bank().equals(other.getBalance_bank()))
            && (this.getBook_balance() == null ? other.getBook_balance() == null : this.getBook_balance().equals(other.getBook_balance()));
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getRecharge_sum_inside() == null) ? 0 : getRecharge_sum_inside().hashCode());
        result = prime * result + ((getRecharge_sum_outside() == null) ? 0 : getRecharge_sum_outside().hashCode());
        result = prime * result + ((getRecharge_count_inside() == null) ? 0 : getRecharge_count_inside().hashCode());
        result = prime * result + ((getRecharge_count_outside() == null) ? 0 : getRecharge_count_outside().hashCode());
        result = prime * result + ((getWithdrawals_sum_inside() == null) ? 0 : getWithdrawals_sum_inside().hashCode());
        result = prime * result + ((getWithdrawals_sum_outside() == null) ? 0 : getWithdrawals_sum_outside().hashCode());
        result = prime * result + ((getWithdrawals_count_inside() == null) ? 0 : getWithdrawals_count_inside().hashCode());
        result = prime * result + ((getWithdrawals_count_outside() == null) ? 0 : getWithdrawals_count_outside().hashCode());
        result = prime * result + ((getRecharge_count_plat() == null) ? 0 : getRecharge_count_plat().hashCode());
        result = prime * result + ((getRecharge_sum_plat() == null) ? 0 : getRecharge_sum_plat().hashCode());
        result = prime * result + ((getWithdrawals_count_plat() == null) ? 0 : getWithdrawals_count_plat().hashCode());
        result = prime * result + ((getWithdrawals_sum_plat() == null) ? 0 : getWithdrawals_sum_plat().hashCode());
        result = prime * result + ((getLiquidation() == null) ? 0 : getLiquidation().hashCode());
        result = prime * result + ((getLiquidation_recordtime() == null) ? 0 : getLiquidation_recordtime().hashCode());
        result = prime * result + ((getBalance_sum() == null) ? 0 : getBalance_sum().hashCode());
        result = prime * result + ((getBalance_bank() == null) ? 0 : getBalance_bank().hashCode());
        result = prime * result + ((getBook_balance() == null) ? 0 : getBook_balance().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pid=").append(pid);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", clear_date=").append(clear_date);
        sb.append(", recharge_sum_inside=").append(recharge_sum_inside);
        sb.append(", recharge_sum_outside=").append(recharge_sum_outside);
        sb.append(", recharge_count_inside=").append(recharge_count_inside);
        sb.append(", recharge_count_outside=").append(recharge_count_outside);
        sb.append(", withdrawals_sum_inside=").append(withdrawals_sum_inside);
        sb.append(", withdrawals_sum_outside=").append(withdrawals_sum_outside);
        sb.append(", withdrawals_count_inside=").append(withdrawals_count_inside);
        sb.append(", withdrawals_count_outside=").append(withdrawals_count_outside);
        sb.append(", recharge_count_plat=").append(recharge_count_plat);
        sb.append(", recharge_sum_plat=").append(recharge_sum_plat);
        sb.append(", withdrawals_count_plat=").append(withdrawals_count_plat);
        sb.append(", withdrawals_sum_plat=").append(withdrawals_sum_plat);
        sb.append(", liquidation=").append(liquidation);
        sb.append(", liquidation_recordtime=").append(liquidation_recordtime);
        sb.append(", balance_sum=").append(balance_sum);
        sb.append(", balance_bank=").append(balance_bank);
        sb.append(", book_balance=").append(book_balance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}