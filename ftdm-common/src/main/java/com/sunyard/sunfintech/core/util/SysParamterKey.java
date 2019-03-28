package com.sunyard.sunfintech.core.util;

/**
 * Created by PengZY on 2018/2/8.
 */
public class SysParamterKey {

    public static final String FTDM = "FTDM";

    /**
     * 验密地址
     */
    public static final String SKIP_CHECK_PWD = "skip_check_pwd";
    /*
     *  mall_no
     *  overdue_time
     *  提现申请的过期时间
     */
    public static final String OVERDUE_TIME = "overdue_time";

    /*
     *  mall_no
     *  com_prod_limit
     *  对公标的金额超限
     */
    public static final String COM_PROD_LIMIT = "com_prod_limit";

    /*
     *  mall_no
     *  pri_prod_limit
     *  对公标的金额超限
     */
    public static final String PRI_PROD_LIMIT = "pri_prod_limit";

    /*
     *  mall_no
     *  mall_name
     *  短信获取名称
     */
    public static final String MALL_NAME = "mall_name";

    /*
     *  mall_no
     *  SYSTEM_ADMIN_MOBILE
     *  获取管理员参数
     */
    public static final String SYSTEM_ADMIN_MOBILE = "SYSTEM_ADMIN_MOBILE";


    /*
     *  FTDM
     *  recharge_trans_code
     *  充值的trans_code
     */
    public static final String RECHARGE_TRANS_CODE = "recharge_trans_code";

    /*
     *  FTDM
     *  recharge_trans_code
     *  充值开始时间
     */
    public static final String RECHARGE_START_TIME = "recharge_start_time";

    /*
     *  FTDM
     *  recharge_trans_code
     *  充值结束时间
     */
    public static final String RECHARGE_END_TIME = "recharge_end_time";

    /*
     *  FTDM
     *  msg_channel_code
     *  短信通道编码
     */
    public static final String MSG_CHANNEL_CODE = "msg_channel_code";

    /*
     *  mall_no
     *  bind_card_switch
     *  投资是否绑卡
     */
    public static final String BIND_CARD_SWITCH = "bind_card_switch";

    /*
     *  mall_no
     *  card_bind_limit
     *  绑卡数量
     */
    public static final String CARD_BIND_LIMIT = "card_bind_limit";

    /*
     *  mall_no
     *  credit_card_bind_limit
     *  绑卡数量
     */
    public static final String CREDIT_CARD_BIND_LIMIT = "credit_card_bind_limit";

    /*
     *  mall_no
     *  type_chan
     *
     */
    public static final String TYPE_CHAN = "type_chan";

    /*
     *  mall_no
     *  identifying_code_time_out
     *  授权超时
     */
    public static final String IDENTIFYING_CODE_TIME_OUT = "identifying_code_time_out";



    /*
     *  mall_no
     *  identifying_code_error_num
     *  错误次数
     */
    public static final String IDENTIFYING_CODE_ERROR_NUM = "identifying_code_error_num";

    /*
     *  mall_no
     *  fee_account
     *  手续费
     */
    public static final String FEE_ACCOUNT = "fee_account";

    /*
     *  mall_no
     *  auth_appay_notify
     *  授权申请短信
     */
    public static final String AUTH_APPAY_NOTIFY = "auth_appay_notify";


    /*
     *  mall_no
     *  is_request_thirdparty
     *  是否走绑卡接口
     */
    public static final String IS_REQUEST_THIRDPARTY = "is_request_thirdparty";


    /*
     *  FTDM
     *  is_sign
     *  是否验签
     */
    public static final String IS_SIGN = "is_sign";

    /*
     *  FTDM
     *  white_list
     *  白名单
     */
    public static final String WHITE_LIST = "white_list";
    /*
     *  FTDM
     *  payfee_trans_code
     *
     */
    public static final String PAYFEE_TRANS_CODE = "Payfee_trans_code";

    /**
     * 支付公司备付金账户
     */
    public static final String PAY_ACCOUNT="pay_account_";

    /**
     * 平台在支付公司的商户号
     */
    public static final String PLAT_PAY_ACCOUNT="plat_pay_account_";

    /**
     * 自动加值异步通知地址
     */
    public static final String AUTO_ADD_VALUE_NOTIFY_URL="auto_add_value_notify_url";

    /**
     * 缴费单笔限额
     */
    public static final String PAY_FEE_LIMITED="pay_fee_limited";

    /**
     * 缴费总共限额
     */
    public static final String PAY_FEE_SUM_LIMITED="pay_fee_sum_limited";

    /**
     * 缴费到平台单笔限额
     */
    public static final String PAY_FEE_TO_PLAT_SINGLE_LIMITED="pay_fee_to_plat_single_limited";

    /**
     * 缴费到平台总限额
     */
    public static final String PAY_FEE_TO_PLAT_All_LIMITED="pay_fee_to_plat_all_limited";

    /**
     * 平台提现单笔限额
     */
    public static final String PLAT_WITHDRAW_SINGLE_LIMITED="plat_withdraw_single_limited";

    /**
     * 平台提现总限额
     */
    public static final String PLAT_WITHDRAW_All_LIMITED="plat_withdraw_all_limited";

    /**
     * 清算支付付款帐号
     */
    public static final String PLAT_CLEAR_CARD_NOS = "plat_clear_card_nos";



    /**
     * 一借多贷最大条数
     */
    public static final String PROD_REFUND_MAX_NUM="prod_refund_max_num";

    /**
     * 一借多贷合成数据最大线程数
     */
    public static final String PROD_REFUND_MAX_THREADS_NUM="prod_refund_max_threads_num";

    /**
     * 一借多贷合成数据当前线程数
     */
    public static final String PROD_REFUND_NOW_THREADS_NUM="prod_refund_now_threads_num";

    /**
     * 一借多贷待发列表
     */
    public static final String PROD_REFUND_WAIT_PAY="prod_refund_wait_pay";
}
