package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

public class ModifyPwdRequest extends BaseRequest {
    //平台客户号
    @NotBlank
    private String platcust;

    //用户姓名
    @NotBlank
    private String name;

    //证件号码
    @NotBlank
    private String id_code;

    //同步回调地址
    @NotBlank
    private String return_url;

    //异步通知地址，企业客户必填
    private String notify_url;

    //电话号码，用于返回给前端的，不需要平台传
    private String mobile;

    //流水号，用于返回给前端，不需要平台传
    private String transSerial;

    //客户类型 1-个人 2-企业
    private String type;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransSerial() {
        return transSerial;
    }

    public void setTransSerial(String transSerial) {
        this.transSerial = transSerial;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
