/********************************************
 * 
 *********************************************/

package com.sunyard.sunfintech.core.dic;

/*
 * 产品成立方式
 * 
 * 0-	满额成立
 * 1-	成立日成立
 * 
 */
public enum CreateType {
	
	FULLCRETE("0"),DATECRETE("1");
	
	private String code;
	
	private CreateType(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
