package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdShareInall implements Serializable {
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
    private String prod_id;

    /**
     * 平台用户编号
     */
    private String platcust;

    /**
     * 客户编号
     */
    private String cust_no;

    /**
     * 总额度
     */
    private BigDecimal tot_vol;

    /**
     * 冻结额度
     */
    private BigDecimal frozen_vol;

    /**
     * 持有额度
     */
    private BigDecimal vol;

    /**
     * 删除标记
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
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * prod_share_inall
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
     * @return prod_id 
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * 
     * @param prod_id 
     */
    public void setProd_id(String prod_id) {
        this.prod_id = prod_id == null ? null : prod_id.trim();
    }

    /**
     * 平台用户编号
     * @return platcust 平台用户编号
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 平台用户编号
     * @param platcust 平台用户编号
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 客户编号
     * @return cust_no 客户编号
     */
    public String getCust_no() {
        return cust_no;
    }

    /**
     * 客户编号
     * @param cust_no 客户编号
     */
    public void setCust_no(String cust_no) {
        this.cust_no = cust_no == null ? null : cust_no.trim();
    }

    /**
     * 总额度
     * @return tot_vol 总额度
     */
    public BigDecimal getTot_vol() {
        return tot_vol;
    }

    /**
     * 总额度
     * @param tot_vol 总额度
     */
    public void setTot_vol(BigDecimal tot_vol) {
        this.tot_vol = tot_vol;
    }

    /**
     * 冻结额度
     * @return frozen_vol 冻结额度
     */
    public BigDecimal getFrozen_vol() {
        return frozen_vol;
    }

    /**
     * 冻结额度
     * @param frozen_vol 冻结额度
     */
    public void setFrozen_vol(BigDecimal frozen_vol) {
        this.frozen_vol = frozen_vol;
    }

    /**
     * 持有额度
     * @return vol 持有额度
     */
    public BigDecimal getVol() {
        return vol;
    }

    /**
     * 持有额度
     * @param vol 持有额度
     */
    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

    /**
     * 删除标记
     * @return enabled 删除标记
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 删除标记
     * @param enabled 删除标记
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
     * 扩展字段1
     * @return ext1 扩展字段1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 扩展字段1
     * @param ext1 扩展字段1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 扩展字段2
     * @return ext2 扩展字段2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 扩展字段2
     * @param ext2 扩展字段2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
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
        ProdShareInall other = (ProdShareInall) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getCust_no() == null ? other.getCust_no() == null : this.getCust_no().equals(other.getCust_no()))
            && (this.getTot_vol() == null ? other.getTot_vol() == null : this.getTot_vol().equals(other.getTot_vol()))
            && (this.getFrozen_vol() == null ? other.getFrozen_vol() == null : this.getFrozen_vol().equals(other.getFrozen_vol()))
            && (this.getVol() == null ? other.getVol() == null : this.getVol().equals(other.getVol()))
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
     * @mbggenerated 2017-06-01
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getCust_no() == null) ? 0 : getCust_no().hashCode());
        result = prime * result + ((getTot_vol() == null) ? 0 : getTot_vol().hashCode());
        result = prime * result + ((getFrozen_vol() == null) ? 0 : getFrozen_vol().hashCode());
        result = prime * result + ((getVol() == null) ? 0 : getVol().hashCode());
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
        sb.append(", prod_id=").append(prod_id);
        sb.append(", platcust=").append(platcust);
        sb.append(", cust_no=").append(cust_no);
        sb.append(", tot_vol=").append(tot_vol);
        sb.append(", frozen_vol=").append(frozen_vol);
        sb.append(", vol=").append(vol);
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