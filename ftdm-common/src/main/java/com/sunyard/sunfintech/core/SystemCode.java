package com.sunyard.sunfintech.core;

/**
 * 【功能描述】系统错误码
 *
 * @author wyc  2018/1/22.
 */
public enum SystemCode implements CodeStatus  {
    SUCCESS("10000", "成功"),
    BAD_REQUEST("202", "参数非法"),
    NOT_IN_WHITELIST("203", "不在白名单内");


    private String code;

    private String message;

    private SystemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String  code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
