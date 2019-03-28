package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

/**
 * Created by dany on 2017/6/5.
 */
public class PlatcustOpenData implements Serializable{
    //银行客户编号
    private String bank_client_no;
    //银行电子账户
    private String bank_elec_no;
    //银行卡号
    private String card_no;
    //账户标识
    private String bank_acct_flag;
    //账户分类
    private String bank_acct_kind;
    //签名串
    private String cert_sign;

    public String getBank_client_no() {
        return bank_client_no;
    }

    public void setBank_client_no(String bank_client_no) {
        this.bank_client_no = bank_client_no;
    }

    public String getBank_elec_no() {
        return bank_elec_no;
    }

    public void setBank_elec_no(String bank_elec_no) {
        this.bank_elec_no = bank_elec_no;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getBank_acct_flag() {
        return bank_acct_flag;
    }

    public void setBank_acct_flag(String bank_acct_flag) {
        this.bank_acct_flag = bank_acct_flag;
    }

    public String getBank_acct_kind() {
        return bank_acct_kind;
    }

    public void setBank_acct_kind(String bank_acct_kind) {
        this.bank_acct_kind = bank_acct_kind;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }
}
