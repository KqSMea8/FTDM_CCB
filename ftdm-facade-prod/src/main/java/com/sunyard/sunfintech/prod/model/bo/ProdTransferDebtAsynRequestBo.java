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
public class ProdTransferDebtAsynRequestBo extends BaseRequest {

    /**
     *转让人平台客户号
     */
    @NotBlank
    private String platcust;

    /**
     * 异步通知地址
     */
    @NotBlank
    private String notify_url;

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
    @NotNull
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

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
