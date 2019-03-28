package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author RaoYL
 * @version 20180128
 */
public class EpayBankFourInOneQueryDetail implements Serializable {
    /**
     * 交易类型（D:收款 C:付款）
     */
    private String tran_flag;
    /**
     * 交易时间
     */
    private String tran_date;
    /**
     * 对手账号
     */
    private String oppo_acct;
    /**
     * 对手名称
     */
    private String oppo_name;
    /**
     * 对手开户支行
     */
    private String oppo_branch_id;

    /**
     * 交易金额
     */
    private BigDecimal tran_amt;
    //对手开户行名
    private String oppo_bank_name;
    //摘要
    private String summary;
    //附言
    private String text_note;
    //备注
    private String text_remark;
    //流水
    private String seq_no;

    public String getTran_flag() {
        return tran_flag;
    }

    public void setTran_flag(String trans_flag) {
        this.tran_flag = trans_flag;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String getOppo_acct() {
        return oppo_acct;
    }

    public void setOppo_acct(String oppo_acct) {
        this.oppo_acct = oppo_acct;
    }

    public String getOppo_name() {
        return oppo_name;
    }

    public void setOppo_name(String oppo_name) {
        this.oppo_name = oppo_name;
    }

    public String getOppo_branch_id() {
        return oppo_branch_id;
    }

    public void setOppo_branch_id(String oppo_branch_id) {
        this.oppo_branch_id = oppo_branch_id;
    }

    public BigDecimal getTran_amt() {
        return tran_amt;
    }

    public void setTran_amt(BigDecimal tran_amt) {
        this.tran_amt = tran_amt;
    }

    public String getOppo_bank_name() {
        return oppo_bank_name;
    }

    public void setOppo_bank_name(String oppo_bank_name) {
        this.oppo_bank_name = oppo_bank_name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText_note() {
        return text_note;
    }

    public void setText_note(String text_note) {
        this.text_note = text_note;
    }

    public String getText_remark() {
        return text_remark;
    }

    public void setText_remark(String text_remark) {
        this.text_remark = text_remark;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }
}
