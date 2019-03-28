package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 自有资金账户手续费扣款查询业务数据
 * Created by wubin on 2017/5/5.
 */
public class SubstitutePayFeeData extends BaseModel{

    /**
     * 交易日期
     */
    @NotBlank
    private String trasn_date;

    /**
     * 交易金额
     */
    @NotBlank
    private String amt;

    /**
     * 手续费类型
     1-银联代扣手续费
     2-实名认证手续费
     3-短信手续费
     */
    @NotBlank
    private String fee_type;

    /**
     * 摘要
     */
    @NotBlank
    private String remark;

    public String getTrasn_date() {
        return trasn_date;
    }

    public void setTrasn_date(String trasn_date) {
        this.trasn_date = trasn_date;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
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
