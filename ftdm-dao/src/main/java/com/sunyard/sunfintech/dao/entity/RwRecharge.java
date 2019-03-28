package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RwRecharge implements Serializable {
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
    private String order_no;

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
    private String trans_code;

    /**
     * 
     */
    private String trans_name;

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
    private BigDecimal trans_amt;

    /**
     * 
     */
    private String pay_code;

    /**
     * 
     */
    private BigDecimal fee_amt;

    /**
     * 
     */
    private String client_property;

    /**
     * 
     */
    private String host_req_serial_no;

    /**
     * 
     */
    private Date reques_time;

    /**
     * 
     */
    private Date confirm_time;

    /**
     * 
     */
    private Date last_time;

    /**
     * 
     */
    private String return_code;

    /**
     * 
     */
    private String return_msg;

    /**
     * 
     */
    private String notify_msg;

    /**
     * 
     */
    private String return_url;

    /**
     * 
     */
    private String notify_url;

    /**
     * 
     */
    private String status;

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
     * 
     */
    private String ext3;

    /**
     * 
     */
    private String ext4;

    /**
     * 
     */
    private String ext5;

    /**
     * 
     */
    private String charge_type;

    /**
     * 
     */
    private String hsepay_order_no;

    /**
     * 
     */
    private String self_bank_flag;

    /**
     * 银行卡号
     */
    private String card_no;

    /**
     * 银行卡号绑定手机号
     */
    private String mobile;

    /**
     * 三方账期 YYYYMMDD
     */
    private String payment_date;

    /**
     * rw_recharge
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
     * @return trans_name 
     */
    public String getTrans_name() {
        return trans_name;
    }

    /**
     * 
     * @param trans_name 
     */
    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name == null ? null : trans_name.trim();
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
     * @return trans_amt 
     */
    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    /**
     * 
     * @param trans_amt 
     */
    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
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
     * @return fee_amt 
     */
    public BigDecimal getFee_amt() {
        return fee_amt;
    }

    /**
     * 
     * @param fee_amt 
     */
    public void setFee_amt(BigDecimal fee_amt) {
        this.fee_amt = fee_amt;
    }

    /**
     * 
     * @return client_property 
     */
    public String getClient_property() {
        return client_property;
    }

    /**
     * 
     * @param client_property 
     */
    public void setClient_property(String client_property) {
        this.client_property = client_property == null ? null : client_property.trim();
    }

    /**
     * 
     * @return host_req_serial_no 
     */
    public String getHost_req_serial_no() {
        return host_req_serial_no;
    }

    /**
     * 
     * @param host_req_serial_no 
     */
    public void setHost_req_serial_no(String host_req_serial_no) {
        this.host_req_serial_no = host_req_serial_no == null ? null : host_req_serial_no.trim();
    }

    /**
     * 
     * @return reques_time 
     */
    public Date getReques_time() {
        return reques_time;
    }

    /**
     * 
     * @param reques_time 
     */
    public void setReques_time(Date reques_time) {
        this.reques_time = reques_time;
    }

    /**
     * 
     * @return confirm_time 
     */
    public Date getConfirm_time() {
        return confirm_time;
    }

    /**
     * 
     * @param confirm_time 
     */
    public void setConfirm_time(Date confirm_time) {
        this.confirm_time = confirm_time;
    }

    /**
     * 
     * @return last_time 
     */
    public Date getLast_time() {
        return last_time;
    }

    /**
     * 
     * @param last_time 
     */
    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    /**
     * 
     * @return return_code 
     */
    public String getReturn_code() {
        return return_code;
    }

    /**
     * 
     * @param return_code 
     */
    public void setReturn_code(String return_code) {
        this.return_code = return_code == null ? null : return_code.trim();
    }

    /**
     * 
     * @return return_msg 
     */
    public String getReturn_msg() {
        return return_msg;
    }

    /**
     * 
     * @param return_msg 
     */
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg == null ? null : return_msg.trim();
    }

    /**
     * 
     * @return notify_msg 
     */
    public String getNotify_msg() {
        return notify_msg;
    }

    /**
     * 
     * @param notify_msg 
     */
    public void setNotify_msg(String notify_msg) {
        this.notify_msg = notify_msg == null ? null : notify_msg.trim();
    }

    /**
     * 
     * @return return_url 
     */
    public String getReturn_url() {
        return return_url;
    }

    /**
     * 
     * @param return_url 
     */
    public void setReturn_url(String return_url) {
        this.return_url = return_url == null ? null : return_url.trim();
    }

    /**
     * 
     * @return notify_url 
     */
    public String getNotify_url() {
        return notify_url;
    }

    /**
     * 
     * @param notify_url 
     */
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url == null ? null : notify_url.trim();
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
     * @return ext3 
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * 
     * @param ext3 
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    /**
     * 
     * @return ext4 
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * 
     * @param ext4 
     */
    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    /**
     * 
     * @return ext5 
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * 
     * @param ext5 
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
    }

    /**
     * 
     * @return charge_type 
     */
    public String getCharge_type() {
        return charge_type;
    }

    /**
     * 
     * @param charge_type 
     */
    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type == null ? null : charge_type.trim();
    }

    /**
     * 
     * @return hsepay_order_no 
     */
    public String getHsepay_order_no() {
        return hsepay_order_no;
    }

    /**
     * 
     * @param hsepay_order_no 
     */
    public void setHsepay_order_no(String hsepay_order_no) {
        this.hsepay_order_no = hsepay_order_no == null ? null : hsepay_order_no.trim();
    }

    /**
     * 
     * @return self_bank_flag 
     */
    public String getSelf_bank_flag() {
        return self_bank_flag;
    }

    /**
     * 
     * @param self_bank_flag 
     */
    public void setSelf_bank_flag(String self_bank_flag) {
        this.self_bank_flag = self_bank_flag == null ? null : self_bank_flag.trim();
    }

    /**
     * 银行卡号
     * @return card_no 银行卡号
     */
    public String getCard_no() {
        return card_no;
    }

    /**
     * 银行卡号
     * @param card_no 银行卡号
     */
    public void setCard_no(String card_no) {
        this.card_no = card_no == null ? null : card_no.trim();
    }

    /**
     * 银行卡号绑定手机号
     * @return mobile 银行卡号绑定手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 银行卡号绑定手机号
     * @param mobile 银行卡号绑定手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 三方账期 YYYYMMDD
     * @return payment_date 三方账期 YYYYMMDD
     */
    public String getPayment_date() {
        return payment_date;
    }

    /**
     * 三方账期 YYYYMMDD
     * @param payment_date 三方账期 YYYYMMDD
     */
    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date == null ? null : payment_date.trim();
    }

    /**
     *
     * @mbggenerated 2018-01-23
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
        RwRecharge other = (RwRecharge) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()))
            && (this.getTrans_name() == null ? other.getTrans_name() == null : this.getTrans_name().equals(other.getTrans_name()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getTrans_amt() == null ? other.getTrans_amt() == null : this.getTrans_amt().equals(other.getTrans_amt()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getFee_amt() == null ? other.getFee_amt() == null : this.getFee_amt().equals(other.getFee_amt()))
            && (this.getClient_property() == null ? other.getClient_property() == null : this.getClient_property().equals(other.getClient_property()))
            && (this.getHost_req_serial_no() == null ? other.getHost_req_serial_no() == null : this.getHost_req_serial_no().equals(other.getHost_req_serial_no()))
            && (this.getReques_time() == null ? other.getReques_time() == null : this.getReques_time().equals(other.getReques_time()))
            && (this.getConfirm_time() == null ? other.getConfirm_time() == null : this.getConfirm_time().equals(other.getConfirm_time()))
            && (this.getLast_time() == null ? other.getLast_time() == null : this.getLast_time().equals(other.getLast_time()))
            && (this.getReturn_code() == null ? other.getReturn_code() == null : this.getReturn_code().equals(other.getReturn_code()))
            && (this.getReturn_msg() == null ? other.getReturn_msg() == null : this.getReturn_msg().equals(other.getReturn_msg()))
            && (this.getNotify_msg() == null ? other.getNotify_msg() == null : this.getNotify_msg().equals(other.getNotify_msg()))
            && (this.getReturn_url() == null ? other.getReturn_url() == null : this.getReturn_url().equals(other.getReturn_url()))
            && (this.getNotify_url() == null ? other.getNotify_url() == null : this.getNotify_url().equals(other.getNotify_url()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()))
            && (this.getExt4() == null ? other.getExt4() == null : this.getExt4().equals(other.getExt4()))
            && (this.getExt5() == null ? other.getExt5() == null : this.getExt5().equals(other.getExt5()))
            && (this.getCharge_type() == null ? other.getCharge_type() == null : this.getCharge_type().equals(other.getCharge_type()))
            && (this.getHsepay_order_no() == null ? other.getHsepay_order_no() == null : this.getHsepay_order_no().equals(other.getHsepay_order_no()))
            && (this.getSelf_bank_flag() == null ? other.getSelf_bank_flag() == null : this.getSelf_bank_flag().equals(other.getSelf_bank_flag()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getPayment_date() == null ? other.getPayment_date() == null : this.getPayment_date().equals(other.getPayment_date()));
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        result = prime * result + ((getTrans_name() == null) ? 0 : getTrans_name().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getTrans_amt() == null) ? 0 : getTrans_amt().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getFee_amt() == null) ? 0 : getFee_amt().hashCode());
        result = prime * result + ((getClient_property() == null) ? 0 : getClient_property().hashCode());
        result = prime * result + ((getHost_req_serial_no() == null) ? 0 : getHost_req_serial_no().hashCode());
        result = prime * result + ((getReques_time() == null) ? 0 : getReques_time().hashCode());
        result = prime * result + ((getConfirm_time() == null) ? 0 : getConfirm_time().hashCode());
        result = prime * result + ((getLast_time() == null) ? 0 : getLast_time().hashCode());
        result = prime * result + ((getReturn_code() == null) ? 0 : getReturn_code().hashCode());
        result = prime * result + ((getReturn_msg() == null) ? 0 : getReturn_msg().hashCode());
        result = prime * result + ((getNotify_msg() == null) ? 0 : getNotify_msg().hashCode());
        result = prime * result + ((getReturn_url() == null) ? 0 : getReturn_url().hashCode());
        result = prime * result + ((getNotify_url() == null) ? 0 : getNotify_url().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        result = prime * result + ((getExt4() == null) ? 0 : getExt4().hashCode());
        result = prime * result + ((getExt5() == null) ? 0 : getExt5().hashCode());
        result = prime * result + ((getCharge_type() == null) ? 0 : getCharge_type().hashCode());
        result = prime * result + ((getHsepay_order_no() == null) ? 0 : getHsepay_order_no().hashCode());
        result = prime * result + ((getSelf_bank_flag() == null) ? 0 : getSelf_bank_flag().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getPayment_date() == null) ? 0 : getPayment_date().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", order_no=").append(order_no);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", trans_name=").append(trans_name);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", trans_amt=").append(trans_amt);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", fee_amt=").append(fee_amt);
        sb.append(", client_property=").append(client_property);
        sb.append(", host_req_serial_no=").append(host_req_serial_no);
        sb.append(", reques_time=").append(reques_time);
        sb.append(", confirm_time=").append(confirm_time);
        sb.append(", last_time=").append(last_time);
        sb.append(", return_code=").append(return_code);
        sb.append(", return_msg=").append(return_msg);
        sb.append(", notify_msg=").append(notify_msg);
        sb.append(", return_url=").append(return_url);
        sb.append(", notify_url=").append(notify_url);
        sb.append(", status=").append(status);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", ext3=").append(ext3);
        sb.append(", ext4=").append(ext4);
        sb.append(", ext5=").append(ext5);
        sb.append(", charge_type=").append(charge_type);
        sb.append(", hsepay_order_no=").append(hsepay_order_no);
        sb.append(", self_bank_flag=").append(self_bank_flag);
        sb.append(", card_no=").append(card_no);
        sb.append(", mobile=").append(mobile);
        sb.append(", payment_date=").append(payment_date);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}