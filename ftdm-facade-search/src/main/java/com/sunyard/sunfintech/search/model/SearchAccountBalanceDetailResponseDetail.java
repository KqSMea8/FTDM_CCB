package com.sunyard.sunfintech.search.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

public class SearchAccountBalanceDetailResponseDetail implements Serializable {

	//集团客户号
	private String platcust;

	//平台编号
	private String plat_no;

	//科目
	private String subject;

	//子科目
	private String sub_subject;

	//总金额
	private BigDecimal t_balance;
	
	//可用金额
	private BigDecimal n_balance;
	
	//冻结金额
	private BigDecimal f_balance;

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcuse) {
		this.platcust = platcuse;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSub_subject() {
		return sub_subject;
	}

	public void setSub_subject(String sub_subject) {
		this.sub_subject = sub_subject;
	}

	public BigDecimal getT_balance() {
		return t_balance;
	}

	public void setT_balance(BigDecimal t_balance) {
		this.t_balance = t_balance;
	}

	public BigDecimal getN_balance() {
		return n_balance;
	}

	public void setN_balance(BigDecimal n_balance) {
		this.n_balance = n_balance;
	}

	public BigDecimal getF_balance() {
		return f_balance;
	}

	public void setF_balance(BigDecimal f_balance) {
		this.f_balance = f_balance;
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
