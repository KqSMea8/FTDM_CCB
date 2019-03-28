package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/9/13.
 */
public enum TransferStatus {

    SUCCESS(1),FAIL(2),INPROCESS(0);

    private Integer code;

    private TransferStatus(Integer code){
        this.code= code;
    }

    public Integer getCode(){
        return code;
    }
}
