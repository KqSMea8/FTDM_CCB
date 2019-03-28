package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/8/23.
 */
public enum MessageFlag {
    SEND_MESSAGE("1"),
    NOT_SENT_MESSAGE("0");

    private String flag;

    private MessageFlag(String flag){
        this.flag=flag;
    }

    public String getFlag() {
        return flag;
    }
}
