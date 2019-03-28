/********************************************
 * 
 *********************************************/

package com.sunyard.sunfintech.core.dic;

/*
 * 子科目
 *
 * Invest--投资--01
 * Financing--融资--02
 * Eaccount--电子账户--03
 * Plat--平台自有--11
 * Fee--手续费--13
 * Experience--体验金--14
 * Voucher--抵用金--15
 * PlusRate--加息活动金--16
 * Deposit--保证金--17
 * Prod--标的科目--18
 * Payment--垫付--19
 * Inlinepayment --行内垫付--20
 * INTEREST--利息--21
 */
public enum Ssubject {
	
	INVEST("01","投资"),FINANCING("02","融资"),EACCOUNT("03","电子账户"),PLAT("11","平台自有"),FEE("11","手续费"),EXPERIENCE("11","体验金"),
	VOUCHER("11","抵用金"),PLUSRATE("11","加息活动金"),DEPOSIT("11","保证金"),PROD("18","标的科目"),PAYMENT("19","垫付"),INLINEPAYMENT("20","行内垫付"),INTEREST("21","利息");
	
	private String code;

	private String msg;
	
	private Ssubject(String code,String msg){
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
			case "01":msg=INVEST.getMsg();break;
			case "02":msg=FINANCING.getMsg();break;
			case "03":msg=EACCOUNT.getMsg();break;
			case "11":msg=PLAT.getMsg();break;
			case "13":msg=FEE.getMsg();break;
			case "14":msg=EXPERIENCE.getMsg();break;
			case "15":msg=VOUCHER.getMsg();break;
			case "16":msg=PLUSRATE.getMsg();break;
			case "17":msg=DEPOSIT.getMsg();break;
			case "18":msg=PROD.getMsg();break;
			case "19":msg=PAYMENT.getMsg();break;
			case "20":msg=INLINEPAYMENT.getMsg();break;
			case "21":msg=INTEREST.getMsg();break;
		}
		return msg;
	}
}
