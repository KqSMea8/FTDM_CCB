package com.sunyard.sunfintech.core.dic;

/*
 * 反馈状态
 * 
 * 0-未收到
 * 1-已收到
 * 
 */

public enum FeedBackStatus {
	
	UNRECEIVE("0") ,RECEIVE("1");
	
	private String code;
	
	private FeedBackStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
