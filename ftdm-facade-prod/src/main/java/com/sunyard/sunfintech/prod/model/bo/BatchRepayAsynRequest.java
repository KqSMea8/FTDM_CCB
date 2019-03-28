package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 授权借款人还款申请
 * @author pengzy
 */
public class BatchRepayAsynRequest extends BaseRequest {

	/**
	 * 通知地址
	 */
	@NotBlank
	private String notify_url;

	@NotBlank
	private String data;
	
	private List<BatchRepayRequestDetail> batchRepayRequestDetailList;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		batchRepayRequestDetailList = JSON.parseArray(data,BatchRepayRequestDetail.class);
	}

	public List<BatchRepayRequestDetail> getBatchRepayRequestDetailList() {
		return batchRepayRequestDetailList;
	}

	public void setBatchRepayRequestDetailList(List<BatchRepayRequestDetail> batchRepayRequestDetailList) {
		this.batchRepayRequestDetailList = batchRepayRequestDetailList;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
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
