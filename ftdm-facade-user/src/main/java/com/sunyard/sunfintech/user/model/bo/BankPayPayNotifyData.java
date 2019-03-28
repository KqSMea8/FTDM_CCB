package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;

public class BankPayPayNotifyData extends BaseModel {
	/**
	 * 序列号
	 */
	private String seq_no;
	/**
	 * 账号名称
	 */
	private String tran_amt;
	/**
	 * 交易结果（Y-交易成功，N-交易失败,C-处理中，结果未知）
	 */
	private String status;
	/**
	 * 失败原因（status=N时，可查看失败原因fail_reason）
	 */
	private String fail_reason;
	
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
