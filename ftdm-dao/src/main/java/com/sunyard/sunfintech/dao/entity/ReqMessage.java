package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ReqMessage implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 平台号
     */
    private String plat_no;

    /**
     * 发送通道号
     */
    private String channel_code;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 发送信息
     */
    private String send_info;

    /**
     * 短信类型
     */
    private String trans_code;

    /**
     * 
     */
    private String trans_name;

    /**
     * 发送时间
     */
    private Date send_time;

    /**
     * 订单号
     */
    private String order_no;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private String ext;

    /**
     * 
     */
    private String enabled;

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
     * 异常信息
     */
    private String exception_info;

    /**
     * req_message
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 平台号
     * @return plat_no 平台号
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 平台号
     * @param plat_no 平台号
     */
    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no == null ? null : plat_no.trim();
    }

    /**
     * 发送通道号
     * @return channel_code 发送通道号
     */
    public String getChannel_code() {
        return channel_code;
    }

    /**
     * 发送通道号
     * @param channel_code 发送通道号
     */
    public void setChannel_code(String channel_code) {
        this.channel_code = channel_code == null ? null : channel_code.trim();
    }

    /**
     * 手机号
     * @return mobile 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 发送信息
     * @return send_info 发送信息
     */
    public String getSend_info() {
        return send_info;
    }

    /**
     * 发送信息
     * @param send_info 发送信息
     */
    public void setSend_info(String send_info) {
        this.send_info = send_info == null ? null : send_info.trim();
    }

    /**
     * 短信类型
     * @return trans_code 短信类型
     */
    public String getTrans_code() {
        return trans_code;
    }

    /**
     * 短信类型
     * @param trans_code 短信类型
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
     * 发送时间
     * @return send_time 发送时间
     */
    public Date getSend_time() {
        return send_time;
    }

    /**
     * 发送时间
     * @param send_time 发送时间
     */
    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    /**
     * 订单号
     * @return order_no 订单号
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 订单号
     * @param order_no 订单号
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
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
     * @return ext 
     */
    public String getExt() {
        return ext;
    }

    /**
     * 
     * @param ext 
     */
    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
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
     * 异常信息
     * @return exception_info 异常信息
     */
    public String getException_info() {
        return exception_info;
    }

    /**
     * 异常信息
     * @param exception_info 异常信息
     */
    public void setException_info(String exception_info) {
        this.exception_info = exception_info == null ? null : exception_info.trim();
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
        ReqMessage other = (ReqMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getChannel_code() == null ? other.getChannel_code() == null : this.getChannel_code().equals(other.getChannel_code()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getSend_info() == null ? other.getSend_info() == null : this.getSend_info().equals(other.getSend_info()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()))
            && (this.getTrans_name() == null ? other.getTrans_name() == null : this.getTrans_name().equals(other.getTrans_name()))
            && (this.getSend_time() == null ? other.getSend_time() == null : this.getSend_time().equals(other.getSend_time()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getExt() == null ? other.getExt() == null : this.getExt().equals(other.getExt()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getException_info() == null ? other.getException_info() == null : this.getException_info().equals(other.getException_info()));
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
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getChannel_code() == null) ? 0 : getChannel_code().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getSend_info() == null) ? 0 : getSend_info().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        result = prime * result + ((getTrans_name() == null) ? 0 : getTrans_name().hashCode());
        result = prime * result + ((getSend_time() == null) ? 0 : getSend_time().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getExt() == null) ? 0 : getExt().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getException_info() == null) ? 0 : getException_info().hashCode());
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
        sb.append(", plat_no=").append(plat_no);
        sb.append(", channel_code=").append(channel_code);
        sb.append(", mobile=").append(mobile);
        sb.append(", send_info=").append(send_info);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", trans_name=").append(trans_name);
        sb.append(", send_time=").append(send_time);
        sb.append(", order_no=").append(order_no);
        sb.append(", remark=").append(remark);
        sb.append(", ext=").append(ext);
        sb.append(", enabled=").append(enabled);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", exception_info=").append(exception_info);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}