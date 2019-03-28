package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdProductinfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 产品名称
     */
    private String prod_name;

    /**
     * 产品类型
     */
    private String prod_type;

    /**
     * 发行总额度
     */
    private BigDecimal total_limit;

    /**
     * 剩余额度
     */
    private BigDecimal remain_limit;

    /**
     * 已卖出额度
     */
    private BigDecimal saled_limit;

    /**
     * 产品起息方式
     */
    private String value_type;

    /**
     * 成立方式
     */
    private String create_type;

    /**
     * 分红方式
     */
    private String payout_type;

    /**
     * 产品发行日
     */
    private String sell_date;

    /**
     * 起息日
     */
    private String value_date;

    /**
     * 到期日
     */
    private String expire_date;

    /**
     * 周期
     */
    private Integer cycle;

    /**
     * 周期单位
     */
    private String cycle_unit;

    /**
     * 参与人数
     */
    private Integer parti_num;

    /**
     * 年化收益率
     */
    private BigDecimal ist_year;

    /**
     * 产品状态
     */
    private String prod_state;

    /**
     * 还款方式
     */
    private String repay_type;

    /**
     * 还款账号
     */
    private String prod_account;

    /**
     * 自动出账 0自动出账 1手动出账
     */
    private String charge_off_auto;

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
     * 
     */
    private String risk_lvl;

    /**
     * 
     */
    private String prod_info;

    /**
     * 
     */
    private String buyer_num_limit;

    /**
     * 
     */
    private String overLimit;

    /**
     * 
     */
    private String over_total_limit;

    /**
     * 自动投标标示 0-非自动投标 1-自动投标
     */
    private String auto_flg;

    /**
     * prod_productinfo
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
     * 产品编号
     * @return prod_id 产品编号
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * 产品编号
     * @param prod_id 产品编号
     */
    public void setProd_id(String prod_id) {
        this.prod_id = prod_id == null ? null : prod_id.trim();
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
     * 产品名称
     * @return prod_name 产品名称
     */
    public String getProd_name() {
        return prod_name;
    }

    /**
     * 产品名称
     * @param prod_name 产品名称
     */
    public void setProd_name(String prod_name) {
        this.prod_name = prod_name == null ? null : prod_name.trim();
    }

    /**
     * 产品类型
     * @return prod_type 产品类型
     */
    public String getProd_type() {
        return prod_type;
    }

    /**
     * 产品类型
     * @param prod_type 产品类型
     */
    public void setProd_type(String prod_type) {
        this.prod_type = prod_type == null ? null : prod_type.trim();
    }

    /**
     * 发行总额度
     * @return total_limit 发行总额度
     */
    public BigDecimal getTotal_limit() {
        return total_limit;
    }

    /**
     * 发行总额度
     * @param total_limit 发行总额度
     */
    public void setTotal_limit(BigDecimal total_limit) {
        this.total_limit = total_limit;
    }

    /**
     * 剩余额度
     * @return remain_limit 剩余额度
     */
    public BigDecimal getRemain_limit() {
        return remain_limit;
    }

    /**
     * 剩余额度
     * @param remain_limit 剩余额度
     */
    public void setRemain_limit(BigDecimal remain_limit) {
        this.remain_limit = remain_limit;
    }

    /**
     * 已卖出额度
     * @return saled_limit 已卖出额度
     */
    public BigDecimal getSaled_limit() {
        return saled_limit;
    }

    /**
     * 已卖出额度
     * @param saled_limit 已卖出额度
     */
    public void setSaled_limit(BigDecimal saled_limit) {
        this.saled_limit = saled_limit;
    }

    /**
     * 产品起息方式
     * @return value_type 产品起息方式
     */
    public String getValue_type() {
        return value_type;
    }

    /**
     * 产品起息方式
     * @param value_type 产品起息方式
     */
    public void setValue_type(String value_type) {
        this.value_type = value_type == null ? null : value_type.trim();
    }

    /**
     * 成立方式
     * @return create_type 成立方式
     */
    public String getCreate_type() {
        return create_type;
    }

    /**
     * 成立方式
     * @param create_type 成立方式
     */
    public void setCreate_type(String create_type) {
        this.create_type = create_type == null ? null : create_type.trim();
    }

    /**
     * 分红方式
     * @return payout_type 分红方式
     */
    public String getPayout_type() {
        return payout_type;
    }

    /**
     * 分红方式
     * @param payout_type 分红方式
     */
    public void setPayout_type(String payout_type) {
        this.payout_type = payout_type == null ? null : payout_type.trim();
    }

    /**
     * 产品发行日
     * @return sell_date 产品发行日
     */
    public String getSell_date() {
        return sell_date;
    }

    /**
     * 产品发行日
     * @param sell_date 产品发行日
     */
    public void setSell_date(String sell_date) {
        this.sell_date = sell_date == null ? null : sell_date.trim();
    }

    /**
     * 起息日
     * @return value_date 起息日
     */
    public String getValue_date() {
        return value_date;
    }

    /**
     * 起息日
     * @param value_date 起息日
     */
    public void setValue_date(String value_date) {
        this.value_date = value_date == null ? null : value_date.trim();
    }

    /**
     * 到期日
     * @return expire_date 到期日
     */
    public String getExpire_date() {
        return expire_date;
    }

    /**
     * 到期日
     * @param expire_date 到期日
     */
    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date == null ? null : expire_date.trim();
    }

    /**
     * 周期
     * @return cycle 周期
     */
    public Integer getCycle() {
        return cycle;
    }

    /**
     * 周期
     * @param cycle 周期
     */
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    /**
     * 周期单位
     * @return cycle_unit 周期单位
     */
    public String getCycle_unit() {
        return cycle_unit;
    }

    /**
     * 周期单位
     * @param cycle_unit 周期单位
     */
    public void setCycle_unit(String cycle_unit) {
        this.cycle_unit = cycle_unit == null ? null : cycle_unit.trim();
    }

    /**
     * 参与人数
     * @return parti_num 参与人数
     */
    public Integer getParti_num() {
        return parti_num;
    }

    /**
     * 参与人数
     * @param parti_num 参与人数
     */
    public void setParti_num(Integer parti_num) {
        this.parti_num = parti_num;
    }

    /**
     * 年化收益率
     * @return ist_year 年化收益率
     */
    public BigDecimal getIst_year() {
        return ist_year;
    }

    /**
     * 年化收益率
     * @param ist_year 年化收益率
     */
    public void setIst_year(BigDecimal ist_year) {
        this.ist_year = ist_year;
    }

    /**
     * 产品状态
     * @return prod_state 产品状态
     */
    public String getProd_state() {
        return prod_state;
    }

    /**
     * 产品状态
     * @param prod_state 产品状态
     */
    public void setProd_state(String prod_state) {
        this.prod_state = prod_state == null ? null : prod_state.trim();
    }

    /**
     * 还款方式
     * @return repay_type 还款方式
     */
    public String getRepay_type() {
        return repay_type;
    }

    /**
     * 还款方式
     * @param repay_type 还款方式
     */
    public void setRepay_type(String repay_type) {
        this.repay_type = repay_type == null ? null : repay_type.trim();
    }

    /**
     * 还款账号
     * @return prod_account 还款账号
     */
    public String getProd_account() {
        return prod_account;
    }

    /**
     * 还款账号
     * @param prod_account 还款账号
     */
    public void setProd_account(String prod_account) {
        this.prod_account = prod_account == null ? null : prod_account.trim();
    }

    /**
     * 自动出账 0自动出账 1手动出账
     * @return charge_off_auto 自动出账 0自动出账 1手动出账
     */
    public String getCharge_off_auto() {
        return charge_off_auto;
    }

    /**
     * 自动出账 0自动出账 1手动出账
     * @param charge_off_auto 自动出账 0自动出账 1手动出账
     */
    public void setCharge_off_auto(String charge_off_auto) {
        this.charge_off_auto = charge_off_auto == null ? null : charge_off_auto.trim();
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
     * @return risk_lvl 
     */
    public String getRisk_lvl() {
        return risk_lvl;
    }

    /**
     * 
     * @param risk_lvl 
     */
    public void setRisk_lvl(String risk_lvl) {
        this.risk_lvl = risk_lvl == null ? null : risk_lvl.trim();
    }

    /**
     * 
     * @return prod_info 
     */
    public String getProd_info() {
        return prod_info;
    }

    /**
     * 
     * @param prod_info 
     */
    public void setProd_info(String prod_info) {
        this.prod_info = prod_info == null ? null : prod_info.trim();
    }

    /**
     * 
     * @return buyer_num_limit 
     */
    public String getBuyer_num_limit() {
        return buyer_num_limit;
    }

    /**
     * 
     * @param buyer_num_limit 
     */
    public void setBuyer_num_limit(String buyer_num_limit) {
        this.buyer_num_limit = buyer_num_limit == null ? null : buyer_num_limit.trim();
    }

    /**
     * 
     * @return overLimit 
     */
    public String getOverLimit() {
        return overLimit;
    }

    /**
     * 
     * @param overLimit 
     */
    public void setOverLimit(String overLimit) {
        this.overLimit = overLimit == null ? null : overLimit.trim();
    }

    /**
     * 
     * @return over_total_limit 
     */
    public String getOver_total_limit() {
        return over_total_limit;
    }

    /**
     * 
     * @param over_total_limit 
     */
    public void setOver_total_limit(String over_total_limit) {
        this.over_total_limit = over_total_limit == null ? null : over_total_limit.trim();
    }

    /**
     * 自动投标标示 0-非自动投标 1-自动投标
     * @return auto_flg 自动投标标示 0-非自动投标 1-自动投标
     */
    public String getAuto_flg() {
        return auto_flg;
    }

    /**
     * 自动投标标示 0-非自动投标 1-自动投标
     * @param auto_flg 自动投标标示 0-非自动投标 1-自动投标
     */
    public void setAuto_flg(String auto_flg) {
        this.auto_flg = auto_flg == null ? null : auto_flg.trim();
    }

    /**
     *
     * @mbggenerated 2018-02-27
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
        ProdProductinfo other = (ProdProductinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getProd_name() == null ? other.getProd_name() == null : this.getProd_name().equals(other.getProd_name()))
            && (this.getProd_type() == null ? other.getProd_type() == null : this.getProd_type().equals(other.getProd_type()))
            && (this.getTotal_limit() == null ? other.getTotal_limit() == null : this.getTotal_limit().equals(other.getTotal_limit()))
            && (this.getRemain_limit() == null ? other.getRemain_limit() == null : this.getRemain_limit().equals(other.getRemain_limit()))
            && (this.getSaled_limit() == null ? other.getSaled_limit() == null : this.getSaled_limit().equals(other.getSaled_limit()))
            && (this.getValue_type() == null ? other.getValue_type() == null : this.getValue_type().equals(other.getValue_type()))
            && (this.getCreate_type() == null ? other.getCreate_type() == null : this.getCreate_type().equals(other.getCreate_type()))
            && (this.getPayout_type() == null ? other.getPayout_type() == null : this.getPayout_type().equals(other.getPayout_type()))
            && (this.getSell_date() == null ? other.getSell_date() == null : this.getSell_date().equals(other.getSell_date()))
            && (this.getValue_date() == null ? other.getValue_date() == null : this.getValue_date().equals(other.getValue_date()))
            && (this.getExpire_date() == null ? other.getExpire_date() == null : this.getExpire_date().equals(other.getExpire_date()))
            && (this.getCycle() == null ? other.getCycle() == null : this.getCycle().equals(other.getCycle()))
            && (this.getCycle_unit() == null ? other.getCycle_unit() == null : this.getCycle_unit().equals(other.getCycle_unit()))
            && (this.getParti_num() == null ? other.getParti_num() == null : this.getParti_num().equals(other.getParti_num()))
            && (this.getIst_year() == null ? other.getIst_year() == null : this.getIst_year().equals(other.getIst_year()))
            && (this.getProd_state() == null ? other.getProd_state() == null : this.getProd_state().equals(other.getProd_state()))
            && (this.getRepay_type() == null ? other.getRepay_type() == null : this.getRepay_type().equals(other.getRepay_type()))
            && (this.getProd_account() == null ? other.getProd_account() == null : this.getProd_account().equals(other.getProd_account()))
            && (this.getCharge_off_auto() == null ? other.getCharge_off_auto() == null : this.getCharge_off_auto().equals(other.getCharge_off_auto()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getRisk_lvl() == null ? other.getRisk_lvl() == null : this.getRisk_lvl().equals(other.getRisk_lvl()))
            && (this.getProd_info() == null ? other.getProd_info() == null : this.getProd_info().equals(other.getProd_info()))
            && (this.getBuyer_num_limit() == null ? other.getBuyer_num_limit() == null : this.getBuyer_num_limit().equals(other.getBuyer_num_limit()))
            && (this.getOverLimit() == null ? other.getOverLimit() == null : this.getOverLimit().equals(other.getOverLimit()))
            && (this.getOver_total_limit() == null ? other.getOver_total_limit() == null : this.getOver_total_limit().equals(other.getOver_total_limit()))
            && (this.getAuto_flg() == null ? other.getAuto_flg() == null : this.getAuto_flg().equals(other.getAuto_flg()));
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getProd_name() == null) ? 0 : getProd_name().hashCode());
        result = prime * result + ((getProd_type() == null) ? 0 : getProd_type().hashCode());
        result = prime * result + ((getTotal_limit() == null) ? 0 : getTotal_limit().hashCode());
        result = prime * result + ((getRemain_limit() == null) ? 0 : getRemain_limit().hashCode());
        result = prime * result + ((getSaled_limit() == null) ? 0 : getSaled_limit().hashCode());
        result = prime * result + ((getValue_type() == null) ? 0 : getValue_type().hashCode());
        result = prime * result + ((getCreate_type() == null) ? 0 : getCreate_type().hashCode());
        result = prime * result + ((getPayout_type() == null) ? 0 : getPayout_type().hashCode());
        result = prime * result + ((getSell_date() == null) ? 0 : getSell_date().hashCode());
        result = prime * result + ((getValue_date() == null) ? 0 : getValue_date().hashCode());
        result = prime * result + ((getExpire_date() == null) ? 0 : getExpire_date().hashCode());
        result = prime * result + ((getCycle() == null) ? 0 : getCycle().hashCode());
        result = prime * result + ((getCycle_unit() == null) ? 0 : getCycle_unit().hashCode());
        result = prime * result + ((getParti_num() == null) ? 0 : getParti_num().hashCode());
        result = prime * result + ((getIst_year() == null) ? 0 : getIst_year().hashCode());
        result = prime * result + ((getProd_state() == null) ? 0 : getProd_state().hashCode());
        result = prime * result + ((getRepay_type() == null) ? 0 : getRepay_type().hashCode());
        result = prime * result + ((getProd_account() == null) ? 0 : getProd_account().hashCode());
        result = prime * result + ((getCharge_off_auto() == null) ? 0 : getCharge_off_auto().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getRisk_lvl() == null) ? 0 : getRisk_lvl().hashCode());
        result = prime * result + ((getProd_info() == null) ? 0 : getProd_info().hashCode());
        result = prime * result + ((getBuyer_num_limit() == null) ? 0 : getBuyer_num_limit().hashCode());
        result = prime * result + ((getOverLimit() == null) ? 0 : getOverLimit().hashCode());
        result = prime * result + ((getOver_total_limit() == null) ? 0 : getOver_total_limit().hashCode());
        result = prime * result + ((getAuto_flg() == null) ? 0 : getAuto_flg().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-02-27
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
        sb.append(", prod_name=").append(prod_name);
        sb.append(", prod_type=").append(prod_type);
        sb.append(", total_limit=").append(total_limit);
        sb.append(", remain_limit=").append(remain_limit);
        sb.append(", saled_limit=").append(saled_limit);
        sb.append(", value_type=").append(value_type);
        sb.append(", create_type=").append(create_type);
        sb.append(", payout_type=").append(payout_type);
        sb.append(", sell_date=").append(sell_date);
        sb.append(", value_date=").append(value_date);
        sb.append(", expire_date=").append(expire_date);
        sb.append(", cycle=").append(cycle);
        sb.append(", cycle_unit=").append(cycle_unit);
        sb.append(", parti_num=").append(parti_num);
        sb.append(", ist_year=").append(ist_year);
        sb.append(", prod_state=").append(prod_state);
        sb.append(", repay_type=").append(repay_type);
        sb.append(", prod_account=").append(prod_account);
        sb.append(", charge_off_auto=").append(charge_off_auto);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", risk_lvl=").append(risk_lvl);
        sb.append(", prod_info=").append(prod_info);
        sb.append(", buyer_num_limit=").append(buyer_num_limit);
        sb.append(", overLimit=").append(overLimit);
        sb.append(", over_total_limit=").append(over_total_limit);
        sb.append(", auto_flg=").append(auto_flg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}