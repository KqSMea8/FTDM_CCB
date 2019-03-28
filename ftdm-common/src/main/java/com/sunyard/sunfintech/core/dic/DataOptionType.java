
package com.sunyard.sunfintech.core.dic;

/*
 * 账户类型
 * 
 * Plat-平台账户
 * PlatCust-客户账户
 * ProdAcc-标的账户
 * Invest-投资账户
 * Finance-融资
 */

public enum DataOptionType {

	NEED_INSERT("0"),NOT_TO_DO("1"),NEED_UPDATE("2");

	private String code;

	private DataOptionType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
