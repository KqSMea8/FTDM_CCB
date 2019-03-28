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
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Zz
 *2017年5月4日
 *4.5.7. 借款人线下还款
 */
public class RepayOffLineRequest extends BaseRequest {
	
	/*
	 * 总金额
	 */
	@NotNull
	private BigDecimal  amt;
	/*
	 * 平台客户入账列表
	 */
	@NotBlank
	@JSONField(serialize=false)
	private String platcustList;
	private List<PlatcustListDetail>  platcustlistdetail;
	/*
	 * 异步通知地址
	 */
	@NotBlank
	private String notify_url;
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getPlatcustList() {
		return platcustList;
	}
	public void setPlatcustList(String platcustList) {
		this.platcustList = platcustList;
		if(StringUtils.isNoneBlank(platcustList)) {
			this.platcustlistdetail = JSON.parseArray(platcustList,PlatcustListDetail.class);
		}
	}
	public List<PlatcustListDetail> getPlatcustlistdetail() {
		return platcustlistdetail;
	}
	public void setPlatcustlistdetail(List<PlatcustListDetail> platcustlistdetail) {
		this.platcustlistdetail = platcustlistdetail;
		setPlatcustList(JSON.toJSONString(platcustlistdetail, GlobalConfig.serializerFeature));
		
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
