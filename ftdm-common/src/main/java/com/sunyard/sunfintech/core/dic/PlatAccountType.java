package com.sunyard.sunfintech.core.dic;
/*
1--自有
3--手续费
4--体验金
5--抵用金
6--加息活动金
7--保证金
9--奖励金
10--现金垫付
11--在途垫付
 */

public enum PlatAccountType {
	Own("1"),Fee("03"),Experience("4"),Voucher("5"),PlusRate("6"),Deposit("7"),
	Bounty("9"),Cash("10"),Float("11");

	private String code;

	private PlatAccountType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
