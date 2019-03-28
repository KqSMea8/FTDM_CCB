package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/8/4.
 */
public enum SomeType {

    SOME_INVEST_AMT("1"),SOME_FEE_AMT("2");

    private String code;

    private SomeType(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
