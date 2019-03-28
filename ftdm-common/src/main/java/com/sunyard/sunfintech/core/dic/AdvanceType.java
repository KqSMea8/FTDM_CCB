package com.sunyard.sunfintech.core.dic;


/*
 * 是否垫付
 * 1-不垫付 2-普通垫付(垫资人垫资) 3-行内垫付
 */

public enum AdvanceType {
	NOADVANCE("1"),TOADVANCE("2"),INLINEADVANCE("3");
	
	private String code;
	
	private AdvanceType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
