package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class EaccCardcert implements Serializable {
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
    private String platcust;

    /**
     *
     */
    private String order_no;

    /**
     *
     */
    private BigDecimal amt;

    /**
     *
     */
    private String acct_name;

    /**
     *
     */
    private String acct_no;

    /**
     *
     */
    private String open_branch;

    /**
     *
     */
    private String org_no;

    /**
     * 0-待认证1-已认证
     */
    private String status;

    /**
     * eacc_cardcert
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
     * @return platcust
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     *
     * @param platcust
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     *
     * @return order_no
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     *
     * @param order_no
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     *
     * @return amt
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     *
     * @param amt
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     *
     * @return acct_name
     */
    public String getAcct_name() {
        return acct_name;
    }

    /**
     *
     * @param acct_name
     */
    public void setAcct_name(String acct_name) {
        this.acct_name = acct_name == null ? null : acct_name.trim();
    }

    /**
     *
     * @return acct_no
     */
    public String getAcct_no() {
        return acct_no;
    }

    /**
     *
     * @param acct_no
     */
    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no == null ? null : acct_no.trim();
    }

    /**
     *
     * @return open_branch
     */
    public String getOpen_branch() {
        return open_branch;
    }

    /**
     *
     * @param open_branch
     */
    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch == null ? null : open_branch.trim();
    }

    /**
     *
     * @return org_no
     */
    public String getOrg_no() {
        return org_no;
    }

    /**
     *
     * @param org_no
     */
    public void setOrg_no(String org_no) {
        this.org_no = org_no == null ? null : org_no.trim();
    }

    /**
     * 0-待认证1-已认证
     * @return status 0-待认证1-已认证
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0-待认证1-已认证
     * @param status 0-待认证1-已认证
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     *
     * @mbggenerated 2017-11-21
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
        EaccCardcert other = (EaccCardcert) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
                && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
                && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
                && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()))
                && (this.getAcct_name() == null ? other.getAcct_name() == null : this.getAcct_name().equals(other.getAcct_name()))
                && (this.getAcct_no() == null ? other.getAcct_no() == null : this.getAcct_no().equals(other.getAcct_no()))
                && (this.getOpen_branch() == null ? other.getOpen_branch() == null : this.getOpen_branch().equals(other.getOpen_branch()))
                && (this.getOrg_no() == null ? other.getOrg_no() == null : this.getOrg_no().equals(other.getOrg_no()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        result = prime * result + ((getAcct_name() == null) ? 0 : getAcct_name().hashCode());
        result = prime * result + ((getAcct_no() == null) ? 0 : getAcct_no().hashCode());
        result = prime * result + ((getOpen_branch() == null) ? 0 : getOpen_branch().hashCode());
        result = prime * result + ((getOrg_no() == null) ? 0 : getOrg_no().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", order_no=").append(order_no);
        sb.append(", amt=").append(amt);
        sb.append(", acct_name=").append(acct_name);
        sb.append(", acct_no=").append(acct_no);
        sb.append(", open_branch=").append(open_branch);
        sb.append(", org_no=").append(org_no);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}