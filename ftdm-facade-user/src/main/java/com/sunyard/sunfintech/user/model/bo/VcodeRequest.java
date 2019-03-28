package com.sunyard.sunfintech.user.model.bo;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 验证码
 * @author wyc
 */

public class VcodeRequest implements Serializable {
    /**
     * 流水号
     */
    @NotBlank
    private String trans_serial;
    @NotBlank
    private String origin_order_no;

    private String mobile;

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

    public String getTrans_serial() {
        return trans_serial;
    }

    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
