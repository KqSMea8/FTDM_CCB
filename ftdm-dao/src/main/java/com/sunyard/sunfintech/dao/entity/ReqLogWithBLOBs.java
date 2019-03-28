package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ReqLogWithBLOBs extends ReqLog implements Serializable {
    /**
     * 
     */
    private String req_param;

    /**
     * 
     */
    private String req_result;

    /**
     * req_log
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return req_param 
     */
    public String getReq_param() {
        return req_param;
    }

    /**
     * 
     * @param req_param 
     */
    public void setReq_param(String req_param) {
        this.req_param = req_param == null ? null : req_param.trim();
    }

    /**
     * 
     * @return req_result 
     */
    public String getReq_result() {
        return req_result;
    }

    /**
     * 
     * @param req_result 
     */
    public void setReq_result(String req_result) {
        this.req_result = req_result == null ? null : req_result.trim();
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
        ReqLogWithBLOBs other = (ReqLogWithBLOBs) that;
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
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getReq_param() == null ? other.getReq_param() == null : this.getReq_param().equals(other.getReq_param()))
            && (this.getReq_result() == null ? other.getReq_result() == null : this.getReq_result().equals(other.getReq_result()));
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
        result = prime * result + ((getReq_param() == null) ? 0 : getReq_param().hashCode());
        result = prime * result + ((getReq_result() == null) ? 0 : getReq_result().hashCode());
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
        sb.append(", req_param=").append(req_param);
        sb.append(", req_result=").append(req_result);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}