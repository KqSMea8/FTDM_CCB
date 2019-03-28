package com.sunyard.sunfintech.search.model;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 
 * 充值订单状态查询
 * @author PengZY
 * 
 */
public class SearchChargeStatusRequest extends BaseRequest {

	//原充值订单号
	@NotBlank
	private String original_serial_no;
	
	//发生金额
	@NotNull
	private BigDecimal occur_balance;

	public String getOriginal_serial_no() {
		return original_serial_no;
	}

	public void setOriginal_serial_no(String original_serial_no) {
		this.original_serial_no = original_serial_no;
	}

	public BigDecimal getOccur_balance() {
		return occur_balance;
	}

	public void setOccur_balance(BigDecimal occur_balance) {
		this.occur_balance = occur_balance;
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
