package com.sunyard.sunfintech.user.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Zz
 *2017年5月4日
 *
 */
public class PlatcustListDetail implements Serializable{

	/*
	 * 平台客户入账金额
	 */
	@NotNull
	private BigDecimal amt;
	/*
	 * 平台客户编号
	 */
	@NotBlank
	private String platcust;

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
}
