package com.sunyard.sunfintech.core.dic;

/*
 * 产品状态
 * 
 * 0-产品筹备
 * 1-产品发布
 * 2-产品成立
 * 3-产品失败
 * 4-产品完成。
 *
 */
public enum ProdState {
	
	CREATE("0"),PUBLISH("1"),FOUND("2"),LOSE("3"),OVER("4");
	
	private String code;
	
	private ProdState(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
