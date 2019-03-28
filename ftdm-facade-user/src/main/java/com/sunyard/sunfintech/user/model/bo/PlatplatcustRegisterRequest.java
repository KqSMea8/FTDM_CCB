package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by dany on 2017/6/1.
 */
public class PlatplatcustRegisterRequest extends BaseRequest {

    //平台客户电子账户 老用户输入
    private String platcust;

    //证件类型（1：身份证）,客户必填
    @NotBlank
    private String id_type;

    //证件号码,客户必填
    @NotBlank
    private String id_code;

    //姓名,客户必填
    @NotBlank
    private String name;

    //注冊手機號,客户必填
    @NotBlank
    private String mobile;

    //卡号，客户必填
    @NotBlank
    private String card_no;

    //账号密码，客户必填
    @NotBlank
    private String bank_acct_pwd;

    //预留手机号(必填)
    @NotBlank
    private String pre_mobile;

    //异步通知地址，企業客户必填
    private String notify_url;

    //@NotBlank
    private String terminal_type;

//    @NotBlank
    private String random_key;

    //@NotNull
    private BigDecimal authed_amount;//	M	N(19,2)	授权金额

    //@NotBlank
    private String authed_limittime;//	M	C(8)	授权期限 YYYYMMDD

    //@NotBlank
    private String authed_type;

    //@NotBlank
    private String role;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getBank_acct_pwd() {
        return bank_acct_pwd;
    }

    public void setBank_acct_pwd(String bank_acct_pwd) {
        this.bank_acct_pwd = bank_acct_pwd;
    }

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    public BigDecimal getAuthed_amount() {
        return authed_amount;
    }

    public void setAuthed_amount(BigDecimal authed_amount) {
        this.authed_amount = authed_amount;
    }

    public String getAuthed_limittime() {
        return authed_limittime;
    }

    public void setAuthed_limittime(String authed_limittime) {
        this.authed_limittime = authed_limittime;
    }

    public String getAuthed_type() {
        return authed_type;
    }

    public void setAuthed_type(String authed_type) {
        this.authed_type = authed_type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "PlatplatcustRegisterRequest{" +
                "platcust='" + platcust + '\'' +
                ", id_type='" + id_type + '\'' +
                ", id_code='" + id_code + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", card_no='" + card_no + '\'' +
                ", bank_acct_pwd='" + bank_acct_pwd + '\'' +
                ", pre_mobile='" + pre_mobile + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", terminal_type='" + terminal_type + '\'' +
                ", random_key='" + random_key + '\'' +
                ", authed_amount=" + authed_amount +
                ", authed_limittime='" + authed_limittime + '\'' +
                ", authed_type='" + authed_type + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}