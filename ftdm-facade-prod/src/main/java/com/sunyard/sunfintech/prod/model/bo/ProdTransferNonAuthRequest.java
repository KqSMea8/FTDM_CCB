package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


public class ProdTransferNonAuthRequest extends BaseRequest {

    /**
     * 受让人平台客户号
     */
    private String platcust;

    private String prod_id;

    private BigDecimal trans_amt;

    private String subject_priority;

    private String trans_pwd;

    private String random_key;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public String getSubject_priority() {
        return subject_priority;
    }

    public void setSubject_priority(String subject_priority) {
        this.subject_priority = subject_priority;
    }

    public String getTrans_pwd() {
        return trans_pwd;
    }

    public void setTrans_pwd(String trans_pwd) {
        this.trans_pwd = trans_pwd;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }
}
