package com.sunyard.sunfintech.core.dic;

/*
 * 交易状态常量
 * 
 * 0 - 未激活
 * 1 - 已激活
 * 2 - 作业暂停
 * 3 - 作业成功
 * 4 - 作业失败
 * 5 - 作业中断
 * Z - 正在处理 
 *
 */

public enum DealStatus {
	
	UNACTIVATED("0"),ACTIVATED("1"),PENDING("2"),SUCCESS("3"),FAILURE("4"),ABORT("5"),PROCESSING("Z");
	
	private String code;
	
	private DealStatus(String code){
		this.code=code;
	}
	
	public String getCode(){
		return code;
	}
	
}
