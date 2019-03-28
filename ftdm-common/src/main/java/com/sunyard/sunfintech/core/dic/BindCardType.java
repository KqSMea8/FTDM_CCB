package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/6/15.
 */

/**
 * 0--电子账户开户绑卡
 * 1--短验绑卡
 * 2--无短验绑卡
 * 3--四要素绑卡
 * 4--对公绑卡
 * 5--三要素绑卡
 */
public enum  BindCardType {
    EACCOPENBIND("0"),MSGBINDCARD("1"),NOMSGBINDCARD("2"),FOURFOCUSBINGCARD("3"),ORGBINGCARD("4"),BINDCREDITCARD("5");

    private String code;

    private BindCardType(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
