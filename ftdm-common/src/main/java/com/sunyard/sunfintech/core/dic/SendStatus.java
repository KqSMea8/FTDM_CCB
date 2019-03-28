package com.sunyard.sunfintech.core.dic;

/**
 *
 * 提现指令发送开关
 * 0-禁用，1-启用
 * Created by PengZY on 2017/7/10.
 */
public enum SendStatus {

    CLOSE("0"),OPEN("1");

    private String code;

    private SendStatus(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }

}
