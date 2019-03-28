package com.sunyard.sunfintech.core.dic;

/**
 * 份额明细状态
 * 
 * 0-待确定
 * 1-成功
 * 2-失败
 *
 */
public enum ShareStatus {
	
	WAIT("0"),SUCESS("1"),FAIL("2");
	private String code;
	
	private ShareStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
