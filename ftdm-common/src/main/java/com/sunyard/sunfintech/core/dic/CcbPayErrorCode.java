package com.sunyard.sunfintech.core.dic;

/**
 * Created by dingjy on 2017/7/8.
 */
public enum CcbPayErrorCode {
    //todo 没写好
    ORDER_NOT_EXIT("200306");


    private String code;

    private CcbPayErrorCode(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
