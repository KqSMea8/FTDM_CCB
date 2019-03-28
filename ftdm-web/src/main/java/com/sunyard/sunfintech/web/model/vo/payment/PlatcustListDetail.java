package com.sunyard.sunfintech.web.model.vo.payment;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Zz
 *2017年5月4日
 *
 */
public class PlatcustListDetail {

	/*
	 * 平台客户编号
	 */
	@NotBlank
	private String amt;
	/*
	 * 平台客户编号
	 */
	@NotBlank
	private String platcust;

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
}
