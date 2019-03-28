package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.util.DateUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by terry on 2017/5/3.
 */
public class ProdBatchTransferRequestDataBoTb implements Serializable {

    @NotBlank
    private String detail_no;
    @NotNull
    private BigDecimal trans_share;
    @NotNull
    private BigDecimal trans_amt;
    @NotNull
    private BigDecimal deal_amount;
    @NotNull
    private BigDecimal coupon_amt;
    @NotBlank
    private String deal_platcust;

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    private String commission;

    private Commission commissionObj;

    private String commission_ext;

    private Commission commission_extObj;
    @NotNull
    private Date publish_date;
    @NotNull
    private Date trans_date;
    @NotNull
    private BigDecimal transfer_income;
    @NotBlank
    private String income_acct;
    private String related_prod_ids;
    @NotBlank
    private String subject_priority;
    private String remark;

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public BigDecimal getTrans_share() {
        return trans_share;
    }

    public void setTrans_share(BigDecimal trans_share) {
        this.trans_share = trans_share;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public BigDecimal getDeal_amount() {
        return deal_amount;
    }

    public void setDeal_amount(BigDecimal deal_amount) {
        this.deal_amount = deal_amount;
    }

    public BigDecimal getCoupon_amt() {
        return coupon_amt;
    }

    public void setCoupon_amt(BigDecimal coupon_amt) {
        this.coupon_amt = coupon_amt;
    }

    public String getDeal_platcust() {
        return deal_platcust;
    }

    public void setDeal_platcust(String deal_platcust) {
        this.deal_platcust = deal_platcust;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
        setCommissionObj(JSON.parseObject(commission,Commission.class));
    }

    public Commission getCommissionObj() {
        return commissionObj;
    }

    public void setCommissionObj(Commission commissionObj) {
        this.commissionObj = commissionObj;
    }

    public String getCommission_ext() {
        return commission_ext;
    }

    public void setCommission_ext(String commission_ext) {
        this.commission_ext = commission_ext;
        setCommission_extObj(JSON.parseObject(commission_ext,Commission.class));
    }

    public Commission getCommission_extObj() {
        return commission_extObj;
    }

    public void setCommission_extObj(Commission commission_extObj) {
        this.commission_extObj = commission_extObj;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = DateUtils.parseDate(publish_date);
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = DateUtils.parseDate(trans_date);
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

    public String getRelated_prod_ids() {
        return related_prod_ids;
    }

    public void setRelated_prod_ids(String related_prod_ids) {
        this.related_prod_ids = related_prod_ids;
    }

    public String getSubject_priority() {
        return subject_priority;
    }

    public void setSubject_priority(String subject_priority) {
        this.subject_priority = subject_priority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ProdBatchTransferRequestDataBoTb{" +
                "detail_no='" + detail_no + '\'' +
                ", trans_share=" + trans_share +
                ", trans_amt=" + trans_amt +
                ", deal_amount=" + deal_amount +
                ", coupon_amt=" + coupon_amt +
                ", deal_platcust='" + deal_platcust + '\'' +
                ", commission='" + commission + '\'' +
                ", commissionObj=" + commissionObj +
                ", commission_ext='" + commission_ext + '\'' +
                ", commission_extObj=" + commission_extObj +
                ", publish_date=" + publish_date +
                ", trans_date=" + trans_date +
                ", transfer_income=" + transfer_income +
                ", income_acct='" + income_acct + '\'' +
                ", related_prod_ids='" + related_prod_ids + '\'' +
                ", subject_priority='" + subject_priority + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
