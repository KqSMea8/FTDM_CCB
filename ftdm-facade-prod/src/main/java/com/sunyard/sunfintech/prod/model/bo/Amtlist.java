package com.sunyard.sunfintech.prod.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金处理记录
 * 
 * Created by terry on 2017/5/3.
 */
public class Amtlist implements Serializable {
    /**
     * 平台客户号
     */
    private String platcust;
    /**
     *资金类型 1-现金 2-在途
     */
    private String amttype;
    /**
     * 金额
     */
    private BigDecimal amt;
    /**
     * 备注
     */
    private String remark;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getAmttype() {
        return amttype;
    }

    public void setAmttype(String amttype) {
        this.amttype = amttype;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
