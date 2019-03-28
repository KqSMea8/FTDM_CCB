package com.sunyard.sunfintech.core.dic;

/*
 * 用户类型
 * 
 * 1--个人
 * 2--企业
 */

public enum CusType {

	PERSONAL("1"),COMPANY("2");

	private String code;

	private CusType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
