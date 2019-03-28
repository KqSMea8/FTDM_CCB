package com.sunyard.sunfintech.prod.model.bo;

import com.baomidou.mybatisplus.annotations.TableField;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/4/28.
 */
public class AssignmentProductInfo implements Serializable {
    /**
     *  平台编号
     */
    private String plat_no;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 平台用户编号
     */
    private String platcust;

    /**
     * 客户编号
     */
    private String cust_no;

    /**
     *转让份额
     */
    private BigDecimal trans_share;

    /**
     *成交账号（受让人平台客户编号）
     */
    private String deal_platcust;

    /**
     *交易金额（成交价格+出让人手续费+受让人手续费+转让收益）
     */
    private BigDecimal trans_amt;

    /**
     *出让人手续费说明，Json字符串
     */
    private Commission commission_object;

    /**
     *受让人手续费说明，Json字符串
     */
    private Commission commission_ext_object;

    /**
     *转让收益
     */
    private BigDecimal transfer_income;

    /**
     *收益出资方账户 平台：01；个人：对应platcust
     */
    private String income_acct;

    /**
     *科目优先级0-可提优先1可投优先
     */
    private String subject_priority;

    private String partner_trans_date;

    private String partner_trans_time;

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

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public BigDecimal getTrans_share() {
        return trans_share;
    }

    public void setTrans_share(BigDecimal trans_share) {
        this.trans_share = trans_share;
    }

    public String getDeal_platcust() {
        return deal_platcust;
    }

    public void setDeal_platcust(String deal_platcust) {
        this.deal_platcust = deal_platcust;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public Commission getCommission_object() {
        return commission_object;
    }

    public void setCommission_object(Commission commission_object) {
        this.commission_object = commission_object;
    }

    public Commission getCommission_ext_object() {
        return commission_ext_object;
    }

    public void setCommission_ext_object(Commission commission_ext_object) {
        this.commission_ext_object = commission_ext_object;
    }

    public BigDecimal getTransfer_income() {
        return transfer_income;
    }

    public void setTransfer_income(BigDecimal transfer_income) {
        this.transfer_income = transfer_income;
    }

    public String getIncome_acct() {
        return income_acct;
    }

    public void setIncome_acct(String income_acct) {
        this.income_acct = income_acct;
    }

    public String getSubject_priority() {
        return subject_priority;
    }

    public void setSubject_priority(String subject_priority) {
        this.subject_priority = subject_priority;
    }

    public String getPartner_trans_date() {
        return partner_trans_date;
    }

    public void setPartner_trans_date(String partner_trans_date) {
        this.partner_trans_date = partner_trans_date;
    }

    public String getPartner_trans_time() {
        return partner_trans_time;
    }

    public void setPartner_trans_time(String partner_trans_time) {
        this.partner_trans_time = partner_trans_time;
    }
}
