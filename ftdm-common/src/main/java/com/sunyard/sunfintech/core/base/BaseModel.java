package com.sunyard.sunfintech.core.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Map;

/**
 * 基础模型类
 * 所有的bo，vo，dto都需要继承该类
 * @author heroy
 *
 */
public abstract class BaseModel implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@JSONField(serialize = false)
	private String trans_serial;

	public String getTrans_serial() {
		return trans_serial;
	}

	public void setTrans_serial(String trans_serial) {
		this.trans_serial = trans_serial;
	}



	/**
	 * 记录aop中process之后订单的处理流水状态
	 */
	@JSONField(serialize = false)
	private Map<String,BaseResponse> orderAfterProcessMap;
	/**
	 * 批量处理时，需要取到每个订单的订单号来记录流水。现在流水记录是根据订单号来记录的
	 */




	public Map<String, BaseResponse> getOrderAfterProcessMap() {
		return orderAfterProcessMap;
	}

	public void setOrderAfterProcessMap(Map<String, BaseResponse> orderAfterProcessMap) {
		this.orderAfterProcessMap = orderAfterProcessMap;
	}



}