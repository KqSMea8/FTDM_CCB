package com.sunyard.sunfintech.core.dic;

/*
 * 复核状态
 * 
 * 0--待复核
 * 1--复核通过
 * 2--复核拒绝
 * 	
 */
public enum IsUseStatus {
	
	UNCHECK("0") ,CHECKPAS("1"),CHECKREF("2");
	
	private String code;
	
	private IsUseStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
