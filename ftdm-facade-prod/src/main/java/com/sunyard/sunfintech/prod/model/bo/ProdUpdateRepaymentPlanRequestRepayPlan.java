package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.util.DateUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdUpdateRepaymentPlanRequestRepayPlan implements Serializable{

    /**
     * 还款金额
     */
    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal repay_amt;

    /**
     * 手续费
     */
    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal repay_fee;

    /**
     * 还款期数
     */
    @NotNull
    private int repay_num;

    /**
     * 还款日期
     */
    @NotNull
    private String repay_date;

    public BigDecimal getRepay_amt() {
        return repay_amt;
    }

    public void setRepay_amt(BigDecimal repay_amt) {
        this.repay_amt = repay_amt;
    }

    public BigDecimal getRepay_fee() {
        return repay_fee;
    }

    public void setRepay_fee(BigDecimal repay_fee) {
        this.repay_fee = repay_fee;
    }

    public int getRepay_num() {
        return repay_num;
    }

    public void setRepay_num(int repay_num) {
        this.repay_num = repay_num;
    }

    public String getRepay_date() {
        return repay_date;
    }

    public void setRepay_date(String repay_date) {
        this.repay_date = repay_date;
    }
}
