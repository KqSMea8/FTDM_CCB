
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

public enum AccType {
	
	PLAT("1"),PLATCUST("5"),PRODACC("6"),INVEST("12"),FINANCE("13");
	
	private String code;
	
	private AccType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
