package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ClearCheckError implements Serializable {
    /**
     * 
     */
    private String clear_date;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String platcust;

    /**
     * 
     */
    private String account_type;

    /**
     * 
     */
    private String account_name;

    /**
     * 
     */
    private String clear_code;

    /**
     * 
     */
    private String clear_name;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private String serial_id;

    /**
     * 
     */
    private String order_no;

    /**
     * 0未处理1已处理
     */
    private String status;

    /**
     * 
     */
    private String pay_code;

    /**
     * 
     */
    private BigDecimal trans_amt;

    /**
     * 
     */
    private BigDecimal pay_amt;

    /**
     * clear_check_error
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return clear_date 
     */
    public String getClear_date() {
        return clear_date;
    }

    /**
     * 
     * @param clear_date 
     */
    public void setClear_date(String clear_date) {
        this.clear_date = clear_date == null ? null : clear_date.trim();
    }

    /**
     * 
     * @return plat_no 
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 
     * @param plat_no 
     */
    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no == null ? null : plat_no.trim();
    }

    /**
     * 
     * @return platcust 
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 
     * @param platcust 
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 
     * @return account_type 
     */
    public String getAccount_type() {
        return account_type;
    }

    /**
     * 
     * @param account_type 
     */
    public void setAccount_type(String account_type) {
        this.account_type = account_type == null ? null : account_type.trim();
    }

    /**
     * 
     * @return account_name 
     */
    public String getAccount_name() {
        return account_name;
    }

    /**
     * 
     * @param account_name 
     */
    public void setAccount_name(String account_name) {
        this.account_name = account_name == null ? null : account_name.trim();
    }

    /**
     * 
     * @return clear_code 
     */
    public String getClear_code() {
        return clear_code;
    }

    /**
     * 
     * @param clear_code 
     */
    public void setClear_code(String clear_code) {
        this.clear_code = clear_code == null ? null : clear_code.trim();
    }

    /**
     * 
     * @return clear_name 
     */
    public String getClear_name() {
        return clear_name;
    }

    /**
     * 
     * @param clear_name 
     */
    public void setClear_name(String clear_name) {
        this.clear_name = clear_name == null ? null : clear_name.trim();
    }

    /**
     * 
     * @return remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 
     * @return serial_id 
     */
    public String getSerial_id() {
        return serial_id;
    }

    /**
     * 
     * @param serial_id 
     */
    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id == null ? null : serial_id.trim();
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
     * 0未处理1已处理
     * @return status 0未处理1已处理
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0未处理1已处理
     * @param status 0未处理1已处理
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * @return pay_code 
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 
     * @param pay_code 
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
    }

    /**
     * 
     * @return trans_amt 
     */
    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    /**
     * 
     * @param trans_amt 
     */
    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    /**
     * 
     * @return pay_amt 
     */
    public BigDecimal getPay_amt() {
        return pay_amt;
    }

    /**
     * 
     * @param pay_amt 
     */
    public void setPay_amt(BigDecimal pay_amt) {
        this.pay_amt = pay_amt;
    }

    /**
     *
     * @mbggenerated 2017-07-04
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
        ClearCheckError other = (ClearCheckError) that;
        return (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getAccount_type() == null ? other.getAccount_type() == null : this.getAccount_type().equals(other.getAccount_type()))
            && (this.getAccount_name() == null ? other.getAccount_name() == null : this.getAccount_name().equals(other.getAccount_name()))
            && (this.getClear_code() == null ? other.getClear_code() == null : this.getClear_code().equals(other.getClear_code()))
            && (this.getClear_name() == null ? other.getClear_name() == null : this.getClear_name().equals(other.getClear_name()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getSerial_id() == null ? other.getSerial_id() == null : this.getSerial_id().equals(other.getSerial_id()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getTrans_amt() == null ? other.getTrans_amt() == null : this.getTrans_amt().equals(other.getTrans_amt()))
            && (this.getPay_amt() == null ? other.getPay_amt() == null : this.getPay_amt().equals(other.getPay_amt()));
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getAccount_type() == null) ? 0 : getAccount_type().hashCode());
        result = prime * result + ((getAccount_name() == null) ? 0 : getAccount_name().hashCode());
        result = prime * result + ((getClear_code() == null) ? 0 : getClear_code().hashCode());
        result = prime * result + ((getClear_name() == null) ? 0 : getClear_name().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getSerial_id() == null) ? 0 : getSerial_id().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getTrans_amt() == null) ? 0 : getTrans_amt().hashCode());
        result = prime * result + ((getPay_amt() == null) ? 0 : getPay_amt().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", clear_date=").append(clear_date);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", account_type=").append(account_type);
        sb.append(", account_name=").append(account_name);
        sb.append(", clear_code=").append(clear_code);
        sb.append(", clear_name=").append(clear_name);
        sb.append(", remark=").append(remark);
        sb.append(", serial_id=").append(serial_id);
        sb.append(", order_no=").append(order_no);
        sb.append(", status=").append(status);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", trans_amt=").append(trans_amt);
        sb.append(", pay_amt=").append(pay_amt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}