package com.sunyard.sunfintech.core.dic;

/*
 * 还款状态
 * 
 * 0--未还款
 * 1--待还款
 * 2--已还款
 * 
 */

public enum RepaymentStatus {
	
	NOTPAY("0"),WAITINGPAY("1"),SUCCPAY("2");
	
	private String code;
	
	private RepaymentStatus(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
