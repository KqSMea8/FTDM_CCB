package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by PengZY on 2017/6/1.
 */
public class SuccessData implements Serializable {

    private String detail_no;
    private String platcust;
    private BigDecimal amt;
    private BigDecimal advance_amt;

    public BigDecimal getAdvance_amt() {
        return advance_amt;
    }

    public void setAdvance_amt(BigDecimal advance_amt) {
        this.advance_amt = advance_amt;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

}
