package com.sunyard.sunfintech.user.model.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author Zz
 *2017年5月4日
 *4.5.2. 充值回调通知
 */
public class NotityPaymentRequest implements Serializable {
//	/*
//	 * 请求数据
//	 */
private List<NotityPaymentData> data;

	public List<NotityPaymentData> getData() {
		return data;
	}

	public void setData(List<NotityPaymentData> data) {
		this.data = data;
	}
//	private String data ;
//
//	public String getData() {
//		return data;
//	}
//
//	public void setData(String data) {
//		this.data = data;
//	}
//	private List<NotityPaymentData> data;
//
//	@NotBlank
//	private String data;
//
//	@JSONField(serialize=false)
//	private List<NotityPaymentData> datedetail;
//
//	public String getData() {
//		return data;
//	}
//
//	public void setData(String data) {
//		this.data = data;
//		if(StringUtils.isNoneBlank(data)) {
//			this.datedetail = JSON.parseArray(data,NotityPaymentData.class);
//		}
//	}
//
//	public List<NotityPaymentData> getDatedetail() {
//		return datedetail;
//	}
//
//	public void setDatedetail(List<NotityPaymentData> datedetail) {
//		this.datedetail = datedetail;
//		setData(JSON.toJSONString(datedetail, GlobalConfig.serializerFeature ));
//	}

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
