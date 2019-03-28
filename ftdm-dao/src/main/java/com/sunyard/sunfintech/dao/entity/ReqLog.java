package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ReqLog implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String order_no;

    /**
     * 
     */
    private String req_date;

    /**
     * 
     */
    private String req_ip;

    /**
     * 
     */
    private String method;

    /**
     * 
     */
    private String class_name;

    /**
     * 
     */
    private String method_name;

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
     * req_log
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return req_date 
     */
    public String getReq_date() {
        return req_date;
    }

    /**
     * 
     * @param req_date 
     */
    public void setReq_date(String req_date) {
        this.req_date = req_date == null ? null : req_date.trim();
    }

    /**
     * 
     * @return req_ip 
     */
    public String getReq_ip() {
        return req_ip;
    }

    /**
     * 
     * @param req_ip 
     */
    public void setReq_ip(String req_ip) {
        this.req_ip = req_ip == null ? null : req_ip.trim();
    }

    /**
     * 
     * @return method 
     */
    public String getMethod() {
        return method;
    }

    /**
     * 
     * @param method 
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 
     * @return class_name 
     */
    public String getClass_name() {
        return class_name;
    }

    /**
     * 
     * @param class_name 
     */
    public void setClass_name(String class_name) {
        this.class_name = class_name == null ? null : class_name.trim();
    }

    /**
     * 
     * @return method_name 
     */
    public String getMethod_name() {
        return method_name;
    }

    /**
     * 
     * @param method_name 
     */
    public void setMethod_name(String method_name) {
        this.method_name = method_name == null ? null : method_name.trim();
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
     * @mbggenerated 2018-01-18
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
        ReqLog other = (ReqLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getReq_date() == null ? other.getReq_date() == null : this.getReq_date().equals(other.getReq_date()))
            && (this.getReq_ip() == null ? other.getReq_ip() == null : this.getReq_ip().equals(other.getReq_ip()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getClass_name() == null ? other.getClass_name() == null : this.getClass_name().equals(other.getClass_name()))
            && (this.getMethod_name() == null ? other.getMethod_name() == null : this.getMethod_name().equals(other.getMethod_name()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2018-01-18
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getReq_date() == null) ? 0 : getReq_date().hashCode());
        result = prime * result + ((getReq_ip() == null) ? 0 : getReq_ip().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getClass_name() == null) ? 0 : getClass_name().hashCode());
        result = prime * result + ((getMethod_name() == null) ? 0 : getMethod_name().hashCode());
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
     * @mbggenerated 2018-01-18
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", order_no=").append(order_no);
        sb.append(", req_date=").append(req_date);
        sb.append(", req_ip=").append(req_ip);
        sb.append(", method=").append(method);
        sb.append(", class_name=").append(class_name);
        sb.append(", method_name=").append(method_name);
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