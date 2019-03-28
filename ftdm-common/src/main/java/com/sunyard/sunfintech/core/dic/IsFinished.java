package com.sunyard.sunfintech.core.dic;

/**
 * Created by PengZY on 2018/3/12.
 * 标的是否结清
 *
 * 1: 已经结清
 * 2：没有结清
 */
public enum IsFinished {

    YES("1"),NO("2");

    private String code;

    private IsFinished(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }

}
