package com.sunyard.sunfintech.web.model.vo.product;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestConfirmRequest extends BaseRequest {

    @NotBlank
    private String platcust;

    @NotBlank
    private String prod_id;

    @NotNull
    private BigDecimal trans_amt;

    private BigDecimal experience_amt;

    private BigDecimal coupon_amt;

    private BigDecimal self_amt;

    private BigDecimal in_interest;

    @NotBlank
    private String subject_priority;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        super.setBase_serial_plutcust(platcust);
        this.platcust = platcust;
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
}
