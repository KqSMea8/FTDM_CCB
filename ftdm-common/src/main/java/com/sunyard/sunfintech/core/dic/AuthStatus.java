package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2018/1/7.
 * 1.已授权
 * 2.未授权
 */
public enum AuthStatus {
    ALREADY_AUTH("1","已授权"), NO_AUTH("2","取消授权");

    private String code;

    private AuthStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private String message;

    AuthStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
