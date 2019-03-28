package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.io.Serializable;

/**
 * Created by PengZY on 2017/10/18.
 */
public class PlatfromToPersonData implements Serializable {

    private EaccAccountamtlist eaccAccountamtlist;
    private String mall_no;
    private String order_no;
    private String notify_url;
    private String cause_type;

    public EaccAccountamtlist getEaccAccountamtlist() {
        return eaccAccountamtlist;
    }

    public void setEaccAccountamtlist(EaccAccountamtlist eaccAccountamtlist) {
        this.eaccAccountamtlist = eaccAccountamtlist;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCause_type() {
        return cause_type;
    }

    public void setCause_type(String cause_type) {
        this.cause_type = cause_type;
    }
}
