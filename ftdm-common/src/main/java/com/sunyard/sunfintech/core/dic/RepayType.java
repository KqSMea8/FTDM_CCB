package com.sunyard.sunfintech.core.dic;

/*
 *  还款类型
 * 
 *  0-代偿还款
 *  1-委托还款
 */
public enum RepayType {

	COMPENSATORY("0"),
	ENTRUST("1");

	private String code;
	
	private RepayType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
