package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ClearTransSerialError implements Serializable {
    /**
     * C-充值、T-提现
     */
    private Integer id;

    /**
     * 对账时间
     */
    private String clear_date;

    /**
     * 差错流水号
     */
    private String trans_serial;

    /**
     * 差错流水号
     */
    private String order_no;

    /**
     * 类型(C-充值T-提现)
     */
    private String type;

    /**
     * 状态(0-未处理1-已处理)
     */
    private String status;

    /**
     * 备注
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
     * clear_trans_serial_error
     */
    private static final long serialVersionUID = 1L;

    /**
     * C-充值、T-提现
     * @return id C-充值、T-提现
     */
    public Integer getId() {
        return id;
    }

    /**
     * C-充值、T-提现
     * @param id C-充值、T-提现
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 对账时间
     * @return clear_date 对账时间
     */
    public String getClear_date() {
        return clear_date;
    }

    /**
     * 对账时间
     * @param clear_date 对账时间
     */
    public void setClear_date(String clear_date) {
        this.clear_date = clear_date == null ? null : clear_date.trim();
    }

    /**
     * 差错流水号
     * @return trans_serial 差错流水号
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 差错流水号
     * @param trans_serial 差错流水号
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
    }

    /**
     * 差错流水号
     * @return order_no 差错流水号
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 差错流水号
     * @param order_no 差错流水号
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 类型(C-充值T-提现)
     * @return type 类型(C-充值T-提现)
     */
    public String getType() {
        return type;
    }

    /**
     * 类型(C-充值T-提现)
     * @param type 类型(C-充值T-提现)
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 状态(0-未处理1-已处理)
     * @return status 状态(0-未处理1-已处理)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态(0-未处理1-已处理)
     * @param status 状态(0-未处理1-已处理)
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
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
     * @mbggenerated 2018-01-23
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
        ClearTransSerialError other = (ClearTransSerialError) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clear_date=").append(clear_date);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", order_no=").append(order_no);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
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