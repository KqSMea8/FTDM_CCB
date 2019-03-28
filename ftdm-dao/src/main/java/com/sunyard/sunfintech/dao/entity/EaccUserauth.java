package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EaccUserauth implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 集团编号
     */
    private String mall_no;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 客户号
     */
    private String platcust;

    /**
     * 授权类型（1、出借，2、还款，3、缴费，4、全部）
     */
    private String authed_type;

    /**
     * 授权金额
     */
    private BigDecimal authed_amount;

    /**
     * 授权期限 YYYYMMDD
     */
    private String authed_limittime;

    /**
     * 1已授权，2未授权
     */
    private String authed_status;

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
     * eacc_userauth
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
     * 集团编号
     * @return mall_no 集团编号
     */
    public String getMall_no() {
        return mall_no;
    }

    /**
     * 集团编号
     * @param mall_no 集团编号
     */
    public void setMall_no(String mall_no) {
        this.mall_no = mall_no == null ? null : mall_no.trim();
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
     * 授权类型（1、出借，2、还款，3、缴费，4、全部）
     * @return authed_type 授权类型（1、出借，2、还款，3、缴费，4、全部）
     */
    public String getAuthed_type() {
        return authed_type;
    }

    /**
     * 授权类型（1、出借，2、还款，3、缴费，4、全部）
     * @param authed_type 授权类型（1、出借，2、还款，3、缴费，4、全部）
     */
    public void setAuthed_type(String authed_type) {
        this.authed_type = authed_type == null ? null : authed_type.trim();
    }

    /**
     * 授权金额
     * @return authed_amount 授权金额
     */
    public BigDecimal getAuthed_amount() {
        return authed_amount;
    }

    /**
     * 授权金额
     * @param authed_amount 授权金额
     */
    public void setAuthed_amount(BigDecimal authed_amount) {
        this.authed_amount = authed_amount;
    }

    /**
     * 授权期限 YYYYMMDD
     * @return authed_limittime 授权期限 YYYYMMDD
     */
    public String getAuthed_limittime() {
        return authed_limittime;
    }

    /**
     * 授权期限 YYYYMMDD
     * @param authed_limittime 授权期限 YYYYMMDD
     */
    public void setAuthed_limittime(String authed_limittime) {
        this.authed_limittime = authed_limittime == null ? null : authed_limittime.trim();
    }

    /**
     * 1已授权，2未授权
     * @return authed_status 1已授权，2未授权
     */
    public String getAuthed_status() {
        return authed_status;
    }

    /**
     * 1已授权，2未授权
     * @param authed_status 1已授权，2未授权
     */
    public void setAuthed_status(String authed_status) {
        this.authed_status = authed_status == null ? null : authed_status.trim();
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
     * @mbggenerated 2018-01-10
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
        EaccUserauth other = (EaccUserauth) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getAuthed_type() == null ? other.getAuthed_type() == null : this.getAuthed_type().equals(other.getAuthed_type()))
            && (this.getAuthed_amount() == null ? other.getAuthed_amount() == null : this.getAuthed_amount().equals(other.getAuthed_amount()))
            && (this.getAuthed_limittime() == null ? other.getAuthed_limittime() == null : this.getAuthed_limittime().equals(other.getAuthed_limittime()))
            && (this.getAuthed_status() == null ? other.getAuthed_status() == null : this.getAuthed_status().equals(other.getAuthed_status()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2018-01-10
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getAuthed_type() == null) ? 0 : getAuthed_type().hashCode());
        result = prime * result + ((getAuthed_amount() == null) ? 0 : getAuthed_amount().hashCode());
        result = prime * result + ((getAuthed_limittime() == null) ? 0 : getAuthed_limittime().hashCode());
        result = prime * result + ((getAuthed_status() == null) ? 0 : getAuthed_status().hashCode());
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
     * @mbggenerated 2018-01-10
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
        sb.append(", platcust=").append(platcust);
        sb.append(", authed_type=").append(authed_type);
        sb.append(", authed_amount=").append(authed_amount);
        sb.append(", authed_limittime=").append(authed_limittime);
        sb.append(", authed_status=").append(authed_status);
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