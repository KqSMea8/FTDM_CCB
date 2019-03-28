package com.sunyard.sunfintech.thirdparty.model;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by my on 2018/5/18.
 */
public class WithholdRequest extends BaseRequest {
    @NotBlank
    private String batch_order_no;
    private String notify_url;

    private String pay_code;

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getBatch_order_no() {
        return batch_order_no;
    }

    public void setBatch_order_no(String batch_order_no) {
        this.batch_order_no = batch_order_no;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
