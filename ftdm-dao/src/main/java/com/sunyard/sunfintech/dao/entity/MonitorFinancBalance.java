package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class MonitorFinancBalance implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 监控日期
     */
    private Date monitor_date;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 标的id
     */
    private String prod_id;

    /**
     * 客户号
     */
    private String platcust;

    /**
     * 1-个人2-企业
     */
    private String cus_type;

    /**
     * 客户姓名
     */
    private String cust_name;

    /**
     * 成标时间
     */
    private String prod_date;

    /**
     * 单笔融资余额
     */
    private Long vol;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private String create_by;

    /**
     * 
     */
    private Date update_time;

    /**
     * 
     */
    private String update_by;

    /**
     * monitor_financ_balance
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
     * 监控日期
     * @return monitor_date 监控日期
     */
    public Date getMonitor_date() {
        return monitor_date;
    }

    /**
     * 监控日期
     * @param monitor_date 监控日期
     */
    public void setMonitor_date(Date monitor_date) {
        this.monitor_date = monitor_date;
    }

    /**
     * 平台编号
     * @return plat_no 平台编号
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 平台编号
     * @param plat_no 平台编号
     */
    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no == null ? null : plat_no.trim();
    }

    /**
     * 标的id
     * @return prod_id 标的id
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * 标的id
     * @param prod_id 标的id
     */
    public void setProd_id(String prod_id) {
        this.prod_id = prod_id == null ? null : prod_id.trim();
    }

    /**
     * 客户号
     * @return platcust 客户号
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 客户号
     * @param platcust 客户号
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 1-个人2-企业
     * @return cus_type 1-个人2-企业
     */
    public String getCus_type() {
        return cus_type;
    }

    /**
     * 1-个人2-企业
     * @param cus_type 1-个人2-企业
     */
    public void setCus_type(String cus_type) {
        this.cus_type = cus_type == null ? null : cus_type.trim();
    }

    /**
     * 客户姓名
     * @return cust_name 客户姓名
     */
    public String getCust_name() {
        return cust_name;
    }

    /**
     * 客户姓名
     * @param cust_name 客户姓名
     */
    public void setCust_name(String cust_name) {
        this.cust_name = cust_name == null ? null : cust_name.trim();
    }

    /**
     * 成标时间
     * @return prod_date 成标时间
     */
    public String getProd_date() {
        return prod_date;
    }

    /**
     * 成标时间
     * @param prod_date 成标时间
     */
    public void setProd_date(String prod_date) {
        this.prod_date = prod_date == null ? null : prod_date.trim();
    }

    /**
     * 单笔融资余额
     * @return vol 单笔融资余额
     */
    public Long getVol() {
        return vol;
    }

    /**
     * 单笔融资余额
     * @param vol 单笔融资余额
     */
    public void setVol(Long vol) {
        this.vol = vol;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
     * @mbggenerated 2018-02-03
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
        MonitorFinancBalance other = (MonitorFinancBalance) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMonitor_date() == null ? other.getMonitor_date() == null : this.getMonitor_date().equals(other.getMonitor_date()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getCus_type() == null ? other.getCus_type() == null : this.getCus_type().equals(other.getCus_type()))
            && (this.getCust_name() == null ? other.getCust_name() == null : this.getCust_name().equals(other.getCust_name()))
            && (this.getProd_date() == null ? other.getProd_date() == null : this.getProd_date().equals(other.getProd_date()))
            && (this.getVol() == null ? other.getVol() == null : this.getVol().equals(other.getVol()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()));
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMonitor_date() == null) ? 0 : getMonitor_date().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getCus_type() == null) ? 0 : getCus_type().hashCode());
        result = prime * result + ((getCust_name() == null) ? 0 : getCust_name().hashCode());
        result = prime * result + ((getProd_date() == null) ? 0 : getProd_date().hashCode());
        result = prime * result + ((getVol() == null) ? 0 : getVol().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", monitor_date=").append(monitor_date);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", platcust=").append(platcust);
        sb.append(", cus_type=").append(cus_type);
        sb.append(", cust_name=").append(cust_name);
        sb.append(", prod_date=").append(prod_date);
        sb.append(", vol=").append(vol);
        sb.append(", remark=").append(remark);
        sb.append(", create_time=").append(create_time);
        sb.append(", create_by=").append(create_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}