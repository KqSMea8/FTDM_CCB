package com.sunyard.sunfintech.core.dic;

/**
 * 0是设置密码，1是重置密码
 */
public enum TranFlag {

    SET("0"),RESET("1");

    private String code;

    private TranFlag(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
