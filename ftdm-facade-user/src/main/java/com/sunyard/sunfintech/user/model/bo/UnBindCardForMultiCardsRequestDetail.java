package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class UnBindCardForMultiCardsRequestDetail implements Serializable{

	//明细编号
	@NotBlank
	private String detail_no;
	
	//用户在资金账户管理平台的电子账户
	@NotBlank
	private String platcust;
	
	//卡号
	private String card_no;
	
	//卡类型(1:借记卡，2:信用卡)
	private String card_type;
	
	//预留手机号
	private String mobile;
	
	//原卡号
	@NotBlank
	private String card_no_old;
	
	//原卡类型
	private String card_type_old;
	
	//姓名
	private String name;
	
	//支付通道
	private String pay_code;
	
	//备注
	private String remark;

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

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
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

	public String getCard_type_old() {
		return card_type_old;
	}

	public void setCard_type_old(String card_type_old) {
		this.card_type_old = card_type_old;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
