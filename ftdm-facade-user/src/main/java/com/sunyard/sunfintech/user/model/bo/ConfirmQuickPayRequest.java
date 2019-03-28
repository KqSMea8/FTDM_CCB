package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Zz
 *2017年5月4日
 *
 *
 *快捷充值确认 请求
 */
public class ConfirmQuickPayRequest extends BaseRequest {
	/*
	 * 短信验证码
	 */
	@NotBlank
	private String identifying_code ;
	
	/*
	 * 原快捷支付申请订单号
	 */
	@NotBlank
	private String  origin_order_no;

	/*
	 * 支付通道
	 */
	@NotBlank
	private String pay_code ;

	@NotBlank
	private String platcust;

	@NotBlank
	private String trans_pwd;

	@NotBlank
	private String random_key;

	public String getIdentifying_code() {
		return identifying_code;
	}

	public void setIdentifying_code(String identifying_code) {
		this.identifying_code = identifying_code;
	}

	public String getOrigin_order_no() {
		return origin_order_no;
	}

	public void setOrigin_order_no(String origin_order_no) {
		this.origin_order_no = origin_order_no;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getTrans_pwd() {
		return trans_pwd;
	}

	public void setTrans_pwd(String trans_pwd) {
		this.trans_pwd = trans_pwd;
	}

	public String getRandom_key() {
		return random_key;
	}

	public void setRandom_key(String random_key) {
		this.random_key = random_key;
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
