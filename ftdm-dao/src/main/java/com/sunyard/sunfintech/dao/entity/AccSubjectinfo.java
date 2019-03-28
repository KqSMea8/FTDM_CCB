package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class AccSubjectinfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 01 现金             02  在途
     */
    private String subject;

    /**
     * 01 投资             02  融资             03            电子账户映射             08  标的             
     */
    private String sub_subject;

    /**
     * 
     */
    private String enabled;

    /**
     * 
     */
    private String remark;

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
     * acc_subjectinfo
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
     * 01 现金             02  在途
     * @return subject 01 现金             02  在途
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 01 现金             02  在途
     * @param subject 01 现金             02  在途
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * 01 投资             02  融资             03            电子账户映射             08  标的             
     * @return sub_subject 01 投资             02  融资             03            电子账户映射             08  标的             
     */
    public String getSub_subject() {
        return sub_subject;
    }

    /**
     * 01 投资             02  融资             03            电子账户映射             08  标的             
     * @param sub_subject 01 投资             02  融资             03            电子账户映射             08  标的             
     */
    public void setSub_subject(String sub_subject) {
        this.sub_subject = sub_subject == null ? null : sub_subject.trim();
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
     * @return create_by 
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 
     * @param create_by 
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
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
     * @return update_by 
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 
     * @param update_by 
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     *
     * @mbggenerated 2017-06-01
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
        AccSubjectinfo other = (AccSubjectinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getSub_subject() == null ? other.getSub_subject() == null : this.getSub_subject().equals(other.getSub_subject()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getSub_subject() == null) ? 0 : getSub_subject().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", subject=").append(subject);
        sb.append(", sub_subject=").append(sub_subject);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}