package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ProdTransferKey implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String transfer_id;

    /**
     * prod_transfer
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
     * @return transfer_id 
     */
    public String getTransfer_id() {
        return transfer_id;
    }

    /**
     * 
     * @param transfer_id 
     */
    public void setTransfer_id(String transfer_id) {
        this.transfer_id = transfer_id == null ? null : transfer_id.trim();
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
        ProdTransferKey other = (ProdTransferKey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTransfer_id() == null ? other.getTransfer_id() == null : this.getTransfer_id().equals(other.getTransfer_id()));
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
        result = prime * result + ((getTransfer_id() == null) ? 0 : getTransfer_id().hashCode());
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
        sb.append(", transfer_id=").append(transfer_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}