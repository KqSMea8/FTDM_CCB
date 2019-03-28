package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class EaccClearErrorInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String clear_date;

    /**
     * 
     */
    private String eaccount;

    /**
     * 我方电子账户余额
     */
    private String own_Amt;

    /**
     * 行方电子账户余额
     */
    private String bank_Amt;

    /**
     * 差错信息
     */
    private String error_msg;

    /**
     * 
     */
    private Date create_time;

    /**
     * eacc_clear_error_info
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * @return eaccount 
     */
    public String getEaccount() {
        return eaccount;
    }

    /**
     * 
     * @param eaccount 
     */
    public void setEaccount(String eaccount) {
        this.eaccount = eaccount == null ? null : eaccount.trim();
    }

    /**
     * 我方电子账户余额
     * @return own_Amt 我方电子账户余额
     */
    public String getOwn_Amt() {
        return own_Amt;
    }

    /**
     * 我方电子账户余额
     * @param own_Amt 我方电子账户余额
     */
    public void setOwn_Amt(String own_Amt) {
        this.own_Amt = own_Amt == null ? null : own_Amt.trim();
    }

    /**
     * 行方电子账户余额
     * @return bank_Amt 行方电子账户余额
     */
    public String getBank_Amt() {
        return bank_Amt;
    }

    /**
     * 行方电子账户余额
     * @param bank_Amt 行方电子账户余额
     */
    public void setBank_Amt(String bank_Amt) {
        this.bank_Amt = bank_Amt == null ? null : bank_Amt.trim();
    }

    /**
     * 差错信息
     * @return error_msg 差错信息
     */
    public String getError_msg() {
        return error_msg;
    }

    /**
     * 差错信息
     * @param error_msg 差错信息
     */
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg == null ? null : error_msg.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     *
     * @mbggenerated 2017-08-09
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
        EaccClearErrorInfo other = (EaccClearErrorInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getEaccount() == null ? other.getEaccount() == null : this.getEaccount().equals(other.getEaccount()))
            && (this.getOwn_Amt() == null ? other.getOwn_Amt() == null : this.getOwn_Amt().equals(other.getOwn_Amt()))
            && (this.getBank_Amt() == null ? other.getBank_Amt() == null : this.getBank_Amt().equals(other.getBank_Amt()))
            && (this.getError_msg() == null ? other.getError_msg() == null : this.getError_msg().equals(other.getError_msg()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()));
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getEaccount() == null) ? 0 : getEaccount().hashCode());
        result = prime * result + ((getOwn_Amt() == null) ? 0 : getOwn_Amt().hashCode());
        result = prime * result + ((getBank_Amt() == null) ? 0 : getBank_Amt().hashCode());
        result = prime * result + ((getError_msg() == null) ? 0 : getError_msg().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clear_date=").append(clear_date);
        sb.append(", eaccount=").append(eaccount);
        sb.append(", own_Amt=").append(own_Amt);
        sb.append(", bank_Amt=").append(bank_Amt);
        sb.append(", error_msg=").append(error_msg);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}