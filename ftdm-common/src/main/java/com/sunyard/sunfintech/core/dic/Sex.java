package com.sunyard.sunfintech.core.dic;

/*
 * 性别 
 * 
 * 0--男性
 * 1--女性
 */

public enum Sex {

	MALE("0"),FEMALE("1");
	
	private String code;
	
	private Sex(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
