package com.sunyard.sunfintech.core.dic;

/**
 * Created by terry on 2017/6/20.
 */
public enum  MsgContent {

    MALL_NAME_KEY("mall_name"),
    OPEN_ACCOUNT_NOTIFY("open_account_notify"), //600003(批量实名认证)，600004(电子帐户开户)，610012(绑卡短信确认)，620001(批量绑卡/四要素绑卡)
    RECHARGE_SUCCESS_NOTIFY("recharge_success_notify"), //C00002(网关充值),C00011(快捷充值确认)
    INVEST_SUCCESS_NOTIFY("invest_success_notify"), //P00001(批量投标)  X
    REPAY_NOTIFY("repay_notify"),  //PTA00001(平台转个人)
    INVEST_REFUND_NOTIFY("invest_refund_notify"), //P00006(标的还款)  X
    PROD_TRANS_REFUND_NOTIFY("prod_trans_refund_notify"), //P00005(标的转让)   X
    RW_WITHDRAW_TO_PLAT_NO("rw_withdraw_to_plat_no"), //W00001(提现)W00003(提现)
    BIND_CARD_DEBIT_NOTIFY("bind_card_debit_notify"), //610012(绑卡短信确认)
    BIND_CARD_CREDIT_NOTIFY("bind_card_credit_notify"), //610013(信用卡绑卡)
    AUTH_APPAY_NOTIFY("auth_appay_notify"),//A00001(授权申请短信)
    UNREG_ACCOUNT_NOTIFY("unreg_account_notify"),//640002(用户销户)
    UPDATE_PRE_MOBILE_NOTIFY("update_pre_mobile_notify"),//630003(修改预留手机号)
    CANCEL_AUTH_OPERA_NOTIFY("cancel_auth_opera_notify"),//A00003(授权取消)
    FOUR_ELEMENT_OPEN_ACCOUNT_IDENTIFYING("four_element_open_account_identifying"),//四要素开户验证码
    SET_NEW_PASSWORD_IDENTIFYING("set_new_password_identifying"),//设置交易密码验证码
    RESET_PASSWORD_IDENTIFYING("reset_password_identifying"),//重置密码验证码
    ORG_OPEN_ACCOUNT_IDENTIFYING("org_open_account_identifying"),//对公开户验证码
    ACCOUNT_ACTIVATION_NOTIFY("set_new_password_identifying"),//激活短信通知

    CLEAR_ERROR_NOTIFY("【北京银行】清算%s号出现异常,请立即处理");


//    OPEN_ACCOUNT_NOTIFY("【北京银行】您已在%s平台开通北京银行存管账户，请妥善保管相关信息，如非本人操作请致电4007795526。"), //600003(批量实名认证)，600004(电子帐户开户)，610012(绑卡短信确认)，620001(批量绑卡/四要素绑卡)
//    RECHARGE_SUCCESS_NOTIFY("【北京银行】您在%s平台成功充值%s元，详情可登录%s官网查看。"), //C00002(网关充值),C00011(快捷充值确认)
//    INVEST_SUCCESS_NOTIFY("【北京银行】您在%s平台成功交易%s元，目前存管账户余额%s元。"), //P00001(批量投标)  X
//    REPAY_NOTIFY("【北京银行】%s给您返款%s元，详情可登录%s官网查看。"),  //PTA00001(平台转个人)
//    INVEST_REFUND_NOTIFY("【北京银行】您在%s的投资回款%s元，目前存管账户余额%s元。"), //P00006(标的还款)  X
//    PROD_TRANS_REFUND_NOTIFY("【北京银行】您在%s的债转回款%s元，目前存管账户余额%s元。"), //P00005(标的转让)   X
//    RW_WITHDRAW_TO_PLAT_NO("【北京银行】您在%s平台成功发起提现%s元，详情可登录%s官网查看。"), //W00001(提现)
//    BIND_CARD_DEBIT_NOTIFY("【北京银行】您在%s操作储蓄卡绑定成功，如非本人操作请致电4007795526。"), //610012(绑卡短信确认)
//    BIND_CARD_CREDIT_NOTIFY("【北京银行】您在%s操作信用卡绑定成功，如非本人操作请致电4007795526。"); //610013(信用卡绑卡)
//    AUTH_APPAY_NOTIFY("【北京银行】验证码%s，您正在%s平台进行业务授权，请勿将验证码告知他人，如非本人操作请致电4007795526。")//A00001(授权申请短信)
//    UNREG_ACCOUNT_NOTIFY("【北京银行】您已在%s平台注销北京银行存管账户，请妥善保管相关信息，如非本人操作请致电4007795526。"),//640002(用户销户)
//    UPDATE_PRE_MOBILE_NOTIFY("【北京银行】【北京银行】您正在%s平台进行预留手机号变更，请妥善保管相关信息，如非本人操作请致电4007795526。"),//630003(修改预留手机号)
//    CANCEL_AUTH_OPERA_NOTIFY("【北京银行】您已在%s平台取消业务授权，请妥善保管相关信息，如非本人操作请致电4007795526。"),//A00003(授权取消)

    private String msg;

    private MsgContent(String msg){
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }
}
