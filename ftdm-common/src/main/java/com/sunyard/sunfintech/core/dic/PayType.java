package com.sunyard.sunfintech.core.dic;

/*
 * 支付类型
 * 
 */

public enum PayType {

	AUTHENTICATION("0"), GATEWAY("1");

    private String name;

    private PayType(String code){
        name = code;
    }
    public String getCode(){
        return name;
    }
}
