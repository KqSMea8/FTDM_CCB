package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/8/4.
 */
public enum ReturnAmtType {

    CASH("1"),FLOAT("2"),SOME_CASH_SOME_FLOAT("3");

    private String code;

    private ReturnAmtType(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
