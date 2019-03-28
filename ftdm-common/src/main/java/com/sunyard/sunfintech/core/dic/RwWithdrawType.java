package com.sunyard.sunfintech.core.dic;

/**
 * Created by KouKi on 2018/5/14.
 * 0-代发
 * 1-自动加值
 * 2-代付给用户
 * 3-代付给平台
 */
public enum RwWithdrawType {
    WAIT_SEND("0","代发"), AUTH_ADD_VALUE("1","自动加值"), WAIT_PAY("2", "代付给用户"), WAIT_PAY_PLAT("3", "代付给平台");

    private String code;

    private RwWithdrawType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private String message;

    RwWithdrawType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
