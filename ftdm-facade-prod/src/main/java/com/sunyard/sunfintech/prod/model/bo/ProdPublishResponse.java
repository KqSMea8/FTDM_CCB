package com.sunyard.sunfintech.prod.model.bo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.prod.model.bo.ProdPublishResponseData;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * @author Zz
 *2017年4月18日
 */
public class ProdPublishResponse extends BaseResponse{

	/*
	 * 返回业务数据
	 */
	private String data;

	@JSONField(serialize=false)
	private ProdPublishResponseData data_obj;

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;

	}
	public ProdPublishResponseData getData_obj() {
		return data_obj;
	}
	public void setData_obj(ProdPublishResponseData data_obj) {
		this.data_obj = data_obj;
		this.data = JSON.toJSONString(data_obj, GlobalConfig.serializerFeature);
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
