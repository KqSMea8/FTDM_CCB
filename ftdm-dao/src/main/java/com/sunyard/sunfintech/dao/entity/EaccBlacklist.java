package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class EaccBlacklist implements Serializable {
    /**
     * 
     */
    private Integer id;

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
    private String id_type;

    /**
     * 
     */
    private String id_code;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String sex;

    /**
     * 
     */
    private String mobile;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private String reason;

    /**
     * 
     */
    private Date modify_time;

    /**
     * 
     */
    private String out_flg;

    /**
     * eacc_blacklist
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
     * @return id_type 
     */
    public String getId_type() {
        return id_type;
    }

    /**
     * 
     * @param id_type 
     */
    public void setId_type(String id_type) {
        this.id_type = id_type == null ? null : id_type.trim();
    }

    /**
     * 
     * @return id_code 
     */
    public String getId_code() {
        return id_code;
    }

    /**
     * 
     * @param id_code 
     */
    public void setId_code(String id_code) {
        this.id_code = id_code == null ? null : id_code.trim();
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
     * @return sex 
     */
    public String getSex() {
        return sex;
    }

    /**
     * 
     * @param sex 
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 
     * @return mobile 
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 
     * @return address 
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 
     * @return reason 
     */
    public String getReason() {
        return reason;
    }

    /**
     * 
     * @param reason 
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 
     * @return modify_time 
     */
    public Date getModify_time() {
        return modify_time;
    }

    /**
     * 
     * @param modify_time 
     */
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    /**
     * 
     * @return out_flg 
     */
    public String getOut_flg() {
        return out_flg;
    }

    /**
     * 
     * @param out_flg 
     */
    public void setOut_flg(String out_flg) {
        this.out_flg = out_flg == null ? null : out_flg.trim();
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
        EaccBlacklist other = (EaccBlacklist) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getId_type() == null ? other.getId_type() == null : this.getId_type().equals(other.getId_type()))
            && (this.getId_code() == null ? other.getId_code() == null : this.getId_code().equals(other.getId_code()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getModify_time() == null ? other.getModify_time() == null : this.getModify_time().equals(other.getModify_time()))
            && (this.getOut_flg() == null ? other.getOut_flg() == null : this.getOut_flg().equals(other.getOut_flg()));
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
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getId_type() == null) ? 0 : getId_type().hashCode());
        result = prime * result + ((getId_code() == null) ? 0 : getId_code().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getModify_time() == null) ? 0 : getModify_time().hashCode());
        result = prime * result + ((getOut_flg() == null) ? 0 : getOut_flg().hashCode());
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
        sb.append(", platcust=").append(platcust);
        sb.append(", id_type=").append(id_type);
        sb.append(", id_code=").append(id_code);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", mobile=").append(mobile);
        sb.append(", address=").append(address);
        sb.append(", reason=").append(reason);
        sb.append(", modify_time=").append(modify_time);
        sb.append(", out_flg=").append(out_flg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}