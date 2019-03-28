package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdRefundReqData extends ProdRefundReqDataKey implements Serializable {
    /**
     * 
     */
    private String prod_id;

    /**
     * 
     */
    private String order_no;

    /**
     * 
     */
    private String trans_serial;

    /**
     * 
     */
    private BigDecimal real_repay_amount;

    /**
     * 
     */
    private BigDecimal experience_amt;

    /**
     * 
     */
    private BigDecimal rates_amt;

    /**
     * 
     */
    private BigDecimal repay_fee;

    /**
     * 
     */
    private BigDecimal real_repay_val;

    /**
     * 
     */
    private String cust_no;

    /**
     * 
     */
    private String repay_num;

    /**
     * 
     */
    private Date repay_date;

    /**
     * 
     */
    private Date real_repay_date;

    /**
     * 
     */
    private String is_payoff;

    /**
     * 
     */
    private String repay_flag;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String enabled;

    /**
     * prod_refund_req_data
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return prod_id 
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * 
     * @param prod_id 
     */
    public void setProd_id(String prod_id) {
        this.prod_id = prod_id == null ? null : prod_id.trim();
    }

    /**
     * 
     * @return order_no 
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 
     * @param order_no 
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 
     * @return trans_serial 
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 
     * @param trans_serial 
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
    }

    /**
     * 
     * @return real_repay_amount 
     */
    public BigDecimal getReal_repay_amount() {
        return real_repay_amount;
    }

    /**
     * 
     * @param real_repay_amount 
     */
    public void setReal_repay_amount(BigDecimal real_repay_amount) {
        this.real_repay_amount = real_repay_amount;
    }

    /**
     * 
     * @return experience_amt 
     */
    public BigDecimal getExperience_amt() {
        return experience_amt;
    }

    /**
     * 
     * @param experience_amt 
     */
    public void setExperience_amt(BigDecimal experience_amt) {
        this.experience_amt = experience_amt;
    }

    /**
     * 
     * @return rates_amt 
     */
    public BigDecimal getRates_amt() {
        return rates_amt;
    }

    /**
     * 
     * @param rates_amt 
     */
    public void setRates_amt(BigDecimal rates_amt) {
        this.rates_amt = rates_amt;
    }

    /**
     * 
     * @return repay_fee 
     */
    public BigDecimal getRepay_fee() {
        return repay_fee;
    }

    /**
     * 
     * @param repay_fee 
     */
    public void setRepay_fee(BigDecimal repay_fee) {
        this.repay_fee = repay_fee;
    }

    /**
     * 
     * @return real_repay_val 
     */
    public BigDecimal getReal_repay_val() {
        return real_repay_val;
    }

    /**
     * 
     * @param real_repay_val 
     */
    public void setReal_repay_val(BigDecimal real_repay_val) {
        this.real_repay_val = real_repay_val;
    }

    /**
     * 
     * @return cust_no 
     */
    public String getCust_no() {
        return cust_no;
    }

    /**
     * 
     * @param cust_no 
     */
    public void setCust_no(String cust_no) {
        this.cust_no = cust_no == null ? null : cust_no.trim();
    }

    /**
     * 
     * @return repay_num 
     */
    public String getRepay_num() {
        return repay_num;
    }

    /**
     * 
     * @param repay_num 
     */
    public void setRepay_num(String repay_num) {
        this.repay_num = repay_num == null ? null : repay_num.trim();
    }

    /**
     * 
     * @return repay_date 
     */
    public Date getRepay_date() {
        return repay_date;
    }

    /**
     * 
     * @param repay_date 
     */
    public void setRepay_date(Date repay_date) {
        this.repay_date = repay_date;
    }

    /**
     * 
     * @return real_repay_date 
     */
    public Date getReal_repay_date() {
        return real_repay_date;
    }

    /**
     * 
     * @param real_repay_date 
     */
    public void setReal_repay_date(Date real_repay_date) {
        this.real_repay_date = real_repay_date;
    }

    /**
     * 
     * @return is_payoff 
     */
    public String getIs_payoff() {
        return is_payoff;
    }

    /**
     * 
     * @param is_payoff 
     */
    public void setIs_payoff(String is_payoff) {
        this.is_payoff = is_payoff == null ? null : is_payoff.trim();
    }

    /**
     * 
     * @return repay_flag 
     */
    public String getRepay_flag() {
        return repay_flag;
    }

    /**
     * 
     * @param repay_flag 
     */
    public void setRepay_flag(String repay_flag) {
        this.repay_flag = repay_flag == null ? null : repay_flag.trim();
    }

    /**
     * 
     * @return status 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * @return enabled 
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled 
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProdRefundReqData other = (ProdRefundReqData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getReal_repay_amount() == null ? other.getReal_repay_amount() == null : this.getReal_repay_amount().equals(other.getReal_repay_amount()))
            && (this.getExperience_amt() == null ? other.getExperience_amt() == null : this.getExperience_amt().equals(other.getExperience_amt()))
            && (this.getRates_amt() == null ? other.getRates_amt() == null : this.getRates_amt().equals(other.getRates_amt()))
            && (this.getRepay_fee() == null ? other.getRepay_fee() == null : this.getRepay_fee().equals(other.getRepay_fee()))
            && (this.getReal_repay_val() == null ? other.getReal_repay_val() == null : this.getReal_repay_val().equals(other.getReal_repay_val()))
            && (this.getCust_no() == null ? other.getCust_no() == null : this.getCust_no().equals(other.getCust_no()))
            && (this.getRepay_num() == null ? other.getRepay_num() == null : this.getRepay_num().equals(other.getRepay_num()))
            && (this.getRepay_date() == null ? other.getRepay_date() == null : this.getRepay_date().equals(other.getRepay_date()))
            && (this.getReal_repay_date() == null ? other.getReal_repay_date() == null : this.getReal_repay_date().equals(other.getReal_repay_date()))
            && (this.getIs_payoff() == null ? other.getIs_payoff() == null : this.getIs_payoff().equals(other.getIs_payoff()))
            && (this.getRepay_flag() == null ? other.getRepay_flag() == null : this.getRepay_flag().equals(other.getRepay_flag()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()));
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getReal_repay_amount() == null) ? 0 : getReal_repay_amount().hashCode());
        result = prime * result + ((getExperience_amt() == null) ? 0 : getExperience_amt().hashCode());
        result = prime * result + ((getRates_amt() == null) ? 0 : getRates_amt().hashCode());
        result = prime * result + ((getRepay_fee() == null) ? 0 : getRepay_fee().hashCode());
        result = prime * result + ((getReal_repay_val() == null) ? 0 : getReal_repay_val().hashCode());
        result = prime * result + ((getCust_no() == null) ? 0 : getCust_no().hashCode());
        result = prime * result + ((getRepay_num() == null) ? 0 : getRepay_num().hashCode());
        result = prime * result + ((getRepay_date() == null) ? 0 : getRepay_date().hashCode());
        result = prime * result + ((getReal_repay_date() == null) ? 0 : getReal_repay_date().hashCode());
        result = prime * result + ((getIs_payoff() == null) ? 0 : getIs_payoff().hashCode());
        result = prime * result + ((getRepay_flag() == null) ? 0 : getRepay_flag().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", prod_id=").append(prod_id);
        sb.append(", order_no=").append(order_no);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", real_repay_amount=").append(real_repay_amount);
        sb.append(", experience_amt=").append(experience_amt);
        sb.append(", rates_amt=").append(rates_amt);
        sb.append(", repay_fee=").append(repay_fee);
        sb.append(", real_repay_val=").append(real_repay_val);
        sb.append(", cust_no=").append(cust_no);
        sb.append(", repay_num=").append(repay_num);
        sb.append(", repay_date=").append(repay_date);
        sb.append(", real_repay_date=").append(real_repay_date);
        sb.append(", is_payoff=").append(is_payoff);
        sb.append(", repay_flag=").append(repay_flag);
        sb.append(", status=").append(status);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}