package com.sunyard.sunfintech.web.model.vo.account;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.sunyard.sunfintech.user.model.bo.UnBindCardRequestDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 批量换卡（解绑+换卡申请）
 * @author PengZY
 * 
 */
public class UnBindCardRequest extends BaseRequest {

	//JsonArray批量数据
	@NotBlank
	private String data;
	
	private List<UnBindCardRequestDetail> data_detail;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		if(StringUtils.isNoneBlank(data)) {
			this.data_detail = JSON.parseArray(data,UnBindCardRequestDetail.class);
		}
	}

	public List<UnBindCardRequestDetail> getData_detail() {
		return data_detail;
	}

	public void setData_detail(List<UnBindCardRequestDetail> data_detail) {
		this.data_detail = data_detail;
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
