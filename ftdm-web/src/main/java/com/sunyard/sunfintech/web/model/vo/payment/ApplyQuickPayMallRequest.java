package com.sunyard.sunfintech.web.model.vo.payment;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Zz 2017年5月4日
 *
 *         快捷充值请求 --商城
 */
public class ApplyQuickPayMallRequest extends BaseRequest{

	/*
	 * 用户编号
	 */
	@NotBlank
	private String platcust;

	/*
	 * 充值金额
	 */
	@NotNull
	private BigDecimal amt;

	/*
	 * 异步通知地址
	 */
	@NotBlank
	private String notify_url;

	/*
	 * 同步通知地址
	 */
	@NotBlank
	private String return_url;
	/*
	 * 投融资账户类型，1-投资账户 2-融资账户
	 */
	private String charge_type;
	
	
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
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
