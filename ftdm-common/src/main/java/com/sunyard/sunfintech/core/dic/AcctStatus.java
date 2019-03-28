package com.sunyard.sunfintech.core.dic;
/*
 * 账户状态
 * 
 * 0-未激活
 * 1-已激活
 * 2-已冻结
 */

public enum AcctStatus {
	
	UNACTIVE("0") ,ACTIVE("1"),FORZEN("2");
	
	private String code;
	
	private AcctStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
