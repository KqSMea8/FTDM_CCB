package com.sunyard.sunfintech.search.model;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SearchChargeStatusResponseDetail extends BaseModel{

	//平台编号
	private String plat_no;
	
	//查询的订单编号
	private String query_order_no;

	//流水号
	private String trans_serial;

	//订单状态（0-处理中 1-成功 2-失败 ）
	private String status;

	private String return_msg;

	private String return_code;

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	@Override
	public String getTrans_serial() {
		return trans_serial;
	}

	@Override
	public void setTrans_serial(String trans_serial) {
		this.trans_serial = trans_serial;
	}

	public String getQuery_order_no() {
		return query_order_no;
	}

	public void setQuery_order_no(String query_order_no) {
		this.query_order_no = query_order_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
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
