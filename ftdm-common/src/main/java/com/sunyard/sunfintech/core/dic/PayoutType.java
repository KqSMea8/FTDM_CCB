package com.sunyard.sunfintech.core.dic;

/*
 * 分红方式
 * 
 * 0-	成立分红
 * 1-	还款分红
 * 
 */
public enum PayoutType {
	
	CREATE("0"),REPAY("1");
	
	private String code;
	
	private PayoutType(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
