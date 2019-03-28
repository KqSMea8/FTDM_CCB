package com.sunyard.sunfintech.web.model.vo.payment;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Zz
 *2017年5月4日
 * 
 * 网关充值请求
 */
public class GetewayPayRequest {

		/*
		 * 电子账户
		 */
	
	@NotBlank
	private String type;
	

	/*
	 * 1-投资账户 2-融资账户 必填项
	 */
	@NotBlank
	private String charge_type;
	/*
	 * 银行编码
	 */
	private String  Bankcode;
	/*
	 * 卡类型(借记卡)
	 */
	private String card_type;
	/*
	 * 币种
	 */
	private String currency_code;
	/*
	 * 卡号
	 */
	private String  card_no;
	/*
	 * 金额
	 */
	@NotNull
	private BigDecimal Amt;
	/*
	 * 同步回调地址
	 */
	@NotBlank
	private String return_url;
	/*
	 * 异步通知地址
	 */
	@NotBlank
	private String notify_url ;
	/*
	 * 支付通道
	 */
	@NotBlank
	private String pay_code;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public String getBankcode() {
		return Bankcode;
	}
	public void setBankcode(String bankcode) {
		Bankcode = bankcode;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public BigDecimal getAmt() {
		return Amt;
	}
	public void setAmt(BigDecimal amt) {
		Amt = amt;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getPay_code() {
		return pay_code;
	}
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
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
