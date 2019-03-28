package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;

import javax.validation.constraints.NotNull;

public class RepayOfflineNotifyData extends BaseModel {
	/**
	 * 序列号
	 */
	@NotNull
	private String seq_no;
	/**
	 * 账号名称
	 */
	@NotNull
	private String tran_amt;
	/**
	 * 交易结果（Y-交易成功，N-交易失败,C-处理中，结果未知）
	 */
	@NotNull
	private String status;
	/**
	 * 失败原因（status=N时，可查看失败原因fail_reason）
	 */
	@NotNull
	private String fail_reason;
	/**
	 * 银行返回流水号
	 */
	private String bank_seq_no;
	/**
	 * 汇划路径  大额09 小额03 网银互联06
	 */
	private String pay_path;

	public String getBank_seq_no() {
		return bank_seq_no;
	}

	public void setBank_seq_no(String bank_seq_no) {
		this.bank_seq_no = bank_seq_no;
	}

	public String getPay_path() {
		return pay_path;
	}

	public void setPay_path(String pay_path) {
		this.pay_path = pay_path;
	}

	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	public String getTran_amt() {
		return tran_amt;
	}
	public void setTran_amt(String tran_amt) {
		this.tran_amt = tran_amt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFail_reason() {
		return fail_reason;
	}
	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}
}
