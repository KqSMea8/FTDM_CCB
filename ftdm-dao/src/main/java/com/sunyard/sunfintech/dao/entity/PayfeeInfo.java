package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayfeeInfo implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 集团号
     */
    private String mall_no;

    /**
     * 平台号
     */
    private String plat_no;

    /**
     * 缴费账户
     */
    private String platcust;

    /**
     * 收款账户
     */
    private String payee;

    /**
     * 缴费金额
     */
    private BigDecimal amt;

    /**
     * 缴费账户子科目01,02
     */
    private String account_type;

    /**
     * 标的id
     */
    private String prod_id;

    /**
     * 缴费订单号
     */
    private String order_no;

    /**
     * 缴费流水
     */
    private String trans_serial;

    /**
     * 取消缴费订单号
     */
    private String cancel_order_no;

    /**
     * 取消缴费流水
     */
    private String cancel_trans_serial;

    /**
     * 是否有效 0有效 1无效
     */
    private String enabled;

    /**
     * 摘要备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String create_by;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新人
     */
    private String update_by;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * payfee_info
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     * @return id 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 编号
     * @param id 编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 集团号
     * @return mall_no 集团号
     */
    public String getMall_no() {
        return mall_no;
    }

    /**
     * 集团号
     * @param mall_no 集团号
     */
    public void setMall_no(String mall_no) {
        this.mall_no = mall_no == null ? null : mall_no.trim();
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
     * 缴费账户
     * @return platcust 缴费账户
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 缴费账户
     * @param platcust 缴费账户
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 收款账户
     * @return payee 收款账户
     */
    public String getPayee() {
        return payee;
    }

    /**
     * 收款账户
     * @param payee 收款账户
     */
    public void setPayee(String payee) {
        this.payee = payee == null ? null : payee.trim();
    }

    /**
     * 缴费金额
     * @return amt 缴费金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 缴费金额
     * @param amt 缴费金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 缴费账户子科目01,02
     * @return account_type 缴费账户子科目01,02
     */
    public String getAccount_type() {
        return account_type;
    }

    /**
     * 缴费账户子科目01,02
     * @param account_type 缴费账户子科目01,02
     */
    public void setAccount_type(String account_type) {
        this.account_type = account_type == null ? null : account_type.trim();
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
     * 缴费订单号
     * @return order_no 缴费订单号
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 缴费订单号
     * @param order_no 缴费订单号
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 缴费流水
     * @return trans_serial 缴费流水
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 缴费流水
     * @param trans_serial 缴费流水
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
    }

    /**
     * 取消缴费订单号
     * @return cancel_order_no 取消缴费订单号
     */
    public String getCancel_order_no() {
        return cancel_order_no;
    }

    /**
     * 取消缴费订单号
     * @param cancel_order_no 取消缴费订单号
     */
    public void setCancel_order_no(String cancel_order_no) {
        this.cancel_order_no = cancel_order_no == null ? null : cancel_order_no.trim();
    }

    /**
     * 取消缴费流水
     * @return cancel_trans_serial 取消缴费流水
     */
    public String getCancel_trans_serial() {
        return cancel_trans_serial;
    }

    /**
     * 取消缴费流水
     * @param cancel_trans_serial 取消缴费流水
     */
    public void setCancel_trans_serial(String cancel_trans_serial) {
        this.cancel_trans_serial = cancel_trans_serial == null ? null : cancel_trans_serial.trim();
    }

    /**
     * 是否有效 0有效 1无效
     * @return enabled 是否有效 0有效 1无效
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 是否有效 0有效 1无效
     * @param enabled 是否有效 0有效 1无效
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    /**
     * 摘要备注
     * @return remark 摘要备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 摘要备注
     * @param remark 摘要备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
     * 更新人
     * @return update_by 更新人
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 更新人
     * @param update_by 更新人
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     *
     * @mbggenerated 2018-03-19
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
        PayfeeInfo other = (PayfeeInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getPayee() == null ? other.getPayee() == null : this.getPayee().equals(other.getPayee()))
            && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()))
            && (this.getAccount_type() == null ? other.getAccount_type() == null : this.getAccount_type().equals(other.getAccount_type()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getCancel_order_no() == null ? other.getCancel_order_no() == null : this.getCancel_order_no().equals(other.getCancel_order_no()))
            && (this.getCancel_trans_serial() == null ? other.getCancel_trans_serial() == null : this.getCancel_trans_serial().equals(other.getCancel_trans_serial()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2018-03-19
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getPayee() == null) ? 0 : getPayee().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        result = prime * result + ((getAccount_type() == null) ? 0 : getAccount_type().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getCancel_order_no() == null) ? 0 : getCancel_order_no().hashCode());
        result = prime * result + ((getCancel_trans_serial() == null) ? 0 : getCancel_trans_serial().hashCode());
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
     * @mbggenerated 2018-03-19
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
        sb.append(", payee=").append(payee);
        sb.append(", amt=").append(amt);
        sb.append(", account_type=").append(account_type);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", order_no=").append(order_no);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", cancel_order_no=").append(cancel_order_no);
        sb.append(", cancel_trans_serial=").append(cancel_trans_serial);
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