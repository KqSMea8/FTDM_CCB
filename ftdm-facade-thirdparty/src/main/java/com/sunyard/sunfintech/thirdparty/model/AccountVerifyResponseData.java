package com.sunyard.sunfintech.thirdparty.model;

import java.io.Serializable;

/**
 * Created by terry on 2017/6/4.
 */
public class AccountVerifyResponseData implements Serializable {
    //认证状态
    private String auth_status;

    //签名
    private String cert_sign;

    //认证说明
    private String respMsg;

    //银行编号
    private String bank_id;

    public String getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(String auth_status) {
        this.auth_status = auth_status;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    @Override
    public String toString() {
        return "AccountVerifyResponseData{" +
                "auth_status='" + auth_status + '\'' +
                ", cert_sign='" + cert_sign + '\'' +
                ", respMsg='" + respMsg + '\'' +
                ", bank_id='" + bank_id + '\'' +
                '}';
    }
}
