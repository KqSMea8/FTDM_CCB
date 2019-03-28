package com.sunyard.sunfintech.billcheck.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by wubin on 2017/5/5.
 */
public class ReconciliationRequest extends BaseRequest{

    /**
     * 对账日期
     */
    @NotBlank
    private String paycheck_date;

    public String getPaycheck_date() {
        return paycheck_date;
    }

    public void setPaycheck_date(String paycheck_date) {
        this.paycheck_date = paycheck_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReconciliationRequest that = (ReconciliationRequest) o;

        return paycheck_date != null ? paycheck_date.equals(that.paycheck_date) : that.paycheck_date == null;
    }

    @Override
    public int hashCode() {
        return paycheck_date != null ? paycheck_date.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ReconciliationRequest{" +
                "paycheck_date='" + paycheck_date + '\'' +
                '}';
    }
}
