package com.sunyard.sunfintech.thirdparty.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by my on 2018/5/17.
 */
public class WithholdResponse implements Serializable {

    private String total_num;
    private String total_balance;
    private String detail_no;
    private BigDecimal occur_balance;
    private String summary;
    private String client_name;
    private String card_no;
    private String status;
    private String batch_order_no;
    private String partner_serial_no;
    private String original_serial_no;


    private String fail_cause;

    public String getFail_cause() {
        return fail_cause;
    }

    public void setFail_cause(String fail_cause) {
        this.fail_cause = fail_cause;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(String total_balance) {
        this.total_balance = total_balance;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public BigDecimal getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(BigDecimal occur_balance) {
        this.occur_balance = occur_balance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String pay_status) {
        this.status = pay_status;
    }

    public String getBatch_order_no() {
        return batch_order_no;
    }

    public void setBatch_order_no(String batch_order_no) {
        this.batch_order_no = batch_order_no;
    }

    public String getPartner_serial_no() {
        return partner_serial_no;
    }

    public void setPartner_serial_no(String partner_serial_no) {
        this.partner_serial_no = partner_serial_no;
    }

    public String getOriginal_serial_no() {
        return original_serial_no;
    }

    public void setOriginal_serial_no(String original_serial_no) {
        this.original_serial_no = original_serial_no;
    }
}
