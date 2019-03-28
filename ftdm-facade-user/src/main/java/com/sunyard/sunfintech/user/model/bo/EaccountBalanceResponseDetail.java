package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

public class EaccountBalanceResponseDetail implements Serializable {
	
	//子科目
	private String subject;

	//总金额
	private BigDecimal t_balance;

	//可用金额
	private BigDecimal n_balance;

	//冻结金额
	private BigDecimal f_balance;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
