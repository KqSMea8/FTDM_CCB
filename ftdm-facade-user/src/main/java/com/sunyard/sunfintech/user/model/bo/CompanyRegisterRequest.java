package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CompanyRegisterRequest extends BaseRequest {

    private String pay_code;

    private String platcust;

    /**
     * 企业名称
     */
    @NotBlank
    private String org_name;

    /**
     * 手机号码
     */
    @NotBlank
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 营业执照编号（企业客户，营业执照编号、社会信用代码证要二选一）
     */
    private String business_license;

    /**
     * 社会信用代码证（企业客户，营业执照编号、社会信用代码证要二选一）
     */
    private String bank_license;

    /**
     * 开户行（精确到支行）
     */
    private String open_branch;

    /**
     * 卡号
     */
    @NotBlank
    private String card_no;

    /**
     * 卡类型(1:借记卡)
     */
    private String card_type;

    /**
     * 角色（1：出借人、2：借款人、3：垫资人、4：担保人）多角色用,（英文）分割。
     */
    @NotBlank
    private String role;

    /**
     * 授权类型（1、出借，2、还款，3、缴费，4、全部）
     */
    @NotBlank
    String authed_type;

    /**
     * 授权金额
     */
    @NotNull
    @DecimalMin(value = "0.001")
    private BigDecimal authed_amount;

    /**
     * 授权期限 YYYYMMDD
     */
    @NotBlank
    private String authed_limittime;

    /**
     * 同步回调地址
     */
    @NotBlank
    private String return_url;


    private  String abroad_name;



    /**
     * 异步通知地址，对公客户必填
     */
    private String notify_url;

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    private String id_code;

    private String id_type;

    private String pre_mobile;



    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getAbroad_name() {
        return abroad_name;
    }

    public void setAbroad_name(String abroad_name) {
        this.abroad_name = abroad_name;
    }
    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
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

    public String getAuthed_type() {
        return authed_type;
    }

    public void setAuthed_type(String authed_type) {
        this.authed_type = authed_type;
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

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CompanyRegisterRequest that = (CompanyRegisterRequest) o;

        if (pay_code != null ? !pay_code.equals(that.pay_code) : that.pay_code != null) return false;
        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (org_name != null ? !org_name.equals(that.org_name) : that.org_name != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (business_license != null ? !business_license.equals(that.business_license) : that.business_license != null)
            return false;
        if (bank_license != null ? !bank_license.equals(that.bank_license) : that.bank_license != null) return false;
        if (open_branch != null ? !open_branch.equals(that.open_branch) : that.open_branch != null) return false;
        if (card_no != null ? !card_no.equals(that.card_no) : that.card_no != null) return false;
        if (card_type != null ? !card_type.equals(that.card_type) : that.card_type != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (authed_type != null ? !authed_type.equals(that.authed_type) : that.authed_type != null) return false;
        if (authed_amount != null ? !authed_amount.equals(that.authed_amount) : that.authed_amount != null)
            return false;
        if (authed_limittime != null ? !authed_limittime.equals(that.authed_limittime) : that.authed_limittime != null)
            return false;
        if (return_url != null ? !return_url.equals(that.return_url) : that.return_url != null) return false;
        return notify_url != null ? notify_url.equals(that.notify_url) : that.notify_url == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (pay_code != null ? pay_code.hashCode() : 0);
        result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
        result = 31 * result + (org_name != null ? org_name.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (business_license != null ? business_license.hashCode() : 0);
        result = 31 * result + (bank_license != null ? bank_license.hashCode() : 0);
        result = 31 * result + (open_branch != null ? open_branch.hashCode() : 0);
        result = 31 * result + (card_no != null ? card_no.hashCode() : 0);
        result = 31 * result + (card_type != null ? card_type.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (authed_type != null ? authed_type.hashCode() : 0);
        result = 31 * result + (authed_amount != null ? authed_amount.hashCode() : 0);
        result = 31 * result + (authed_limittime != null ? authed_limittime.hashCode() : 0);
        result = 31 * result + (return_url != null ? return_url.hashCode() : 0);
        result = 31 * result + (notify_url != null ? notify_url.hashCode() : 0);
        return result;
    }

}
