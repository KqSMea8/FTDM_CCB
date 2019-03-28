package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TransChangeCard implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String order_no;

    /**
     * 
     */
    private String trans_serial;

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
    private String trans_date;

    /**
     * 
     */
    private String trans_time;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String card_no_old;

    /**
     * 
     */
    private String mobile_old;

    /**
     * 
     */
    private String card_no;

    /**
     * 
     */
    private String pre_mobile;

    /**
     * 
     */
    private String card_type;

    /**
     * 
     */
    private String pay_code;

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
     * 
     */
    private String ext1;

    /**
     * 
     */
    private String ext2;

    /**
     * trans_change_card
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
     * @return trans_date 
     */
    public String getTrans_date() {
        return trans_date;
    }

    /**
     * 
     * @param trans_date 
     */
    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date == null ? null : trans_date.trim();
    }

    /**
     * 
     * @return trans_time 
     */
    public String getTrans_time() {
        return trans_time;
    }

    /**
     * 
     * @param trans_time 
     */
    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time == null ? null : trans_time.trim();
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
     * @return name 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return card_no_old 
     */
    public String getCard_no_old() {
        return card_no_old;
    }

    /**
     * 
     * @param card_no_old 
     */
    public void setCard_no_old(String card_no_old) {
        this.card_no_old = card_no_old == null ? null : card_no_old.trim();
    }

    /**
     * 
     * @return mobile_old 
     */
    public String getMobile_old() {
        return mobile_old;
    }

    /**
     * 
     * @param mobile_old 
     */
    public void setMobile_old(String mobile_old) {
        this.mobile_old = mobile_old == null ? null : mobile_old.trim();
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
     * @return pre_mobile 
     */
    public String getPre_mobile() {
        return pre_mobile;
    }

    /**
     * 
     * @param pre_mobile 
     */
    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile == null ? null : pre_mobile.trim();
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
     * @return pay_code 
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 
     * @param pay_code 
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
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
     * @return ext1 
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 
     * @param ext1 
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 
     * @return ext2 
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 
     * @param ext2 
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     *
     * @mbggenerated 2017-09-26
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
        TransChangeCard other = (TransChangeCard) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCard_no_old() == null ? other.getCard_no_old() == null : this.getCard_no_old().equals(other.getCard_no_old()))
            && (this.getMobile_old() == null ? other.getMobile_old() == null : this.getMobile_old().equals(other.getMobile_old()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getPre_mobile() == null ? other.getPre_mobile() == null : this.getPre_mobile().equals(other.getPre_mobile()))
            && (this.getCard_type() == null ? other.getCard_type() == null : this.getCard_type().equals(other.getCard_type()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()));
    }

    /**
     *
     * @mbggenerated 2017-09-26
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCard_no_old() == null) ? 0 : getCard_no_old().hashCode());
        result = prime * result + ((getMobile_old() == null) ? 0 : getMobile_old().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getPre_mobile() == null) ? 0 : getPre_mobile().hashCode());
        result = prime * result + ((getCard_type() == null) ? 0 : getCard_type().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-09-26
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", order_no=").append(order_no);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", status=").append(status);
        sb.append(", name=").append(name);
        sb.append(", card_no_old=").append(card_no_old);
        sb.append(", mobile_old=").append(mobile_old);
        sb.append(", card_no=").append(card_no);
        sb.append(", pre_mobile=").append(pre_mobile);
        sb.append(", card_type=").append(card_type);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}