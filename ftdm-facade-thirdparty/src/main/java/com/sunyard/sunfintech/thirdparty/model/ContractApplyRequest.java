package com.sunyard.sunfintech.thirdparty.model;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by my on 2018/5/18.
 */
public class ContractApplyRequest extends BaseRequest {
    @NotBlank
    private String pay_code;

    @NotBlank
    private String name;

    @NotBlank
    private String id_type;

    @NotBlank
    private String id_code;

    @NotBlank
    private String mobile;

    @NotBlank
    private String card_no;

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }
}
