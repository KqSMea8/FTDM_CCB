package com.sunyard.sunfintech.search.model;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SearchProductInfoResponseDetail implements Serializable {
	
	//年利率
	private BigDecimal ist_year;
	
	//总份额
	private BigDecimal total_limit;
	
	//剩余份额
	private BigDecimal remain_limit;
	
	//已卖份额
	private BigDecimal saled_limit;
	
	//是否自动出账
	private String chargeOff_auto;
	
	//平台编号
	private String plat_no;
	
	//产品id
	private String prod_id;
	
	//标的账户
	private String prod_account;
	
	//标的名称
	private String prod_state;
	
	//标的状态
	private String prod_name;

	private String compensation;

	public String getCompensation() {
		return compensation;
	}

	public void setCompensation(List<Map<String,Object>> compensationList) {
		this.compensation = JSON.toJSONString(compensationList, GlobalConfig.serializerFeature);
	}

	public BigDecimal getIst_year() {
		return ist_year;
	}

	public void setIst_year(BigDecimal ist_year) {
		this.ist_year = ist_year;
	}

	public BigDecimal getTotal_limit() {
		return total_limit;
	}

	public void setTotal_limit(BigDecimal total_limit) {
		this.total_limit = total_limit;
	}

	public BigDecimal getRemain_limit() {
		return remain_limit;
	}

	public void setRemain_limit(BigDecimal remain_limit) {
		this.remain_limit = remain_limit;
	}

	public BigDecimal getSaled_limit() {
		return saled_limit;
	}

	public void setSaled_limit(BigDecimal saled_limit) {
		this.saled_limit = saled_limit;
	}

	public String getChargeOff_auto() {
		return chargeOff_auto;
	}

	public void setChargeOff_auto(String chargeOff_auto) {
		this.chargeOff_auto = chargeOff_auto;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_account() {
		return prod_account;
	}

	public void setProd_account(String prod_account) {
		this.prod_account = prod_account;
	}

	public String getProd_state() {
		return prod_state;
	}

	public void setProd_state(String prod_state) {
		this.prod_state = prod_state;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
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
