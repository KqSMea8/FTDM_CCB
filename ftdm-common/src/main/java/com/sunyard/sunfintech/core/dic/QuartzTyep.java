package com.sunyard.sunfintech.core.dic;

/**
 * 自动提现模式
 *
 * 1-银联模式  2-行内模式
 * Created by PengZY on 2017/7/10.
 */
public enum QuartzTyep {

    UNIONPAY("1"),BANK("2");

    private String code;

    private QuartzTyep(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }

}
