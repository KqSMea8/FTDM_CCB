package com.sunyard.sunfintech.account.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/9/8.
 */
public class QueryPayStatus implements Serializable {
    private String mall_no;
    private String trans_serial;
    private BigDecimal trans_amt;

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public String getTrans_serial() {
        return trans_serial;
    }

    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    @Override
    public String toString() {
        return "QueryPayStatus{" +
                "mall_no='" + mall_no + '\'' +
                ", trans_serial='" + trans_serial + '\'' +
                ", trans_amt=" + trans_amt +
                '}';
    }
}
