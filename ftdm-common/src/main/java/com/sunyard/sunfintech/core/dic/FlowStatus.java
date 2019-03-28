package com.sunyard.sunfintech.core.dic;

/*
 *  流水状态
 *  
 *  0-	处理中
 *  1-	交易成功
 *  2-	交易失败
 *  3-  未知状况
 *  11- 请求成功
 *  12- 请求失败
 *  13- 请求未知
 *  21- 确认成功
 *  22- 确认失败
 *  23- 确认未知
 *  24- 允许重试
 */

public enum FlowStatus {
	
	INPROCESS("0"),SUCCESS("1"),FAIL("2"),UNKNOWN("3"),REQUESTSUCCESS("11"),REQUESTFAIL("12"),REQUESTUNKNOWN("13"),CONFIRMSUCCESS("21"),CONFIRMFAIL("22"),CONFIRMUNKOWN("23"),WILLTRY("24");

	private String code;
	
	private FlowStatus(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
}
