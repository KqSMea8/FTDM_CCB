package com.sunyard.sunfintech.account.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 随机因子
 */
public class RamdomKeyRequest extends BaseRequest{
    private String password_type;

    public String getPassword_type() {
        return password_type;
    }

    public void setPassword_type(String password_type) {
        this.password_type = password_type;
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
