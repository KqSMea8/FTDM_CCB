package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 自有资金账户手续费扣款查询
 * Created by wubin on 2017/5/5.
 */
public class SubstitutePayFeeRequest extends BaseRequest {

    /**
     * 手续费类型
     1-银联代扣手续费
     2-实名认证手续费
     3-短信手续费
     */
    @NotBlank
    private String fee_type;

    /**
     * 查询区间，开始日期
     */
    @NotBlank
    private String start_date;

    /**
     * 查询区间，结束日期
     */
    @NotBlank
    private String end_date;

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
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
