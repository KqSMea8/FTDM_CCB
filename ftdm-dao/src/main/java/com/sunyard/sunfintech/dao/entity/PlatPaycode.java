package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class PlatPaycode extends PlatPaycodeKey implements Serializable {
    /**
     * 
     */
    private String mall_no;

    /**
     * 
     */
    private String pay_code_name;

    /**
     * 
     */
    private String payment_plat_no;

    /**
     * 
     */
    private String channel_id;

    /**
     * 
     */
    private String is_transaction;

    /**
     * 
     */
    private String ext_2;

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
     * 是否银行收单：1-是 0-否；默认0
     */
    private String is_bankcheck;

    /**
     * 受否申请短验分开，一般申请即发短信，还有一种申请和发短信分开，0-申请即发短信， 1-分开
     */
    private String is_msgcheck;

    /**
     * 0-不自动清算1-自动清算
     */
    private String auto_clear;

    /**
     * 清算入金帐号，只有入金的填
     */
    private String clear_card_no;

    /**
     * plat_paycode
     */
    private static final long serialVersionUID = 1L;

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
     * @return pay_code_name 
     */
    public String getPay_code_name() {
        return pay_code_name;
    }

    /**
     * 
     * @param pay_code_name 
     */
    public void setPay_code_name(String pay_code_name) {
        this.pay_code_name = pay_code_name == null ? null : pay_code_name.trim();
    }

    /**
     * 
     * @return payment_plat_no 
     */
    public String getPayment_plat_no() {
        return payment_plat_no;
    }

    /**
     * 
     * @param payment_plat_no 
     */
    public void setPayment_plat_no(String payment_plat_no) {
        this.payment_plat_no = payment_plat_no == null ? null : payment_plat_no.trim();
    }

    /**
     * 
     * @return channel_id 
     */
    public String getChannel_id() {
        return channel_id;
    }

    /**
     * 
     * @param channel_id 
     */
    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id == null ? null : channel_id.trim();
    }

    /**
     * 
     * @return is_transaction 
     */
    public String getIs_transaction() {
        return is_transaction;
    }

    /**
     * 
     * @param is_transaction 
     */
    public void setIs_transaction(String is_transaction) {
        this.is_transaction = is_transaction == null ? null : is_transaction.trim();
    }

    /**
     * 
     * @return ext_2 
     */
    public String getExt_2() {
        return ext_2;
    }

    /**
     * 
     * @param ext_2 
     */
    public void setExt_2(String ext_2) {
        this.ext_2 = ext_2 == null ? null : ext_2.trim();
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
     * 是否银行收单：1-是 0-否；默认0
     * @return is_bankcheck 是否银行收单：1-是 0-否；默认0
     */
    public String getIs_bankcheck() {
        return is_bankcheck;
    }

    /**
     * 是否银行收单：1-是 0-否；默认0
     * @param is_bankcheck 是否银行收单：1-是 0-否；默认0
     */
    public void setIs_bankcheck(String is_bankcheck) {
        this.is_bankcheck = is_bankcheck == null ? null : is_bankcheck.trim();
    }

    /**
     * 受否申请短验分开，一般申请即发短信，还有一种申请和发短信分开，0-申请即发短信， 1-分开
     * @return is_msgcheck 受否申请短验分开，一般申请即发短信，还有一种申请和发短信分开，0-申请即发短信， 1-分开
     */
    public String getIs_msgcheck() {
        return is_msgcheck;
    }

    /**
     * 受否申请短验分开，一般申请即发短信，还有一种申请和发短信分开，0-申请即发短信， 1-分开
     * @param is_msgcheck 受否申请短验分开，一般申请即发短信，还有一种申请和发短信分开，0-申请即发短信， 1-分开
     */
    public void setIs_msgcheck(String is_msgcheck) {
        this.is_msgcheck = is_msgcheck == null ? null : is_msgcheck.trim();
    }

    /**
     * 0-不自动清算1-自动清算
     * @return auto_clear 0-不自动清算1-自动清算
     */
    public String getAuto_clear() {
        return auto_clear;
    }

    /**
     * 0-不自动清算1-自动清算
     * @param auto_clear 0-不自动清算1-自动清算
     */
    public void setAuto_clear(String auto_clear) {
        this.auto_clear = auto_clear == null ? null : auto_clear.trim();
    }

    /**
     * 清算入金帐号，只有入金的填
     * @return clear_card_no 清算入金帐号，只有入金的填
     */
    public String getClear_card_no() {
        return clear_card_no;
    }

    /**
     * 清算入金帐号，只有入金的填
     * @param clear_card_no 清算入金帐号，只有入金的填
     */
    public void setClear_card_no(String clear_card_no) {
        this.clear_card_no = clear_card_no == null ? null : clear_card_no.trim();
    }

    /**
     *
     * @mbggenerated 2018-07-02
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
        PlatPaycode other = (PlatPaycode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getPay_code_name() == null ? other.getPay_code_name() == null : this.getPay_code_name().equals(other.getPay_code_name()))
            && (this.getPayment_plat_no() == null ? other.getPayment_plat_no() == null : this.getPayment_plat_no().equals(other.getPayment_plat_no()))
            && (this.getChannel_id() == null ? other.getChannel_id() == null : this.getChannel_id().equals(other.getChannel_id()))
            && (this.getIs_transaction() == null ? other.getIs_transaction() == null : this.getIs_transaction().equals(other.getIs_transaction()))
            && (this.getExt_2() == null ? other.getExt_2() == null : this.getExt_2().equals(other.getExt_2()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getIs_bankcheck() == null ? other.getIs_bankcheck() == null : this.getIs_bankcheck().equals(other.getIs_bankcheck()))
            && (this.getIs_msgcheck() == null ? other.getIs_msgcheck() == null : this.getIs_msgcheck().equals(other.getIs_msgcheck()))
            && (this.getAuto_clear() == null ? other.getAuto_clear() == null : this.getAuto_clear().equals(other.getAuto_clear()))
            && (this.getClear_card_no() == null ? other.getClear_card_no() == null : this.getClear_card_no().equals(other.getClear_card_no()));
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getPay_code_name() == null) ? 0 : getPay_code_name().hashCode());
        result = prime * result + ((getPayment_plat_no() == null) ? 0 : getPayment_plat_no().hashCode());
        result = prime * result + ((getChannel_id() == null) ? 0 : getChannel_id().hashCode());
        result = prime * result + ((getIs_transaction() == null) ? 0 : getIs_transaction().hashCode());
        result = prime * result + ((getExt_2() == null) ? 0 : getExt_2().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getIs_bankcheck() == null) ? 0 : getIs_bankcheck().hashCode());
        result = prime * result + ((getIs_msgcheck() == null) ? 0 : getIs_msgcheck().hashCode());
        result = prime * result + ((getAuto_clear() == null) ? 0 : getAuto_clear().hashCode());
        result = prime * result + ((getClear_card_no() == null) ? 0 : getClear_card_no().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mall_no=").append(mall_no);
        sb.append(", pay_code_name=").append(pay_code_name);
        sb.append(", payment_plat_no=").append(payment_plat_no);
        sb.append(", channel_id=").append(channel_id);
        sb.append(", is_transaction=").append(is_transaction);
        sb.append(", ext_2=").append(ext_2);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", is_bankcheck=").append(is_bankcheck);
        sb.append(", is_msgcheck=").append(is_msgcheck);
        sb.append(", auto_clear=").append(auto_clear);
        sb.append(", clear_card_no=").append(clear_card_no);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}