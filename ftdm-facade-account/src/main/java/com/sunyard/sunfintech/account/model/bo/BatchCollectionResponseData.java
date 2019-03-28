package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;

/**
 * Created by terry on 2017/7/16.
 */
public class BatchCollectionResponseData implements Serializable {

     private String partner_id;	//C8	合作商编号
     private String partner_serial_no;	//C32	合作商流水
     private String pay_status;	//C1	支付状态
     private String pay_finish_date;	//C8	支付完成日期
     private String channel_id;	//C32	支付通道ID
     private String cert_sign;	//C500	签名串

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

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "BatchCollectionResponseData{" +
                "partner_id='" + partner_id + '\'' +
                ", partner_serial_no='" + partner_serial_no + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", pay_finish_date='" + pay_finish_date + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
