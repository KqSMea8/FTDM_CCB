package com.sunyard.sunfintech.user.modelold.bo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class AccountDetailTran implements Serializable {

    /**
     * 业务种类
     */
    private String kind_busi;

    /**
     * 业务种类码
     */
    private String code_busi;

    /**
     * 本行账号
     */
    private String acct_no;

    /**
     *本行账号户名
     */
    private String acct_name;

    /**
     *借贷标志
     */
    private String flag_dc;

    /**
     *对方账号
     */
    private String acct_no1;

    /**
     *对方账号户名
     */
    private String acct_name1;

    /**
     *现转标志
     */
    private String flag1;
    /**
     *交易金额
     */
    private String amt_tran;
    /**
     *摘要
     */
    private String text_abstract;
    /**
     *渠道日期
     */
    private String date_channel;
    /**
     *对账日期
     */
    private String date_acct_chk;
    /**
     *前端流水号
     */
    private String seq_no_front19;
    /**
     *记账日期
     */
    private String date_charge_acct;
    /**
     *记账网点号
     */
    private String sub_branch_id_acct;
    /**
     *签名串
     */
    @JSONField(serialize = false)
    private String cert_sign;

    public String getKind_busi() {
        return kind_busi;
    }

    public void setKind_busi(String kind_busi) {
        this.kind_busi = kind_busi;
    }

    public String getCode_busi() {
        return code_busi;
    }

    public void setCode_busi(String code_busi) {
        this.code_busi = code_busi;
    }

    public String getAcct_no() {
        return acct_no;
    }

    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no;
    }

    public String getAcct_name() {
        return acct_name;
    }

    public void setAcct_name(String acct_name) {
        this.acct_name = acct_name;
    }

    public String getFlag_dc() {
        return flag_dc;
    }

    public void setFlag_dc(String flag_dc) {
        this.flag_dc = flag_dc;
    }

    public String getAcct_no1() {
        return acct_no1;
    }

    public void setAcct_no1(String acct_no1) {
        this.acct_no1 = acct_no1;
    }

    public String getAcct_name1() {
        return acct_name1;
    }

    public void setAcct_name1(String acct_name1) {
        this.acct_name1 = acct_name1;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getAmt_tran() {
        return amt_tran;
    }

    public void setAmt_tran(String amt_tran) {
        this.amt_tran = amt_tran;
    }

    public String getText_abstract() {
        return text_abstract;
    }

    public void setText_abstract(String text_abstract) {
        this.text_abstract = text_abstract;
    }

    public String getDate_channel() {
        return date_channel;
    }

    public void setDate_channel(String date_channel) {
        this.date_channel = date_channel;
    }

    public String getDate_acct_chk() {
        return date_acct_chk;
    }

    public void setDate_acct_chk(String date_acct_chk) {
        this.date_acct_chk = date_acct_chk;
    }

    public String getSeq_no_front19() {
        return seq_no_front19;
    }

    public void setSeq_no_front19(String seq_no_front19) {
        this.seq_no_front19 = seq_no_front19;
    }

    public String getDate_charge_acct() {
        return date_charge_acct;
    }

    public void setDate_charge_acct(String date_charge_acct) {
        this.date_charge_acct = date_charge_acct;
    }

    public String getSub_branch_id_acct() {
        return sub_branch_id_acct;
    }

    public void setSub_branch_id_acct(String sub_branch_id_acct) {
        this.sub_branch_id_acct = sub_branch_id_acct;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    @Override
    public String toString() {
        return "AccountDetailTran{" +
                "kind_busi='" + kind_busi + '\'' +
                ", code_busi='" + code_busi + '\'' +
                ", acct_no='" + acct_no + '\'' +
                ", acct_name='" + acct_name + '\'' +
                ", flag_dc='" + flag_dc + '\'' +
                ", acct_no1='" + acct_no1 + '\'' +
                ", acct_name1='" + acct_name1 + '\'' +
                ", flag1='" + flag1 + '\'' +
                ", amt_tran='" + amt_tran + '\'' +
                ", text_abstract='" + text_abstract + '\'' +
                ", date_channel='" + date_channel + '\'' +
                ", date_acct_chk='" + date_acct_chk + '\'' +
                ", seq_no_front19='" + seq_no_front19 + '\'' +
                ", date_charge_acct='" + date_charge_acct + '\'' +
                ", sub_branch_id_acct='" + sub_branch_id_acct + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                '}';
    }
}
