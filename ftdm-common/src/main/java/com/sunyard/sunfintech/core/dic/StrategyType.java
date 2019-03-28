package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2018/6/21.
 */
public enum StrategyType {

    AMTLIST("1"),REQLOG("2");

    private String code;

    private StrategyType(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
