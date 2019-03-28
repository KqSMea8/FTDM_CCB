package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;

/**
 * Created by terry on 2017/6/27.
 */
public class EpayAgentPayRealResponseDetail implements Serializable {
    /**
     * 合作商编号
     */
    private String partner_id;

    /**
     * 合作商流水
     */
    private String partner_serial_no;

    /**
     * 客户姓名
     */
    private String client_name;

    /**
     *交易金额
     */
    private String occur_balance;

    /**
     *订单号
     */
    private String hsepay_order_no;

    /**
     *支付状态
     */
    private String pay_status;

    /**
     *支付完成日期
     */
    private String pay_finish_date;

    /**
     *上送支付通道流水号（惠付）
     */
    private String host_req_serial_no;

    /**
     *签名串
     */
    private String cert_sign;

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

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(String occur_balance) {
        this.occur_balance = occur_balance;
    }

    public String getHsepay_order_no() {
        return hsepay_order_no;
    }

    public void setHsepay_order_no(String hsepay_order_no) {
        this.hsepay_order_no = hsepay_order_no;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_finish_date() {
        return pay_finish_date;
    }

    public void setPay_finish_date(String pay_finish_date) {
        this.pay_finish_date = pay_finish_date;
    }

    public String getHost_req_serial_no() {
        return host_req_serial_no;
    }

    public void setHost_req_serial_no(String host_req_serial_no) {
        this.host_req_serial_no = host_req_serial_no;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "EpayAgentPayRealResponseDetail{" +
                "partner_id='" + partner_id + '\'' +
                ", partner_serial_no='" + partner_serial_no + '\'' +
                ", client_name='" + client_name + '\'' +
                ", occur_balance='" + occur_balance + '\'' +
                ", hsepay_order_no='" + hsepay_order_no + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", pay_finish_date='" + pay_finish_date + '\'' +
                ", host_req_serial_no='" + host_req_serial_no + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
