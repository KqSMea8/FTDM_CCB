package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

/**
 * @author Zz
 *2017年5月4日
 *商城充值回调
 */
public class NotityPaymentResponse implements Serializable {
	private String recode;

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

}
