package com.sunyard.sunfintech.core.util;

/**
 * Created by terry on 2018/1/6.
 */
public class URLConfigUtil {

    /**
     * mall
     * epay_server
     * E支付服务器地址
     */
    public static final String EPAY_SERVER_KEY = "epay_server";

    public static final String PARAMENTER_KYE = "skip_check_pwd";

    public static final String EPAY_AGR_BIND ="epay_agr_bind";

    public static final String EPAY_AGR_BIND_STATUS ="epay_agr_bind_status";

    //用户实名认证（账户三要素四要素认证）接口KEY，E支付老接口
    public static final String BAND_ACCOUNT_VERIFY_KEY_OLD = "epay_bank_account_verify_old";

    //用户实名认证（账户三要素四要素认证）接口KEY
    public static final String BAND_ACCOUNT_VERIFY_KEY = "epay_bank_account_verify";


    /**
     * 协议支付确认支付收单
     */
    public static final String AGENT_EMENT_QUICK_PAY_URL = "agent_ement_quick_pay_url";

    //修改预留手机号
    public static final String PHONE_CHANGE_VERIFY_KEY = "phone_change";

    //解除绑卡接口KEY
    public static final String UNBIND_REAL_KEY = "unbind_real";

    //用户实名认证（账户三要素四要素认证）接口KEY
    public static final String BAND_ACCOUNT_VERIFY3_KEY = "epay_bank_account_verify3";

    //icis销户icis/customerCancel
    public static final String ICIS_CUSTOMERCANCEL = "icis_customerCancel";

    //icis客户冻结icis_customerFreeze
    public static final String ICIS_CUSTOMERFREEZE = "icis_customerFreeze";

    //icis客户解冻icis_customerUnfreeze
    public static final String ICIS_CUSTOMERUNFREEZE = "icis_customerUnfreeze";

    //icis客户信息变更
    public static final String ICIS_CUSTOMERCHANGE = "icis_customerChange";

    //icis客户解绑
    public static final String ICIS_CUSTOMERUNBIND = "icis_customerUnbind";

    //icis密码修改重置
    public static final String ICIS_PASSWORDMODIFY = "icis_passwordModify";

    //icis密码验证
    public static final String ICIS_PASSWORDCHECK = "icis_passwordCheck";

    /**
     * mall_no
     * cashier_balance_query
     * 真实电子账户余额查询地址
     */
    public static final String CASHIER_BALANCE_QUERY = "cashier_balance_query";

    /**
     * mall_no
     * agent_pay_real
     * 云融惠付单笔代付地址（行内划转，单笔代付）
     */
    public static final String AGENT_PAY_REAL = "agent_pay_real";

    /**
     * plat_no
     * plat_ftp_name
     * 平台ftp地址
     */
    public static final String PLAT_FTP_NAME = "plat_ftp_name";

    /**
     * mall_no
     * epay_bank_more_credits
     * 云融惠付批量代付（新） 地址
     */
    public static final String BATCH_PAY = "epay_bank_more_credits";

    /**
     * mall_no
     * batch_collection
     * 云融惠付批量代扣  地址
     */
    public static final String BATCH_COLLECTION = "batch_collection";

    /**
     * mall_no
     * epay_bank_more_credits_query
     * 云融惠付批量代付查询（新）  地址
     */
    public static final String BATCH_PAY_QUERY_NEW = "epay_bank_more_credits_query";

    /**
     * mall_no
     * query_pay_status
     * 单笔订单查询  地址
     */
    public static final String QUERY_PAY_STATUS = "query_pay_status";

    /**
     * mall_no
     * epay_bank_reverse
     * 电子账户转账冲正  地址
     */
    public static final String EPAY_BANK_REVERSE = "epay_bank_reverse";

    /**
     * mall_no
     * plat_sorting_url
     *   地址
     */
    public static final String PLAT_SORTING_URL = "plat_sorting_url";

    /**
     * mall_no
     * clear_notify
     *  是否需要清算通知
     */
    public static final String CLEAR_NOTIFY = "clear_notify";

    /**
     * mall_no
     * bill_check_ready_notify_url
     *  对账完成后通知中间平台
     */
    public static final String BILL_CHECK_READY_NOTIFY_URL = "bill_check_ready_notify_url";

    /**
     * mall_no
     * clear_file_ready_notify_url
     *  清算完成后通知中间平台
     */
    public static final String CLEAR_FILE_READY_NOTIFY_URL = "clear_file_ready_notify_url";

    /**
     * mall_no
     * plat_server
     *  平台地址
     */
    public static final String PLAT_SERVER = "plat_server";

    /**
     * mall_no
     * quick_pay_url_notify
     *  平台充值异步回调URL
     */
    public static final String QUICK_PAY_URL_NOTIFY = "quick_pay_url_notify";

    /**
     * FTDM
     * yunpay_check_file_url
     *  对账文件下载地址
     */
    public static final String YUNPAY_CHECK_FILE_URL = "yunpay_check_file_url";

    /**
     * FTDM
     * web_local_server
     *  存管服务URL
     */
    public static final String WEB_LOCAL_SERVER = "web_local_server";

    /**
     * FTDM
     * web_local_server
     *  存管服务URL
     */
    public static final String OUT_WEB_LOCAL_SERVER = "out_web_local_server";

    /**
     * FTDM
     * payplat_single_pay_return
     *  行内代付回调地址
     */
    public static final String PAYPLAT_SINGLE_PAY_RETURN = "payplat_single_pay_return";

    /**
     * mall_no
     * payplat_single_pay
     *  行内单笔代付
     */
    public static final String PAYPLAT_SINGLE_PAY  = "payplat_single_pay";

    /**
     * mall_no
     * payplat_single_pay
     *  行内单笔代付(连连)
     */
    public static final String PAYPLAT_SINGLE_PAY_LL  = "payplat_single_pay_ll";

    /**
     * mall_no
     * epay_query_accdetail
     *  账户往来明细查询接口
     */
    public static final String EPAY_QUERY_ACCDETAIL  = "epay_query_accdetail";

    /**
     * mall_no
     * bank_pay_url
     *  行内支付接口
     */
    public static final String BANK_PAY_URL = "bank_pay_url";

    /**
     * mall_no
     * payplat_refund
     *  提现退款
     */
    public static final String PAYPLAT_REFUND = "payplat_refund";

    /**
     * mall_no
     * quick_pay_url
     *  快捷支付申请
     */
    public static final String QUICK_PAY_URL = "quick_pay_url";

    /**
     * mall_no
     * agent_quick_pay_url
     *  行内收单快捷支付申请
     */
    public static final String BANK_QUICK_PAY_URL ="bank_quick_pay_url";

    /**
     * mall_no
     * agent_quick_pay_url
     *  支付收单快捷支付申请
     */
    public static final String AGENT_QUICK_PAY_URL ="agent_quick_pay_url";
    /**
     * 协议支付申请支付收单
     */
    public static final String AGENT_EMENT_PAY_URL ="agent_ement_pay_url";

    /**
     * mall_no
     * epay_bank_fourInOneQuery
     *  四合一账务往来交易明细查询
     */
    public static final String EPAY_BANK_FOUR_IN_ONE_QUERY = "epay_bank_fourInOneQuery";

    /**
     * mall_no
     * payplat_single_pay_query
     *  代付单笔订单查询 /eis/bobpay/bobsp/v1/payplat_single_pay_query
     */
    public static final String  PAYPLAT_SINGLE_PAY_QUERY = "payplat_single_pay_query";

    /**
     * mall_no
     * epay_query_balance
     *  平台对公账户查询 /eis/bobpay/bobsp/v1/payplat_single_pay_query
     */
    public static final String EPAY_QUERY_BALANCE = "epay_query_balance";

    /**
     * mall_no
     * notity_payment
     *  充值回调地址（快捷，网关通用）数据库系统参数表中需要配置
     */
    public static final String RECHARGE_RESULT_NOTIFY = "notify_payment";

    /**
     * mall_no
     * agent_collection_verify
     *  快捷支付短验申请（快捷，网关通用）数据库系统参数表中需要配置
     */
    public static final String AGENT_COLLECTION_VERIFY = "agent_collection_verify";

    /**
     * mall_no
     * collection_url
     *  代扣充值key
     */
    public static final String COLLECTION_URL = "collection_url";

    /**
     * mall_no
     * confirm_quick_pay_url
     *  快捷支付确认 key
     */
    public static final String CONFIRM_QUICK_PAY_URL = "confirm_quick_pay_url";

    /**
     * mall_no
<<<<<<< HEAD
=======
     * bank_confirm_quick_pay_url
     *  行内收单快捷支付确认 key
     */
    public static final String BANK_CONFIRM_QUICK_PAY_URL = "bank_confirm_quick_pay_url";

    /**
     * mall_no
     * agent_confirm_quick_pay_url
     *  支付收单快捷支付确认 key
     */
    public static final String AGENT_CONFIRM_QUICK_PAY_URL = "agent_confirm_quick_pay_url";


    /**
     * mall_no
     * gateway_pay
     *  网关充值请求 key
     */
    public static final String GATEWAY_PAY_URL = "gateway_pay";

    /**
     * mall_no
     * repay_offline_notify_url
     *  线下还款回调URL
     */
    public static final String REPAY_OFFLINE_NOTIFY_URL = "repay_offline_notify_url";

    /**
     * mall_no
     * repay_offline_notify_url
     *  平台提现异步回调URL
     */
    public static final String PLAT_WITHDRAW_NOTIFY_URL = "plat_withdraw_notify_url";

    /**
     * mall_no
     * icis_randomGet
     *
     */
    public static final String ICIS_RANDOMGET = "icis_randomGet";

    /**
     * FTDM
     * icis_randomGet
     * 代扣充值回调URL
     */
    public static final String COLLECTION_URL_NOTIFY = "collection_url_notify";

    /**
     * mall_no
     * open_account
     * 电子账户开户接口KEY
     */
    public static final String OPEN_ACOUNT_KEY = "open_account";

    /**
     * mall_no
     * open_account
     * 电子账户开户老接口KEY
     */
    public static final String OPEN_ACOUNT_OLD_KEY = "open_account_old";

    //无短验绑卡KEY
    public static final String BIND_REAL_NOMSG = "bind_real_nomsg";

    //自动加值
    public static final String AUTO_ADD_VALUE ="auto_add_value";

    //提现代发url
    public static final String SEND_VALUE ="send_value";

    /**
     * CCB-提现代发异步通知地址key
     */
    public static final String NOTIFY_URL_WITHDRAW_SEND_KEY ="withdraw_send_url_key";
    //自动加值查询
    public static final String AUTO_ADD_VALUE_QUERY ="auto_add_value_query";

    //平台银行卡信息
    public static final String PLAT_CARD_INFO = "plat_card_info";

    //协议支付申请
    public static final String AGR_PAY_APPLY ="agr_pay_apply";

    //协议支付确认
    public static final String AGR_PAY_CONFIRM ="agr_pay_confirm";

}
