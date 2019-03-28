package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProdDividendRule implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String prod_id;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String payout_plat_no;

    /**
     * 
     */
    private String payout_plat_account;

    /**
     * 
     */
    private BigDecimal payout_ratio;

    /**
     * 
     */
    private BigDecimal payout_amt;

    /**
     * prod_dividend_rule
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
     * @return prod_id 
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * 
     * @param prod_id 
     */
    public void setProd_id(String prod_id) {
        this.prod_id = prod_id == null ? null : prod_id.trim();
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
     * @return payout_plat_no 
     */
    public String getPayout_plat_no() {
        return payout_plat_no;
    }

    /**
     * 
     * @param payout_plat_no 
     */
    public void setPayout_plat_no(String payout_plat_no) {
        this.payout_plat_no = payout_plat_no == null ? null : payout_plat_no.trim();
    }

    /**
     * 
     * @return payout_plat_account 
     */
    public String getPayout_plat_account() {
        return payout_plat_account;
    }

    /**
     * 
     * @param payout_plat_account 
     */
    public void setPayout_plat_account(String payout_plat_account) {
        this.payout_plat_account = payout_plat_account == null ? null : payout_plat_account.trim();
    }

    /**
     * 
     * @return payout_ratio 
     */
    public BigDecimal getPayout_ratio() {
        return payout_ratio;
    }

    /**
     * 
     * @param payout_ratio 
     */
    public void setPayout_ratio(BigDecimal payout_ratio) {
        this.payout_ratio = payout_ratio;
    }

    /**
     * 
     * @return payout_amt 
     */
    public BigDecimal getPayout_amt() {
        return payout_amt;
    }

    /**
     * 
     * @param payout_amt 
     */
    public void setPayout_amt(BigDecimal payout_amt) {
        this.payout_amt = payout_amt;
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
        ProdDividendRule other = (ProdDividendRule) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPayout_plat_no() == null ? other.getPayout_plat_no() == null : this.getPayout_plat_no().equals(other.getPayout_plat_no()))
            && (this.getPayout_plat_account() == null ? other.getPayout_plat_account() == null : this.getPayout_plat_account().equals(other.getPayout_plat_account()))
            && (this.getPayout_ratio() == null ? other.getPayout_ratio() == null : this.getPayout_ratio().equals(other.getPayout_ratio()))
            && (this.getPayout_amt() == null ? other.getPayout_amt() == null : this.getPayout_amt().equals(other.getPayout_amt()));
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
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPayout_plat_no() == null) ? 0 : getPayout_plat_no().hashCode());
        result = prime * result + ((getPayout_plat_account() == null) ? 0 : getPayout_plat_account().hashCode());
        result = prime * result + ((getPayout_ratio() == null) ? 0 : getPayout_ratio().hashCode());
        result = prime * result + ((getPayout_amt() == null) ? 0 : getPayout_amt().hashCode());
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
        sb.append(", prod_id=").append(prod_id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", payout_plat_no=").append(payout_plat_no);
        sb.append(", payout_plat_account=").append(payout_plat_account);
        sb.append(", payout_ratio=").append(payout_ratio);
        sb.append(", payout_amt=").append(payout_amt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}