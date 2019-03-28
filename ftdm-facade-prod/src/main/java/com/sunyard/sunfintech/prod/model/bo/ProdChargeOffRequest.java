package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.dao.entity.ProdChargeoff;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdChargeOffRequest extends BaseRequest {

    @NotBlank
    private String prod_id;

    @NotBlank
    private String charge_off_list;
    private List<ProdChargeoff> charge_off_listArray;

    @NotBlank
    private String notify_url;

    @NotBlank
    private String pay_code;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getCharge_off_list() {
        return charge_off_list;
    }

    public void setCharge_off_list(String charge_off_list) {
        this.charge_off_list = charge_off_list;
        setCharge_off_listArray(JSON.parseArray(charge_off_list,ProdChargeoff.class));
    }

    public List<ProdChargeoff> getCharge_off_listArray() {
        return charge_off_listArray;
    }

    public void setCharge_off_listArray(List<ProdChargeoff> charge_off_listArray) {
        this.charge_off_listArray = charge_off_listArray;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }
}
