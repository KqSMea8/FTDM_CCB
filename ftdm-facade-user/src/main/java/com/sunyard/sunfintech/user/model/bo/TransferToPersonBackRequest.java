package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 平台转帐个人撤销
 * Created by wuml on 2017/10/12.
 */
public class TransferToPersonBackRequest extends BaseRequest {
    @NotBlank
    private String ori_order_no;

    @NotNull
    @DecimalMin(value="0")
    private BigDecimal amount;

    public String getOri_order_no() {
        return ori_order_no;
    }

    public void setOri_order_no(String ori_order_no) {
        super.setBase_serial_ori_order_no(ori_order_no);
        this.ori_order_no = ori_order_no;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
