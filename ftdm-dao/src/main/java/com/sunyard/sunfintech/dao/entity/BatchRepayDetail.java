package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class BatchRepayDetail implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String trans_serial;

    /**
     * 
     */
    private BigDecimal occur_balance;

    /**
     * 
     */
    private String summary;

    /**
     * 
     */
    private String card_no;

    /**
     * 
     */
    private String status;

    /**
     * batch_repay_detail
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
     * @return trans_serial 
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 
     * @param trans_serial 
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
    }

    /**
     * 
     * @return occur_balance 
     */
    public BigDecimal getOccur_balance() {
        return occur_balance;
    }

    /**
     * 
     * @param occur_balance 
     */
    public void setOccur_balance(BigDecimal occur_balance) {
        this.occur_balance = occur_balance;
    }

    /**
     * 
     * @return summary 
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary 
     */
    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    /**
     * 
     * @return card_no 
     */
    public String getCard_no() {
        return card_no;
    }

    /**
     * 
     * @param card_no 
     */
    public void setCard_no(String card_no) {
        this.card_no = card_no == null ? null : card_no.trim();
    }

    /**
     * 
     * @return status 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     *
     * @mbggenerated 2018-09-20
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
        BatchRepayDetail other = (BatchRepayDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getOccur_balance() == null ? other.getOccur_balance() == null : this.getOccur_balance().equals(other.getOccur_balance()))
            && (this.getSummary() == null ? other.getSummary() == null : this.getSummary().equals(other.getSummary()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getOccur_balance() == null) ? 0 : getOccur_balance().hashCode());
        result = prime * result + ((getSummary() == null) ? 0 : getSummary().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", occur_balance=").append(occur_balance);
        sb.append(", summary=").append(summary);
        sb.append(", card_no=").append(card_no);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}