package com.sunyard.sunfintech.core.dic;
/**
 * PLAT 自有
 * FLOATPAYMENT 在途垫付
 * INVEST 投资账户
 * FINANCING 融资账户
 * @author Lid
 *
 */
public enum AccountBalanceType {
	PLAT("1"),FLOATPAYMENT("11"),INVEST("12"),FINANCING("13");

	private String code;

	private AccountBalanceType(String code){
		this.code= code;
	}

	public String getCode(){
		return code;
	}
}
