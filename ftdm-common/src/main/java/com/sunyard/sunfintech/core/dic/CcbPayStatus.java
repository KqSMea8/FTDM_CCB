package com.sunyard.sunfintech.core.dic;

/**
 * Created by dingjy on 2017/6/22.
 * SUCCESS--成功
 * FAIL--失败
 * REQUEST_VERIFICATION_CODE_FAIL--请求认证支付第一步失败(请求短信失败后的状态)
 * INIT--初始化（网关充值pay返回表单后，数据库中的初始化状态）
 * PROCESSING--处理中
 * NON_PAYMENT--未支付/待确认支付(请求短信成功,还未支付的状态)
 * REIMBURSE--退款
 */
public enum  CcbPayStatus {
    SUCCESS("20","成功"),
    FAIL("21","失败"),
    REQUEST_VERIFICATION_CODE_FAIL("22","请求认证支付第一步失败(请求短信失败后的状态)"),
    INIT("23","初始化"),
    PROCESSING("24","处理中"),
    NON_PAYMENT("25","未支付/待确认支付"),
    REIMBURSE("26","退款");

    private String code;

    private String msg;

    private CcbPayStatus(String code,String msg){
        this.code= code;
        this.msg=msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public String getMsgByCode(String code){
        String msg="未知枚举";
        switch (code){
            case "20":msg=SUCCESS.getMsg();break;
            case "21":msg=FAIL.getMsg();break;
            case "22":msg=REQUEST_VERIFICATION_CODE_FAIL.getMsg();break;
            case "23":msg=INIT.getMsg();break;
            case "24":msg=PROCESSING.getMsg();break;
            case "25":msg=NON_PAYMENT.getMsg();break;
            case "26":msg=REIMBURSE.getMsg();break;
        }
        return msg;
    }
}
