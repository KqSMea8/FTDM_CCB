package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/7/3.
 */
public enum MsgType {
    MALL_NAME_KEY("mall_name"),
    OPEN_ACCOUNT_NOTIFY("8"),
    RECHARGE_SUCCESS_NOTIFY("1"),
    INVEST_SUCCESS_NOTIFY("6"),
    REPAY_NOTIFY("2"),
    INVEST_REFUND_NOTIFY("9"),
    PROD_TRANS_REFUND_NOTIFY("7");

    private String type;

    private MsgType(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }
}
