package com.sunyard.sunfintech.search.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * 
 * 标的投资明细查询   ---响应modal---
 * @author PengZY
 * 
 */
public class SearchProductInvestmentDetailResponse extends BaseResponse {

	//返回业务数据
	private String data;

	@JSONField(serialize = false)
	private List<Map<String,Object>> detail_map;

	@JSONField(serialize = false)
	private List<SearchProductInvestmentDetailResponseDetail> data_detail;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<SearchProductInvestmentDetailResponseDetail> getData_detail() {
		return data_detail;
	}
	public void setData_detail(List<SearchProductInvestmentDetailResponseDetail> data_detail) {
		this.data_detail = data_detail;
		setData(JSON.toJSONString(data_detail,GlobalConfig.serializerFeature));
	}

/*	public List<Map<String, Object>> getDetail_map() {
		return detail_map;
	}*/
	public void setDetail_map(List<Map<String,Object>> detail_map) {
		this.detail_map = detail_map;
		setData(JSON.toJSONString(detail_map,GlobalConfig.serializerFeature));
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
