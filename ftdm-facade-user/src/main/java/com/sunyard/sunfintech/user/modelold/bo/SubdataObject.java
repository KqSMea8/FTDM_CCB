package com.sunyard.sunfintech.user.modelold.bo;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 分账成标
 * Created by wubin on 2017/5/10.
 */
public class SubdataObject extends BaseModel {
    /**
     * 分账的客户编号
     */
    @NotBlank
    private String plat_cust;
    /**
     * 分账金额
     */
    @NotNull
    @DecimalMin(value="0.000")
    private BigDecimal amt;
    /**
     * 分账原因说明
     */
    private String remark;

    public String getPlat_cust() {
        return plat_cust;
    }

    public void setPlat_cust(String plat_cust) {
        this.plat_cust = plat_cust;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
