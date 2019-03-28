package com.sunyard.sunfintech.web.model.vo.account;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by dany on 2017/6/1.
 */
public class PlatplatcustRegisterForRegularSubscribersRequest extends BaseRequest {

    //平台客户电子账户 老用户输入
    @NotBlank
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
    private String bank_acct_pwd;

    //预留手机号(必填)
    @NotBlank
    private String pre_mobile;

    //异步通知地址，企業客户必填
    private String notify_url;

    //@NotBlank
    private String terminal_type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatplatcustRegisterForRegularSubscribersRequest that = (PlatplatcustRegisterForRegularSubscribersRequest) o;

        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (id_type != null ? !id_type.equals(that.id_type) : that.id_type != null) return false;
        if (id_code != null ? !id_code.equals(that.id_code) : that.id_code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (card_no != null ? !card_no.equals(that.card_no) : that.card_no != null) return false;
        if (bank_acct_pwd != null ? !bank_acct_pwd.equals(that.bank_acct_pwd) : that.bank_acct_pwd != null)
            return false;
        if (pre_mobile != null ? !pre_mobile.equals(that.pre_mobile) : that.pre_mobile != null) return false;
        if (notify_url != null ? !notify_url.equals(that.notify_url) : that.notify_url != null) return false;
        return terminal_type != null ? terminal_type.equals(that.terminal_type) : that.terminal_type == null;
    }

    @Override
    public int hashCode() {
        int result = platcust != null ? platcust.hashCode() : 0;
        result = 31 * result + (id_type != null ? id_type.hashCode() : 0);
        result = 31 * result + (id_code != null ? id_code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (card_no != null ? card_no.hashCode() : 0);
        result = 31 * result + (bank_acct_pwd != null ? bank_acct_pwd.hashCode() : 0);
        result = 31 * result + (pre_mobile != null ? pre_mobile.hashCode() : 0);
        result = 31 * result + (notify_url != null ? notify_url.hashCode() : 0);
        result = 31 * result + (terminal_type != null ? terminal_type.hashCode() : 0);
        return result;
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
                '}';
    }
}