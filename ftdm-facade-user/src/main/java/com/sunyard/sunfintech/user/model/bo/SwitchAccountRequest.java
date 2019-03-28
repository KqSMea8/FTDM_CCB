package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SwitchAccountRequest extends BaseRequest {
    /**
     * 平台客户编号
     */
    @NotNull
    private String platcust;

    /**
     * 转账金额
     */
    @DecimalMin(value = "0.001")
    private BigDecimal amt;

    /**
     * 转账类型(1:从投资账户到融资账户)
     */
    @NotNull
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SwitchAccountRequest that = (SwitchAccountRequest) o;

        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (amt != null ? !amt.equals(that.amt) : that.amt != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = platcust != null ? platcust.hashCode() : 0;
        result = 31 * result + (amt != null ? amt.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SwitchAccountRequest{" +
                "platcust='" + platcust + '\'' +
                ", amt=" + amt +
                ", type='" + type + '\'' +
                '}';
    }
}
