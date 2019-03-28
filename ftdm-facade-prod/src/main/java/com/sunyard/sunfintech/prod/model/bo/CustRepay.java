package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.util.DateUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by terry on 2017/5/4.
 */
public class CustRepay implements Serializable{

    /**
     * 投资人平台客户号
     */
    @NotBlank
    private String cust_no;

    /**
     * 实际还款金额（实际还款本金+体验金+加息金+利息+手续费）
     */
    @NotNull
    private BigDecimal real_repay_amt;

    /**
     * 实际还款本金
     */
    @NotNull
    private BigDecimal real_repay_amount;

    /**
     * 体验金
     */
    private BigDecimal experience_amt;

    /**
     * 加息金
     */
    private BigDecimal rates_amt;

    /**
     * 实际还款利息
     */
    @NotNull
    private BigDecimal real_repay_val;

    /**
     * 手续费
     */
    @NotNull
    private BigDecimal repay_fee;

    /**
     * 还款期数
     */
    @NotNull
    private Integer repay_num;

    /**
     * 还款日期
     */
    @NotNull
    private Date repay_date;

    /**
     * 实际还款日期
     */
    @NotNull
    private Date real_repay_date;

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public BigDecimal getReal_repay_amt() {
        return real_repay_amt;
    }

    public void setReal_repay_amt(BigDecimal real_repay_amt) {
        this.real_repay_amt = real_repay_amt;
    }

    public BigDecimal getReal_repay_amount() {
        return real_repay_amount;
    }

    public void setReal_repay_amount(BigDecimal real_repay_amount) {
        this.real_repay_amount = real_repay_amount;
    }

    public BigDecimal getExperience_amt() {
        return experience_amt;
    }

    public void setExperience_amt(BigDecimal experience_amt) {
        this.experience_amt = experience_amt;
    }

    public BigDecimal getRates_amt() {
        return rates_amt;
    }

    public void setRates_amt(BigDecimal rates_amt) {
        this.rates_amt = rates_amt;
    }

    public BigDecimal getReal_repay_val() {
        return real_repay_val;
    }

    public void setReal_repay_val(BigDecimal real_repay_val) {
        this.real_repay_val = real_repay_val;
    }

    public BigDecimal getRepay_fee() {
        return repay_fee;
    }

    public void setRepay_fee(BigDecimal repay_fee) {
        this.repay_fee = repay_fee;
    }

    public Integer getRepay_num() {
        return repay_num;
    }

    public void setRepay_num(Integer repay_num) {
        this.repay_num = repay_num;
    }

    public Date getRepay_date() {
        return repay_date;
    }

    public void setRepay_date(String repay_date) {
        this.repay_date = DateUtils.parseDate(repay_date);
    }

    public Date getReal_repay_date() {
        return real_repay_date;
    }

    public void setReal_repay_date(String real_repay_date) {
        this.real_repay_date = DateUtils.parseDate(real_repay_date);
    }
}
