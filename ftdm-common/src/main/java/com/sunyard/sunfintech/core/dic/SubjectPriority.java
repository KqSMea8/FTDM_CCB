package com.sunyard.sunfintech.core.dic;

/*
 * 付款科目优先级
 * 
 * 付款科目优先级
 * 0 可提优先 
 * 1 可投优先
 * 2 两者均分
 * 3 平台托管科目
 * 4 自定义优先级
 */

public enum SubjectPriority {
	
	EXTRACTPRIORITY("0"),INVESTPRIORITY("1"),TWOSPLIT("2"),PLATTRUSTSUBJECT("3"),CUSTOMPRIORITY("4");
	
	private String code;
	
	private SubjectPriority(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
