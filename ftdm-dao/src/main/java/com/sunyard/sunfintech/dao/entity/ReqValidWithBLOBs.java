package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ReqValidWithBLOBs extends ReqValid implements Serializable {
    /**
     * 异常信息记录
     */
    private String exception_info;

    /**
     * 请求返回数据（不解析，直接记录）
     */
    private String return_data;

    /**
     * req_valid
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常信息记录
     * @return exception_info 异常信息记录
     */
    public String getException_info() {
        return exception_info;
    }

    /**
     * 异常信息记录
     * @param exception_info 异常信息记录
     */
    public void setException_info(String exception_info) {
        this.exception_info = exception_info == null ? null : exception_info.trim();
    }

    /**
     * 请求返回数据（不解析，直接记录）
     * @return return_data 请求返回数据（不解析，直接记录）
     */
    public String getReturn_data() {
        return return_data;
    }

    /**
     * 请求返回数据（不解析，直接记录）
     * @param return_data 请求返回数据（不解析，直接记录）
     */
    public void setReturn_data(String return_data) {
        this.return_data = return_data == null ? null : return_data.trim();
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
        ReqValidWithBLOBs other = (ReqValidWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getRequest_time() == null ? other.getRequest_time() == null : this.getRequest_time().equals(other.getRequest_time()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getException_info() == null ? other.getException_info() == null : this.getException_info().equals(other.getException_info()))
            && (this.getReturn_data() == null ? other.getReturn_data() == null : this.getReturn_data().equals(other.getReturn_data()));
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
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getRequest_time() == null) ? 0 : getRequest_time().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getException_info() == null) ? 0 : getException_info().hashCode());
        result = prime * result + ((getReturn_data() == null) ? 0 : getReturn_data().hashCode());
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
        sb.append(", exception_info=").append(exception_info);
        sb.append(", return_data=").append(return_data);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}