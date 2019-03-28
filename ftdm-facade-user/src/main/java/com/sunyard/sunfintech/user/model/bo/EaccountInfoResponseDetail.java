package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class EaccountInfoResponseDetail implements Serializable {

	//平台客户号
	private String platcust;

	//账户姓名
	private String name;
	
	//证件号
	private String pid;
	
	//绑定银行卡
	private String boundbankid;
	
	//预留手机号
	private String mobile;

	//电子账户开户标记  0-未开通 1-已开通
	private String eflg;

	public String getPlatcust() {return platcust;}

	public void setPlatcust(String platcust) {this.platcust = platcust;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBoundbankid() {
		return boundbankid;
	}

	public void setBoundbankid(String boundbankid) {
		this.boundbankid = boundbankid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEflg() {return eflg;}

	public void setEflg(String eflg) {this.eflg = eflg;}

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
