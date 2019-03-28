package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/7/13.
 */
public class BatchPayDetail implements Serializable {

     private String detail_no;	//C32	明细序号	Y
     private BigDecimal occur_balance;	//N16.2	金额	Y
     private String summary;	//C256	摘要	N
     private String client_name;	//C60	客户姓名	Y
     private String card_no;	//C32	银行卡号	Y
     private String bank_id;	//C16	开户行号	N
     private String client_property;	//C1	公私标志，默认0-个人	N

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public BigDecimal getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(BigDecimal occur_balance) {
        this.occur_balance = occur_balance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getClient_property() {
        return client_property;
    }

    public void setClient_property(String client_property) {
        this.client_property = client_property;
    }
}
