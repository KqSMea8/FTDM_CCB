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
 * 平台提现
 * Created by wubin on 2017/5/5.
 */
public class WithdrawRequest extends BaseRequest {

    /**
     * 金额
     */
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    /**
     * 异步通知地址
     */
    private String notify_url;

    /**
     *提现类型
     * 0-平台自有金提现（默认）
     */
    private String withdraw_type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(String withdraw_type) {
        this.withdraw_type = withdraw_type;
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
