package com.sunyard.sunfintech.core.dic;
/*
PLATTRANSLIMIT -- 平台限额
PLATTRANSACCOUNT--平台转账原因
 */
public enum ComParamType {
    PLATTRANSLIMIT("PTL00001"),PLATTRANSACCOUNT("PTA00001");
    private String code;

    private ComParamType(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}