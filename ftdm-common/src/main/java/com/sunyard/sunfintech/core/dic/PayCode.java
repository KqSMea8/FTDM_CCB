
package com.sunyard.sunfintech.core.dic;

/*
 * 支付编码
 * 
 * YB-易宝
 * FY-富有
 * YZF-翼支付
 * ZJ-中金
 * LL-连连
 * RBDS-融宝代收
 * RBDF-融宝代付
 * WYB-网易宝
 */

public enum PayCode {
	
	YB("051"),YZF("061"),FY("071"),ZJ("018"),LL("029"),RBDS("034"),RBDF("033"),WYB("028");
	
	private String code;
	
	private PayCode(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
