package com.sunyard.sunfintech.core.dic;

/*
 * 科目
 * 
 * Cash--现金--01
 * Float--在途--02
 *
 */
public enum Tsubject {
	CASH("01","现金"),FLOAT("02","在途");
	
	private String code;

	private String msg;
	
	private Tsubject(String code,String msg){
		this.code= code;
		this.msg=msg;
	}
	
	public String getCode(){
		return code;
	}

	public String getMsg(){
		return msg;
	}

	public String getMsgByCode(String code){
		String msg="未知枚举";
		switch (code){
			case "01":msg=CASH.getMsg();break;
			case "02":msg=FLOAT.getMsg();break;
		}
		return msg;
	}
}
