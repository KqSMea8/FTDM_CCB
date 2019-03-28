package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseModel;
import com.sunyard.sunfintech.core.base.BaseSerialNoRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestDataTb extends BaseSerialNoRequest {
public ProdInvestData getAsyncData(String proId) {
    ProdInvestData prodInvestData = new ProdInvestData();
    BeanUtils.copyProperties(this, prodInvestData);
    prodInvestData.setProd_id(proId);
    return prodInvestData;
}
    /**
     * 明细编号
     */
    @NotBlank
    private String detail_no;

    /**
     * 投资人编号
     */
    @NotBlank
    private String platcust;

    /**
     * 交易金额
     */
    @NotNull
    private BigDecimal trans_amt;

    /**
     * 体验金金额
     */
    private BigDecimal experience_amt;

    /**
     * 抵用券金额
     */
    private BigDecimal coupon_amt;

    /**
     * 自费金额
     */
    private BigDecimal self_amt;

    /**
     * 加息
     */
    private BigDecimal in_interest;

    /**
     * 科目优先级
     */
    @NotBlank
    private String subject_priority;

    private String notify_url;

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        super.setBase_serial_order_no(detail_no);
        this.detail_no = detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        super.setBase_serial_plutcust(platcust);
        this.platcust = platcust;
    }
    /**
     * 手续费说明
     */
    private String commission;

    private Commission commissionObj;
    public Commission getCommissionObj() {
        return commissionObj;
    }

    public void setCommissionObj(Commission commissionObj) {
        this.commissionObj = commissionObj;
    }
    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public BigDecimal getExperience_amt() {
        return experience_amt;
    }

    public void setExperience_amt(BigDecimal experience_amt) {
        this.experience_amt = experience_amt;
    }

    public BigDecimal getCoupon_amt() {
        return coupon_amt;
    }

    public void setCoupon_amt(BigDecimal coupon_amt) {
        this.coupon_amt = coupon_amt;
    }

    public BigDecimal getSelf_amt() {
        return self_amt;
    }

    public void setSelf_amt(BigDecimal self_amt) {
        this.self_amt = self_amt;
    }

    public BigDecimal getIn_interest() {
        return in_interest;
    }

    public void setIn_interest(BigDecimal in_interest) {
        this.in_interest = in_interest;
    }

    public String getSubject_priority() {
        return subject_priority;
    }

    public void setSubject_priority(String subject_priority) {
        this.subject_priority = subject_priority;
    }

    @Override
    public String toString() {
        return "ProdInvestDataTb{" +
                "detail_no='" + detail_no + '\'' +
                ", platcust='" + platcust + '\'' +
                ", trans_amt=" + trans_amt +
                ", experience_amt=" + experience_amt +
                ", coupon_amt=" + coupon_amt +
                ", self_amt=" + self_amt +
                ", in_interest=" + in_interest +
                ", subject_priority='" + subject_priority + '\'' +
                '}';
    }
}
