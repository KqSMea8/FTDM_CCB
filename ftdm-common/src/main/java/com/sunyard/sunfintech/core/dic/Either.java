package com.sunyard.sunfintech.core.dic;

/*
 * 启用禁用
 * 
 * 0、启用  
 * 1、禁用
 * 
 */

public enum Either {

	ZERO("0"), ONE("1");
	
	private String code;
	
	private Either(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
