package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class EaccAccountBalanceSum implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String count_date;

    /**
     * 
     */
    private String own_balance;

    /**
     * 
     */
    private String other_balance;

    /**
     * 
     */
    private Date create_time;

    /**
     * eacc_account_balance_sum
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
     * @return count_date 
     */
    public String getCount_date() {
        return count_date;
    }

    /**
     * 
     * @param count_date 
     */
    public void setCount_date(String count_date) {
        this.count_date = count_date == null ? null : count_date.trim();
    }

    /**
     * 
     * @return own_balance 
     */
    public String getOwn_balance() {
        return own_balance;
    }

    /**
     * 
     * @param own_balance 
     */
    public void setOwn_balance(String own_balance) {
        this.own_balance = own_balance == null ? null : own_balance.trim();
    }

    /**
     * 
     * @return other_balance 
     */
    public String getOther_balance() {
        return other_balance;
    }

    /**
     * 
     * @param other_balance 
     */
    public void setOther_balance(String other_balance) {
        this.other_balance = other_balance == null ? null : other_balance.trim();
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
     * @mbggenerated 2017-08-15
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
        EaccAccountBalanceSum other = (EaccAccountBalanceSum) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCount_date() == null ? other.getCount_date() == null : this.getCount_date().equals(other.getCount_date()))
            && (this.getOwn_balance() == null ? other.getOwn_balance() == null : this.getOwn_balance().equals(other.getOwn_balance()))
            && (this.getOther_balance() == null ? other.getOther_balance() == null : this.getOther_balance().equals(other.getOther_balance()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()));
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCount_date() == null) ? 0 : getCount_date().hashCode());
        result = prime * result + ((getOwn_balance() == null) ? 0 : getOwn_balance().hashCode());
        result = prime * result + ((getOther_balance() == null) ? 0 : getOther_balance().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", count_date=").append(count_date);
        sb.append(", own_balance=").append(own_balance);
        sb.append(", other_balance=").append(other_balance);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}