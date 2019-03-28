package com.sunyard.sunfintech.web.model.vo.platform;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 平台非存管账户出金
 * Created by wubin on 2017/5/5.
 */
public class WithdrawNoDepositoryRequest extends BaseRequest {

    /**
     * 账户类型(21-垫付账户)
     */
    @NotBlank
    private String account_type;

    /**
     * 金额
     */
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String paymessage;

    /**
     * 代发指令
     */
    private PaymessageObject paymessageObject;

    /**
     * 异步通知地址
     */
    @NotBlank
    private String notify_url;

    public String getPaymessage() {
        return paymessage;
    }

    public void setPaymessage(String paymessage) {
        this.paymessage = paymessage;
        if(StringUtils.isNoneBlank(paymessage)){
            this.paymessageObject = JSON.parseObject(paymessage,PaymessageObject.class);
        }
    }

    public PaymessageObject getPaymessageObject() {
        return paymessageObject;
    }

    public void setPaymessageObject(PaymessageObject paymessageObject) {
        this.paymessageObject = paymessageObject;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
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
