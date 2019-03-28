package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ProdRefundReqDataKey implements Serializable {
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
    private String mall_no;

    /**
     * prod_refund_req_data
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
     * @return mall_no 
     */
    public String getMall_no() {
        return mall_no;
    }

    /**
     * 
     * @param mall_no 
     */
    public void setMall_no(String mall_no) {
        this.mall_no = mall_no == null ? null : mall_no.trim();
    }

    /**
     *
     * @mbggenerated 2018-07-23
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
        ProdRefundReqDataKey other = (ProdRefundReqDataKey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()));
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", mall_no=").append(mall_no);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}