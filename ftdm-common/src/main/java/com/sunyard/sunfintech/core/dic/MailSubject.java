package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/12/26.
 */
public enum MailSubject {

    SYSTEM_ERROR_SUBJECT("存管2.0系统异常通知");

    private String subject;

    private MailSubject(String subject){
        this.subject=subject;
    }

    public String getSubject(){
        return subject;
    }

}
