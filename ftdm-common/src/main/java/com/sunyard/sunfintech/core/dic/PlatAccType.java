package com.sunyard.sunfintech.core.dic;

/**
 * 平台账户类型
 *
 * 01存管账户、02自有账户、03清算账户、04保证金账户
 *
 */

public enum PlatAccType {
	
	PLATTRUST("01"),PLATOWN("02"),PLATCLEAR("03"),PLATCASH("04");
	
	private String code;
	
	private PlatAccType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
