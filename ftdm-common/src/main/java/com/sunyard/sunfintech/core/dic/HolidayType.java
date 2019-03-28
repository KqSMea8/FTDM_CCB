package com.sunyard.sunfintech.core.dic;
/*
 * 0-放假
 * 1-上班第一天
 */

public enum HolidayType {
    RestDay("1"), WorkFirst("2");

    private String code;

    private HolidayType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
