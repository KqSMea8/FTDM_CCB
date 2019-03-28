package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class MessageSwithesKey implements Serializable {
    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String trans_code;

    /**
     * message_swithes
     */
    private static final long serialVersionUID = 1L;

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
     * @return trans_code 
     */
    public String getTrans_code() {
        return trans_code;
    }

    /**
     * 
     * @param trans_code 
     */
    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code == null ? null : trans_code.trim();
    }

    /**
     *
     * @mbggenerated 2017-06-23
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
        MessageSwithesKey other = (MessageSwithesKey) that;
        return (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()));
    }

    /**
     *
     * @mbggenerated 2017-06-23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-06-23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", plat_no=").append(plat_no);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}