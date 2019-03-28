package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

public class BankPayData implements Serializable {
	/**
	 * 序列号
	 */
	private String seq_no;
	/**
	 * 卡号
	 */
	private String card_no;
	/**
	 * 身份证号（对公非必填）
	 */
	private String id_card;
	/**
	 * 姓名
	 */
	private String cust_name;
	/**
	 * 交易金额
	 */
	private String tran_amt;
	/**
	 *
	 */
	private String bank_code;
	/**
	 *
	 */
	private String bank_name;
	/**
	 * 银行卡预留手机号（对公非必填）
	 */
	private String phoneNum;

	/**
	 *
	 */
	private String remark;

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getTran_amt() {
		return tran_amt;
	}

	public void setTran_amt(String tran_amt) {
		this.tran_amt = tran_amt;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
