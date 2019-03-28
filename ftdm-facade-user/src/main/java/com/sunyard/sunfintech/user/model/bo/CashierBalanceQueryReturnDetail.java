package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PengZY on 2017/7/14.
 */
public class CashierBalanceQueryReturnDetail implements Serializable {

    //户名
    private String client_name;

    //证件类型
    private String id_type;

    //证件号码
    private String id_number;

    //手机号
    private String mobile_tel;

    //开户行
    private String open_bank;

    //开户行名
    private String open_bank_name;

    //实时余额
    private String real_time_balance;

    //今日余额
    private String today_balance;

    //昨日余额
    private String yesterday_balance;

    //签名串
    private String cert_sign;

    private List<SubAcctBulkPackage> subAcctBulkPackages;

    public List<SubAcctBulkPackage> getSubAcctBulkPackages() {
        return subAcctBulkPackages;
    }

    public void setSubAcctBulkPackages(List<SubAcctBulkPackage> subAcctBulkPackages) {
        this.subAcctBulkPackages = subAcctBulkPackages;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getMobile_tel() {
        return mobile_tel;
    }

    public void setMobile_tel(String mobile_tel) {
        this.mobile_tel = mobile_tel;
    }

    public String getOpen_bank() {
        return open_bank;
    }

    public void setOpen_bank(String open_bank) {
        this.open_bank = open_bank;
    }

    public String getOpen_bank_name() {
        return open_bank_name;
    }

    public void setOpen_bank_name(String open_bank_name) {
        this.open_bank_name = open_bank_name;
    }

    public String getReal_time_balance() {
        return real_time_balance;
    }

    public void setReal_time_balance(String real_time_balance) {
        this.real_time_balance = real_time_balance;
    }

    public String getToday_balance() {
        return today_balance;
    }

    public void setToday_balance(String today_balance) {
        this.today_balance = today_balance;
    }

    public String getYesterday_balance() {
        return yesterday_balance;
    }

    public void setYesterday_balance(String yesterday_balance) {
        this.yesterday_balance = yesterday_balance;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "PayplatPinglePayReturnData{" +
                "client_name='" + client_name + '\'' +
                ", id_type='" + id_type + '\'' +
                ", id_number='" + id_number + '\'' +
                ", mobile_tel='" + mobile_tel + '\'' +
                ", open_bank='" + open_bank + '\'' +
                ", open_bank_name='" + open_bank_name + '\'' +
                ", real_time_balance='" + real_time_balance + '\'' +
                ", today_balance='" + today_balance + '\'' +
                ", yesterday_balance='" + yesterday_balance + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
