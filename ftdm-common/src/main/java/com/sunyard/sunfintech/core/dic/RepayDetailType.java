package com.sunyard.sunfintech.core.dic;

/**
 * 查询类型
 * 查询投资人收款记录 1
 * 查询借款人还款记录 2
 * @author Lid
 *
 */
public enum RepayDetailType {
	REPAYMENTLIST("1"),PRODBORROWERREALREPAY("2");

	private String code;

	private RepayDetailType(String code){
		this.code= code;
	}

	public String getCode(){
		return code;
	}
}
