package com.sunyard.sunfintech.user.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author PengZY
 * Created by PengZY on 2017/5/26.
 */
public class EaccFinancinfoDetail implements Serializable {

    @NotBlank
    private String cust_no;//融资人的平台客户编号

    @NotBlank
    private String trustee_platcust;// 受托人客户号

    @NotBlank
    private String reg_date;//申请日期（YYYY-MM-DD）

    @NotBlank
    private String reg_time;//申请时间（HH:mm:ss）

    @NotNull
    private BigDecimal financ_int;//融资利息（eg：0.12）

    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal fee_int;//手续费（eg：0.01）

    private String financ_purpose;//借款用途[限制100字符以内]

    private String use_date;//用款日期（YYYY-MM-DD）

    private String bank_no;//大额行号

    private String city_code;//城市编码

    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal financ_amt;//融资金额[N(19,2)]

    private String open_branch;//产品收款人开户行

    private String withdraw_account;//产品收款人银行卡号

    private String account_type;// 卡类型(1-个人 2-企业)

    private String payee_name;//产品收款人姓名

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getTrustee_platcust() {
        return trustee_platcust;
    }

    public void setTrustee_platcust(String trustee_platcust) {
        this.trustee_platcust = trustee_platcust;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public BigDecimal getFinanc_int() {
        return financ_int;
    }

    public void setFinanc_int(BigDecimal financ_int) {
        this.financ_int = financ_int;
    }

    public BigDecimal getFee_int() {
        return fee_int;
    }

    public void setFee_int(BigDecimal fee_int) {
        this.fee_int = fee_int;
    }

    public String getFinanc_purpose() {
        return financ_purpose;
    }

    public void setFinanc_purpose(String financ_purpose) {
        this.financ_purpose = financ_purpose;
    }

    public String getUse_date() {
        return use_date;
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public BigDecimal getFinanc_amt() {
        return financ_amt;
    }

    public void setFinanc_amt(BigDecimal financ_amt) {
        this.financ_amt = financ_amt;
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

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getPayee_name() {
        return payee_name;
    }

    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name;
    }

    @Override
    public String toString() {
        return "EaccFinancinfoDetail{" +
                "cust_no='" + cust_no + '\'' +
                ", trustee_platcust='" + trustee_platcust + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", financ_int=" + financ_int +
                ", fee_int=" + fee_int +
                ", financ_purpose='" + financ_purpose + '\'' +
                ", use_date='" + use_date + '\'' +
                ", bank_no='" + bank_no + '\'' +
                ", city_code='" + city_code + '\'' +
                ", financ_amt=" + financ_amt +
                ", open_branch='" + open_branch + '\'' +
                ", withdraw_account='" + withdraw_account + '\'' +
                ", account_type='" + account_type + '\'' +
                ", payee_name='" + payee_name + '\'' +
                '}';
    }
}
