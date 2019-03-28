package com.sunyard.sunfintech.listcheck.model;

import java.math.BigDecimal;

/**
 * 资金流水核对具体内容
 * @author heroy
 * @version 2018/6/12
 */
public class TransAmtCheckContent implements Comparable{
    /**
     * 本金
     */
    private BigDecimal amt;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 红包
     */
    private BigDecimal gift;

    /**
     * 手续费
     */
    private BigDecimal fee;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getGift() {
        return gift;
    }

    public void setGift(BigDecimal gift) {
        this.gift = gift;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public TransAmtCheckContent() {
        this.amt = BigDecimal.ZERO;
        this.interest = BigDecimal.ZERO;
        this.gift = BigDecimal.ZERO;
        this.fee = BigDecimal.ZERO;
    }

    /**
     * 比较器
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if(o instanceof TransAmtCheckContent){
            TransAmtCheckContent transAmt = (TransAmtCheckContent)o;
            //本金不相同
            if(0!=this.amt.compareTo(transAmt.getAmt())){
                return ListCheckResult.AMTERROR.getCode();
            }
            //利息不相同
            if(0!=this.interest.compareTo(transAmt.getInterest())){
                return ListCheckResult.INTERESTERROR.getCode();
            }
            //红包不相同
            if(0!=this.gift.compareTo(transAmt.getGift())){
                return ListCheckResult.GIFTERROR.getCode();
            }
            //手续费不相同
            if(0!=this.fee.compareTo(transAmt.getFee())){
                return ListCheckResult.FEEERROR.getCode();
            }
            return ListCheckResult.EQUAL.getCode();
        }
        return 0;

    }
}
