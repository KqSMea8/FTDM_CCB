package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;

/**
 * Created by terry on 2017/5/19.
 */
public class QueryPlatAccountParams implements Serializable {
    private String plat_no;
    private String prod_id;
    private String subject;
    private String sub_subject;

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
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
}
