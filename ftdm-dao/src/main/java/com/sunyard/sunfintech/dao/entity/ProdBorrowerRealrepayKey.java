package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ProdBorrowerRealrepayKey implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 融资编号
     */
    private String borrower_id;

    /**
     * prod_borrower_realrepay
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
     * 融资编号
     * @return borrower_id 融资编号
     */
    public String getBorrower_id() {
        return borrower_id;
    }

    /**
     * 融资编号
     * @param borrower_id 融资编号
     */
    public void setBorrower_id(String borrower_id) {
        this.borrower_id = borrower_id == null ? null : borrower_id.trim();
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
        ProdBorrowerRealrepayKey other = (ProdBorrowerRealrepayKey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBorrower_id() == null ? other.getBorrower_id() == null : this.getBorrower_id().equals(other.getBorrower_id()));
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
        result = prime * result + ((getBorrower_id() == null) ? 0 : getBorrower_id().hashCode());
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
        sb.append(", borrower_id=").append(borrower_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}