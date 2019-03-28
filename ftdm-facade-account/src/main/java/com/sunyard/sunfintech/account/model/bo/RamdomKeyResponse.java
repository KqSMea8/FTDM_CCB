package com.sunyard.sunfintech.account.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 随机因子
 */
public class RamdomKeyResponse extends BaseResponse{
    private String random_key;
    private String random_value;

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    public String getRandom_value() {
        return random_value;
    }

    public void setRandom_value(String random_value) {
        this.random_value = random_value;
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
