package com.sunyard.sunfintech.core.dic;
/*
 * 清算异常
 * 0-未清算
 * 1-已清算
 * 2-清算异常
 */

public enum LiquidationStatus {

	UNCHECK("0") ,CHECKED("1"),CHECK_ERROR("2");

	private String code;

	private LiquidationStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
