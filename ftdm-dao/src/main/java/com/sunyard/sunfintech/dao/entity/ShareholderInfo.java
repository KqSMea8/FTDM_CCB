package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShareholderInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String shareholder_name;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String job;

    /**
     * 
     */
    private BigDecimal proportion;

    /**
     * 
     */
    private String id_type;

    /**
     * 
     */
    private String id_code;

    /**
     * 
     */
    private String address;

    /**
     * shareholder_info
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
     * @return shareholder_name 
     */
    public String getShareholder_name() {
        return shareholder_name;
    }

    /**
     * 
     * @param shareholder_name 
     */
    public void setShareholder_name(String shareholder_name) {
        this.shareholder_name = shareholder_name == null ? null : shareholder_name.trim();
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
     * @return job 
     */
    public String getJob() {
        return job;
    }

    /**
     * 
     * @param job 
     */
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    /**
     * 
     * @return proportion 
     */
    public BigDecimal getProportion() {
        return proportion;
    }

    /**
     * 
     * @param proportion 
     */
    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    /**
     * 
     * @return id_type 
     */
    public String getId_type() {
        return id_type;
    }

    /**
     * 
     * @param id_type 
     */
    public void setId_type(String id_type) {
        this.id_type = id_type == null ? null : id_type.trim();
    }

    /**
     * 
     * @return id_code 
     */
    public String getId_code() {
        return id_code;
    }

    /**
     * 
     * @param id_code 
     */
    public void setId_code(String id_code) {
        this.id_code = id_code == null ? null : id_code.trim();
    }

    /**
     * 
     * @return address 
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
        ShareholderInfo other = (ShareholderInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getShareholder_name() == null ? other.getShareholder_name() == null : this.getShareholder_name().equals(other.getShareholder_name()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getJob() == null ? other.getJob() == null : this.getJob().equals(other.getJob()))
            && (this.getProportion() == null ? other.getProportion() == null : this.getProportion().equals(other.getProportion()))
            && (this.getId_type() == null ? other.getId_type() == null : this.getId_type().equals(other.getId_type()))
            && (this.getId_code() == null ? other.getId_code() == null : this.getId_code().equals(other.getId_code()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()));
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
        result = prime * result + ((getShareholder_name() == null) ? 0 : getShareholder_name().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
        result = prime * result + ((getProportion() == null) ? 0 : getProportion().hashCode());
        result = prime * result + ((getId_type() == null) ? 0 : getId_type().hashCode());
        result = prime * result + ((getId_code() == null) ? 0 : getId_code().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
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
        sb.append(", shareholder_name=").append(shareholder_name);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", job=").append(job);
        sb.append(", proportion=").append(proportion);
        sb.append(", id_type=").append(id_type);
        sb.append(", id_code=").append(id_code);
        sb.append(", address=").append(address);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}