package com.sunyard.sunfintech.core.dic;

/*
 * 冻结状态
 * 
 * 0-冻结
 * 1-解冻
 *
 */

public enum FrozenStatus {
	
	FROZEN("0"), UNFROZEN("1");
	
	private String code;
	
	private FrozenStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
