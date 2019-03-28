package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author pzy
 *2017年5月4日
 *4.6.1.	批量提现
 */
public class BatchWithdrawRequest extends BaseRequest {

	/*
	 * 总数量
	 */
	@NotNull
	private Integer total_num;

	/*
	 * JsonArray明细数据
	 */
	@NotBlank
	private String data;

	@JSONField(serialize=false)
	private List<DateDetail> datedetail;

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		if(StringUtils.isNoneBlank(data)) {
			this.datedetail = JSON.parseArray(data,DateDetail.class);
		}
	}

	public List<DateDetail> getDatedetail() {
		return datedetail;
	}

	public void setDatedetail(List<DateDetail> datedetail) {
		this.datedetail = datedetail;
		setData(JSON.toJSONString(datedetail, GlobalConfig.serializerFeature ));
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
