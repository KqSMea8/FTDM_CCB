package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

/**
 * @author Zz
 *2017年5月4日
 *商城充值回调
 */
public class NotityPaymentresponseData implements Serializable{
	private String URL;
	private String Data;
	private String recode;

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}
}
