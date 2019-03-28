package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2018/1/7.
 */
public class  Confirm4ElementsRegisterRequest extends BaseRequest implements Serializable {

    //====================================内部页面参数=======================
    //短信验证码
    private String identifying_code;

    //原开户申请订单号
    private String origin_order_no;

    //密钥类型
    private String type;
    //======================================================================

    private String platcust;
    @NotBlank
    private String name;//	M	C(10)	用户姓名
    @NotBlank
    private String id_type;//	M	C(2)	证件类型（1：身份证）
    @NotBlank
    private String id_code;//	M	C(20)	证件号码
    @NotBlank
    private String mobile;//	M	C(20)	手机号码
    private String email;//	O	C(30)	电子邮箱
    private String sex;//	O	C(2)	性别（0:男性，1:女性）
    private String birthday;//	O	C(32)	出生日期
    private String open_branch;//	O	C(32)	开户行
    @NotBlank
    private String card_no;//	M	C(25)	卡号
    private String card_type;//	O	C(1)	卡类型(1:借记卡)
    @NotBlank
    private String pre_mobile;//	M	C(20)	预留手机号
    @NotBlank
    private String pay_code;//	M	C(20)	支付通道（可选银行智能绑卡通道）
    @NotBlank
    private String role;//	M	C（1）	角色（1：出借人、2：借款人、3：垫资人、4：担保人）
    @NotNull
    private BigDecimal authed_amount;//	M	N(19,2)	授权金额
    @NotBlank
    private String authed_limittime;//	M	C(8)	授权期限 YYYYMMDD
    @NotBlank
    private String authed_type;

    //	交易密码
    //@NotBlank
    private String trans_pwd;

    //密码因子
    //@NotBlank
    private String random_key;

    @NotBlank
    private String notify_url;

    public String getIdentifying_code() {
        return identifying_code;
    }

    public void setIdentifying_code(String identifying_code) {
        this.identifying_code = identifying_code;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Confirm4ElementsRegisterRequest that = (Confirm4ElementsRegisterRequest) o;

        if (identifying_code != null ? !identifying_code.equals(that.identifying_code) : that.identifying_code != null)
            return false;
        if (origin_order_no != null ? !origin_order_no.equals(that.origin_order_no) : that.origin_order_no != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (id_type != null ? !id_type.equals(that.id_type) : that.id_type != null) return false;
        if (id_code != null ? !id_code.equals(that.id_code) : that.id_code != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (open_branch != null ? !open_branch.equals(that.open_branch) : that.open_branch != null) return false;
        if (card_no != null ? !card_no.equals(that.card_no) : that.card_no != null) return false;
        if (card_type != null ? !card_type.equals(that.card_type) : that.card_type != null) return false;
        if (pre_mobile != null ? !pre_mobile.equals(that.pre_mobile) : that.pre_mobile != null) return false;
        if (pay_code != null ? !pay_code.equals(that.pay_code) : that.pay_code != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (authed_amount != null ? !authed_amount.equals(that.authed_amount) : that.authed_amount != null)
            return false;
        if (authed_limittime != null ? !authed_limittime.equals(that.authed_limittime) : that.authed_limittime != null)
            return false;
        if (authed_type != null ? !authed_type.equals(that.authed_type) : that.authed_type != null) return false;
        if (trans_pwd != null ? !trans_pwd.equals(that.trans_pwd) : that.trans_pwd != null) return false;
        if (random_key != null ? !random_key.equals(that.random_key) : that.random_key != null) return false;
        return notify_url != null ? notify_url.equals(that.notify_url) : that.notify_url == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (identifying_code != null ? identifying_code.hashCode() : 0);
        result = 31 * result + (origin_order_no != null ? origin_order_no.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (id_type != null ? id_type.hashCode() : 0);
        result = 31 * result + (id_code != null ? id_code.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (open_branch != null ? open_branch.hashCode() : 0);
        result = 31 * result + (card_no != null ? card_no.hashCode() : 0);
        result = 31 * result + (card_type != null ? card_type.hashCode() : 0);
        result = 31 * result + (pre_mobile != null ? pre_mobile.hashCode() : 0);
        result = 31 * result + (pay_code != null ? pay_code.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (authed_amount != null ? authed_amount.hashCode() : 0);
        result = 31 * result + (authed_limittime != null ? authed_limittime.hashCode() : 0);
        result = 31 * result + (authed_type != null ? authed_type.hashCode() : 0);
        result = 31 * result + (trans_pwd != null ? trans_pwd.hashCode() : 0);
        result = 31 * result + (random_key != null ? random_key.hashCode() : 0);
        result = 31 * result + (notify_url != null ? notify_url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Confirm4ElementsRegisterRequest{" +
                "identifying_code='" + identifying_code + '\'' +
                ", origin_order_no='" + origin_order_no + '\'' +
                ", type='" + type + '\'' +
                ", platcust='" + platcust + '\'' +
                ", name='" + name + '\'' +
                ", id_type='" + id_type + '\'' +
                ", id_code='" + id_code + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", open_branch='" + open_branch + '\'' +
                ", card_no='" + card_no + '\'' +
                ", card_type='" + card_type + '\'' +
                ", pre_mobile='" + pre_mobile + '\'' +
                ", pay_code='" + pay_code + '\'' +
                ", role='" + role + '\'' +
                ", authed_amount=" + authed_amount +
                ", authed_limittime='" + authed_limittime + '\'' +
                ", authed_type='" + authed_type + '\'' +
                ", trans_pwd='" + trans_pwd + '\'' +
                ", random_key='" + random_key + '\'' +
                ", notify_url='" + notify_url + '\'' +
                '}';
    }
}
