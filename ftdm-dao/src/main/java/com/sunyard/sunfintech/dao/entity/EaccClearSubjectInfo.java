package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class EaccClearSubjectInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String eaccount;

    /**
     * 
     */
    private String t_balance;

    /**
     * 
     */
    private String n_balance;

    /**
     * 
     */
    private String f_balance;

    /**
     * 
     */
    private String clear_date;

    /**
     * 
     */
    private String subject;

    /**
     * 
     */
    private String sub_subject;

    /**
     * 
     */
    private Date create_time;

    /**
     * eacc_clear_subject_info
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
     * @return t_balance 
     */
    public String getT_balance() {
        return t_balance;
    }

    /**
     * 
     * @param t_balance 
     */
    public void setT_balance(String t_balance) {
        this.t_balance = t_balance == null ? null : t_balance.trim();
    }

    /**
     * 
     * @return n_balance 
     */
    public String getN_balance() {
        return n_balance;
    }

    /**
     * 
     * @param n_balance 
     */
    public void setN_balance(String n_balance) {
        this.n_balance = n_balance == null ? null : n_balance.trim();
    }

    /**
     * 
     * @return f_balance 
     */
    public String getF_balance() {
        return f_balance;
    }

    /**
     * 
     * @param f_balance 
     */
    public void setF_balance(String f_balance) {
        this.f_balance = f_balance == null ? null : f_balance.trim();
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
     * @return subject 
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject 
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * 
     * @return sub_subject 
     */
    public String getSub_subject() {
        return sub_subject;
    }

    /**
     * 
     * @param sub_subject 
     */
    public void setSub_subject(String sub_subject) {
        this.sub_subject = sub_subject == null ? null : sub_subject.trim();
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
        EaccClearSubjectInfo other = (EaccClearSubjectInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEaccount() == null ? other.getEaccount() == null : this.getEaccount().equals(other.getEaccount()))
            && (this.getT_balance() == null ? other.getT_balance() == null : this.getT_balance().equals(other.getT_balance()))
            && (this.getN_balance() == null ? other.getN_balance() == null : this.getN_balance().equals(other.getN_balance()))
            && (this.getF_balance() == null ? other.getF_balance() == null : this.getF_balance().equals(other.getF_balance()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getSub_subject() == null ? other.getSub_subject() == null : this.getSub_subject().equals(other.getSub_subject()))
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
        result = prime * result + ((getEaccount() == null) ? 0 : getEaccount().hashCode());
        result = prime * result + ((getT_balance() == null) ? 0 : getT_balance().hashCode());
        result = prime * result + ((getN_balance() == null) ? 0 : getN_balance().hashCode());
        result = prime * result + ((getF_balance() == null) ? 0 : getF_balance().hashCode());
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getSub_subject() == null) ? 0 : getSub_subject().hashCode());
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
        sb.append(", eaccount=").append(eaccount);
        sb.append(", t_balance=").append(t_balance);
        sb.append(", n_balance=").append(n_balance);
        sb.append(", f_balance=").append(f_balance);
        sb.append(", clear_date=").append(clear_date);
        sb.append(", subject=").append(subject);
        sb.append(", sub_subject=").append(sub_subject);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}