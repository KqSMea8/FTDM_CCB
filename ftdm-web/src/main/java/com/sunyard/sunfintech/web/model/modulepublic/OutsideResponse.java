package com.sunyard.sunfintech.web.model.modulepublic;

import java.io.Serializable;

/**
 * Created by djh on 2017/5/31.
 */
public class OutsideResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 请求结果（只有结果成功或失败），如果结果异常回网络异常直接抛出异常
	 */
	private Boolean result;
	/**
	 * 返回数据，不解析
	 */
	private String return_data;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getReturn_data() {
		return return_data;
	}

	public void setReturn_data(String return_data) {
		this.return_data = return_data;
	}
	
}
