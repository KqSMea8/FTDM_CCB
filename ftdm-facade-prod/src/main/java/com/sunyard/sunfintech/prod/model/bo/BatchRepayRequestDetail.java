package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSONArray;
import com.sunyard.sunfintech.core.base.BaseModel;
import com.sunyard.sunfintech.prod.model.bo.PlatSubData;
import com.sunyard.sunfintech.prod.model.bo.SubdataObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 借款人还款申请批量明细数据
 * @author pengzy
 */
public class BatchRepayRequestDetail extends BaseModel {

    /**
     * 明细编号
     */
    @NotBlank
    private String detail_no;

    /**
     * 标的编号
     */
    @NotBlank
    private String prod_id;

    /**
     * 还款期数
     */
    @NotNull
    private Integer repay_num;

    /**
     * 计划还款日期
     */
    @NotNull
    private Date repay_date;

    /**
     * 计划还款金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal repay_amt;

    /**
     * 实际还款日期
     */
    @NotNull
    private Date real_repay_date;

    /**
     * 平台客户编号
     */
    @NotBlank
    private String platcust;

    /**
     * 交易金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal trans_amt;

    /**
     * 备注
     */
    private String remark;

    public Integer getRepay_num() {
        return repay_num;
    }

    public void setRepay_num(Integer repay_num) {
        this.repay_num = repay_num;
    }

    public Date getRepay_date() {
        return repay_date;
    }

    public void setRepay_date(Date repay_date) {
        this.repay_date = repay_date;
    }

    public BigDecimal getRepay_amt() {
        return repay_amt;
    }

    public void setRepay_amt(BigDecimal repay_amt) {
        this.repay_amt = repay_amt;
    }

    public Date getReal_repay_date() {
        return real_repay_date;
    }

    public void setReal_repay_date(Date real_repay_date) {
        this.real_repay_date = real_repay_date;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
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
