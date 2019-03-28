package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author bob
 *
 *代扣请求
 */
public class CollectionRequest extends BaseRequest {

    /*
     * 用户编号
     */
    @NotBlank
    private String platcust;
    /*
     * 姓名
     */
    @NotBlank
    private String name;
    /*
     * 银行卡号
     */
    @NotBlank
    private String  card_no;
    /*
     * 卡类型
     */
    private String card_type;
    /*
     * 币种
     */
    private String currency_code;
    /*
     * 证件类型
     */
    @NotBlank
    private String id_type;
    /*
     * 证件号
     */
    @NotBlank
    private String id_code;
    /*
     * 银行预留手机号
     */
    private String mobile;
    /*
     * 电子邮箱
     */
    private String email;
    /*
     * 充值金额
     */
    @NotNull
    private BigDecimal amt;
    /*
     * 支付通道
     */
    @NotBlank
    private String pay_code;
    /*
     * 投融资账户类型，1-投资账户  2-融资账户
     */
    private String charge_type ;
    /*
     * 北京银行或其他银行标识，1：北京银行，2:其他银行
     */
    private String branch_flag ;
    /*
     * 异步通知地址
     */
    @NotBlank
    private String notify_url;

    /*
     * 账户所在省
     * 当为中金支付，且账户是中国银行时，必填
     */
    private String province_code;
    /*
    * 账户所在市
    * 当为中金支付，且账户是中国银行时，必填
    */
    private String city_code;

    private String pay_channel;

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
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
    public String getCurrency_code() {
        return currency_code;
    }
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
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
    public BigDecimal getAmt() {
        return amt;
    }
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }
    public String getPay_code() {
        return pay_code;
    }
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }
    public String getCharge_type() {
        return charge_type;
    }
    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }
    public String getBranch_flag() {
        return branch_flag;
    }
    public void setBranch_flag(String branch_flag) {
        this.branch_flag = branch_flag;
    }
    public String getNotify_url() {
        return notify_url;
    }
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
    public String getProvince_code() {
        return province_code;
    }
    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }
    public String getCity_code() {
        return city_code;
    }
    public void setCity_code(String city_code) {
        this.city_code = city_code;
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
