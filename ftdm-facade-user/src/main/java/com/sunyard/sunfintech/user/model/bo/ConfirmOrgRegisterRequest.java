package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseSerialNoRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by terry on 2018/1/10.
 */
public class ConfirmOrgRegisterRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "原始单号",example = "")
    private String origin_order_no;
    @ApiModelProperty(value = "确认状态",example = "")
    private String confirm_status;
    @ApiModelProperty(value = "平台客户号",example = "")
    private String platcust;

    private String confirm_note;

    private String random_key;

    private String password;

    private String card_no;

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public String getConfirm_status() {
        return confirm_status;
    }

    public void setConfirm_status(String confirm_status) {
        this.confirm_status = confirm_status;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getConfirm_note() {
        return confirm_note;
    }

    public void setConfirm_note(String confirm_note) {
        this.confirm_note = confirm_note;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }
}
