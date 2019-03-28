package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class CardInfoDetail implements Serializable {

	//绑定银行卡
	private String boundbankid;

	//预留手机号
	private String mobile;

	//绑卡类型0-非三要素绑卡 1-三要素
	private String type;

	private String pay_code;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBoundbankid() {
		return boundbankid;
	}

	public void setBoundbankid(String boundbankid) {
		this.boundbankid = boundbankid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CardInfoDetail that = (CardInfoDetail) o;

		if (boundbankid != null ? !boundbankid.equals(that.boundbankid) : that.boundbankid != null) return false;
		if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
		if (type != null ? !type.equals(that.type) : that.type != null) return false;
		return pay_code != null ? pay_code.equals(that.pay_code) : that.pay_code == null;
	}

	@Override
	public int hashCode() {
		int result = boundbankid != null ? boundbankid.hashCode() : 0;
		result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (pay_code != null ? pay_code.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "CardInfoDetail{" +
				"boundbankid='" + boundbankid + '\'' +
				", mobile='" + mobile + '\'' +
				", type='" + type + '\'' +
				", pay_code='" + pay_code + '\'' +
				'}';
	}
}
