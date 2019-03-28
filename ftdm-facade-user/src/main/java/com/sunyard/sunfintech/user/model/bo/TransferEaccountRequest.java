package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/6/5.
 */
public class TransferEaccountRequest extends BaseRequest {

    /**
     * 集团客户号
     */
    @NotBlank
    private String platcust;

    /**
     * 转账金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal amt;

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

    @Override
    public String toString() {
        return "TransferEaccountRequest{" +
                "platcust='" + platcust + '\'' +
                ", amt=" + amt +
                '}';
    }
}
