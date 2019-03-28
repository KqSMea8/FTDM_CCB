package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;

/**
 * Created by terry on 2017/6/4.
 */
public class YunPayBaseErrorResponse implements Serializable {

    //签名
    private String cert_sign;

    //错误编号
    private String error_code;

    //系统辅助信息
    private String error_extinfo;

    //错误编号
    private String error_no;

    //错误信息
    private String error_info;

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_extinfo() {
        return error_extinfo;
    }

    public void setError_extinfo(String error_extinfo) {
        this.error_extinfo = error_extinfo;
    }

    public String getError_no() {
        return error_no;
    }

    public void setError_no(String error_no) {
        this.error_no = error_no;
    }

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

//    @Override
    public String toString(String className) {
        return className+"{" +
                "cert_sign='" + cert_sign + '\'' +
                ", error_code='" + error_code + '\'' +
                ", error_extinfo='" + error_extinfo + '\'' +
                ", error_no='" + error_no + '\'' +
                ", error_info='" + error_info + '\'' +
                '}';
    }
}
