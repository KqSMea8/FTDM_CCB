package com.sunyard.sunfintech.core.dic;
/*
 * 对账异常
 * 0-未对账
 * 1-已对账
 * 2-对账异常
 * 3-预清算正常
 * 4-预清算异常
 */

public enum ClearStatus {
	
	UNCHECK("0") ,CHECKED("1"),CHECK_ERROR("2"),CHECK_CLEAR_SUC("3"),CHECK_CLEAR_ERR("4");
	
	private String code;
	
	private ClearStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
