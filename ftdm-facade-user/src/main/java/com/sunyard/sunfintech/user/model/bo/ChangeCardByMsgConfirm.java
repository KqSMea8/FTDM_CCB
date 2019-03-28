package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class ChangeCardByMsgConfirm extends BaseRequest {
    @NotBlank
    private String platcust;     //平台客户编号：已实名认证用户，参数必填
    @NotBlank
    private String name;         //	姓名
    @NotBlank
    private String card_no_old;  //原卡号
    @NotBlank
    private String mobile_old;   //原卡预留手机号
    @NotBlank
    private String card_no;      //新卡号
    @NotBlank
    private String mobile;       //新预留手机号
    @NotBlank
    private String pay_code;     //支付通道（可选银行智能绑卡通道）
    @NotBlank
    private String origin_order_no;//原绑卡申请订单号

    @NotBlank
    private String identifying_code;

    public String getIdentifying_code() {
        return identifying_code;
    }

    public void setIdentifying_code(String identifying_code) {
        this.identifying_code = identifying_code;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_no_old() {
        return card_no_old;
    }

    public void setCard_no_old(String card_no_old) {
        this.card_no_old = card_no_old;
    }

    public String getMobile_old() {
        return mobile_old;
    }

    public void setMobile_old(String mobile_old) {
        this.mobile_old = mobile_old;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
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
