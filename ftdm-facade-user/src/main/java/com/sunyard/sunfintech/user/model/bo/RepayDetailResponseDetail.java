package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class RepayDetailResponseDetail implements Serializable{

	//产品编号
	private String prod_id;

	//产品名称
	private String prod_name;
	
	//还款期数
	private String repay_num;
	
	//计划还款时间
	private String repay_date;
	
	//实际还款时间
	private String real_repay_date;
	
	//计划还款金额
	private String repay_amt;
	
	//实际还款金额
	private String real_repay_amt;
	
	//状态
	private String status;
	
	//平台编号
	private String plat_no;

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getRepay_num() {
		return repay_num;
	}

	public void setRepay_num(String repay_num) {
		this.repay_num = repay_num;
	}

	public String getRepay_date() {
		return repay_date;
	}

	public void setRepay_date(String repay_date) {
		this.repay_date = repay_date;
	}

	public String getReal_repay_date() {
		return real_repay_date;
	}

	public void setReal_repay_date(String real_repay_date) {
		this.real_repay_date = real_repay_date;
	}

	public String getRepay_amt() {
		return repay_amt;
	}

	public void setRepay_amt(String repay_amt) {
		this.repay_amt = repay_amt;
	}

	public String getReal_repay_amt() {
		return real_repay_amt;
	}

	public void setReal_repay_amt(String real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
}
