package com.sunyard.sunfintech.core.dic;

/**
 * Created by PengZY on 2017/5/28.
 * 支付状态/交易状态
 * 充值信息表business_recharge,交易状态status字段
 * PayAgentCollectionApplyResponse返回字段pay_status
 * PayAgentCollectionConfirmResponse返回字段pay_status
 *
 * 20	交易成功
 * 21	交易失败
 * 22	待发送短信(请求短信失败后的状态)
 * 23	初始化
 * 24	处理中
 * 25   未支付/待确认支付(请求短信成功,还未支付的状态)
 * 26   退款
 *
 * 	FAILNO("6","交易失败未扣款"),
 *  FAILYES("9","交易失败已扣款"),
 *
 */

public enum PayStatus {

    SUCCESS("20","交易成功"),
    FAIL("21","交易失败"),
    WAIT_SMS("22","待发送短信"),
    INIT_PAY("23","初始化"),
    PAYING("24","处理中"),
    WAIT_PAY("25","未支付"),
    REFUND("26","退款"),
    REFUNDPSUCCESS("31","已退款"),

    PAYMENTFAILNO("6","交易失败未扣款"),
    PAYMENTFAILYES("7","交易失败已扣款"),
    PAYMENTPROCESSING("2","处理中"),
    PAYMENTSUCCESS("3","成功"),

    /**
     * CCB提现状态
     */
    FAIL_DF("31","代发失败"),
    FAIL_ZDJZ("41","自动加值失败"),
    CCB_INIT_PAY("33","初始化"),
    CCB_SENDING("34","代发处理中"),
    CCB_SEND_SUCCESS("35","代发成功"),
    CCB_ADDING("36","自动加值处理中"),
    CCB_ADD_SUCCESS("37","自动加值成功"),
    CCB_PAYING("38","代付处理中");

    private String code;
    private String msg;

    private PayStatus(String code, String msg){
        this.code= code;
        this.msg=msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return  msg;
    }


}
