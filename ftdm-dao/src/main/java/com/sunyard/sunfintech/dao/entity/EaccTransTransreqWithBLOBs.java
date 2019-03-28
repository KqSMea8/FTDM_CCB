package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class EaccTransTransreqWithBLOBs extends EaccTransTransreq implements Serializable {
    /**
     * 
     */
    private String res_params;

    /**
     * 
     */
    private String req_params;

    /**
     * eacc_trans_transreq
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return res_params 
     */
    public String getRes_params() {
        return res_params;
    }

    /**
     * 
     * @param res_params 
     */
    public void setRes_params(String res_params) {
        this.res_params = res_params == null ? null : res_params.trim();
    }

    /**
     * 
     * @return req_params 
     */
    public String getReq_params() {
        return req_params;
    }

    /**
     * 
     * @param req_params 
     */
    public void setReq_params(String req_params) {
        this.req_params = req_params == null ? null : req_params.trim();
    }

    /**
     *
     * @mbggenerated 2018-02-04
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
        EaccTransTransreqWithBLOBs other = (EaccTransTransreqWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getParent_trans_serial() == null ? other.getParent_trans_serial() == null : this.getParent_trans_serial().equals(other.getParent_trans_serial()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getEaccount() == null ? other.getEaccount() == null : this.getEaccount().equals(other.getEaccount()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getProperty() == null ? other.getProperty() == null : this.getProperty().equals(other.getProperty()))
            && (this.getOppo_eaccount() == null ? other.getOppo_eaccount() == null : this.getOppo_eaccount().equals(other.getOppo_eaccount()))
            && (this.getOppo_name() == null ? other.getOppo_name() == null : this.getOppo_name().equals(other.getOppo_name()))
            && (this.getOppo_property() == null ? other.getOppo_property() == null : this.getOppo_property().equals(other.getOppo_property()))
            && (this.getTrans_amt() == null ? other.getTrans_amt() == null : this.getTrans_amt().equals(other.getTrans_amt()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()))
            && (this.getRes_params() == null ? other.getRes_params() == null : this.getRes_params().equals(other.getRes_params()))
            && (this.getReq_params() == null ? other.getReq_params() == null : this.getReq_params().equals(other.getReq_params()));
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getParent_trans_serial() == null) ? 0 : getParent_trans_serial().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getEaccount() == null) ? 0 : getEaccount().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getProperty() == null) ? 0 : getProperty().hashCode());
        result = prime * result + ((getOppo_eaccount() == null) ? 0 : getOppo_eaccount().hashCode());
        result = prime * result + ((getOppo_name() == null) ? 0 : getOppo_name().hashCode());
        result = prime * result + ((getOppo_property() == null) ? 0 : getOppo_property().hashCode());
        result = prime * result + ((getTrans_amt() == null) ? 0 : getTrans_amt().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        result = prime * result + ((getRes_params() == null) ? 0 : getRes_params().hashCode());
        result = prime * result + ((getReq_params() == null) ? 0 : getReq_params().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", res_params=").append(res_params);
        sb.append(", req_params=").append(req_params);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}