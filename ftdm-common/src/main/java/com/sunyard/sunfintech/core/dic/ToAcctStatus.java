package com.sunyard.sunfintech.core.dic;

/*
 * 到账状态
 * 
 * 0-资金未到
 * 1-资金收到
 */

public enum ToAcctStatus {
	
	UNREACH("0"),REACH("1"),OVER("3");
	
	private String code;
	
	private ToAcctStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
