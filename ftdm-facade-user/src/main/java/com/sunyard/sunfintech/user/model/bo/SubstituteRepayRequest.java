package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 标的代偿（委托）还款
 * Created by wubin on 2017/5/3.
 */
public class SubstituteRepayRequest extends BaseRequest{

    /**
     * 产品编号
     */
    @NotBlank
    private String prod_id;

    /**
     * 还款期数
     */
    @NotNull
    private Integer repay_num;

    /**
     * 计划还款日期
     */
    @NotBlank

    private String repay_date;

    /**
     * 计划还款金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal repay_amt;

    /**
     * 实际还款日期
     */
    @NotBlank
    private String real_repay_date;

    /**
     * 实际还款金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal real_repay_amt;

    /**
     * 平台客户编号（借款人）
     */
    @NotBlank
    private String platcust;

    /**
     * 代偿人平台客户编号
     */
    @NotBlank
    private String compensation_platcust;

    /**
     * 交易金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal trans_amt;

    /**
     * 还款类型 0-代偿还款 1-委托还款 2-平台风险金代偿
     */
    @NotBlank
    private String repay_type;

    /**
     * 交易密码
     */
    private String trans_pwd;

    /**
     * 交易密码随机数
     */
    private String random_key;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public Integer getRepay_num() {
        return repay_num;
    }

    public void setRepay_num(Integer repay_num) {
        this.repay_num = repay_num;
    }

    public String getRepay_date() {
        return repay_date;
    }

    public void setRepay_date(String repay_date) {
        this.repay_date = repay_date;
    }

    public BigDecimal getRepay_amt() {
        return repay_amt;
    }

    public void setRepay_amt(BigDecimal repay_amt) {
        this.repay_amt = repay_amt;
    }

    public String getReal_repay_date() {
        return real_repay_date;
    }

    public void setReal_repay_date(String real_repay_date) {
        this.real_repay_date = real_repay_date;
    }

    public BigDecimal getReal_repay_amt() {
        return real_repay_amt;
    }

    public void setReal_repay_amt(BigDecimal real_repay_amt) {
        this.real_repay_amt = real_repay_amt;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getCompensation_platcust() {
        return compensation_platcust;
    }

    public void setCompensation_platcust(String compensation_platcust) {
        this.compensation_platcust = compensation_platcust;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public String getRepay_type() {
        return repay_type;
    }

    public void setRepay_type(String repay_type) {
        this.repay_type = repay_type;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    public String getTrans_pwd() {
        return trans_pwd;
    }

    public void setTrans_pwd(String trans_pwd) {
        this.trans_pwd = trans_pwd;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
