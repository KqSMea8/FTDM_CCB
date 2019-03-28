package com.sunyard.sunfintech.core.dic;

/*
 * 0 - 注销
 * 1 - 正常
 * 2 - 注册
 */

public enum UsersTatus {

	NORMAL("1"),UNNORMAL("0"),REGIST("2");

	private String code;
	
	private UsersTatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
