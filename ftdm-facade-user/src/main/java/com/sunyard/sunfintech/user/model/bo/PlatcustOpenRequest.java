package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

/**
 * Created by dany on 2017/6/2.
 */
public class PlatcustOpenRequest implements Serializable{
    //合作商编号
    private String partner_id;
    //合作商流水
    private String partner_serial_no;
    //合作商交易日期
    private String partner_trans_date;
    //合作商交易时间
    private String partner_trans_time;
    //商户用户编号
    private String partner_userid;
    //银行编号/电子账号
    private String bank_acct_no;
    //客户姓名
    private String client_name;
   //银行卡号
    private String card_no;
   //cvv2码：信用卡填
    private String cvv2;
   //有效日期：信用卡填
    private String valid_date;
   //证件类型
    private String id_kind;
   //证件号码
    private String id_no;
  //证件有效期类型
    private String id_valid_type;
   //证件有效期起始日期
    private String date_from;
   //证件有效期终止日期
    private String date_to;
   //手机号码
    private String mobile_tel;
   //用户地址
    private String address;
  //账号密码
    private String bank_acct_pwd;
   //终端类型
    private String terminal_type;
    //签名串
    private String cert_sign;

    public String getPartner_id() {return partner_id;}

    public void setPartner_id(String partner_id) {this.partner_id = partner_id;}

    public String getPartner_serial_no() {
        return partner_serial_no;
    }

    public void setPartner_serial_no(String partner_serial_no) {
        this.partner_serial_no = partner_serial_no;
    }

    public String getPartner_trans_date() {
        return partner_trans_date;
    }

    public void setPartner_trans_date(String partner_trans_date) {
        this.partner_trans_date = partner_trans_date;
    }

    public String getPartner_trans_time() {
        return partner_trans_time;
    }

    public void setPartner_trans_time(String partner_trans_time) {
        this.partner_trans_time = partner_trans_time;
    }

    public String getPartner_userid() {
        return partner_userid;
    }

    public void setPartner_userid(String partner_userid) {
        this.partner_userid = partner_userid;
    }

    public String getBank_acct_no() {
        return bank_acct_no;
    }

    public void setBank_acct_no(String bank_acct_no) {
        this.bank_acct_no = bank_acct_no;
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

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getId_kind() {
        return id_kind;
    }

    public void setId_kind(String id_kind) {
        this.id_kind = id_kind;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getId_valid_type() {
        return id_valid_type;
    }

    public void setId_valid_type(String id_valid_type) {
        this.id_valid_type = id_valid_type;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public String getMobile_tel() {
        return mobile_tel;
    }

    public void setMobile_tel(String mobile_tel) {
        this.mobile_tel = mobile_tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank_acct_pwd() {
        return bank_acct_pwd;
    }

    public void setBank_acct_pwd(String bank_acct_pwd) {
        this.bank_acct_pwd = bank_acct_pwd;
    }

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }
}
