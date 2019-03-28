package com.sunyard.sunfintech.core.dic;

/*
 * 代偿人类型
	0-代偿人代偿(默认)
	1-委托代偿
	2-风险金代偿
 */

public enum CompensationType {

	DEBIT("0"),CREDIT("1"),DXJDC("2");

	private String code;

	private CompensationType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
