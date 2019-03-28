package com.sunyard.sunfintech.prod.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdChargeOffRequestChargeOffList {

    /**
     * 垫付 2:垫付，1：不垫付
     */
    private String is_advance;

    /**
     * 融资人的平台客户号
     */
    @NotBlank
    private String platcust;

    /**
     * 真实融资金额（出账金额）
     */
    @NotNull
    private BigDecimal out_amt;

    /**
     * 产品收款人开户行
     */
    @NotBlank
    private String open_branch;

    /**
     * 产品收款人银行卡号
     */
    @NotBlank
    private String withdraw_account;

    /**
     * 产品收款人户名
     */
    @NotBlank
    private String payee_name;

    /**
     * 公私标示(0-个人 1-公司)
     */
    private String client_property;

    /**
     * 城市编码（富友必填）
     */
    private String city_code;

    public String getIs_advance() {
        return is_advance;
    }

    public void setIs_advance(String is_advance) {
        this.is_advance = is_advance;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public BigDecimal getOut_amt() {
        return out_amt;
    }

    public void setOut_amt(BigDecimal out_amt) {
        this.out_amt = out_amt;
    }

    public String getOpen_branch() {
        return open_branch;
    }

    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch;
    }

    public String getWithdraw_account() {
        return withdraw_account;
    }

    public void setWithdraw_account(String withdraw_account) {
        this.withdraw_account = withdraw_account;
    }

    public String getPayee_name() {
        return payee_name;
    }

    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name;
    }

    public String getClient_property() {
        return client_property;
    }

    public void setClient_property(String client_property) {
        this.client_property = client_property;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }
}
