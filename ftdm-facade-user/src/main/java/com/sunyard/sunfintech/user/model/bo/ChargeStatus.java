package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * 充值订单状态查询参数
 * Created by dingjy on 2017/5/24.
 */
public class ChargeStatus extends BaseRequest {
    //原充值订单号
    @NotBlank
    private String original_serial_no;

    //发生金额
    @NotBlank
    private BigDecimal occur_balance;

    public String getOriginal_serial_no() {
        return original_serial_no;
    }

    public void setOriginal_serial_no(String original_serial_no) {
        this.original_serial_no = original_serial_no;
    }

    public BigDecimal getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(BigDecimal occur_balance) {
        this.occur_balance = occur_balance;
    }
}
