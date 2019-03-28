package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;

/**
 * Created by terry on 2017/9/8.
 */
public class QueryPayStatusResponseDetail implements Serializable {
    private String pay_status;
    private String paycheck_date;
    private String original_serial_no;
    private String occur_balance;
    private String cert_sign;

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPaycheck_date() {
        return paycheck_date;
    }

    public void setPaycheck_date(String paycheck_date) {
        this.paycheck_date = paycheck_date;
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

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "QueryPayStatusResponseDetail{" +
                "pay_status='" + pay_status + '\'' +
                ", paycheck_date='" + paycheck_date + '\'' +
                ", original_serial_no='" + original_serial_no + '\'' +
                ", occur_balance='" + occur_balance + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
