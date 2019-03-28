package com.sunyard.sunfintech.core.dic;

/**
 *
 * 提现任务受理开关
 *
 * 0-禁用，1-启用
 *
 * Created by PengZY on 2017/7/10.
 */
public enum ReceiveStatus {

    CLOSE("0"),OPEN("1");

    private String code;

    private ReceiveStatus(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }

}
