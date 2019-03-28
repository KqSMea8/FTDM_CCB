package com.sunyard.sunfintech.core.dic;

/*
 *  0、成标后自动出账至借款人投资账户
 *  1、调用成标出账接口，出账至标的对应收款账户
 *  2、垫付出账-成标出账至平台垫付实体账户
 */

public enum ChargeOffType {
	
	ZERO("0"), ONE("1"),TWO("2");

	private String code;
	
	private static String[] codes={"0","1","2"};
	
	private ChargeOffType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	public static String[] getcodes() {
		return codes;
	}
}
