package com.sunyard.sunfintech.user.modelold.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.user.model.bo.CompensateRepayRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by PengZY on 2018/3/5.
 */
public class CompensateRepayRequestOld extends BaseRequest {

    /**
     * 产品编号
     */
    @NotBlank
    private String prod_id;

    /**
     * 还款金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal repay_amt;

    @DecimalMin(value="0.000")
    private BigDecimal fee_amt;

    /**
     * 平台客户编号（借款人）
     */
    @NotBlank
    private String platcust;

    /**
     * 代偿人平台客户编号
     */
    private String compensation_platcust;

    /**
     * 0-代偿人代偿(默认)
     1-风险金代偿
     */
    private String compensation_type;

    public String getCompensation_type() {
        return compensation_type;
    }

    public void setCompensation_type(String compensation_type) {
        this.compensation_type = compensation_type;
    }

    public BigDecimal getFee_amt() {
        return fee_amt;
    }

    public void setFee_amt(BigDecimal fee_amt) {
        this.fee_amt = fee_amt;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public BigDecimal getRepay_amt() {
        return repay_amt;
    }

    public void setRepay_amt(BigDecimal repay_amt) {
        this.repay_amt = repay_amt;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getCompensation_platcust() {
        return compensation_platcust;
    }

    public void setCompensation_platcust(String compensation_platcust) {
        this.compensation_platcust = compensation_platcust;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompensateRepayRequestOld that = (CompensateRepayRequestOld) o;

        if (prod_id != null ? !prod_id.equals(that.prod_id) : that.prod_id != null) return false;
        if (repay_amt != null ? !repay_amt.equals(that.repay_amt) : that.repay_amt != null) return false;
        if (fee_amt != null ? !fee_amt.equals(that.fee_amt) : that.fee_amt != null) return false;
        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (compensation_platcust != null ? !compensation_platcust.equals(that.compensation_platcust) : that.compensation_platcust != null)
            return false;
        return compensation_type != null ? compensation_type.equals(that.compensation_type) : that.compensation_type == null;
    }

    @Override
    public int hashCode() {
        int result = prod_id != null ? prod_id.hashCode() : 0;
        result = 31 * result + (repay_amt != null ? repay_amt.hashCode() : 0);
        result = 31 * result + (fee_amt != null ? fee_amt.hashCode() : 0);
        result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
        result = 31 * result + (compensation_platcust != null ? compensation_platcust.hashCode() : 0);
        result = 31 * result + (compensation_type != null ? compensation_type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompensateRepayRequestOld{" +
                "prod_id='" + prod_id + '\'' +
                ", repay_amt=" + repay_amt +
                ", fee_amt=" + fee_amt +
                ", platcust='" + platcust + '\'' +
                ", compensation_platcust='" + compensation_platcust + '\'' +
                ", compensation_type='" + compensation_type + '\'' +
                '}';
    }

}
