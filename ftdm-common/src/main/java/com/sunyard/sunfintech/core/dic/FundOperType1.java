package com.sunyard.sunfintech.core.dic;
/*
 * 冻结解冻
 * 
 * frozen 冻结
 * unfrozen 解冻
 */
public enum FundOperType1 {
	
	FROZEN("0"),UNFROZEN("1");
	
	private String code;
	
	private FundOperType1(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
