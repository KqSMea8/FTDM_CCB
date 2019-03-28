package com.sunyard.sunfintech.core.dic;

/**
 * 模块名称
 * thirdparty 三方
 * account 账户
 * prod 标的
 * user 用户
 * web 对外接口层
 * ftdm 全局
 * @author heroy
 * @version 2018/1/10
 */
public enum ServiceName {
    THIRDPARY("thirdparty"),ACCOUNT("account"),PROD("prod"),USER("user"), WEB("web"), FTDM("ftdm");
    private String name;
    private ServiceName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
