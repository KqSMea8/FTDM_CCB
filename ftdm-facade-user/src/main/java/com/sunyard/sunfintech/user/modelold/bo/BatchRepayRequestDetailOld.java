package com.sunyard.sunfintech.user.modelold.bo;

import com.alibaba.fastjson.JSONArray;
import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by PengZY on 2018/3/5.
 */
public class BatchRepayRequestDetailOld extends BaseModel {

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
     * 实际还款金额
     */
    @NotNull
    @DecimalMin(value="0.001")
    private BigDecimal real_repay_amt;

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
     * 手续费金额
     */
    @DecimalMin(value="0.000")
    private BigDecimal fee_amt;

    /**
     * 备注
     */
    private String remark;

    private String subdata;
    private List<SubdataObject> SubdataObjectList;

    private String plat_subdata;
    private List<PlatSubData> platSubDataObject;

    public String getSubdata() {
        return subdata;
    }

    public void setSubdata(String subdata) {
        this.subdata = subdata;
        setSubdataObjectList(JSONArray.parseArray(subdata,SubdataObject.class));
    }

    public List<SubdataObject> getSubdataObjectList() {
        return SubdataObjectList;
    }

    public void setSubdataObjectList(List<SubdataObject> subdataObjectList) {
        SubdataObjectList = subdataObjectList;
    }

    public String getPlat_subdata() {
        return plat_subdata;
    }

    public void setPlat_subdata(String plat_subdata) {
        this.plat_subdata = plat_subdata;
        setPlatSubDataObject(JSONArray.parseArray(plat_subdata,PlatSubData.class));
    }

    public List<PlatSubData> getPlatSubDataObject() {
        return platSubDataObject;
    }

    public void setPlatSubDataObject(List<PlatSubData> platSubDataObject) {
        this.platSubDataObject = platSubDataObject;
    }

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

    public BigDecimal getReal_repay_amt() {
        return real_repay_amt;
    }

    public void setReal_repay_amt(BigDecimal real_repay_amt) {
        this.real_repay_amt = real_repay_amt;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public BigDecimal getFee_amt() {
        return fee_amt;
    }

    public void setFee_amt(BigDecimal fee_amt) {
        this.fee_amt = fee_amt;
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
