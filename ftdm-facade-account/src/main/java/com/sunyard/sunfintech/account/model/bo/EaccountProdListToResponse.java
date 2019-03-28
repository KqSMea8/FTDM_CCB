package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;

/**
 * Created by my on 2018/5/3.
 */
public class EaccountProdListToResponse implements Serializable {

    private String vol;
    private String trans_date;
    private String trans_time;
    private String platcust;
    private String trans_code;
    private String plat_no;
    private String in_interest;
    private String amt_type;

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
    }

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getIn_interest() {
        return in_interest;
    }

    public void setIn_interest(String in_interest) {
        this.in_interest = in_interest;
    }

    public String getAmt_type() {
        return amt_type;
    }

    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type;
    }
}
