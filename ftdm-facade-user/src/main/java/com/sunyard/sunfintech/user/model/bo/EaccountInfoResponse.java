package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 电子账户信息查询   --- 响应modal---
 * @author PengZY
 * 
 */
public class EaccountInfoResponse  extends BaseResponse {

	//返回业务数据
	private String data;
	
	@JSONField(serialize = false)
	private EaccountInfoResponseDetail data_detail;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public EaccountInfoResponseDetail getData_detail() {
		return data_detail;
	}

	public void setData_detail(EaccountInfoResponseDetail data_detail) {
		this.data_detail = data_detail;
		setData(JSON.toJSONString(data_detail,GlobalConfig.serializerFeature));
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
