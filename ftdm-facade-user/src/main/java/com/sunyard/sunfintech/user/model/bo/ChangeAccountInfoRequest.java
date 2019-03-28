package com.sunyard.sunfintech.user.model.bo;


import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 
 * 客户信息变更
 * @author PengZY
 * 
 */
public class ChangeAccountInfoRequest extends BaseRequest {
	
	//平台客户号
	@NotBlank
	private String platcust;
	
	//客户类型（1:个人客户，2:企业客户，不传参数则默认为”个人客户“）
	@NotBlank
	private String cus_type;
	
	//电子邮箱
	private String email;
	
	//手机号码
	private String mobile;
	
	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getCus_type() {
		return cus_type;
	}

	public void setCus_type(String cus_type) {
		this.cus_type = cus_type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "ChangeAccountInfoRequest{" +
				"platcust='" + platcust + '\'' +
				", cus_type='" + cus_type + '\'' +
				", email='" + email + '\'' +
				", mobile='" + mobile + '\'' +
				'}';
	}
}
