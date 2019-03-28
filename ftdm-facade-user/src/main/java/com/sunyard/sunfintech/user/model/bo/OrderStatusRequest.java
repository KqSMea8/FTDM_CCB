package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 订单状态查询
 * @author PengZY
 * 
 */
public class OrderStatusRequest extends BaseRequest{
	
	//查询的订单号（针对批量接口，对应detail_no）
	@NotBlank
	private String query_order_no;

	public String getQuery_order_no() {
		return query_order_no;
	}

	public void setQuery_order_no(String query_order_no) {
		this.query_order_no = query_order_no;
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
