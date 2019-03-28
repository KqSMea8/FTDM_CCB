package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class ChangeCardByMsgRequest extends BaseRequest {
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
    private String pre_mobile;       //新预留手机号
    @NotBlank
    private String pay_code;     //支付通道（可选银行智能绑卡通道）

    private String card_type;

    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
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


    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
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
