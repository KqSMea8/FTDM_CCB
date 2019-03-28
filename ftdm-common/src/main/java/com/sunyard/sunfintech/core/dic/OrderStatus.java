package com.sunyard.sunfintech.core.dic;

/*
 * 处理状态
 *
 * 0-处理中
 * 1-交易成功
 * 2-交易失败
 * 11-请求成功
 * 12-请求失败
 * 21-确认成功
 * 22-确认失败
 *
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OrderStatus {

	PROCESSING("0","处理中"),
	SUCCESS("1","交易成功"),
	FAIL("2","交易失败"),
	REQUESTSUCCESS("11","请求成功"),
	REQUESTFAIL("12","请求失败"),
	//REQUESTUNKNOWN("13","请求未知"),
	CONFIRMSUCCESS("21","确认成功"),
	CONFIRMFAIL("22","确认失败"),
	//CONFIRMUNKOWN("23","确认未知");

	REFUNDPROCESSING("30","退款处理中"),
	PAY_TO_PLAT_PROCESSING("33","回拨处理中"),
	REFUNDPSUCCESS("31","退款成功"),
	REFUNDPFAIL("32","退款失败");
	//private final   Logger logger = LogManager.getLogger(this.getClass());

	private String code;
	private String msg;
	
	private OrderStatus(String code, String msg){
		this.code= code;
		this.msg=msg;
	}
	public static String getMsgByCode(String code) {
		String msg = "";
		try {
			for (OrderStatus orderStatus : OrderStatus.values()) {
				if (orderStatus.getCode().equals(code)) {
					return orderStatus.getMsg();
				}
			}

		} catch (Exception e) {
			msg="异常信息:"+e.getMessage();
			//logger.error("getMsgByCode()异常",e);
		}
		return msg;
	}

	public String getCode(){
		return code;
	}

	public String getMsg(){
		return  msg;
	}
}
