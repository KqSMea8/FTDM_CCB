package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EaccAccountamtlist implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 交易流水号
     */
    private String trans_serial;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 平台客户号
     */
    private String platcust;

    /**
     * 科目
     */
    private String subject;

    /**
     * 
     */
    private String sub_subject;

    /**
     * 交易代码
     */
    private String trans_code;

    /**
     * 交易名称
     */
    private String trans_name;

    /**
     * 交易日期
     */
    private String trans_date;

    /**
     * 交易时间
     */
    private String trans_time;

    /**
     * 资金流向(0-入金  1-出金  2-冻结  3-解冻)
     */
    private String amt_type;

    /**
     * 
     */
    private String oppo_plat_no;

    /**
     * 对手账户
     */
    private String oppo_platcust;

    /**
     * 对手科目
     */
    private String oppo_subject;

    /**
     * 
     */
    private String oppo_sub_subject;

    /**
     * 变动金额
     */
    private BigDecimal amt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 转换状态0-未转换1-部分转换2全部转换
     */
    private String switch_state;

    /**
     * 已转换金额
     */
    private BigDecimal switch_amt;

    /**
     * 支付公司编号
     */
    private String pay_code;

    /**
     * 账期
     */
    private Date account_date;

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
     * 
     */
    private String ext4;

    /**
     * 
     */
    private String ext5;

    /**
     * 
     */
    private String enabled;

    /**
     * 
     */
    private String ramerk;

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
     * 
     */
    private String order_no;

    /**
     * 
     */
    private BigDecimal balance;

    /**
     * eacc_accountamtlist
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
     * 交易流水号
     * @return trans_serial 交易流水号
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 交易流水号
     * @param trans_serial 交易流水号
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
     * 平台客户号
     * @return platcust 平台客户号
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 平台客户号
     * @param platcust 平台客户号
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 科目
     * @return subject 科目
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 科目
     * @param subject 科目
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * 
     * @return sub_subject 
     */
    public String getSub_subject() {
        return sub_subject;
    }

    /**
     * 
     * @param sub_subject 
     */
    public void setSub_subject(String sub_subject) {
        this.sub_subject = sub_subject == null ? null : sub_subject.trim();
    }

    /**
     * 交易代码
     * @return trans_code 交易代码
     */
    public String getTrans_code() {
        return trans_code;
    }

    /**
     * 交易代码
     * @param trans_code 交易代码
     */
    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code == null ? null : trans_code.trim();
    }

    /**
     * 交易名称
     * @return trans_name 交易名称
     */
    public String getTrans_name() {
        return trans_name;
    }

    /**
     * 交易名称
     * @param trans_name 交易名称
     */
    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name == null ? null : trans_name.trim();
    }

    /**
     * 交易日期
     * @return trans_date 交易日期
     */
    public String getTrans_date() {
        return trans_date;
    }

    /**
     * 交易日期
     * @param trans_date 交易日期
     */
    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date == null ? null : trans_date.trim();
    }

    /**
     * 交易时间
     * @return trans_time 交易时间
     */
    public String getTrans_time() {
        return trans_time;
    }

    /**
     * 交易时间
     * @param trans_time 交易时间
     */
    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time == null ? null : trans_time.trim();
    }

    /**
     * 资金流向(0-入金  1-出金  2-冻结  3-解冻)
     * @return amt_type 资金流向(0-入金  1-出金  2-冻结  3-解冻)
     */
    public String getAmt_type() {
        return amt_type;
    }

    /**
     * 资金流向(0-入金  1-出金  2-冻结  3-解冻)
     * @param amt_type 资金流向(0-入金  1-出金  2-冻结  3-解冻)
     */
    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type == null ? null : amt_type.trim();
    }

    /**
     * 
     * @return oppo_plat_no 
     */
    public String getOppo_plat_no() {
        return oppo_plat_no;
    }

    /**
     * 
     * @param oppo_plat_no 
     */
    public void setOppo_plat_no(String oppo_plat_no) {
        this.oppo_plat_no = oppo_plat_no == null ? null : oppo_plat_no.trim();
    }

    /**
     * 对手账户
     * @return oppo_platcust 对手账户
     */
    public String getOppo_platcust() {
        return oppo_platcust;
    }

    /**
     * 对手账户
     * @param oppo_platcust 对手账户
     */
    public void setOppo_platcust(String oppo_platcust) {
        this.oppo_platcust = oppo_platcust == null ? null : oppo_platcust.trim();
    }

    /**
     * 对手科目
     * @return oppo_subject 对手科目
     */
    public String getOppo_subject() {
        return oppo_subject;
    }

    /**
     * 对手科目
     * @param oppo_subject 对手科目
     */
    public void setOppo_subject(String oppo_subject) {
        this.oppo_subject = oppo_subject == null ? null : oppo_subject.trim();
    }

    /**
     * 
     * @return oppo_sub_subject 
     */
    public String getOppo_sub_subject() {
        return oppo_sub_subject;
    }

    /**
     * 
     * @param oppo_sub_subject 
     */
    public void setOppo_sub_subject(String oppo_sub_subject) {
        this.oppo_sub_subject = oppo_sub_subject == null ? null : oppo_sub_subject.trim();
    }

    /**
     * 变动金额
     * @return amt 变动金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 变动金额
     * @param amt 变动金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
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
     * 转换状态0-未转换1-部分转换2全部转换
     * @return switch_state 转换状态0-未转换1-部分转换2全部转换
     */
    public String getSwitch_state() {
        return switch_state;
    }

    /**
     * 转换状态0-未转换1-部分转换2全部转换
     * @param switch_state 转换状态0-未转换1-部分转换2全部转换
     */
    public void setSwitch_state(String switch_state) {
        this.switch_state = switch_state == null ? null : switch_state.trim();
    }

    /**
     * 已转换金额
     * @return switch_amt 已转换金额
     */
    public BigDecimal getSwitch_amt() {
        return switch_amt;
    }

    /**
     * 已转换金额
     * @param switch_amt 已转换金额
     */
    public void setSwitch_amt(BigDecimal switch_amt) {
        this.switch_amt = switch_amt;
    }

    /**
     * 支付公司编号
     * @return pay_code 支付公司编号
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 支付公司编号
     * @param pay_code 支付公司编号
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
    }

    /**
     * 账期
     * @return account_date 账期
     */
    public Date getAccount_date() {
        return account_date;
    }

    /**
     * 账期
     * @param account_date 账期
     */
    public void setAccount_date(Date account_date) {
        this.account_date = account_date;
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
     * @return ext4 
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * 
     * @param ext4 
     */
    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    /**
     * 
     * @return ext5 
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * 
     * @param ext5 
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
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
     * @return ramerk 
     */
    public String getRamerk() {
        return ramerk;
    }

    /**
     * 
     * @param ramerk 
     */
    public void setRamerk(String ramerk) {
        this.ramerk = ramerk == null ? null : ramerk.trim();
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
     * @return order_no 
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 
     * @param order_no 
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 
     * @return balance 
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 
     * @param balance 
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     *
     * @mbggenerated 2018-01-06
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
        EaccAccountamtlist other = (EaccAccountamtlist) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getSub_subject() == null ? other.getSub_subject() == null : this.getSub_subject().equals(other.getSub_subject()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()))
            && (this.getTrans_name() == null ? other.getTrans_name() == null : this.getTrans_name().equals(other.getTrans_name()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getAmt_type() == null ? other.getAmt_type() == null : this.getAmt_type().equals(other.getAmt_type()))
            && (this.getOppo_plat_no() == null ? other.getOppo_plat_no() == null : this.getOppo_plat_no().equals(other.getOppo_plat_no()))
            && (this.getOppo_platcust() == null ? other.getOppo_platcust() == null : this.getOppo_platcust().equals(other.getOppo_platcust()))
            && (this.getOppo_subject() == null ? other.getOppo_subject() == null : this.getOppo_subject().equals(other.getOppo_subject()))
            && (this.getOppo_sub_subject() == null ? other.getOppo_sub_subject() == null : this.getOppo_sub_subject().equals(other.getOppo_sub_subject()))
            && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getSwitch_state() == null ? other.getSwitch_state() == null : this.getSwitch_state().equals(other.getSwitch_state()))
            && (this.getSwitch_amt() == null ? other.getSwitch_amt() == null : this.getSwitch_amt().equals(other.getSwitch_amt()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getAccount_date() == null ? other.getAccount_date() == null : this.getAccount_date().equals(other.getAccount_date()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()))
            && (this.getExt4() == null ? other.getExt4() == null : this.getExt4().equals(other.getExt4()))
            && (this.getExt5() == null ? other.getExt5() == null : this.getExt5().equals(other.getExt5()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRamerk() == null ? other.getRamerk() == null : this.getRamerk().equals(other.getRamerk()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()));
    }

    /**
     *
     * @mbggenerated 2018-01-06
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getSub_subject() == null) ? 0 : getSub_subject().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        result = prime * result + ((getTrans_name() == null) ? 0 : getTrans_name().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getAmt_type() == null) ? 0 : getAmt_type().hashCode());
        result = prime * result + ((getOppo_plat_no() == null) ? 0 : getOppo_plat_no().hashCode());
        result = prime * result + ((getOppo_platcust() == null) ? 0 : getOppo_platcust().hashCode());
        result = prime * result + ((getOppo_subject() == null) ? 0 : getOppo_subject().hashCode());
        result = prime * result + ((getOppo_sub_subject() == null) ? 0 : getOppo_sub_subject().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getSwitch_state() == null) ? 0 : getSwitch_state().hashCode());
        result = prime * result + ((getSwitch_amt() == null) ? 0 : getSwitch_amt().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getAccount_date() == null) ? 0 : getAccount_date().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        result = prime * result + ((getExt4() == null) ? 0 : getExt4().hashCode());
        result = prime * result + ((getExt5() == null) ? 0 : getExt5().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRamerk() == null) ? 0 : getRamerk().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-06
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
        sb.append(", platcust=").append(platcust);
        sb.append(", subject=").append(subject);
        sb.append(", sub_subject=").append(sub_subject);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", trans_name=").append(trans_name);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", amt_type=").append(amt_type);
        sb.append(", oppo_plat_no=").append(oppo_plat_no);
        sb.append(", oppo_platcust=").append(oppo_platcust);
        sb.append(", oppo_subject=").append(oppo_subject);
        sb.append(", oppo_sub_subject=").append(oppo_sub_subject);
        sb.append(", amt=").append(amt);
        sb.append(", remark=").append(remark);
        sb.append(", switch_state=").append(switch_state);
        sb.append(", switch_amt=").append(switch_amt);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", account_date=").append(account_date);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", ext3=").append(ext3);
        sb.append(", ext4=").append(ext4);
        sb.append(", ext5=").append(ext5);
        sb.append(", enabled=").append(enabled);
        sb.append(", ramerk=").append(ramerk);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", order_no=").append(order_no);
        sb.append(", balance=").append(balance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}