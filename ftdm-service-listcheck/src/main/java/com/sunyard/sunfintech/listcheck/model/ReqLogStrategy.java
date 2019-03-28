package com.sunyard.sunfintech.listcheck.model;

import com.alibaba.fastjson.JSON;

/**
 * 配置
 * @author heroy
 * @version 2018/6/12
 */
public class ReqLogStrategy {
    private String transCode;
    private String amt; //本金
    private String interest; //利息
    private String gift; //红包
    private String fee; //手续费
    private String log_type;//log类型
    private String origin_order_no;//原订单号

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getLog_type() {
        return log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public static void main(String[] args) {
        ReqLogStrategy reqLogStrategy = new ReqLogStrategy();
        reqLogStrategy.setAmt("trans_amt");
        reqLogStrategy.setFee("fee");
        reqLogStrategy.setGift("coupon_amt");
        reqLogStrategy.setInterest("real_repay_val");
        reqLogStrategy.setTransCode("P00001");

        System.out.println(JSON.toJSONString(reqLogStrategy));
    }
}
