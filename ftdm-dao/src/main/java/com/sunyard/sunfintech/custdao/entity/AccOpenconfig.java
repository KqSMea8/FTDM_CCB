package com.sunyard.sunfintech.custdao.entity;

import java.util.Date;

/**
 * Created by dany on 2017/6/15.
 */
public class AccOpenconfig {
    private  long id ;
    private  String  mall_no;
    private  String plat_no;
    private  String account_type;
    private  String account_name;
    private  String subject;
    private  String sub_subject ;
    private  String  enabled;
    private  String  remard;
    private  String  create_by;
    private  String  create_time;
    private  String  update_by;
    private  Date  datetime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSub_subject() {
        return sub_subject;
    }

    public void setSub_subject(String sub_subject) {
        this.sub_subject = sub_subject;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getRemard() {
        return remard;
    }

    public void setRemard(String remard) {
        this.remard = remard;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
