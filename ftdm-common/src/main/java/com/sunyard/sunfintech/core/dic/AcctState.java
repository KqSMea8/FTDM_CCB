/********************************************
 * 文件名称: ACCTSTA <br/>
 * 功能说明: 到账状态
 *********************************************/

package com.sunyard.sunfintech.core.dic;
/*
 * 0-资金未到
 * 1-资金收到
 */

public enum AcctState {
	UnReach("0"),Reach("1"),Over("3");

	private String code;

	private AcctState(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
