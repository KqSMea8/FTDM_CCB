package com.sunyard.sunfintech.core.dic;

/*
 * 卡类型  
 * 
 * 1--借记卡
 * 2--信用卡
 */

public enum CardType {

	DEBIT("1"),CREDIT("2");
	
	private String code;
	
	private CardType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
