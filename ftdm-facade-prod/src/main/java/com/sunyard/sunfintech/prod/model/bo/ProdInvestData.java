package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseModel;
import com.sunyard.sunfintech.core.base.BaseSerialNoRequest;
import com.sunyard.sunfintech.core.util.BeanUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.prod.model.bo.Commission;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestData extends BaseSerialNoRequest {

    public ProdInvestData copy(){
        ProdInvestData prodInvestData=new ProdInvestData();
        BeanUtils.copyProperties(this,prodInvestData);
        if (StringUtils.isNotBlank( this.commission)){
            prodInvestData.setCommission(this.commission);
        }
        return prodInvestData;
    }
    /**
     * 明细编号
     */
    @NotBlank
    private String detail_no;

    /**
     * 原申请订单号
     */
    private String origin_order_no;

    @NotBlank
    private String prod_id;

    /**
     * 投资人编号
     */
    @NotBlank
    private String platcust;

    /**
     * 交易金额
     */
    @NotNull
    private BigDecimal trans_amt;

    /**
     * 体验金金额
     */
    private BigDecimal experience_amt;

    /**
     * 抵用券金额
     */
    private BigDecimal coupon_amt;

    /**
     * 自费金额
     */
    private BigDecimal self_amt;

    /**
     * 加息
     */
    private BigDecimal in_interest;

    /**
     * 科目优先级
     */
    @NotBlank
    private String subject_priority;

    /**
     * 手续费说明
     */
    private String commission;

    private Commission commissionObj;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        super.setBase_serial_order_no(detail_no);
        this.detail_no = detail_no;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        super.setBase_serial_ori_order_no(origin_order_no);
        this.origin_order_no = origin_order_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        super.setBase_serial_plutcust(platcust);
        this.platcust = platcust;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public BigDecimal getExperience_amt() {
        return experience_amt;
    }

    public void setExperience_amt(BigDecimal experience_amt) {
        this.experience_amt = experience_amt;
    }

    public BigDecimal getCoupon_amt() {
        return coupon_amt;
    }

    public void setCoupon_amt(BigDecimal coupon_amt) {
        this.coupon_amt = coupon_amt;
    }

    public BigDecimal getSelf_amt() {
        return self_amt;
    }

    public void setSelf_amt(BigDecimal self_amt) {
        this.self_amt = self_amt;
    }

    public BigDecimal getIn_interest() {
        return in_interest;
    }

    public void setIn_interest(BigDecimal in_interest) {
        this.in_interest = in_interest;
    }

    public String getSubject_priority() {
        return subject_priority;
    }

    public void setSubject_priority(String subject_priority) {
        this.subject_priority = subject_priority;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
        setCommissionObj(JSON.parseObject(commission,Commission.class));
    }

    public Commission getCommissionObj() {
        return commissionObj;
    }

    public void setCommissionObj(Commission commissionObj) {
        this.commissionObj = commissionObj;
    }

    @Override
    public String toString() {
        return "ProdInvestData{" +
                "detail_no='" + detail_no + '\'' +
                ", prod_id='" + prod_id + '\'' +
                ", platcust='" + platcust + '\'' +
                ", trans_amt=" + trans_amt +
                ", experience_amt=" + experience_amt +
                ", coupon_amt=" + coupon_amt +
                ", self_amt=" + self_amt +
                ", in_interest=" + in_interest +
                ", subject_priority='" + subject_priority + '\'' +
                ", commission='" + commission + '\'' +
                ", commissionObj=" + commissionObj +
                '}';
    }
}
