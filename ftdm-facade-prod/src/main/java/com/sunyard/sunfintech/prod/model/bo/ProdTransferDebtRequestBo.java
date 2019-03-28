package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by terry on 2017/4/28.
 */
public class ProdTransferDebtRequestBo extends BaseRequest {

    /**
     * 明细号
     */
    private String detail_no;

    /**
     * 原申请单
     */
    private String origin_order_no;

    /**
     *转让人平台客户号
     */
    @NotBlank
    private String platcust;

    /**
     *转让份额
     */
    @NotNull
    private BigDecimal trans_share;

    /**
     *产品编号
     */
    @NotBlank
    private String prod_id;

    /**
     *交易金额（成交价格+出让人手续费+受让人手续费+转让收益）
     */
    @NotNull
    private BigDecimal trans_amt;

    /**
     *自费价格
     */
    @NotNull
    private BigDecimal deal_amount;

    /**
     *抵用劵金额
     */
    private BigDecimal coupon_amt;

    /**
     *成交账号（受让人平台客户编号）
     */
    @NotBlank
    private String deal_platcust;

    /**
     *出让人手续费说明，Json字符串
     */
    private String commission;
    private Commission commissionObject;

    /**
     *受让人手续费说明，Json字符串
     */
    private String commission_ext;
    private Commission commission_extObject;

    /**
     *发布时间(YYYY-MM-DD HH:mm:ss)
     */
    @NotNull
    private Date publish_date;

    /**
     *成交时间(YYYY-MM-DD HH:mm:ss)
     */
    @NotNull
    private Date trans_date;

    /**
     *转让收益
     */
    @NotNull
    private BigDecimal transfer_income;

    /**
     *收益出资方账户 平台：01；个人：对应platcust
     */
    @NotBlank
    private String income_acct;

    /**
     *涉及的标的编号，不同标的之间用逗号分隔
     */
    private String related_prod_ids;

    /**
     *科目优先级0-可提优先1可投优先
     */
    @NotBlank
    private String subject_priority;

    private String interface_version;


    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public BigDecimal getTrans_share() {
        return trans_share;
    }

    public void setTrans_share(BigDecimal trans_share) {
        this.trans_share = trans_share;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
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
        if(!StringUtils.isBlank(commission)){
            setCommissionObject(JSON.parseObject(commission,Commission.class));
        }
    }

    public Commission getCommissionObject() {
        return commissionObject;
    }

    public void setCommissionObject(Commission commissionObject) {
        this.commissionObject = commissionObject;
    }

    public String getCommission_ext() {
        return commission_ext;
    }

    public void setCommission_ext(String commission_ext) {
        this.commission_ext = commission_ext;
        if(!StringUtils.isBlank(commission_ext)){
            setCommission_extObject(JSON.parseObject(commission_ext,Commission.class));
        }
    }

    public Commission getCommission_extObject() {
        return commission_extObject;
    }

    public void setCommission_extObject(Commission commission_extObject) {
        this.commission_extObject = commission_extObject;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
            this.publish_date = DateUtils.parseDate(publish_date);
    }

    public void setPublishDate(Date date){
        this.publish_date=date;
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = DateUtils.parseDate(trans_date);
    }

    public void setTransDate(Date date){
        this.trans_date=date;
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

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
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
        if (!super.equals(o)) return false;

        ProdTransferDebtRequestBo that = (ProdTransferDebtRequestBo) o;

        if (detail_no != null ? !detail_no.equals(that.detail_no) : that.detail_no != null) return false;
        if (origin_order_no != null ? !origin_order_no.equals(that.origin_order_no) : that.origin_order_no != null)
            return false;
        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (trans_share != null ? !trans_share.equals(that.trans_share) : that.trans_share != null) return false;
        if (prod_id != null ? !prod_id.equals(that.prod_id) : that.prod_id != null) return false;
        if (trans_amt != null ? !trans_amt.equals(that.trans_amt) : that.trans_amt != null) return false;
        if (deal_amount != null ? !deal_amount.equals(that.deal_amount) : that.deal_amount != null) return false;
        if (coupon_amt != null ? !coupon_amt.equals(that.coupon_amt) : that.coupon_amt != null) return false;
        if (deal_platcust != null ? !deal_platcust.equals(that.deal_platcust) : that.deal_platcust != null)
            return false;
        if (commission != null ? !commission.equals(that.commission) : that.commission != null) return false;
        if (commissionObject != null ? !commissionObject.equals(that.commissionObject) : that.commissionObject != null)
            return false;
        if (commission_ext != null ? !commission_ext.equals(that.commission_ext) : that.commission_ext != null)
            return false;
        if (commission_extObject != null ? !commission_extObject.equals(that.commission_extObject) : that.commission_extObject != null)
            return false;
        if (publish_date != null ? !publish_date.equals(that.publish_date) : that.publish_date != null) return false;
        if (trans_date != null ? !trans_date.equals(that.trans_date) : that.trans_date != null) return false;
        if (transfer_income != null ? !transfer_income.equals(that.transfer_income) : that.transfer_income != null)
            return false;
        if (income_acct != null ? !income_acct.equals(that.income_acct) : that.income_acct != null) return false;
        if (related_prod_ids != null ? !related_prod_ids.equals(that.related_prod_ids) : that.related_prod_ids != null)
            return false;
        if (subject_priority != null ? !subject_priority.equals(that.subject_priority) : that.subject_priority != null)
            return false;
        return interface_version != null ? interface_version.equals(that.interface_version) : that.interface_version == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (detail_no != null ? detail_no.hashCode() : 0);
        result = 31 * result + (origin_order_no != null ? origin_order_no.hashCode() : 0);
        result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
        result = 31 * result + (trans_share != null ? trans_share.hashCode() : 0);
        result = 31 * result + (prod_id != null ? prod_id.hashCode() : 0);
        result = 31 * result + (trans_amt != null ? trans_amt.hashCode() : 0);
        result = 31 * result + (deal_amount != null ? deal_amount.hashCode() : 0);
        result = 31 * result + (coupon_amt != null ? coupon_amt.hashCode() : 0);
        result = 31 * result + (deal_platcust != null ? deal_platcust.hashCode() : 0);
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        result = 31 * result + (commissionObject != null ? commissionObject.hashCode() : 0);
        result = 31 * result + (commission_ext != null ? commission_ext.hashCode() : 0);
        result = 31 * result + (commission_extObject != null ? commission_extObject.hashCode() : 0);
        result = 31 * result + (publish_date != null ? publish_date.hashCode() : 0);
        result = 31 * result + (trans_date != null ? trans_date.hashCode() : 0);
        result = 31 * result + (transfer_income != null ? transfer_income.hashCode() : 0);
        result = 31 * result + (income_acct != null ? income_acct.hashCode() : 0);
        result = 31 * result + (related_prod_ids != null ? related_prod_ids.hashCode() : 0);
        result = 31 * result + (subject_priority != null ? subject_priority.hashCode() : 0);
        result = 31 * result + (interface_version != null ? interface_version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProdTransferDebtRequestBo{" +
                "detail_no='" + detail_no + '\'' +
                ", origin_order_no='" + origin_order_no + '\'' +
                ", platcust='" + platcust + '\'' +
                ", trans_share=" + trans_share +
                ", prod_id='" + prod_id + '\'' +
                ", trans_amt=" + trans_amt +
                ", deal_amount=" + deal_amount +
                ", coupon_amt=" + coupon_amt +
                ", deal_platcust='" + deal_platcust + '\'' +
                ", commission='" + commission + '\'' +
                ", commissionObject=" + commissionObject +
                ", commission_ext='" + commission_ext + '\'' +
                ", commission_extObject=" + commission_extObject +
                ", publish_date=" + publish_date +
                ", trans_date=" + trans_date +
                ", transfer_income=" + transfer_income +
                ", income_acct='" + income_acct + '\'' +
                ", related_prod_ids='" + related_prod_ids + '\'' +
                ", subject_priority='" + subject_priority + '\'' +
                ", interface_version='" + interface_version + '\'' +
                '}';
    }
}
