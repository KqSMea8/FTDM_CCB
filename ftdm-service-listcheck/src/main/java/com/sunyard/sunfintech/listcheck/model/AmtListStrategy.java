package com.sunyard.sunfintech.listcheck.model;

/**
 * @author heroy
 * @version 2018/6/12
 */
public class AmtListStrategy {
    String transCode;
    SubSubjectDirection amt; //本金
    SubSubjectDirection interest; //利息
    SubSubjectDirection gift; //红包
    SubSubjectDirection fee; //

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public SubSubjectDirection getAmt() {
        return amt;
    }

    public void setAmt(SubSubjectDirection amt) {
        this.amt = amt;
    }

    public SubSubjectDirection getInterest() {
        return interest;
    }

    public void setInterest(SubSubjectDirection interest) {
        this.interest = interest;
    }

    public SubSubjectDirection getGift() {
        return gift;
    }

    public void setGift(SubSubjectDirection gift) {
        this.gift = gift;
    }

    public SubSubjectDirection getFee() {
        return fee;
    }

    public void setFee(SubSubjectDirection fee) {
        this.fee = fee;
    }
}
