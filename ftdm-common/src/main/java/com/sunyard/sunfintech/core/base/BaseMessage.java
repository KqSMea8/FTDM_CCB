/**
 * 
 */
package com.sunyard.sunfintech.core.base;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 基础信息类
 * @author heroy
 * @version 20170327
 */
public class BaseMessage extends BaseModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回信息代码
	 */
	@NotBlank
	private String recode;
	
	/**
	 * 返回具体处理信息
	 * 如果成功位success
	 */
	@NotBlank
	private String remsg;

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	
	public String getRemsg() {
		return remsg;
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
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
