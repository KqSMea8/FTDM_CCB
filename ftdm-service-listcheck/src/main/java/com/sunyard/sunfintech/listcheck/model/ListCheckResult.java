package com.sunyard.sunfintech.listcheck.model;

/**
 * 资金流水核对结果
 * @author heroy
 * @version 2018/6/12
 */
public enum ListCheckResult {
    EQUAL(1,"资金流水正确"),
    AMTERROR(2,"本金不平"),
    INTERESTERROR(3,"利息不平"),
    GIFTERROR(4,"红包不平"),
    FEEERROR(5,"手续费不平"),
    NO_AMT_LIST(6,"无资金流水"),
    PROD_SHARE_LIST_ERROR(7,"投资明细不平"),
    NO_PROD_SHARE_LIST(8,"投资明细缺失"),
    PROD_INALL_ERROR(9,"份额不平"),
    NO_PROD_INALL(10,"份额缺失"),
    AMT_LIST_NO_ROLLBACK(11,"资金流水未回滚"),
    SHARE_LIST_NO_ROLLBACK(12,"投资明细未回滚"),
    SHARE_INALL_NO_ROLLBACK(13,"份额未回滚"),
    ORDER_PROCESS(14,"订单处理中"),
    NO_REQLOG(15,"缺少req_log"),
    ERROR(99,"对账异常"),
    GETMSG(-1,"获取信息");

    private int code;

    private String msg;

    private ListCheckResult(int code, String msg){
        this.code= code;
        this.msg=msg;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public String getMsgByCode(int code){
        String msg="未知枚举";
        switch (code){
            case 1:msg=EQUAL.getMsg();break;
            case 2:msg=AMTERROR.getMsg();break;
            case 3:msg=INTERESTERROR.getMsg();break;
            case 4:msg=GIFTERROR.getMsg();break;
            case 5:msg=FEEERROR.getMsg();break;
            case 6:msg= NO_AMT_LIST.getMsg();break;
            case 7:msg= PROD_SHARE_LIST_ERROR.getMsg();break;
            case 8:msg= NO_PROD_SHARE_LIST.getMsg();break;
            case 9:msg= PROD_INALL_ERROR.getMsg();break;
            case 10:msg= NO_PROD_INALL.getMsg();break;
        }
        return msg;
    }
}
