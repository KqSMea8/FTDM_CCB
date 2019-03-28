package com.sunyard.sunfintech.user.model.bo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RefundPayResponseData implements Serializable {
    /*
     *合作商编号
     */
    @NotNull
    private String partner_id;
    /*
     *原交易流水号
     */
    @NotNull
    private String original_serial_no;
    /*
     *垫资金额
     */
    private String df_amount;
    /*
     *金额
     */
    @NotNull
    private String occur_balance;
    /*
     *退款状态
     * 3-成功，2-处理中，7-失败
     */
    @NotNull
    private String pay_status;
    /*
     *签名
     */
    @NotNull
    private String cert_sign;

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getOriginal_serial_no() {
        return original_serial_no;
    }

    public void setOriginal_serial_no(String original_serial_no) {
        this.original_serial_no = original_serial_no;
    }

    public String getDf_amount() {
        return df_amount;
    }

    public void setDf_amount(String df_amount) {
        this.df_amount = df_amount;
    }

    public String getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(String occur_balance) {
        this.occur_balance = occur_balance;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "RefundPayResponseData{" +
                "partner_id='" + partner_id + '\'' +
                ", original_serial_no='" + original_serial_no + '\'' +
                ", df_amount='" + df_amount + '\'' +
                ", occur_balance='" + occur_balance + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
