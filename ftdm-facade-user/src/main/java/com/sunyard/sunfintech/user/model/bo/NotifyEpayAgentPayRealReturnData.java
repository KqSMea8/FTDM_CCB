package com.sunyard.sunfintech.user.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by PengZY on 2017/7/12.
 */
public class NotifyEpayAgentPayRealReturnData implements Serializable {

    //合作商编号
    @NotBlank
    private String partner_id;

    //合作商交易流水号
    @NotBlank
    private String partner_serial_no;

    //平台订单号
    @NotBlank
    private String hsepay_order_no;

    //商户订单金额
    @NotNull
    private String order_balance;

    //代付日期
    private String df_trans_date;

    //支付完成日期
    private String pay_finish_date;

    //支付完成时间
    private String pay_finish_time;

    //订单实际支付金额
    private String real_pay_balance;

    //支付状态
    private String pay_status;

    //失败原因
    private String fail_cause;

    //失败原因对应的响应码
    private String fail_code;

    //支付通道编号
    private String channel_id;

    //支付通道流水号
    private String channel_serial_no;

    //签名串
    private String cert_sign;

    public String getDf_trans_date() {
        return df_trans_date;
    }

    public void setDf_trans_date(String df_trans_date) {
        this.df_trans_date = df_trans_date;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getPartner_serial_no() {
        return partner_serial_no;
    }

    public void setPartner_serial_no(String partner_serial_no) {
        this.partner_serial_no = partner_serial_no;
    }

    public String getHsepay_order_no() {
        return hsepay_order_no;
    }

    public void setHsepay_order_no(String hsepay_order_no) {
        this.hsepay_order_no = hsepay_order_no;
    }

    public String getOrder_balance() {
        return order_balance;
    }

    public void setOrder_balance(String order_balance) {
        this.order_balance = order_balance;
    }

    public String getPay_finish_date() {
        return pay_finish_date;
    }

    public void setPay_finish_date(String pay_finish_date) {
        this.pay_finish_date = pay_finish_date;
    }

    public String getPay_finish_time() {
        return pay_finish_time;
    }

    public void setPay_finish_time(String pay_finish_time) {
        this.pay_finish_time = pay_finish_time;
    }

    public String getReal_pay_balance() {
        return real_pay_balance;
    }

    public void setReal_pay_balance(String real_pay_balance) {
        this.real_pay_balance = real_pay_balance;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getFail_cause() {
        return fail_cause;
    }

    public void setFail_cause(String fail_cause) {
        this.fail_cause = fail_cause;
    }

    public String getFail_code() {
        return fail_code;
    }

    public void setFail_code(String fail_code) {
        this.fail_code = fail_code;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_serial_no() {
        return channel_serial_no;
    }

    public void setChannel_serial_no(String channel_serial_no) {
        this.channel_serial_no = channel_serial_no;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "NotifyEpayAgentPayRealReturnData{" +
                "partner_id='" + partner_id + '\'' +
                ", partner_serial_no='" + partner_serial_no + '\'' +
                ", hsepay_order_no='" + hsepay_order_no + '\'' +
                ", order_balance='" + order_balance + '\'' +
                ", df_trans_date='" + df_trans_date + '\'' +
                ", pay_finish_date='" + pay_finish_date + '\'' +
                ", pay_finish_time='" + pay_finish_time + '\'' +
                ", real_pay_balance='" + real_pay_balance + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", fail_cause='" + fail_cause + '\'' +
                ", fail_code='" + fail_code + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", channel_serial_no='" + channel_serial_no + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
