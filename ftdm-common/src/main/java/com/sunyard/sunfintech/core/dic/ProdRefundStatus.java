package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2018/7/19.
 */
public enum ProdRefundStatus {

    PAYING("0"),SUCCESS("1"),WAITPAY("2"),FAIL("3");

    private String code;

    private ProdRefundStatus(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
