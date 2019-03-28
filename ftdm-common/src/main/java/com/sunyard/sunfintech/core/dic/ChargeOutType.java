package com.sunyard.sunfintech.core.dic;
/*
 * 出账状态
 * 
 * 0--自动出账 
 * 1--手动出账
 */

public enum ChargeOutType {
	
	AUTO("0") ,MANUAL("1");
	
	private String code;
	
	private ChargeOutType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
