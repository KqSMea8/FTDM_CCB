package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/9/13.
 */
public class BankReverse implements Serializable {

    private String partner_trans_date;
    private String original_serial_no;
    private BigDecimal occur_balance;
    private String mall_no;

    public String getPartner_trans_date() {
        return partner_trans_date;
    }

    public void setPartner_trans_date(String partner_trans_date) {
        this.partner_trans_date = partner_trans_date;
    }

    public String getOriginal_serial_no() {
        return original_serial_no;
    }

    public void setOriginal_serial_no(String original_serial_no) {
        this.original_serial_no = original_serial_no;
    }

    public BigDecimal getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(BigDecimal occur_balance) {
        this.occur_balance = occur_balance;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    @Override
    public String toString() {
        return "BankReverse{" +
                "partner_trans_date='" + partner_trans_date + '\'' +
                ", original_serial_no='" + original_serial_no + '\'' +
                ", occur_balance=" + occur_balance +
                ", mall_no='" + mall_no + '\'' +
                '}';
    }
}
