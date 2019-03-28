package com.sunyard.sunfintech.listcheck.model;

/**
 * 资金转账方向
 * @author heroy
 * @version 2018/6/12
 */
public enum TransDirection {
    PLAT2PERSON("平台转个人"),
    PLAT2PLAT("平台转平台"),
    PERSON2PLAT("个人转平台"),
    PERSON2PERSON("个人转个人");

    TransDirection(String msg){
        this.msg = msg;
    }
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
