
package com.sunyard.sunfintech.core.dic;

/*
 * 密码设置类型
 * 
 * SET_NEW_PASSWORD：设置新密码
 * RESET_PASSWORD：重置密码
 */

public enum PwdSetType {

	SET_NEW_PASSWORD("1"),RESET_PASSWORD("2");

	private String code;

	private PwdSetType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
