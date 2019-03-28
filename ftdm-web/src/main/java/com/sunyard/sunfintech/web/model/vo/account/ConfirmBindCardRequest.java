package com.sunyard.sunfintech.web.model.vo.account;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 短验绑卡（可包含开户）确认
 * @author PengZY
 * 
 */
public class ConfirmBindCardRequest extends BaseRequest {

	//平台客户编号：已实名认证用户，参数必
	private String platcust;
	
	//证件类型：未实名认证用户，参数必填
	private String id_type;
	
	//证件号码：未实名认证用户，参数必填
	private String id_code;
	
	//姓名：未实名认证用户，参数必填
	private String name;
	
	//卡号 
	@NotBlank
	private String card_no;
	
	//卡类型(1:借记卡，2:信用卡)
	private String card_type;
	
	//预留手机号
	@NotBlank
	private String pre_mobile;
	
	//支付通道
	@NotBlank
	private String pay_code;
	
	//短信验证码
	@NotBlank
	private String identifying_code;
	
	//原绑卡申请订单号
	@NotBlank
	private String origin_order_no;

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_code() {
		return id_code;
	}

	public void setId_code(String id_code) {
		this.id_code = id_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPre_mobile() {
		return pre_mobile;
	}

	public void setPre_mobile(String pre_mobile) {
		this.pre_mobile = pre_mobile;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

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
