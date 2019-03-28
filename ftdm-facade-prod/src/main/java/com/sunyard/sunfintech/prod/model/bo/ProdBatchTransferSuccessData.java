package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.prod.model.bo.Amtlist;

import java.io.Serializable;

/**
 * Created by terry on 2017/5/3.
 */
public class ProdBatchTransferSuccessData implements Serializable {
    private String detail_no;
    private String mobile;
    private String platcust;
    private Amtlist amtlist;

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public Amtlist getAmtlist() {
        return amtlist;
    }

    public void setAmtlist(Amtlist amtlist) {
        this.amtlist = amtlist;
    }
}
