package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class EaccClearCheckInfo implements Serializable {
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
     * 
     */
    private String balance;

    /**
     * 是否动账，Y动账，N未动账
     */
    private String is_active;

    /**
     * 
     */
    private Date create_time;

    /**
     * eacc_clear_check_info
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
     * 
     * @return balance 
     */
    public String getBalance() {
        return balance;
    }

    /**
     * 
     * @param balance 
     */
    public void setBalance(String balance) {
        this.balance = balance == null ? null : balance.trim();
    }

    /**
     * 是否动账，Y动账，N未动账
     * @return is_active 是否动账，Y动账，N未动账
     */
    public String getIs_active() {
        return is_active;
    }

    /**
     * 是否动账，Y动账，N未动账
     * @param is_active 是否动账，Y动账，N未动账
     */
    public void setIs_active(String is_active) {
        this.is_active = is_active == null ? null : is_active.trim();
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
        EaccClearCheckInfo other = (EaccClearCheckInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getEaccount() == null ? other.getEaccount() == null : this.getEaccount().equals(other.getEaccount()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()))
            && (this.getIs_active() == null ? other.getIs_active() == null : this.getIs_active().equals(other.getIs_active()))
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
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        result = prime * result + ((getIs_active() == null) ? 0 : getIs_active().hashCode());
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
        sb.append(", balance=").append(balance);
        sb.append(", is_active=").append(is_active);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}