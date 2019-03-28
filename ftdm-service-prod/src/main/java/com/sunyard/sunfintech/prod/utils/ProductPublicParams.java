package com.sunyard.sunfintech.prod.utils;

/**
 * Created by terry on 2017/7/21.
 */
public class ProductPublicParams {

    public static long maxRepayNum=3000;//最大还款数量3000
    public static long maxRepayThreadsNum=3;
    public static final String fundPayWaitQueryListKey="fundPayWaitQueryList";
    public static final String waitQueryOrder="waitQueryOrder";
    public static final String waitRefundList="waitRefundList";
    public static final String batchPayUnknownList ="batchPayUnknownList";

    public static void setMaxRepayNum(long maxRepayNum) {
        ProductPublicParams.maxRepayNum = maxRepayNum;
    }

    public static void setMaxRepayThreadsNum(long maxRepayThreadsNum) {
        ProductPublicParams.maxRepayThreadsNum = maxRepayThreadsNum;
    }
}
