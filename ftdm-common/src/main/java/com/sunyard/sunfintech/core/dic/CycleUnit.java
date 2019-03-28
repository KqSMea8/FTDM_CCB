package com.sunyard.sunfintech.core.dic;

/*
 * 循环方式
 * 
 * day-天
 * week-周
 * month-月
 * quarter-季
 * halfyear
 * year-年
 */
public enum CycleUnit {
	
	DAY("1"),WEEK("2"),MONTH("3"),QUARTER("4"),HALFYEAR("5"),YEAR("6");
	
	private String code;
	
	private CycleUnit(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
