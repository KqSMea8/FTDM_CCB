package com.sunyard.sunfintech.core.dic;

/*
 * 冻结类型
 * 
 * 0-账户冻结
 * 1-份额冻结
 *
 */

public enum FrozenType {
	
	ACCFROZEN("0"),SHAREFROZEN("1");
	
	private String code;
	
	private FrozenType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
