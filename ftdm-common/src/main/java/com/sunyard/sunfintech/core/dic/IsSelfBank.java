package com.sunyard.sunfintech.core.dic;

/**
 * Created by Lid on 2017/7/16.
 * 是否本行卡
 * YES 是
 * NO 否
 */
public enum IsSelfBank {
    YES("0"),NO("1");

    private String code;

    private IsSelfBank(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
