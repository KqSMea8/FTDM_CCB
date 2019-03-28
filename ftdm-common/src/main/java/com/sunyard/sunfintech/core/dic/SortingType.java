package com.sunyard.sunfintech.core.dic;

/**
 * Created by Lid on 2017/7/18.
 * START 开始清算
 * END 结束清算
 */
public enum SortingType {
    START("1"),END("2"),PRE_CLEAR_EXCEPTION("3");
    private String code;

    private SortingType(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
