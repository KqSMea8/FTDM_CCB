package com.sunyard.sunfintech.core.dic;

/*
 * 婚姻状态
 * 
 * 0--已婚
 * 1--未婚
 */
public enum Marital {
	
	MARRIED("0"),SPINSTERHOOD("1");
	
	private String code;

	private Marital(String code) {
		this.code = code;
	}
	public String getCode(){
		return code;
	}
}
