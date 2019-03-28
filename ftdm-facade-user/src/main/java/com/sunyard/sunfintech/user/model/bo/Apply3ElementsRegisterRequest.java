package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2018/1/8.
 */
public class Apply3ElementsRegisterRequest extends BaseRequest implements Serializable {

    //===========================内部页面参数=====================
    //流水号
    private String trans_serial;

    //原开户申请订单号
    private String origin_order_no;

    private String type;
    //=============================================================

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
    private String pay_code;//	M	C(20)	支付通道（可选银行智能绑卡通道）
    @NotBlank
    private String role;//	M	C（10）	角色（1：出借人、2：借款人、3：垫资人、4：担保人）
    @NotNull
    private BigDecimal authed_amount;//	M	N(19,2)	授权金额
    @NotBlank
    private String authed_limittime;//	M	C(8)	授权期限 YYYYMMDD
    @NotBlank
    private String authed_type;
    @NotBlank
    private String trans_pwd;//	M	C(100)	交易密码
    @NotBlank
    private String random_key;// 	M	C(100)	交易密码随机数

    @NotBlank
    private String notify_url;

    public String getTrans_serial() {
        return trans_serial;
    }

    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}