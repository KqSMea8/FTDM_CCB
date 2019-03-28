package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdTransfer extends ProdTransferKey implements Serializable {
    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String eacc_no;

    /**
     * 
     */
    private String prod_id;

    /**
     * 
     */
    private Date publish_date;

    /**
     * 
     */
    private BigDecimal transfer_vol;

    /**
     * 
     */
    private BigDecimal transfer_amt;

    /**
     * 
     */
    private BigDecimal transfer_fee;

    /**
     * 
     */
    private String deal_eacc_no;

    /**
     * 
     */
    private Date deal_time;

    /**
     * prod_transfer
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
     * @return eacc_no 
     */
    public String getEacc_no() {
        return eacc_no;
    }

    /**
     * 
     * @param eacc_no 
     */
    public void setEacc_no(String eacc_no) {
        this.eacc_no = eacc_no == null ? null : eacc_no.trim();
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
     * @return publish_date 
     */
    public Date getPublish_date() {
        return publish_date;
    }

    /**
     * 
     * @param publish_date 
     */
    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    /**
     * 
     * @return transfer_vol 
     */
    public BigDecimal getTransfer_vol() {
        return transfer_vol;
    }

    /**
     * 
     * @param transfer_vol 
     */
    public void setTransfer_vol(BigDecimal transfer_vol) {
        this.transfer_vol = transfer_vol;
    }

    /**
     * 
     * @return transfer_amt 
     */
    public BigDecimal getTransfer_amt() {
        return transfer_amt;
    }

    /**
     * 
     * @param transfer_amt 
     */
    public void setTransfer_amt(BigDecimal transfer_amt) {
        this.transfer_amt = transfer_amt;
    }

    /**
     * 
     * @return transfer_fee 
     */
    public BigDecimal getTransfer_fee() {
        return transfer_fee;
    }

    /**
     * 
     * @param transfer_fee 
     */
    public void setTransfer_fee(BigDecimal transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    /**
     * 
     * @return deal_eacc_no 
     */
    public String getDeal_eacc_no() {
        return deal_eacc_no;
    }

    /**
     * 
     * @param deal_eacc_no 
     */
    public void setDeal_eacc_no(String deal_eacc_no) {
        this.deal_eacc_no = deal_eacc_no == null ? null : deal_eacc_no.trim();
    }

    /**
     * 
     * @return deal_time 
     */
    public Date getDeal_time() {
        return deal_time;
    }

    /**
     * 
     * @param deal_time 
     */
    public void setDeal_time(Date deal_time) {
        this.deal_time = deal_time;
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
        ProdTransfer other = (ProdTransfer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTransfer_id() == null ? other.getTransfer_id() == null : this.getTransfer_id().equals(other.getTransfer_id()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getEacc_no() == null ? other.getEacc_no() == null : this.getEacc_no().equals(other.getEacc_no()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPublish_date() == null ? other.getPublish_date() == null : this.getPublish_date().equals(other.getPublish_date()))
            && (this.getTransfer_vol() == null ? other.getTransfer_vol() == null : this.getTransfer_vol().equals(other.getTransfer_vol()))
            && (this.getTransfer_amt() == null ? other.getTransfer_amt() == null : this.getTransfer_amt().equals(other.getTransfer_amt()))
            && (this.getTransfer_fee() == null ? other.getTransfer_fee() == null : this.getTransfer_fee().equals(other.getTransfer_fee()))
            && (this.getDeal_eacc_no() == null ? other.getDeal_eacc_no() == null : this.getDeal_eacc_no().equals(other.getDeal_eacc_no()))
            && (this.getDeal_time() == null ? other.getDeal_time() == null : this.getDeal_time().equals(other.getDeal_time()));
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
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getEacc_no() == null) ? 0 : getEacc_no().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPublish_date() == null) ? 0 : getPublish_date().hashCode());
        result = prime * result + ((getTransfer_vol() == null) ? 0 : getTransfer_vol().hashCode());
        result = prime * result + ((getTransfer_amt() == null) ? 0 : getTransfer_amt().hashCode());
        result = prime * result + ((getTransfer_fee() == null) ? 0 : getTransfer_fee().hashCode());
        result = prime * result + ((getDeal_eacc_no() == null) ? 0 : getDeal_eacc_no().hashCode());
        result = prime * result + ((getDeal_time() == null) ? 0 : getDeal_time().hashCode());
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
        sb.append(", plat_no=").append(plat_no);
        sb.append(", eacc_no=").append(eacc_no);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", publish_date=").append(publish_date);
        sb.append(", transfer_vol=").append(transfer_vol);
        sb.append(", transfer_amt=").append(transfer_amt);
        sb.append(", transfer_fee=").append(transfer_fee);
        sb.append(", deal_eacc_no=").append(deal_eacc_no);
        sb.append(", deal_time=").append(deal_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}