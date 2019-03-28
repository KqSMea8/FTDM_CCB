package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.dao.entity.RwRecharge;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * @author Raoyulu
 *2017年11月9日
 *快捷充值确认响应
 */
public class ConfirmQuickPayResponse extends BaseResponse {
	/*
	 *快捷充值确认返回业务数据
	 */
	//返回业务数据
	private String data;

	@JSONField(serialize = false)
	private ConfirmQuickPayResponseDetail data_detail;

	@JSONField(serialize = false)
	private RwRecharge rwRecharge;

	private Map<String,Object> notify_msg;

	public Map<String, Object> getNotify_msg() {
		return notify_msg;
	}

	public void setNotify_msg(Map<String, Object> notify_msg) {
		this.notify_msg = notify_msg;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public ConfirmQuickPayResponseDetail getData_detail() {
		return data_detail;
	}
	public void setData_detail(ConfirmQuickPayResponseDetail data_detail) {
		this.data_detail = data_detail;
		if(data_detail != null){
			this.data = JSON.toJSONString(data_detail, GlobalConfig.serializerFeature);
		}
	}

	public RwRecharge getRwRecharge() {
		return rwRecharge;
	}

	public void setRwRecharge(RwRecharge rwRecharge) {
		this.rwRecharge = rwRecharge;
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
