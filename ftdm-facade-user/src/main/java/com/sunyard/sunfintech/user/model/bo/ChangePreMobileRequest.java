package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class ChangePreMobileRequest extends BaseRequest {
    /**
     * 平台客户编号
     */
    @NotBlank
    private String platcust;

    /**
     * 姓名
     */
    @NotBlank
    private String name;

    /**
     * 原卡号
     */
    @NotBlank
    private String card_no_old;

    /**
     * 原卡预留手机号
     */
    @NotBlank
    private String mobile_old;

    /**
     * 新预留手机号
     */
    @NotBlank
    private String pre_mobile;

    /**
     * 支付通道（增加银行智能路由通道编号）
     */
    @NotBlank
    private String pay_code;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangePreMobileRequest that = (ChangePreMobileRequest) o;

        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (card_no_old != null ? !card_no_old.equals(that.card_no_old) : that.card_no_old != null) return false;
        if (mobile_old != null ? !mobile_old.equals(that.mobile_old) : that.mobile_old != null) return false;
        if (pre_mobile != null ? !pre_mobile.equals(that.pre_mobile) : that.pre_mobile != null) return false;
        return pay_code != null ? pay_code.equals(that.pay_code) : that.pay_code == null;
    }

    @Override
    public int hashCode() {
        int result = platcust != null ? platcust.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (card_no_old != null ? card_no_old.hashCode() : 0);
        result = 31 * result + (mobile_old != null ? mobile_old.hashCode() : 0);
        result = 31 * result + (pre_mobile != null ? pre_mobile.hashCode() : 0);
        result = 31 * result + (pay_code != null ? pay_code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChangePreMobileRequest{" +
                "platcust='" + platcust + '\'' +
                ", name='" + name + '\'' +
                ", card_no_old='" + card_no_old + '\'' +
                ", mobile_old='" + mobile_old + '\'' +
                ", pre_mobile='" + pre_mobile + '\'' +
                ", pay_code='" + pay_code + '\'' +
                '}';
    }
}
