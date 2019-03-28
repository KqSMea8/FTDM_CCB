package com.sunyard.sunfintech.prod.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ProductInvestmentDetailResponseDetail implements Serializable{

	private String amt_type;

//	//交易份额
//	private String orderId;

	//交易份额
	private String vol;

	//交易日期
	private String trans_date;

	//交易时间
	private String trans_time;

	//平台客户编号
	private String platcust;
//
//	//客户名称
//	private String name;

	//产品名称
	private String prod_name;

	//平台名称
	private String plat_name;

	//加息利率
	private String in_interest;

	public String getAmt_type() {
		return amt_type;
	}

	public void setAmt_type(String amt_type) {
		this.amt_type = amt_type;
	}

//	public String getOrderId() {
//		return orderId;
//	}

//	public void setOrderId(String orderId) {
//		this.orderId = orderId;
//	}

	public String getVol() {
		return vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getPlat_name() {
		return plat_name;
	}

	public void setPlat_name(String plat_name) {
		this.plat_name = plat_name;
	}

	public String getIn_interest() {
		return in_interest;
	}

	public void setIn_interest(String in_interest) {
		this.in_interest = in_interest;
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
