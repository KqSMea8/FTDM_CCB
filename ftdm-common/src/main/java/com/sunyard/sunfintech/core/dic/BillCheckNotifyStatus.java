package com.sunyard.sunfintech.core.dic;

/**
 * 0-初始，1-成功，2-失败
 */
public enum BillCheckNotifyStatus {

    INIT("0"),
    SUCCESS("1"),
    FAILURE("2");

    private String code;

    private BillCheckNotifyStatus(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
