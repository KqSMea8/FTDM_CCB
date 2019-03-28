package com.sunyard.sunfintech.account.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by my on 2018/5/2.
 */
public class EaccountProdListResponse implements Serializable {





    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 平台客户号
     */
    private String platcust;

    /**
     * 科目
     */
    private String subject;

    /**
     *
     */
    private String sub_subject;

    /**
     * 交易代码
     */
    private String trans_code;

    /**
     * 交易名称
     */
    private String trans_name;

    /**
     * 交易日期
     */
    private String trans_date;

    /**
     * 交易时间
     */
    private String trans_time;

    /**
     * 资金流向(0-入金  1-出金  2-冻结  3-解冻)
     */
    private String amt_type;

    /**
     *
     */
    private String oppo_plat_no;

    /**
     * 对手账户
     */
    private String oppo_platcust;

    /**
     * 对手科目
     */
    private String oppo_subject;

    /**
     *
     */
    private String oppo_sub_subject;

    /**
     * 变动金额
     */
    private BigDecimal amt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 转换状态0-未转换1-部分转换2全部转换
     */
    private String switch_state;

    /**
     * 已转换金额
     */
    private BigDecimal switch_amt;

    /**
     * 支付公司编号
     */
    private String pay_code;

    /**
     * 账期
     */
    private Date account_date;


    /**
     *
     */
    private String enabled;

    /**
     *
     */
    private String ramerk;

    /**
     *
     */
    private String create_by;

    /**
     *
     */
    private Date create_time;

    /**
     *
     */
    private String update_by;

    /**
     *
     */
    private Date update_time;

    /**
     *
     */
    private String order_no;

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSub_subject() {
        return sub_subject;
    }

    public void setSub_subject(String sub_subject) {
        this.sub_subject = sub_subject;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }


    public String getTrans_date() {
        return trans_date;
    }


    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getAmt_type() {
        return amt_type;
    }

    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type;
    }

    public String getOppo_plat_no() {
        return oppo_plat_no;
    }

    public void setOppo_plat_no(String oppo_plat_no) {
        this.oppo_plat_no = oppo_plat_no;
    }

    public String getOppo_platcust() {
        return oppo_platcust;
    }

    public void setOppo_platcust(String oppo_platcust) {
        this.oppo_platcust = oppo_platcust;
    }

    public String getOppo_subject() {
        return oppo_subject;
    }

    public void setOppo_subject(String oppo_subject) {
        this.oppo_subject = oppo_subject;
    }

    public String getOppo_sub_subject() {
        return oppo_sub_subject;
    }

    public void setOppo_sub_subject(String oppo_sub_subject) {
        this.oppo_sub_subject = oppo_sub_subject;
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

    public String getSwitch_state() {
        return switch_state;
    }

    public void setSwitch_state(String switch_state) {
        this.switch_state = switch_state;
    }

    public BigDecimal getSwitch_amt() {
        return switch_amt;
    }

    public void setSwitch_amt(BigDecimal switch_amt) {
        this.switch_amt = switch_amt;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public Date getAccount_date() {
        return account_date;
    }

    public void setAccount_date(Date account_date) {
        this.account_date = account_date;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getRamerk() {
        return ramerk;
    }

    public void setRamerk(String ramerk) {
        this.ramerk = ramerk;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }


    public String getOrder_no() {
        return order_no;
    }


    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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
