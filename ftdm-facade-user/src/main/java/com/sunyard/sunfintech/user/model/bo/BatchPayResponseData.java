package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by terry on 2017/7/13.
 */
public class BatchPayResponseData {

    private String partner_serial_no;
    private String pay_status;
    private String pay_finish_date;
    private String channel_id;
    private String cert_sign;
    private String self_bank_flag;

    public String getSelf_bank_flag() {
        return self_bank_flag;
    }

    public void setSelf_bank_flag(String self_bank_flag) {
        this.self_bank_flag = self_bank_flag;
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
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "BatchPayResponseData{" +
                "partner_serial_no='" + partner_serial_no + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", pay_finish_date='" + pay_finish_date + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
