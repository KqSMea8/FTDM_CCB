package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/7/19.
 */
public class BatchPayQueryResponseDataDetail implements Serializable {

     private BigDecimal occur_balance;
     private String card_no;
     private String pay_status;
     private String fail_cause;

    public BigDecimal getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(BigDecimal occur_balance) {
        this.occur_balance = occur_balance;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getFail_cause() {
        return fail_cause;
    }

    public void setFail_cause(String fail_cause) {
        this.fail_cause = fail_cause;
    }

    @Override
    public String toString() {
        return "BatchPayQueryResponseDataDetail{" +
                "occur_balance=" + occur_balance +
                ", card_no='" + card_no + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", fail_cause='" + fail_cause + '\'' +
                '}';
    }
}
