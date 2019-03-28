package com.sunyard.sunfintech.thirdparty.model;

import java.io.Serializable;

/**
 * Created by my on 2018/5/18.
 */
public class ContractApplyResponse implements Serializable {
    private String pay_code;
    private String status;

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
