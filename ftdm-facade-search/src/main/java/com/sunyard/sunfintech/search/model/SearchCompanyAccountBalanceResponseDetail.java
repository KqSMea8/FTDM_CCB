package com.sunyard.sunfintech.search.model;

import java.io.Serializable;

/**
 * @author RaoYL
 * @version 20180126
 */
public class SearchCompanyAccountBalanceResponseDetail implements Serializable{
    /**
     *开户网点
     */
    private String open_bank;
    /**
     *户名
     */
    private String acct_name_ch;
    /**
     *实时余额
     */
    private String real_time_balance;
    /**
     *当日金额
     */
    private String today_amt;
    /**
     *昨日金额
     */
    private String yesterday_amt;

    /**
     * 建行差异字段
     */
    private String acct_status;

    public String getOpen_bank() {
        return open_bank;
    }

    public void setOpen_bank(String open_bank) {
        this.open_bank = open_bank;
    }

    public String getAcct_name_ch() {
        return acct_name_ch;
    }

    public void setAcct_name_ch(String acct_name_ch) {
        this.acct_name_ch = acct_name_ch;
    }

    public String getReal_time_balance() {
        return real_time_balance;
    }

    public void setReal_time_balance(String real_time_balance) {
        this.real_time_balance = real_time_balance;
    }

    public String getToday_amt() {
        return today_amt;
    }

    public void setToday_amt(String today_amt) {
        this.today_amt = today_amt;
    }

    public String getYesterday_amt() {
        return yesterday_amt;
    }

    public void setYesterday_amt(String yesterday_amt) {
        this.yesterday_amt = yesterday_amt;
    }

    public String getAcct_status() {
        return acct_status;
    }

    public void setAcct_status(String acct_status) {
        this.acct_status = acct_status;
    }

    @Override
    public String toString() {
        return "CompanyAccountDetailData{" +
                "open_bank='" + open_bank + '\'' +
                ", acct_name_ch='" + acct_name_ch + '\'' +
                ", real_time_balance='" + real_time_balance + '\'' +
                ", today_amt='" + today_amt + '\'' +
                ", yesterday_amt='" + yesterday_amt + '\'' +
                ", acct_status='" + acct_status + '\''+
                '}';
    }
}
