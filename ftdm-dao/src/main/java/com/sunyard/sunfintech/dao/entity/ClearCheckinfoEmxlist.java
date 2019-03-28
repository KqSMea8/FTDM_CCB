package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClearCheckinfoEmxlist implements Serializable {
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
    private Date trans_date;

    /**
     * 
     */
    private String serial_no;

    /**
     * 
     */
    private String order_no;

    /**
     * 
     */
    private BigDecimal amt;

    /**
     * 
     */
    private BigDecimal charge;

    /**
     * 
     */
    private String order_type;

    /**
     * 
     */
    private String pay_code;

    /**
     * 
     */
    private String tripartite_num;

    /**
     * 
     */
    private String payment_plat_no;

    /**
     * 
     */
    private String payment_pay_code;

    /**
     * clear_checkinfo_emxlist
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
     * @return trans_date 
     */
    public Date getTrans_date() {
        return trans_date;
    }

    /**
     * 
     * @param trans_date 
     */
    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    /**
     * 
     * @return serial_no 
     */
    public String getSerial_no() {
        return serial_no;
    }

    /**
     * 
     * @param serial_no 
     */
    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no == null ? null : serial_no.trim();
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
     * @return amt 
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 
     * @param amt 
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 
     * @return charge 
     */
    public BigDecimal getCharge() {
        return charge;
    }

    /**
     * 
     * @param charge 
     */
    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    /**
     * 
     * @return order_type 
     */
    public String getOrder_type() {
        return order_type;
    }

    /**
     * 
     * @param order_type 
     */
    public void setOrder_type(String order_type) {
        this.order_type = order_type == null ? null : order_type.trim();
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
     * @return tripartite_num 
     */
    public String getTripartite_num() {
        return tripartite_num;
    }

    /**
     * 
     * @param tripartite_num 
     */
    public void setTripartite_num(String tripartite_num) {
        this.tripartite_num = tripartite_num == null ? null : tripartite_num.trim();
    }

    /**
     * 
     * @return payment_plat_no 
     */
    public String getPayment_plat_no() {
        return payment_plat_no;
    }

    /**
     * 
     * @param payment_plat_no 
     */
    public void setPayment_plat_no(String payment_plat_no) {
        this.payment_plat_no = payment_plat_no == null ? null : payment_plat_no.trim();
    }

    /**
     * 
     * @return payment_pay_code 
     */
    public String getPayment_pay_code() {
        return payment_pay_code;
    }

    /**
     * 
     * @param payment_pay_code 
     */
    public void setPayment_pay_code(String payment_pay_code) {
        this.payment_pay_code = payment_pay_code == null ? null : payment_pay_code.trim();
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
        ClearCheckinfoEmxlist other = (ClearCheckinfoEmxlist) that;
        return (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getSerial_no() == null ? other.getSerial_no() == null : this.getSerial_no().equals(other.getSerial_no()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()))
            && (this.getCharge() == null ? other.getCharge() == null : this.getCharge().equals(other.getCharge()))
            && (this.getOrder_type() == null ? other.getOrder_type() == null : this.getOrder_type().equals(other.getOrder_type()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getTripartite_num() == null ? other.getTripartite_num() == null : this.getTripartite_num().equals(other.getTripartite_num()))
            && (this.getPayment_plat_no() == null ? other.getPayment_plat_no() == null : this.getPayment_plat_no().equals(other.getPayment_plat_no()))
            && (this.getPayment_pay_code() == null ? other.getPayment_pay_code() == null : this.getPayment_pay_code().equals(other.getPayment_pay_code()));
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
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getSerial_no() == null) ? 0 : getSerial_no().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        result = prime * result + ((getCharge() == null) ? 0 : getCharge().hashCode());
        result = prime * result + ((getOrder_type() == null) ? 0 : getOrder_type().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getTripartite_num() == null) ? 0 : getTripartite_num().hashCode());
        result = prime * result + ((getPayment_plat_no() == null) ? 0 : getPayment_plat_no().hashCode());
        result = prime * result + ((getPayment_pay_code() == null) ? 0 : getPayment_pay_code().hashCode());
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
        sb.append(", trans_date=").append(trans_date);
        sb.append(", serial_no=").append(serial_no);
        sb.append(", order_no=").append(order_no);
        sb.append(", amt=").append(amt);
        sb.append(", charge=").append(charge);
        sb.append(", order_type=").append(order_type);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", tripartite_num=").append(tripartite_num);
        sb.append(", payment_plat_no=").append(payment_plat_no);
        sb.append(", payment_pay_code=").append(payment_pay_code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}