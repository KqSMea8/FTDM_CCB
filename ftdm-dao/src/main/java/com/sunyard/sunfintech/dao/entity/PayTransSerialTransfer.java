package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class PayTransSerialTransfer implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String pay_trans_serial;

    /**
     * 
     */
    private String trans_serial;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String enabled;

    /**
     * pay_trans_serial_transfer
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
     * @return pay_trans_serial 
     */
    public String getPay_trans_serial() {
        return pay_trans_serial;
    }

    /**
     * 
     * @param pay_trans_serial 
     */
    public void setPay_trans_serial(String pay_trans_serial) {
        this.pay_trans_serial = pay_trans_serial == null ? null : pay_trans_serial.trim();
    }

    /**
     * 
     * @return trans_serial 
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 
     * @param trans_serial 
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
    }

    /**
     * 
     * @return status 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
     * @mbggenerated 2018-07-18
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
        PayTransSerialTransfer other = (PayTransSerialTransfer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPay_trans_serial() == null ? other.getPay_trans_serial() == null : this.getPay_trans_serial().equals(other.getPay_trans_serial()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()));
    }

    /**
     *
     * @mbggenerated 2018-07-18
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPay_trans_serial() == null) ? 0 : getPay_trans_serial().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-07-18
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pay_trans_serial=").append(pay_trans_serial);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", status=").append(status);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}