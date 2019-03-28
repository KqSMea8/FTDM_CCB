package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ClearCheckinfo implements Serializable {
    /**
     * 
     */
    private Date clear_date;

    /**
     * 
     */
    private Date last_date;

    /**
     * 
     */
    private Date start_time;

    /**
     * 
     */
    private Date end_time;

    /**
     * 数据备份 总分对账 分账核对 生成对账文件 对账确认
     */
    private String clear_code;

    /**
     * 数据备份 总分对账 分账核对 生成对账文件 对账确认
     */
    private String clear_name;

    /**
     * '0-开始 1-结束 2-异常'; 
     */
    private String status;

    /**
     * clear_checkinfo
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
     * @return last_date 
     */
    public Date getLast_date() {
        return last_date;
    }

    /**
     * 
     * @param last_date 
     */
    public void setLast_date(Date last_date) {
        this.last_date = last_date;
    }

    /**
     * 
     * @return start_time 
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 
     * @param start_time 
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 
     * @return end_time 
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 
     * @param end_time 
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 数据备份 总分对账 分账核对 生成对账文件 对账确认
     * @return clear_code 数据备份 总分对账 分账核对 生成对账文件 对账确认
     */
    public String getClear_code() {
        return clear_code;
    }

    /**
     * 数据备份 总分对账 分账核对 生成对账文件 对账确认
     * @param clear_code 数据备份 总分对账 分账核对 生成对账文件 对账确认
     */
    public void setClear_code(String clear_code) {
        this.clear_code = clear_code == null ? null : clear_code.trim();
    }

    /**
     * 数据备份 总分对账 分账核对 生成对账文件 对账确认
     * @return clear_name 数据备份 总分对账 分账核对 生成对账文件 对账确认
     */
    public String getClear_name() {
        return clear_name;
    }

    /**
     * 数据备份 总分对账 分账核对 生成对账文件 对账确认
     * @param clear_name 数据备份 总分对账 分账核对 生成对账文件 对账确认
     */
    public void setClear_name(String clear_name) {
        this.clear_name = clear_name == null ? null : clear_name.trim();
    }

    /**
     * '0-开始 1-结束 2-异常'; 
     * @return status '0-开始 1-结束 2-异常'; 
     */
    public String getStatus() {
        return status;
    }

    /**
     * '0-开始 1-结束 2-异常'; 
     * @param status '0-开始 1-结束 2-异常'; 
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        ClearCheckinfo other = (ClearCheckinfo) that;
        return (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getLast_date() == null ? other.getLast_date() == null : this.getLast_date().equals(other.getLast_date()))
            && (this.getStart_time() == null ? other.getStart_time() == null : this.getStart_time().equals(other.getStart_time()))
            && (this.getEnd_time() == null ? other.getEnd_time() == null : this.getEnd_time().equals(other.getEnd_time()))
            && (this.getClear_code() == null ? other.getClear_code() == null : this.getClear_code().equals(other.getClear_code()))
            && (this.getClear_name() == null ? other.getClear_name() == null : this.getClear_name().equals(other.getClear_name()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
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
        result = prime * result + ((getLast_date() == null) ? 0 : getLast_date().hashCode());
        result = prime * result + ((getStart_time() == null) ? 0 : getStart_time().hashCode());
        result = prime * result + ((getEnd_time() == null) ? 0 : getEnd_time().hashCode());
        result = prime * result + ((getClear_code() == null) ? 0 : getClear_code().hashCode());
        result = prime * result + ((getClear_name() == null) ? 0 : getClear_name().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
        sb.append(", last_date=").append(last_date);
        sb.append(", start_time=").append(start_time);
        sb.append(", end_time=").append(end_time);
        sb.append(", clear_code=").append(clear_code);
        sb.append(", clear_name=").append(clear_name);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}