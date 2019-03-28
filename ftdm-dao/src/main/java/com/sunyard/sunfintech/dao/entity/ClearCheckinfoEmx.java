package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClearCheckinfoEmx implements Serializable {
    /**
     * 
     */
    private Date clear_date;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private Date get_time;

    /**
     * 
     */
    private String send_time;

    /**
     * 
     */
    private String check_time;

    /**
     * 
     */
    private Integer e_int;

    /**
     * 
     */
    private BigDecimal e_mat;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String payment_plat_no;

    /**
     * clear_checkinfo_emx
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return clear_date 
     */
    public Date getClear_date() {
        return clear_date;
    }

    /**
     * 
     * @param clear_date 
     */
    public void setClear_date(Date clear_date) {
        this.clear_date = clear_date;
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
     * @return get_time 
     */
    public Date getGet_time() {
        return get_time;
    }

    /**
     * 
     * @param get_time 
     */
    public void setGet_time(Date get_time) {
        this.get_time = get_time;
    }

    /**
     * 
     * @return send_time 
     */
    public String getSend_time() {
        return send_time;
    }

    /**
     * 
     * @param send_time 
     */
    public void setSend_time(String send_time) {
        this.send_time = send_time == null ? null : send_time.trim();
    }

    /**
     * 
     * @return check_time 
     */
    public String getCheck_time() {
        return check_time;
    }

    /**
     * 
     * @param check_time 
     */
    public void setCheck_time(String check_time) {
        this.check_time = check_time == null ? null : check_time.trim();
    }

    /**
     * 
     * @return e_int 
     */
    public Integer getE_int() {
        return e_int;
    }

    /**
     * 
     * @param e_int 
     */
    public void setE_int(Integer e_int) {
        this.e_int = e_int;
    }

    /**
     * 
     * @return e_mat 
     */
    public BigDecimal getE_mat() {
        return e_mat;
    }

    /**
     * 
     * @param e_mat 
     */
    public void setE_mat(BigDecimal e_mat) {
        this.e_mat = e_mat;
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
        ClearCheckinfoEmx other = (ClearCheckinfoEmx) that;
        return (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getGet_time() == null ? other.getGet_time() == null : this.getGet_time().equals(other.getGet_time()))
            && (this.getSend_time() == null ? other.getSend_time() == null : this.getSend_time().equals(other.getSend_time()))
            && (this.getCheck_time() == null ? other.getCheck_time() == null : this.getCheck_time().equals(other.getCheck_time()))
            && (this.getE_int() == null ? other.getE_int() == null : this.getE_int().equals(other.getE_int()))
            && (this.getE_mat() == null ? other.getE_mat() == null : this.getE_mat().equals(other.getE_mat()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPayment_plat_no() == null ? other.getPayment_plat_no() == null : this.getPayment_plat_no().equals(other.getPayment_plat_no()));
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
        result = prime * result + ((getGet_time() == null) ? 0 : getGet_time().hashCode());
        result = prime * result + ((getSend_time() == null) ? 0 : getSend_time().hashCode());
        result = prime * result + ((getCheck_time() == null) ? 0 : getCheck_time().hashCode());
        result = prime * result + ((getE_int() == null) ? 0 : getE_int().hashCode());
        result = prime * result + ((getE_mat() == null) ? 0 : getE_mat().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPayment_plat_no() == null) ? 0 : getPayment_plat_no().hashCode());
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
        sb.append(", get_time=").append(get_time);
        sb.append(", send_time=").append(send_time);
        sb.append(", check_time=").append(check_time);
        sb.append(", e_int=").append(e_int);
        sb.append(", e_mat=").append(e_mat);
        sb.append(", status=").append(status);
        sb.append(", payment_plat_no=").append(payment_plat_no);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}