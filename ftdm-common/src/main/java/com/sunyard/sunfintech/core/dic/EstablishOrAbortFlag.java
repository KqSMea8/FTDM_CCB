package com.sunyard.sunfintech.core.dic;

/*
 * 标的成废标志
 * 2-成立
 * 3-废除
 */

public enum EstablishOrAbortFlag {

	ESTABLISH("2"),ABORT("3");

	private String code;

	private EstablishOrAbortFlag(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
