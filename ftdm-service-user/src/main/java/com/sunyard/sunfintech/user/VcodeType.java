package com.sunyard.sunfintech.user;

import com.sunyard.sunfintech.core.CodeStatus;

/**
 * 【功能描述】
 *
 * @author wyc  2018/1/24.
 */

public enum VcodeType  {

    ModifyPwd("1", "修改密码"),
    SetPwd("2", "设置密码"),
    OrgRegister("3", "企业开户"),;


    private String code;

    private String message;

    private VcodeType(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String  code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
}