package com.sunyard.sunfintech.core.dic;

/*
 *	0-	未转换
 *	1-	部分转换
 *	2-	全部转换
 *
 */

public enum SwitchState {

	NOSWITCH("0"),PARTSWITCH("1"),ALLSWITCH("2");

	private String code;

	private SwitchState(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
