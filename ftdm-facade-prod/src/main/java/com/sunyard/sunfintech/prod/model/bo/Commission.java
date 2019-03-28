package com.sunyard.sunfintech.prod.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by terry on 2017/4/28.
 */
public class Commission implements Serializable {

    /**
     * 分配平台类型（01-自由 11-垫付）
     */
    @NotBlank
    private String payout_plat_type;

    /**
     * 手续费固定金额
     */
    @NotNull
    private BigDecimal payout_amt;

    public String getPayout_plat_type() {
        return payout_plat_type;
    }

    public void setPayout_plat_type(String payout_plat_type) {
        this.payout_plat_type = payout_plat_type;
    }

    public BigDecimal getPayout_amt() {
        return payout_amt;
    }

    public void setPayout_amt(BigDecimal payout_amt) {
        this.payout_amt = payout_amt;
    }
}
