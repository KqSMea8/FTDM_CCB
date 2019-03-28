package com.sunyard.sunfintech.core.dic;

/*
 * 投融资转换类型
 * 1.投资转融资
 * 2.融资转投资(暂不开放)
 */

public enum SwitchType {

    INVEST_TO_FINANCING("1"), FINANCING_TO_INVEST("2");

    private String name;

    SwitchType(String code){
        name = code;
    }

    public String getCode(){
        return name;
    }
}
