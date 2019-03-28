package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;

/**
 * Created by terry on 2017/9/13.
 */
public class BankReverseResponseDetail implements Serializable {
    private String partner_id;
    private String pay_status;
    private String original_serial_no;
    private String occur_balance;
    private String respMsg;
    private String cert_sign;

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getOriginal_serial_no() {
        return original_serial_no;
    }

    public void setOriginal_serial_no(String original_serial_no) {
        this.original_serial_no = original_serial_no;
    }

    public String getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(String occur_balance) {
        this.occur_balance = occur_balance;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "BankReverseResponseDetail{" +
                "partner_id='" + partner_id + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", original_serial_no='" + original_serial_no + '\'' +
                ", occur_balance='" + occur_balance + '\'' +
                ", respMsg='" + respMsg + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}

