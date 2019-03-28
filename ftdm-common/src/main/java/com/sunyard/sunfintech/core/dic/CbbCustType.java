package com.sunyard.sunfintech.core.dic;

/**
 * Created by dingjy on 2017/7/3.
 */
public enum CbbCustType {
    PERSONAL("0"),COMPANY("1");

    private String code;

    private CbbCustType(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
