package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdCompensationInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 流水号
     */
    private String trans_serial;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 代偿客户编号
     */
    private String compensation_platcust;

    /**
     * 还款期数
     */
    private Integer repay_num;

    /**
     * 计划还款日期
     */
    private String repay_date;

    /**
     * 计划还款金额
     */
    private BigDecimal repay_amt;

    /**
     * 实际还款日期
     */
    private String real_repay_date;

    /**
     * 实际还款金额
     */
    private BigDecimal real_repay_amt;

    /**
     * 是否删除
     */
    private String enabled;

    /**
     * 手续费金额
     */
    private BigDecimal fee_amt;

    /**
     * 还款类型 0-代偿还款 1-委托还款
     */
    private String repay_type;

    /**
     * 平台订单号
     */
    private String order_no;

    /**
     * 状态 1-成功
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 扩展字段1
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * 创建人
     */
    private String create_by;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 修改人
     */
    private String update_by;

    /**
     * 修改时间
     */
    private Date update_time;

    /**
     * prod_compensation_info
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
     * 流水号
     * @return trans_serial 流水号
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 流水号
     * @param trans_serial 流水号
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
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
     * 代偿客户编号
     * @return compensation_platcust 代偿客户编号
     */
    public String getCompensation_platcust() {
        return compensation_platcust;
    }

    /**
     * 代偿客户编号
     * @param compensation_platcust 代偿客户编号
     */
    public void setCompensation_platcust(String compensation_platcust) {
        this.compensation_platcust = compensation_platcust == null ? null : compensation_platcust.trim();
    }

    /**
     * 还款期数
     * @return repay_num 还款期数
     */
    public Integer getRepay_num() {
        return repay_num;
    }

    /**
     * 还款期数
     * @param repay_num 还款期数
     */
    public void setRepay_num(Integer repay_num) {
        this.repay_num = repay_num;
    }

    /**
     * 计划还款日期
     * @return repay_date 计划还款日期
     */
    public String getRepay_date() {
        return repay_date;
    }

    /**
     * 计划还款日期
     * @param repay_date 计划还款日期
     */
    public void setRepay_date(String repay_date) {
        this.repay_date = repay_date == null ? null : repay_date.trim();
    }

    /**
     * 计划还款金额
     * @return repay_amt 计划还款金额
     */
    public BigDecimal getRepay_amt() {
        return repay_amt;
    }

    /**
     * 计划还款金额
     * @param repay_amt 计划还款金额
     */
    public void setRepay_amt(BigDecimal repay_amt) {
        this.repay_amt = repay_amt;
    }

    /**
     * 实际还款日期
     * @return real_repay_date 实际还款日期
     */
    public String getReal_repay_date() {
        return real_repay_date;
    }

    /**
     * 实际还款日期
     * @param real_repay_date 实际还款日期
     */
    public void setReal_repay_date(String real_repay_date) {
        this.real_repay_date = real_repay_date == null ? null : real_repay_date.trim();
    }

    /**
     * 实际还款金额
     * @return real_repay_amt 实际还款金额
     */
    public BigDecimal getReal_repay_amt() {
        return real_repay_amt;
    }

    /**
     * 实际还款金额
     * @param real_repay_amt 实际还款金额
     */
    public void setReal_repay_amt(BigDecimal real_repay_amt) {
        this.real_repay_amt = real_repay_amt;
    }

    /**
     * 手续费金额
     * @return fee_amt 手续费金额
     */
    public BigDecimal getFee_amt() {
        return fee_amt;
    }

    /**
     * 手续费金额
     * @param fee_amt 手续费金额
     */
    public void setFee_amt(BigDecimal fee_amt) {
        this.fee_amt = fee_amt;
    }

    /**
     * 还款类型 0-代偿还款 1-委托还款
     * @return repay_type 还款类型 0-代偿还款 1-委托还款
     */
    public String getRepay_type() {
        return repay_type;
    }

    /**
     * 还款类型 0-代偿还款 1-委托还款
     * @param repay_type 还款类型 0-代偿还款 1-委托还款
     */
    public void setRepay_type(String repay_type) {
        this.repay_type = repay_type == null ? null : repay_type.trim();
    }

    /**
     * 平台订单号
     * @return order_no 平台订单号
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 平台订单号
     * @param order_no 平台订单号
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 状态 1-成功
     * @return status 状态 1-成功
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态 1-成功
     * @param status 状态 1-成功
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 是否删除
     * @return enabled 是否删除
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 是否删除
     * @param enabled 是否删除
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
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
     * 创建人
     * @return create_by 创建人
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 创建人
     * @param create_by 创建人
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间
     * @param create_time 创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 修改人
     * @return update_by 修改人
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 修改人
     * @param update_by 修改人
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 修改时间
     * @param update_time 修改时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     *
     * @mbggenerated 2017-06-27
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
        ProdCompensationInfo other = (ProdCompensationInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getCompensation_platcust() == null ? other.getCompensation_platcust() == null : this.getCompensation_platcust().equals(other.getCompensation_platcust()))
            && (this.getRepay_num() == null ? other.getRepay_num() == null : this.getRepay_num().equals(other.getRepay_num()))
            && (this.getRepay_date() == null ? other.getRepay_date() == null : this.getRepay_date().equals(other.getRepay_date()))
            && (this.getRepay_amt() == null ? other.getRepay_amt() == null : this.getRepay_amt().equals(other.getRepay_amt()))
            && (this.getReal_repay_date() == null ? other.getReal_repay_date() == null : this.getReal_repay_date().equals(other.getReal_repay_date()))
            && (this.getReal_repay_amt() == null ? other.getReal_repay_amt() == null : this.getReal_repay_amt().equals(other.getReal_repay_amt()))
            && (this.getFee_amt() == null ? other.getFee_amt() == null : this.getFee_amt().equals(other.getFee_amt()))
            && (this.getRepay_type() == null ? other.getRepay_type() == null : this.getRepay_type().equals(other.getRepay_type()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2017-06-27
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getCompensation_platcust() == null) ? 0 : getCompensation_platcust().hashCode());
        result = prime * result + ((getRepay_num() == null) ? 0 : getRepay_num().hashCode());
        result = prime * result + ((getRepay_date() == null) ? 0 : getRepay_date().hashCode());
        result = prime * result + ((getRepay_amt() == null) ? 0 : getRepay_amt().hashCode());
        result = prime * result + ((getReal_repay_date() == null) ? 0 : getReal_repay_date().hashCode());
        result = prime * result + ((getReal_repay_amt() == null) ? 0 : getReal_repay_amt().hashCode());
        result = prime * result + ((getFee_amt() == null) ? 0 : getFee_amt().hashCode());
        result = prime * result + ((getRepay_type() == null) ? 0 : getRepay_type().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-06-27
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", compensation_platcust=").append(compensation_platcust);
        sb.append(", repay_num=").append(repay_num);
        sb.append(", repay_date=").append(repay_date);
        sb.append(", repay_amt=").append(repay_amt);
        sb.append(", real_repay_date=").append(real_repay_date);
        sb.append(", real_repay_amt=").append(real_repay_amt);
        sb.append(", fee_amt=").append(fee_amt);
        sb.append(", repay_type=").append(repay_type);
        sb.append(", order_no=").append(order_no);
        sb.append(", status=").append(status);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}