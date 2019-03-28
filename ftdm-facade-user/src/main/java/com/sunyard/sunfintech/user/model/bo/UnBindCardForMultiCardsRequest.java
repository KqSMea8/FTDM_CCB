package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.user.model.bo.UnBindCardForMultiCardsRequestDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 
 * 批量解绑
 * @author Bob
 * 
 */
public class UnBindCardForMultiCardsRequest extends BaseRequest {

	//JsonArray批量数据
	@NotBlank
	private String data;
	
	private List<UnBindCardForMultiCardsRequestDetail> data_detail;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		if(StringUtils.isNoneBlank(data)) {
			this.data_detail = JSON.parseArray(data,UnBindCardForMultiCardsRequestDetail.class);
		}
	}

	public List<UnBindCardForMultiCardsRequestDetail> getData_detail() {
		return data_detail;
	}

	public void setData_detail(List<UnBindCardForMultiCardsRequestDetail> data_detail) {
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
