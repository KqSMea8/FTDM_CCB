package com.sunyard.sunfintech.core.dic;

/*
 * 是否使用
 * 
 * 0-否
 * 1-是
 * 
 */

public enum IsUse {

	NO("0"),YES("1");
	
	private String code;
	
	private IsUse(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
