package com.sunyard.sunfintech.core.dic;

/**
 * Created by dingjy on 2017/7/1.
 * 01--入金后实时发送（目标手机号码为用户在网贷平台上的注册手机号码）
 * 02--出金返回代付成功结果后发送（目标手机号码为用户在网贷平台上的注册手机号码）
 * 03--入金短信验证码（目标手机号码为用户绑定银行卡的预留手机号码）
 * 04--出金短信验证码（目标手机号码为用户在网贷平台上的注册手机号码）
 * 05--存管账户存管账户开户短信验证码（目标手机号码为用户将要绑定的银行卡预留手机号码）
 * 06--投资成功后发送短信通知（目标手机号码为用户在网贷平台上的注册手机号码）
 * 07--债权转让成功后发送短信通知（目标手机号码为用户在网贷平台上的注册手机号码）
 * 08--存管账户开通成功后发送短信通知（目标手机号码为用户在网贷平台上的注册手机号码）
 * 09--标的还款后给出借人发送短信通知（目标手机号码为用户在网贷平台上的注册手机号码）
 */
public enum CcbMsgType {
    DEPOSIT("01"),
    WITHDRAW("02"),
    DEPOSIT_AUTH_CODE("03"),
    WITHDRAW_AUTH_CODE("04"),
    OPEN_ACCOUNT_AUTH_CODE("05"),
    INVEST_SUCCESS_MSG("06"),
    ASSIGNMENT_OF_DEBT_MSG("07"),
    OPEN_ACCOUNT_SUCCESS_MSG("08"),
    PROD_REFUND_SUCCESS_MSG("09");

    private String code;

    private CcbMsgType(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
