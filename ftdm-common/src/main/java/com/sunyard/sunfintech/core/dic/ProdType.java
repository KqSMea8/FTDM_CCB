package com.sunyard.sunfintech.core.dic;

/*
 * 产品状态
 * 
 * 0-周期性产品
 * 1-活期产品
 */
public enum ProdType {
	
	PERIOD("0"),CURRENT("1");
	
	private String code;
	
	private ProdType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
