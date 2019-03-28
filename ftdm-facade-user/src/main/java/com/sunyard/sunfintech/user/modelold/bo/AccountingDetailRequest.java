package com.sunyard.sunfintech.user.modelold.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 平台真实账务往来查询
 * @author RaoYL
 * 
 */
public class AccountingDetailRequest extends BaseRequest {

	/**
	 * 往来标志D:收款 C:付款 A:全部
	 */
	@NotBlank
	private String io_flag;
	/**
	 * 开始日期
	 */
	@NotBlank
	private String date_from;
	/**
	 * 结束日期
	 */
	@NotBlank
	private String date_to;
	/**
	 * 账户类型，1存管户，2自有金，3清算户
	 */
	@NotBlank
	private String account_type;

	public String getIo_flag() {
		return io_flag;
	}

	public void setIo_flag(String io_flag) {
		this.io_flag = io_flag;
	}

	public String getDate_from() {
		return date_from;
	}

	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}

	public String getDate_to() {
		return date_to;
	}

	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
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
