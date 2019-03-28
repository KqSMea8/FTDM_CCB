package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 资金冻结解冻返回业务数据
 * Created by wubin on 2017/5/5.
 */
public class FreezeFundData extends BaseModel{

    /**
     * 商户请求订单号
     */
    @NotBlank
    private String order_no;

    /**
     * 订单状态0:已接受, 1:处理中,2:处理成功,3:处理失败
     */
    @NotBlank
    private String order_status;

    /**
     * 系统处理日期(yyyyMMddHHmmss)
     */
    @NotBlank
    private String process_date;

    /**
     * 平台流水号
     */
    @NotBlank
    private String query_id;

    /**
     * 冻结金额
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 平台客户电子账户
     */
    @NotBlank
    private String platcust;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
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
