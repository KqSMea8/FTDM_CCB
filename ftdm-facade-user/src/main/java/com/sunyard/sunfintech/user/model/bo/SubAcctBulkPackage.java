package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

/**
 * Created by PengZY on 2017/7/19.
 */
public class SubAcctBulkPackage implements Serializable {

    private String ccy;
    private String save_type;
    private String sub_acct_no;
    private String available_balance;
    private String balance;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getSave_type() {
        return save_type;
    }

    public void setSave_type(String save_type) {
        this.save_type = save_type;
    }

    public String getSub_acct_no() {
        return sub_acct_no;
    }

    public void setSub_acct_no(String sub_acct_no) {
        this.sub_acct_no = sub_acct_no;
    }

    public String getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(String available_balance) {
        this.available_balance = available_balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
