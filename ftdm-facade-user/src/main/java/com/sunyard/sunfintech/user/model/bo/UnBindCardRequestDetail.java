package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class UnBindCardRequestDetail implements Serializable{

	private String pay_code;

	//明细编号
	@NotBlank
	private String detail_no;
	
	//用户在资金账户管理平台的电子账户
	@NotBlank
	private String platcust;
	
	//预留手机号
	private String mobile;
	
	//原卡号
	@NotBlank
	private String card_no_old;
	
	//备注
	private String remark;

	private String interface_version;

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCard_no_old() {
		return card_no_old;
	}

	public void setCard_no_old(String card_no_old) {
		this.card_no_old = card_no_old;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInterface_version() {
		return interface_version;
	}

	public void setInterface_version(String interface_version) {
		this.interface_version = interface_version;
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

		UnBindCardRequestDetail that = (UnBindCardRequestDetail) o;

		if (pay_code != null ? !pay_code.equals(that.pay_code) : that.pay_code != null) return false;
		if (detail_no != null ? !detail_no.equals(that.detail_no) : that.detail_no != null) return false;
		if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
		if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
		if (card_no_old != null ? !card_no_old.equals(that.card_no_old) : that.card_no_old != null) return false;
		if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
		return interface_version != null ? interface_version.equals(that.interface_version) : that.interface_version == null;
	}

	@Override
	public int hashCode() {
		int result = pay_code != null ? pay_code.hashCode() : 0;
		result = 31 * result + (detail_no != null ? detail_no.hashCode() : 0);
		result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
		result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
		result = 31 * result + (card_no_old != null ? card_no_old.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (interface_version != null ? interface_version.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UnBindCardRequestDetail{" +
				"pay_code='" + pay_code + '\'' +
				", detail_no='" + detail_no + '\'' +
				", platcust='" + platcust + '\'' +
				", mobile='" + mobile + '\'' +
				", card_no_old='" + card_no_old + '\'' +
				", remark='" + remark + '\'' +
				", interface_version='" + interface_version + '\'' +
				'}';
	}
}

