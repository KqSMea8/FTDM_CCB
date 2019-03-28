package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 成标时  还款计划
 * Created by wubin on 2017/5/10.
 */
public class RepayPlanListObject extends BaseModel{
    /**
     * 还款金额
     */
    private String repay_amt;
    /**
     * 手续费
     */
    private String repay_fee;
    /**
     * 还款期数
     */
    private String repay_num;
    /**
     * 还款日期
     */
    private String repay_date;

    public String getRepay_amt() {
        return repay_amt;
    }

    public void setRepay_amt(String repay_amt) {
        this.repay_amt = repay_amt;
    }

    public String getRepay_fee() {
        return repay_fee;
    }

    public void setRepay_fee(String repay_fee) {
        this.repay_fee = repay_fee;
    }

    public String getRepay_num() {
        return repay_num;
    }

    public void setRepay_num(String repay_num) {
        this.repay_num = repay_num;
    }

    public String getRepay_date() {
        return repay_date;
    }

    public void setRepay_date(String repay_date) {
        this.repay_date = repay_date;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
