package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2018/3/9.
 */
public enum IsBankCheck {

    YES("1"),NO("0");

    private String code;

    private IsBankCheck(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
