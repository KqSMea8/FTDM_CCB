package com.sunyard.sunfintech.account.util;

/**
 * Created by terry on 2017/5/17.
 */
public class ThirdPartyUrlLib {

    //服务器地址KEY
    public static final String EPAY_SERVER_KEY="epay_server";

    //云融惠付单笔代付
    public static final String AGENT_PAY_REAL = "agent_pay_real";

    //云融惠付批量代付（新）
    public static final String BATCH_PAY = "epay_bank_more_credits";

    //云融惠付批量代扣
    public static final String BATCH_COLLECTION = "batch_collection";

    //云融惠付批量代付查询（新）
    public static final String BATCH_PAY_QUERY_NEW="epay_bank_more_credits_query";

    //单笔订单查询
    public static final String QUERY_PAY_STATUS = "query_pay_status";

    //电子账户转账冲正
    public static final String EPAY_BANK_REVERSE="epay_bank_reverse";

    //对账完成后通知中间平台
    public static final String BILL_CHECK_READY_NOTIFY_URL = "bill_check_ready_notify_url";

    //清算文件生成后通知中间平台
    public static final String CLEAR_FILE_READY_NOTIFY_URL = "clear_file_ready_notify_url";
}
