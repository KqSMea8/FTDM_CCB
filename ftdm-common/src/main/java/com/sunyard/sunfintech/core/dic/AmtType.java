package com.sunyard.sunfintech.core.dic;
/*
 * 资金流向
 * 
 * 0-收入 
 * 1-支出 
 * 2-冻结
 * 3-解冻
 */

public enum AmtType {
	
	INCOME("0"),EXPENSIVE("1"),FROZEN("2"),UNFROZEN("3");
	
	private String code;
	
	private AmtType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
