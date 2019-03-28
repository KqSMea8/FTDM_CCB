package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 分账成标
 * Created by wubin on 2017/5/10.
 */
public class SubdataObject extends BaseModel{
    /**
     * 分账的客户编号
     */
    private String plat_cust;
    /**
     * 分账金额
     */
    private String amt;
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

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
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
