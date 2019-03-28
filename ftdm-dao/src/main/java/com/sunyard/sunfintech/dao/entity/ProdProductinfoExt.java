package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdProductinfoExt implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String prod_id;

    /**
     * 
     */
    private String plat_no;

    /**
     * 风险等级
     */
    private String risk_lvl;

    /**
     * 产品信息
     */
    private String prod_info;

    /**
     * 超额控制标识
     */
    private String is_advance;

    /**
     * 允许超卖金额
     */
    private BigDecimal payout_allratio;

    /**
     * 购买人数限制
     */
    private Integer buyer_num_limit;

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
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * prod_productinfo_ext
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
     * 风险等级
     * @return risk_lvl 风险等级
     */
    public String getRisk_lvl() {
        return risk_lvl;
    }

    /**
     * 风险等级
     * @param risk_lvl 风险等级
     */
    public void setRisk_lvl(String risk_lvl) {
        this.risk_lvl = risk_lvl == null ? null : risk_lvl.trim();
    }

    /**
     * 产品信息
     * @return prod_info 产品信息
     */
    public String getProd_info() {
        return prod_info;
    }

    /**
     * 产品信息
     * @param prod_info 产品信息
     */
    public void setProd_info(String prod_info) {
        this.prod_info = prod_info == null ? null : prod_info.trim();
    }

    /**
     * 超额控制标识
     * @return is_advance 超额控制标识
     */
    public String getIs_advance() {
        return is_advance;
    }

    /**
     * 超额控制标识
     * @param is_advance 超额控制标识
     */
    public void setIs_advance(String is_advance) {
        this.is_advance = is_advance == null ? null : is_advance.trim();
    }

    /**
     * 允许超卖金额
     * @return payout_allratio 允许超卖金额
     */
    public BigDecimal getPayout_allratio() {
        return payout_allratio;
    }

    /**
     * 允许超卖金额
     * @param payout_allratio 允许超卖金额
     */
    public void setPayout_allratio(BigDecimal payout_allratio) {
        this.payout_allratio = payout_allratio;
    }

    /**
     * 购买人数限制
     * @return buyer_num_limit 购买人数限制
     */
    public Integer getBuyer_num_limit() {
        return buyer_num_limit;
    }

    /**
     * 购买人数限制
     * @param buyer_num_limit 购买人数限制
     */
    public void setBuyer_num_limit(Integer buyer_num_limit) {
        this.buyer_num_limit = buyer_num_limit;
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
     * @mbggenerated 2017-05-31
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
        ProdProductinfoExt other = (ProdProductinfoExt) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getRisk_lvl() == null ? other.getRisk_lvl() == null : this.getRisk_lvl().equals(other.getRisk_lvl()))
            && (this.getProd_info() == null ? other.getProd_info() == null : this.getProd_info().equals(other.getProd_info()))
            && (this.getIs_advance() == null ? other.getIs_advance() == null : this.getIs_advance().equals(other.getIs_advance()))
            && (this.getPayout_allratio() == null ? other.getPayout_allratio() == null : this.getPayout_allratio().equals(other.getPayout_allratio()))
            && (this.getBuyer_num_limit() == null ? other.getBuyer_num_limit() == null : this.getBuyer_num_limit().equals(other.getBuyer_num_limit()))
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
     * @mbggenerated 2017-05-31
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getRisk_lvl() == null) ? 0 : getRisk_lvl().hashCode());
        result = prime * result + ((getProd_info() == null) ? 0 : getProd_info().hashCode());
        result = prime * result + ((getIs_advance() == null) ? 0 : getIs_advance().hashCode());
        result = prime * result + ((getPayout_allratio() == null) ? 0 : getPayout_allratio().hashCode());
        result = prime * result + ((getBuyer_num_limit() == null) ? 0 : getBuyer_num_limit().hashCode());
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
     * @mbggenerated 2017-05-31
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", risk_lvl=").append(risk_lvl);
        sb.append(", prod_info=").append(prod_info);
        sb.append(", is_advance=").append(is_advance);
        sb.append(", payout_allratio=").append(payout_allratio);
        sb.append(", buyer_num_limit=").append(buyer_num_limit);
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