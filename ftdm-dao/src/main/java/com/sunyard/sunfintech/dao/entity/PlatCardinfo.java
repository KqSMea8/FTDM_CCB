package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PlatCardinfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String mall_no;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String card_type;

    /**
     * 
     */
    private String card_no;

    /**
     * 
     */
    private String card_name;

    /**
     * 
     */
    private String dedust_no;

    /**
     * 
     */
    private String pay_no;

    /**
     * 
     */
    private BigDecimal real_time_balance;

    /**
     * 
     */
    private BigDecimal today_amt;

    /**
     * 
     */
    private BigDecimal yesterday_amt;

    /**
     * 
     */
    private Date query_date;

    /**
     * 
     */
    private String enabled;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private String create_by;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private String update_by;

    /**
     * 
     */
    private Date update_time;

    /**
     * plat_cardinfo
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
     * @return card_type 
     */
    public String getCard_type() {
        return card_type;
    }

    /**
     * 
     * @param card_type 
     */
    public void setCard_type(String card_type) {
        this.card_type = card_type == null ? null : card_type.trim();
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
     * @return card_name 
     */
    public String getCard_name() {
        return card_name;
    }

    /**
     * 
     * @param card_name 
     */
    public void setCard_name(String card_name) {
        this.card_name = card_name == null ? null : card_name.trim();
    }

    /**
     * 
     * @return dedust_no 
     */
    public String getDedust_no() {
        return dedust_no;
    }

    /**
     * 
     * @param dedust_no 
     */
    public void setDedust_no(String dedust_no) {
        this.dedust_no = dedust_no == null ? null : dedust_no.trim();
    }

    /**
     * 
     * @return pay_no 
     */
    public String getPay_no() {
        return pay_no;
    }

    /**
     * 
     * @param pay_no 
     */
    public void setPay_no(String pay_no) {
        this.pay_no = pay_no == null ? null : pay_no.trim();
    }

    /**
     * 
     * @return real_time_balance 
     */
    public BigDecimal getReal_time_balance() {
        return real_time_balance;
    }

    /**
     * 
     * @param real_time_balance 
     */
    public void setReal_time_balance(BigDecimal real_time_balance) {
        this.real_time_balance = real_time_balance;
    }

    /**
     * 
     * @return today_amt 
     */
    public BigDecimal getToday_amt() {
        return today_amt;
    }

    /**
     * 
     * @param today_amt 
     */
    public void setToday_amt(BigDecimal today_amt) {
        this.today_amt = today_amt;
    }

    /**
     * 
     * @return yesterday_amt 
     */
    public BigDecimal getYesterday_amt() {
        return yesterday_amt;
    }

    /**
     * 
     * @param yesterday_amt 
     */
    public void setYesterday_amt(BigDecimal yesterday_amt) {
        this.yesterday_amt = yesterday_amt;
    }

    /**
     * 
     * @return query_date 
     */
    public Date getQuery_date() {
        return query_date;
    }

    /**
     * 
     * @param query_date 
     */
    public void setQuery_date(Date query_date) {
        this.query_date = query_date;
    }

    /**
     * 
     * @return enabled 
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled 
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    /**
     * 
     * @return remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 
     * @return create_by 
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 
     * @param create_by 
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_by 
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 
     * @param update_by 
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
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
        PlatCardinfo other = (PlatCardinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getCard_type() == null ? other.getCard_type() == null : this.getCard_type().equals(other.getCard_type()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getCard_name() == null ? other.getCard_name() == null : this.getCard_name().equals(other.getCard_name()))
            && (this.getDedust_no() == null ? other.getDedust_no() == null : this.getDedust_no().equals(other.getDedust_no()))
            && (this.getPay_no() == null ? other.getPay_no() == null : this.getPay_no().equals(other.getPay_no()))
            && (this.getReal_time_balance() == null ? other.getReal_time_balance() == null : this.getReal_time_balance().equals(other.getReal_time_balance()))
            && (this.getToday_amt() == null ? other.getToday_amt() == null : this.getToday_amt().equals(other.getToday_amt()))
            && (this.getYesterday_amt() == null ? other.getYesterday_amt() == null : this.getYesterday_amt().equals(other.getYesterday_amt()))
            && (this.getQuery_date() == null ? other.getQuery_date() == null : this.getQuery_date().equals(other.getQuery_date()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
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
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getCard_type() == null) ? 0 : getCard_type().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getCard_name() == null) ? 0 : getCard_name().hashCode());
        result = prime * result + ((getDedust_no() == null) ? 0 : getDedust_no().hashCode());
        result = prime * result + ((getPay_no() == null) ? 0 : getPay_no().hashCode());
        result = prime * result + ((getReal_time_balance() == null) ? 0 : getReal_time_balance().hashCode());
        result = prime * result + ((getToday_amt() == null) ? 0 : getToday_amt().hashCode());
        result = prime * result + ((getYesterday_amt() == null) ? 0 : getYesterday_amt().hashCode());
        result = prime * result + ((getQuery_date() == null) ? 0 : getQuery_date().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
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
        sb.append(", mall_no=").append(mall_no);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", card_type=").append(card_type);
        sb.append(", card_no=").append(card_no);
        sb.append(", card_name=").append(card_name);
        sb.append(", dedust_no=").append(dedust_no);
        sb.append(", pay_no=").append(pay_no);
        sb.append(", real_time_balance=").append(real_time_balance);
        sb.append(", today_amt=").append(today_amt);
        sb.append(", yesterday_amt=").append(yesterday_amt);
        sb.append(", query_date=").append(query_date);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}