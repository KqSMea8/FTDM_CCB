package com.sunyard.sunfintech.core.dic;
/*
 * 卡状态
 * 
 * 0-未激活
 * 1-已激活
 * 2-已冻结
 */

public enum CardStatus {
	
	UNACTIVE("0") ,ACTIVE("1"),FORZEN("2");
	
	private String code;
	
	private CardStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
