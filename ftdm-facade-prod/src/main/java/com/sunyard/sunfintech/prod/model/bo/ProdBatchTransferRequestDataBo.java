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
public class ProdBatchTransferRequestDataBo implements Serializable {

    @NotBlank
    private String platcust;

    private String origin_order_no;
    @NotBlank
    private String detail_no;
    @NotBlank
    private String prod_id;
    @NotNull
    private BigDecimal trans_share;
    @NotNull
    private BigDecimal trans_amt;
    @NotNull
    private BigDecimal deal_amount;
    private BigDecimal coupon_amt;
    @NotBlank
    private String deal_platcust;

    private String interface_version;

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

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

    public String getInterface_version() {
        return interface_version;
    }

    public void setInterface_version(String interface_version) {
        this.interface_version = interface_version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdBatchTransferRequestDataBo dataBo = (ProdBatchTransferRequestDataBo) o;

        if (platcust != null ? !platcust.equals(dataBo.platcust) : dataBo.platcust != null) return false;
        if (origin_order_no != null ? !origin_order_no.equals(dataBo.origin_order_no) : dataBo.origin_order_no != null)
            return false;
        if (detail_no != null ? !detail_no.equals(dataBo.detail_no) : dataBo.detail_no != null) return false;
        if (prod_id != null ? !prod_id.equals(dataBo.prod_id) : dataBo.prod_id != null) return false;
        if (trans_share != null ? !trans_share.equals(dataBo.trans_share) : dataBo.trans_share != null) return false;
        if (trans_amt != null ? !trans_amt.equals(dataBo.trans_amt) : dataBo.trans_amt != null) return false;
        if (deal_amount != null ? !deal_amount.equals(dataBo.deal_amount) : dataBo.deal_amount != null) return false;
        if (coupon_amt != null ? !coupon_amt.equals(dataBo.coupon_amt) : dataBo.coupon_amt != null) return false;
        if (deal_platcust != null ? !deal_platcust.equals(dataBo.deal_platcust) : dataBo.deal_platcust != null)
            return false;
        if (interface_version != null ? !interface_version.equals(dataBo.interface_version) : dataBo.interface_version != null)
            return false;
        if (commission != null ? !commission.equals(dataBo.commission) : dataBo.commission != null) return false;
        if (commissionObj != null ? !commissionObj.equals(dataBo.commissionObj) : dataBo.commissionObj != null)
            return false;
        if (commission_ext != null ? !commission_ext.equals(dataBo.commission_ext) : dataBo.commission_ext != null)
            return false;
        if (commission_extObj != null ? !commission_extObj.equals(dataBo.commission_extObj) : dataBo.commission_extObj != null)
            return false;
        if (publish_date != null ? !publish_date.equals(dataBo.publish_date) : dataBo.publish_date != null)
            return false;
        if (trans_date != null ? !trans_date.equals(dataBo.trans_date) : dataBo.trans_date != null) return false;
        if (transfer_income != null ? !transfer_income.equals(dataBo.transfer_income) : dataBo.transfer_income != null)
            return false;
        if (income_acct != null ? !income_acct.equals(dataBo.income_acct) : dataBo.income_acct != null) return false;
        if (related_prod_ids != null ? !related_prod_ids.equals(dataBo.related_prod_ids) : dataBo.related_prod_ids != null)
            return false;
        if (subject_priority != null ? !subject_priority.equals(dataBo.subject_priority) : dataBo.subject_priority != null)
            return false;
        return remark != null ? remark.equals(dataBo.remark) : dataBo.remark == null;
    }

    @Override
    public int hashCode() {
        int result = platcust != null ? platcust.hashCode() : 0;
        result = 31 * result + (origin_order_no != null ? origin_order_no.hashCode() : 0);
        result = 31 * result + (detail_no != null ? detail_no.hashCode() : 0);
        result = 31 * result + (prod_id != null ? prod_id.hashCode() : 0);
        result = 31 * result + (trans_share != null ? trans_share.hashCode() : 0);
        result = 31 * result + (trans_amt != null ? trans_amt.hashCode() : 0);
        result = 31 * result + (deal_amount != null ? deal_amount.hashCode() : 0);
        result = 31 * result + (coupon_amt != null ? coupon_amt.hashCode() : 0);
        result = 31 * result + (deal_platcust != null ? deal_platcust.hashCode() : 0);
        result = 31 * result + (interface_version != null ? interface_version.hashCode() : 0);
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        result = 31 * result + (commissionObj != null ? commissionObj.hashCode() : 0);
        result = 31 * result + (commission_ext != null ? commission_ext.hashCode() : 0);
        result = 31 * result + (commission_extObj != null ? commission_extObj.hashCode() : 0);
        result = 31 * result + (publish_date != null ? publish_date.hashCode() : 0);
        result = 31 * result + (trans_date != null ? trans_date.hashCode() : 0);
        result = 31 * result + (transfer_income != null ? transfer_income.hashCode() : 0);
        result = 31 * result + (income_acct != null ? income_acct.hashCode() : 0);
        result = 31 * result + (related_prod_ids != null ? related_prod_ids.hashCode() : 0);
        result = 31 * result + (subject_priority != null ? subject_priority.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProdBatchTransferRequestDataBo{" +
                "platcust='" + platcust + '\'' +
                ", origin_order_no='" + origin_order_no + '\'' +
                ", detail_no='" + detail_no + '\'' +
                ", prod_id='" + prod_id + '\'' +
                ", trans_share=" + trans_share +
                ", trans_amt=" + trans_amt +
                ", deal_amount=" + deal_amount +
                ", coupon_amt=" + coupon_amt +
                ", deal_platcust='" + deal_platcust + '\'' +
                ", interface_version='" + interface_version + '\'' +
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
