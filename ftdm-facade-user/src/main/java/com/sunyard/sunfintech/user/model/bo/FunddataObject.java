package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 成标时  分佣说明
 */
public class FunddataObject implements Serializable{
	/**
	 * 平台类型 01-现金  11-在途
	 */
	private String payout_plat_type;
	
	/**
	 * 手续费固定金额
	 */
	private BigDecimal payout_amt;

	public String getPayout_plat_type() {
		return payout_plat_type;
	}

	public void setPayout_plat_type(String payout_plat_type) {
		this.payout_plat_type = payout_plat_type;
	}

	public BigDecimal getPayout_amt() {
		return payout_amt;
	}

	public void setPayout_amt(BigDecimal payout_amt) {
		this.payout_amt = payout_amt;
	}

// TODO toString equals hashCode
	
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
