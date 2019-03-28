package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by terry on 2017/5/4.
 */
public class BatchCustRepayOld implements Serializable{

    /**
     * 明细编号
     */
    @NotBlank
    private String detail_no;

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

    //==================非请求参数======================
    private String order_no;

    private String mall_no;

    private String plat_no;

    private String prod_id;

    private String is_payoff;

    private String repay_flag;

    private String partner_trans_time;

    private String partner_trans_date;

    private String amt_type;

    private BigDecimal real_eaccount_amt;

    private String some_type;

    private BigDecimal some_cash_amt;

    private TransTransreq transTransreq;
    //==================================================

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

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

    public void setRepay_date(Date repay_date) {
        this.repay_date = repay_date;
    }

    public void setReal_repay_date(Date real_repay_date) {
        this.real_repay_date = real_repay_date;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getIs_payoff() {
        return is_payoff;
    }

    public void setIs_payoff(String is_payoff) {
        this.is_payoff = is_payoff;
    }

    public String getRepay_flag() {
        return repay_flag;
    }

    public void setRepay_flag(String repay_flag) {
        this.repay_flag = repay_flag;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPartner_trans_time() {
        return partner_trans_time;
    }

    public void setPartner_trans_time(String partner_trans_time) {
        this.partner_trans_time = partner_trans_time;
    }

    public String getPartner_trans_date() {
        return partner_trans_date;
    }

    public void setPartner_trans_date(String partner_trans_date) {
        this.partner_trans_date = partner_trans_date;
    }

    public String getAmt_type() {
        return amt_type;
    }

    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type;
    }

    public BigDecimal getReal_eaccount_amt() {
        return real_eaccount_amt;
    }

    public void setReal_eaccount_amt(BigDecimal real_eaccount_amt) {
        this.real_eaccount_amt = real_eaccount_amt;
    }

    public String getSome_type() {
        return some_type;
    }

    public void setSome_type(String some_type) {
        this.some_type = some_type;
    }

    public BigDecimal getSome_cash_amt() {
        return some_cash_amt;
    }

    public void setSome_cash_amt(BigDecimal some_cash_amt) {
        this.some_cash_amt = some_cash_amt;
    }

    public TransTransreq getTransTransreq() {
        return transTransreq;
    }

    public void setTransTransreq(TransTransreq transTransreq) {
        this.transTransreq = transTransreq;
    }
}
