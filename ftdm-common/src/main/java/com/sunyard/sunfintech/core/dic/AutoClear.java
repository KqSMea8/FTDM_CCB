package com.sunyard.sunfintech.core.dic;

/*
 *	0-	不自动清算
 *	1-	自动清算
 *
 */

public enum AutoClear {

	AUTO_CLEAR("0"),NO_AUTO_CLEAR("1");

	private String code;

	private AutoClear(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
