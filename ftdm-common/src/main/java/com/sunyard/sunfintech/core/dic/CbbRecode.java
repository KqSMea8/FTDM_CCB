package com.sunyard.sunfintech.core.dic;

/**
 * Created by dingjy on 2017/6/14.
 */
public enum CbbRecode {
    SUCCESS("000000"),
    FAIL("999999"),
    VERIFICATION_ERROR("001003"),
    VERIFICATION_ERROR1("BF00105"),//宝付验证码错误
    VERIFICATION_ERROR2("9700"),//连连验证码错误
    VERIFICATION_OUT_OF_DATE("001002"),
    VERIFICATION_ERROR_TOO_MUCH("020513"),
    VERIFICATION_ERROR_TOO_MUCH1("BF00261"),//宝付验证码错误次数过多
    VERIFICATION_ERROR_TOO_MUCH2("9702"),//连连验证码错误次数过多
    COMMUNICATION_OVERTIME("200400"),
    COMMUNICATION_OVERTIME1("200205"),//支付验证码超时
    COMMUNICATION_OVERTIME2("BF00260"),//宝付验证码超时
    COMMUNICATION_OVERTIME3("9703"),//连连验证码超时
    ORDER_HAS_PAY("200107");//支付返回订单已支付
    private String code;

    private CbbRecode(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }
}
