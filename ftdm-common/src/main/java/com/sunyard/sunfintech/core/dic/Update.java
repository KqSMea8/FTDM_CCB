package com.sunyard.sunfintech.core.dic;

/*
 * 更新流水
 * 
 * 0-更新中
 * 1-更新成功
 * 2-更新失败
 * 
 */

public enum Update {
	
	UPDATEING("0"),SUCCESS("1"),FAIL("2");
	
	private String code;
	
	private Update(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
