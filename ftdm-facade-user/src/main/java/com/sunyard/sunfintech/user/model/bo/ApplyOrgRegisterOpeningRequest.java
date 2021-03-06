package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by my on 2018/4/20.
 */
public class ApplyOrgRegisterOpeningRequest extends BaseRequest implements Serializable {
    private String platcust;

    private String pre_mobile;

    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    /*public String getId_card() {
        return id_code;
    }

    public void setId_card(String id_card) {
        this.id_code = id_card;
    }*/

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    private String id_code;

    private String abroad_name;//境外开户姓名

    @NotBlank
    private String mobile;//	M	C(20)	手机号码
    private String email;//	O	C(30)	电子邮箱
    private String business_license;//	O	C(32)	营业执照编号（企业客户，营业执照编号、社会信用代码证要二选一）
    private String bank_license;//	O	C(32)	社会信用代码证（企业客户，营业执照编号、社会信用代码证要二选一）
    private String open_branch;//	O	C(32)	开户行（精确到支行）
    @NotBlank
    private String card_no;//	M	C(25)	卡号
    private String card_type;//	O	C(1)	卡类型(1:借记卡)
    @NotBlank
    private String id_type;//证件类型

    @NotBlank
    private String role;//	M	C（1）	角色（1：出借人、2：借款人、3：垫资人、4：担保人）
    @NotNull
    private BigDecimal authed_amount;//	M	N(19,2)	授权金额
    @NotBlank
    private String authed_limittime;//	M	C(8)	授权期限 YYYYMMDD
    @NotBlank
    private String authed_type;

    //	交易密码
    @NotBlank
    private String trans_pwd;

    //密码因子
    @NotBlank
    private String random_key;

    @NotBlank
    private String notify_url;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getId_type() {
        return id_type;
    }


    public String getAbroad_name() {
        return abroad_name;
    }

    public void setAbroad_name(String abroad_name) {
        this.abroad_name = abroad_name;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getBank_license() {
        return bank_license;
    }

    public void setBank_license(String bank_license) {
        this.bank_license = bank_license;
    }

    public String getOpen_branch() {
        return open_branch;
    }

    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getTrans_pwd() {
        return trans_pwd;
    }

    public void setTrans_pwd(String trans_pwd) {
        this.trans_pwd = trans_pwd;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}


