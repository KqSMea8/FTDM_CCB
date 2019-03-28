package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EaccTransTransreq implements Serializable {
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
    private String parent_trans_serial;

    /**
     * 
     */
    private String trans_serial;

    /**
     * 
     */
    private String eaccount;

    /**
     * 
     */
    private String name;

    /**
     * 1：个人账户 2：对公账户
     */
    private Integer property;

    /**
     * 
     */
    private String oppo_eaccount;

    /**
     * 
     */
    private String oppo_name;

    /**
     * 1：个人账户 2：对公账户
     */
    private Integer oppo_property;

    /**
     * 
     */
    private BigDecimal trans_amt;

    /**
     * 0：处理中 1：处理成功 2：处理失败
     */
    private Integer status;

    /**
     * 
     */
    private Integer enabled;

    /**
     * 
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
     * eacc_trans_transreq
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
     * @return parent_trans_serial 
     */
    public String getParent_trans_serial() {
        return parent_trans_serial;
    }

    /**
     * 
     * @param parent_trans_serial 
     */
    public void setParent_trans_serial(String parent_trans_serial) {
        this.parent_trans_serial = parent_trans_serial == null ? null : parent_trans_serial.trim();
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
     * @return eaccount 
     */
    public String getEaccount() {
        return eaccount;
    }

    /**
     * 
     * @param eaccount 
     */
    public void setEaccount(String eaccount) {
        this.eaccount = eaccount == null ? null : eaccount.trim();
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
     * 1：个人账户 2：对公账户
     * @return property 1：个人账户 2：对公账户
     */
    public Integer getProperty() {
        return property;
    }

    /**
     * 1：个人账户 2：对公账户
     * @param property 1：个人账户 2：对公账户
     */
    public void setProperty(Integer property) {
        this.property = property;
    }

    /**
     * 
     * @return oppo_eaccount 
     */
    public String getOppo_eaccount() {
        return oppo_eaccount;
    }

    /**
     * 
     * @param oppo_eaccount 
     */
    public void setOppo_eaccount(String oppo_eaccount) {
        this.oppo_eaccount = oppo_eaccount == null ? null : oppo_eaccount.trim();
    }

    /**
     * 
     * @return oppo_name 
     */
    public String getOppo_name() {
        return oppo_name;
    }

    /**
     * 
     * @param oppo_name 
     */
    public void setOppo_name(String oppo_name) {
        this.oppo_name = oppo_name == null ? null : oppo_name.trim();
    }

    /**
     * 1：个人账户 2：对公账户
     * @return oppo_property 1：个人账户 2：对公账户
     */
    public Integer getOppo_property() {
        return oppo_property;
    }

    /**
     * 1：个人账户 2：对公账户
     * @param oppo_property 1：个人账户 2：对公账户
     */
    public void setOppo_property(Integer oppo_property) {
        this.oppo_property = oppo_property;
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
     * 0：处理中 1：处理成功 2：处理失败
     * @return status 0：处理中 1：处理成功 2：处理失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0：处理中 1：处理成功 2：处理失败
     * @param status 0：处理中 1：处理成功 2：处理失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * @return enabled 
     */
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled 
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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
     * @mbggenerated 2018-02-04
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
        EaccTransTransreq other = (EaccTransTransreq) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getParent_trans_serial() == null ? other.getParent_trans_serial() == null : this.getParent_trans_serial().equals(other.getParent_trans_serial()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getEaccount() == null ? other.getEaccount() == null : this.getEaccount().equals(other.getEaccount()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getProperty() == null ? other.getProperty() == null : this.getProperty().equals(other.getProperty()))
            && (this.getOppo_eaccount() == null ? other.getOppo_eaccount() == null : this.getOppo_eaccount().equals(other.getOppo_eaccount()))
            && (this.getOppo_name() == null ? other.getOppo_name() == null : this.getOppo_name().equals(other.getOppo_name()))
            && (this.getOppo_property() == null ? other.getOppo_property() == null : this.getOppo_property().equals(other.getOppo_property()))
            && (this.getTrans_amt() == null ? other.getTrans_amt() == null : this.getTrans_amt().equals(other.getTrans_amt()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()));
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getParent_trans_serial() == null) ? 0 : getParent_trans_serial().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getEaccount() == null) ? 0 : getEaccount().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getProperty() == null) ? 0 : getProperty().hashCode());
        result = prime * result + ((getOppo_eaccount() == null) ? 0 : getOppo_eaccount().hashCode());
        result = prime * result + ((getOppo_name() == null) ? 0 : getOppo_name().hashCode());
        result = prime * result + ((getOppo_property() == null) ? 0 : getOppo_property().hashCode());
        result = prime * result + ((getTrans_amt() == null) ? 0 : getTrans_amt().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mall_no=").append(mall_no);
        sb.append(", parent_trans_serial=").append(parent_trans_serial);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", eaccount=").append(eaccount);
        sb.append(", name=").append(name);
        sb.append(", property=").append(property);
        sb.append(", oppo_eaccount=").append(oppo_eaccount);
        sb.append(", oppo_name=").append(oppo_name);
        sb.append(", oppo_property=").append(oppo_property);
        sb.append(", trans_amt=").append(trans_amt);
        sb.append(", status=").append(status);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_time=").append(create_time);
        sb.append(", create_by=").append(create_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", ext3=").append(ext3);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}