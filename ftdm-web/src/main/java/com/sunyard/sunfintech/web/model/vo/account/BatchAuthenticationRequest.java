package com.sunyard.sunfintech.web.model.vo.account;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.user.model.bo.BatchAuthenticationRequestDetail;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 批量开户（实名认证）
 * @author PengZY
 * 
 */
public class BatchAuthenticationRequest extends BaseRequest {

	//总数量
	@NotNull
	private Long total_num;

	//JsonArray，批量数据
	@NotBlank
	private String data;
	
	//发送明细
	private List<BatchAuthenticationRequestDetail> dataObject;

	public Long getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Long total_num) {
		this.total_num = total_num;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		if(StringUtils.isNoneBlank(data)) {
			this.dataObject = JSON.parseArray(data,BatchAuthenticationRequestDetail.class);
		}
	}

	public List<BatchAuthenticationRequestDetail> getDataObject() {
		return dataObject;
	}

	public void setDataObject(List<BatchAuthenticationRequestDetail> dataObject) {
		this.dataObject = dataObject;
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
