package com.sunyard.sunfintech.core.dic;

/**
 * Created by dingjy on 2017/6/4.
 * 0   待支付
 * 2   处理中
 * 3   交易成功
 * 7   交易失败
 * 9   待短验
 */
public enum EpayStatus {
    WAIT_PAY("0","待支付"),PAYING("2","处理中"),SUCCESS("3","交易成功"),FAILRETURN("6","交易失败未扣款"), FAIL("7","交易失败已扣款"),WAIT_SMS("9","待短验");

    private String code;
    private String msg;

    private EpayStatus(String code, String msg){
        this.code= code;
        this.msg=msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return  msg;
    }

    public String getMsgByCode(String code){
        String msg="未知枚举";
        switch (code){
            case "0":msg=WAIT_PAY.getMsg();break;
            case "2":msg=PAYING.getMsg();break;
            case "3":msg=SUCCESS.getMsg();break;
            case "6":msg=FAILRETURN.getMsg();break;
            case "7":msg=FAIL.getMsg();break;
            case "9":msg=WAIT_SMS.getMsg();break;
        }
        return msg;
    }
}
