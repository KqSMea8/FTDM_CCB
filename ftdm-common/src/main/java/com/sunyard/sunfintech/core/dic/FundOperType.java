package com.sunyard.sunfintech.core.dic;

/*
 * 资金原子操作类型
 * 
 * frozen 冻结
 * unfrozen 解冻
 * chargeOff 出账
 * chargeEnter 入账
 * transfer 转账
 * 
 */

public enum FundOperType {
	
	FROZEN("01"),UNFROZEN("02"),CHARGEOFF("03"),CHARGEENTER("04"),TRANSFER("05");
	
	private String code;
	
	private FundOperType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
