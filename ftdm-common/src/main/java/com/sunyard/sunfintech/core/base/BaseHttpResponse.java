package com.sunyard.sunfintech.core.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * HTTP请求返回的数据bean
 * @author heroy
 * @version 20170208
 */
public class BaseHttpResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 * 如：200，202，404，505
	 */
	private int statusCode;
	
	/**
	 * 返回信息字符串
	 */
	private String entityString;
	
	/**
	 * 状态消息
	 */
	private String errorMessage;

	/**
	 * 构造函数
	 * @param statusCode 状态码
	 * @param entityString 返回信息字符串
	 * @param errorMessage 状态消息
	 */
	public BaseHttpResponse(int statusCode, String entityString,
			String errorMessage) {
		super();
		this.statusCode = statusCode;
		this.entityString = entityString;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the entityString
	 */
	public String getEntityString() {
		return entityString;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
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
