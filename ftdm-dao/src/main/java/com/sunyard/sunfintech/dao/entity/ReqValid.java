package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ReqValid implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 认证类型（0-实名认证，1-银行卡四要素认证）
     */
    private String type;

    /**
     * 平台号
     */
    private String plat_no;

    /**
     * 请求时间
     */
    private Date request_time;

    /**
     * 身份证
     */
    private String pid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 银行卡号
     */
    private String card_no;

    /**
     * 银行卡预留手机号
     */
    private String mobile;

    /**
     * 认证结果（true,false）
     */
    private String result;

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
     * req_valid
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
     * 认证类型（0-实名认证，1-银行卡四要素认证）
     * @return type 认证类型（0-实名认证，1-银行卡四要素认证）
     */
    public String getType() {
        return type;
    }

    /**
     * 认证类型（0-实名认证，1-银行卡四要素认证）
     * @param type 认证类型（0-实名认证，1-银行卡四要素认证）
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 请求时间
     * @return request_time 请求时间
     */
    public Date getRequest_time() {
        return request_time;
    }

    /**
     * 请求时间
     * @param request_time 请求时间
     */
    public void setRequest_time(Date request_time) {
        this.request_time = request_time;
    }

    /**
     * 身份证
     * @return pid 身份证
     */
    public String getPid() {
        return pid;
    }

    /**
     * 身份证
     * @param pid 身份证
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 银行卡预留手机号
     * @return mobile 银行卡预留手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 银行卡预留手机号
     * @param mobile 银行卡预留手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 认证结果（true,false）
     * @return result 认证结果（true,false）
     */
    public String getResult() {
        return result;
    }

    /**
     * 认证结果（true,false）
     * @param result 认证结果（true,false）
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
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
        ReqValid other = (ReqValid) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getRequest_time() == null ? other.getRequest_time() == null : this.getRequest_time().equals(other.getRequest_time()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
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
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getRequest_time() == null) ? 0 : getRequest_time().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
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
        sb.append(", type=").append(type);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", request_time=").append(request_time);
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", card_no=").append(card_no);
        sb.append(", mobile=").append(mobile);
        sb.append(", result=").append(result);
        sb.append(", order_no=").append(order_no);
        sb.append(", remark=").append(remark);
        sb.append(", enabled=").append(enabled);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}