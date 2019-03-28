package com.sunyard.sunfintech.core.dic;

/*
 *IDENTITY_CARD 身份证
 */
public enum IdType {

    IDENTITY_CARD("1");

    private String code;

    private IdType(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
