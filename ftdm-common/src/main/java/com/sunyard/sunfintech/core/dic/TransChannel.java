package com.sunyard.sunfintech.core.dic;

/*
 * 交易渠道
 * 
 * 	0--WEB端
 *	1-- 手机端
 *	2--微信端
 */
public enum TransChannel {
	
	WEB("0"),ANDROID("1"),IOS("2"),WECHAT("3");
	
	private String code;
	
	private TransChannel(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
