package com.sunyard.sunfintech.core.dic;

/*
 * 11--提现
 * 12--充值
 */

public enum CardPurpose {

	WITHDRAW("11"),CHARGE("12");
	
	private String code;
	
	private CardPurpose(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
