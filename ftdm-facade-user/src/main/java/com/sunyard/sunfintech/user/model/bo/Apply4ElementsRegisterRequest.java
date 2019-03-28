package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2018/1/6.
 */
public class Apply4ElementsRegisterRequest extends BaseRequest {

    //======页面所需数据=====
    //开户页面申请订单号
    private String origin_order_no;

    //=====================================

    //证件类型（1：身份证）,个人客户必填
    @NotBlank
    private String id_type;

    //证件号码,个人客户必填
    @NotBlank
    private String id_code;

    //姓名,个人客户必填
    @NotBlank
    private String name;

    private String email;

    @NotBlank
    private String mobile;

    private String sex;

    private String birthday;

    //卡号，个人客户必填
    private String card_no;

    //卡类型(1:借记卡，2:信用卡)
    private String card_type;

    //预留手机号(如果此手机号和注册的手机号不一致时，则必填)
    private String pre_mobile;

    //开户银行（精确到分行），对公客户必填
    private String open_branch;

    //支付通道
    @NotBlank
    private String pay_code;

    //角色（1：出借人、2：借款人、3：垫资人、4：担保人）
    @NotBlank
    private String role;

    //授权类型
    @NotBlank
    private String authed_type;

    //授权金额
    @NotNull
    private BigDecimal authed_amount;

    //授权期限 YYYYMMDD
    @NotBlank
    private String authed_limittime;

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getOpen_branch() {
        return open_branch;
    }

    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch;
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
}
