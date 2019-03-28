package com.sunyard.sunfintech.core.dic;

/*
 * 产品起息方式
 *	0-	满额度起息
 *	1-	成立日起息
 *	2-	交易日起息
 *
 */

public enum ValueType {
	
	FULLVALUE("0"),DATEVALUE("1"),TRANDATE("2");
	
	private String code;
	
	private ValueType(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
