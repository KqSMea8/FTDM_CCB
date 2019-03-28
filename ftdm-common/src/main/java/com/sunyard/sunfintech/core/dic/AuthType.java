package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2018/1/21.
 */
public enum AuthType {
    /**
     * 1、出借，2、还款，3、缴费，4、全部
     */
    AUTH_INVEST("1","出借"), AUTH_REFUND("2","还款"), AUTH_RECHARGE("3","缴费");

    private String code;

    private AuthType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private String message;

    AuthType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
