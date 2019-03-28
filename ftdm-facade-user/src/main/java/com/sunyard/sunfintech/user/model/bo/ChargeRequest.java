package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 平台充值
 * Created by wubin on 2017/5/5.
 */
public class ChargeRequest extends BaseRequest{

    /**
     * 金额
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 异步通知地址
     */
    @NotBlank
    private String notify_url;

    /**
     *充值类型
     * 0-自有金充值（默认）
     * 1-保险金充值
     */
    private String recharge_type = "0";

    public String getRecharge_type() {
        return recharge_type;
    }

    public void setRecharge_type(String recharge_type) {
        this.recharge_type = recharge_type;
    }

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
