package com.sunyard.sunfintech.user.model.bo;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.StringUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 借款人批量还款
 * @author wubin
 */
public class BatchRepayRequest extends BaseRequest {

	/**
	 * 批量明细数据
	 */
	@NotBlank
	private String data;
	
	private List<BatchRepayRequestDetail> batchRepayRequestDetailList;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		if(StringUtils.isNoneBlank(data)){
			batchRepayRequestDetailList = JSON.parseArray(data,BatchRepayRequestDetail.class);
		}
	}

	public List<BatchRepayRequestDetail> getBatchRepayRequestDetailList() {
		return batchRepayRequestDetailList;
	}

	public void setBatchRepayRequestDetailList(List<BatchRepayRequestDetail> batchRepayRequestDetailList) {
		this.batchRepayRequestDetailList = batchRepayRequestDetailList;
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
