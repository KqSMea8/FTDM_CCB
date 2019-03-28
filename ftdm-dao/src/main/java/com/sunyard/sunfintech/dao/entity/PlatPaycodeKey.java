package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class PlatPaycodeKey implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String pay_code;

    /**
     * plat_paycode
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
     * @return pay_code 
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 
     * @param pay_code 
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
    }

    /**
     *
     * @mbggenerated 2018-07-02
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
        PlatPaycodeKey other = (PlatPaycodeKey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()));
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}