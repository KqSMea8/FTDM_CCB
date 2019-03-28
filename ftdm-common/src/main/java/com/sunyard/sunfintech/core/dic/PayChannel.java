package com.sunyard.sunfintech.core.dic;

/*
 * 支付通道
 */

public enum PayChannel {
	
	E_BANK("E-BANK"),YS("YS"),E8TONG("E8TONG"),CZCB("CZCB"),YEEPAY("YEEPAY");
	
	private String code;
	
	private PayChannel(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
