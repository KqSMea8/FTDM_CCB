package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdChargeoff implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 交易流水号
     */
    private String trans_serial;

    /**
     * 交易日期
     */
    private String trans_date;

    /**
     * 交易时间
     */
    private String trans_time;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 融资人账号
     */
    private String platcust;

    /**
     * 出账金额
     */
    private BigDecimal out_amt;

    /**
     * 产品收款人开户行
     */
    private String open_branch;

    /**
     * 产品收款人银行卡号
     */
    private String withdraw_account;

    /**
     * 产品收款人户名
     */
    private String payee_name;

    /**
     * 公私标示
     */
    private String client_property;

    /**
     * 城市编码
     */
    private String city_code;

    /**
     * 银行编码
     */
    private String bank_id;

    /**
     * 银行名称
     */
    private String brabank_name;

    /**
     * 垫付标示
     */
    private String is_advance;

    /**
     * 支付状态
     */
    private String pay_status;

    /**
     * 到账状态
     */
    private String acct_state;

    /**
     * 提现编号
     */
    private String withdraw_no;

    /**
     * 支付时间
     */
    private String pay_time;

    /**
     * 确认时间
     */
    private String finish_time;

    /**
     * 请求三方支付流水编号
     */
    private String host_req_serial_no;

    /**
     * 删除标记
     */
    private String enabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建用户
     */
    private String create_by;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 修改用户
     */
    private String update_by;

    /**
     * 修改时间
     */
    private Date update_time;

    /**
     * 预留1
     */
    private String ext1;

    /**
     * 预留2
     */
    private String ext2;

    /**
     * 预留3
     */
    private String ext3;

    /**
     * 预留4
     */
    private String ext4;

    /**
     * 预留5
     */
    private String ext5;
    /**
     * 开户行所在省编码(宝付对公比填)
     */
    private String province_code;

    /**
     * prod_chargeoff
     */
    private static final long serialVersionUID = 1L;


    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

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
     * 融资人账号
     * @return platcust 融资人账号
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 融资人账号
     * @param platcust 融资人账号
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 出账金额
     * @return out_amt 出账金额
     */
    public BigDecimal getOut_amt() {
        return out_amt;
    }

    /**
     * 出账金额
     * @param out_amt 出账金额
     */
    public void setOut_amt(BigDecimal out_amt) {
        this.out_amt = out_amt;
    }

    /**
     * 产品收款人开户行
     * @return open_branch 产品收款人开户行
     */
    public String getOpen_branch() {
        return open_branch;
    }

    /**
     * 产品收款人开户行
     * @param open_branch 产品收款人开户行
     */
    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch == null ? null : open_branch.trim();
    }

    /**
     * 产品收款人银行卡号
     * @return withdraw_account 产品收款人银行卡号
     */
    public String getWithdraw_account() {
        return withdraw_account;
    }

    /**
     * 产品收款人银行卡号
     * @param withdraw_account 产品收款人银行卡号
     */
    public void setWithdraw_account(String withdraw_account) {
        this.withdraw_account = withdraw_account == null ? null : withdraw_account.trim();
    }

    /**
     * 产品收款人户名
     * @return payee_name 产品收款人户名
     */
    public String getPayee_name() {
        return payee_name;
    }

    /**
     * 产品收款人户名
     * @param payee_name 产品收款人户名
     */
    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name == null ? null : payee_name.trim();
    }

    /**
     * 公私标示
     * @return client_property 公私标示
     */
    public String getClient_property() {
        return client_property;
    }

    /**
     * 公私标示
     * @param client_property 公私标示
     */
    public void setClient_property(String client_property) {
        this.client_property = client_property == null ? null : client_property.trim();
    }

    /**
     * 城市编码
     * @return city_code 城市编码
     */
    public String getCity_code() {
        return city_code;
    }

    /**
     * 城市编码
     * @param city_code 城市编码
     */
    public void setCity_code(String city_code) {
        this.city_code = city_code == null ? null : city_code.trim();
    }

    /**
     * 银行编码
     * @return bank_id 银行编码
     */
    public String getBank_id() {
        return bank_id;
    }

    /**
     * 银行编码
     * @param bank_id 银行编码
     */
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id == null ? null : bank_id.trim();
    }

    /**
     * 银行名称
     * @return brabank_name 银行名称
     */
    public String getBrabank_name() {
        return brabank_name;
    }

    /**
     * 银行名称
     * @param brabank_name 银行名称
     */
    public void setBrabank_name(String brabank_name) {
        this.brabank_name = brabank_name == null ? null : brabank_name.trim();
    }

    /**
     * 垫付标示
     * @return is_advance 垫付标示
     */
    public String getIs_advance() {
        return is_advance;
    }

    /**
     * 垫付标示
     * @param is_advance 垫付标示
     */
    public void setIs_advance(String is_advance) {
        this.is_advance = is_advance == null ? null : is_advance.trim();
    }

    /**
     * 支付状态
     * @return pay_status 支付状态
     */
    public String getPay_status() {
        return pay_status;
    }

    /**
     * 支付状态
     * @param pay_status 支付状态
     */
    public void setPay_status(String pay_status) {
        this.pay_status = pay_status == null ? null : pay_status.trim();
    }

    /**
     * 到账状态
     * @return acct_state 到账状态
     */
    public String getAcct_state() {
        return acct_state;
    }

    /**
     * 到账状态
     * @param acct_state 到账状态
     */
    public void setAcct_state(String acct_state) {
        this.acct_state = acct_state == null ? null : acct_state.trim();
    }

    /**
     * 提现编号
     * @return withdraw_no 提现编号
     */
    public String getWithdraw_no() {
        return withdraw_no;
    }

    /**
     * 提现编号
     * @param withdraw_no 提现编号
     */
    public void setWithdraw_no(String withdraw_no) {
        this.withdraw_no = withdraw_no == null ? null : withdraw_no.trim();
    }

    /**
     * 支付时间
     * @return pay_time 支付时间
     */
    public String getPay_time() {
        return pay_time;
    }

    /**
     * 支付时间
     * @param pay_time 支付时间
     */
    public void setPay_time(String pay_time) {
        this.pay_time = pay_time == null ? null : pay_time.trim();
    }

    /**
     * 确认时间
     * @return finish_time 确认时间
     */
    public String getFinish_time() {
        return finish_time;
    }

    /**
     * 确认时间
     * @param finish_time 确认时间
     */
    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time == null ? null : finish_time.trim();
    }

    /**
     * 请求三方支付流水编号
     * @return host_req_serial_no 请求三方支付流水编号
     */
    public String getHost_req_serial_no() {
        return host_req_serial_no;
    }

    /**
     * 请求三方支付流水编号
     * @param host_req_serial_no 请求三方支付流水编号
     */
    public void setHost_req_serial_no(String host_req_serial_no) {
        this.host_req_serial_no = host_req_serial_no == null ? null : host_req_serial_no.trim();
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
     * 创建用户
     * @return create_by 创建用户
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 创建用户
     * @param create_by 创建用户
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
     * 修改用户
     * @return update_by 修改用户
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 修改用户
     * @param update_by 修改用户
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
     * 预留1
     * @return ext1 预留1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 预留1
     * @param ext1 预留1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 预留2
     * @return ext2 预留2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 预留2
     * @param ext2 预留2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * 预留3
     * @return ext3 预留3
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * 预留3
     * @param ext3 预留3
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    /**
     * 预留4
     * @return ext4 预留4
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * 预留4
     * @param ext4 预留4
     */
    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    /**
     * 预留5
     * @return ext5 预留5
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * 预留5
     * @param ext5 预留5
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
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
        ProdChargeoff other = (ProdChargeoff) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getOut_amt() == null ? other.getOut_amt() == null : this.getOut_amt().equals(other.getOut_amt()))
            && (this.getOpen_branch() == null ? other.getOpen_branch() == null : this.getOpen_branch().equals(other.getOpen_branch()))
            && (this.getWithdraw_account() == null ? other.getWithdraw_account() == null : this.getWithdraw_account().equals(other.getWithdraw_account()))
            && (this.getPayee_name() == null ? other.getPayee_name() == null : this.getPayee_name().equals(other.getPayee_name()))
            && (this.getClient_property() == null ? other.getClient_property() == null : this.getClient_property().equals(other.getClient_property()))
            && (this.getCity_code() == null ? other.getCity_code() == null : this.getCity_code().equals(other.getCity_code()))
            && (this.getBank_id() == null ? other.getBank_id() == null : this.getBank_id().equals(other.getBank_id()))
            && (this.getBrabank_name() == null ? other.getBrabank_name() == null : this.getBrabank_name().equals(other.getBrabank_name()))
            && (this.getIs_advance() == null ? other.getIs_advance() == null : this.getIs_advance().equals(other.getIs_advance()))
            && (this.getPay_status() == null ? other.getPay_status() == null : this.getPay_status().equals(other.getPay_status()))
            && (this.getAcct_state() == null ? other.getAcct_state() == null : this.getAcct_state().equals(other.getAcct_state()))
            && (this.getWithdraw_no() == null ? other.getWithdraw_no() == null : this.getWithdraw_no().equals(other.getWithdraw_no()))
            && (this.getPay_time() == null ? other.getPay_time() == null : this.getPay_time().equals(other.getPay_time()))
            && (this.getFinish_time() == null ? other.getFinish_time() == null : this.getFinish_time().equals(other.getFinish_time()))
            && (this.getHost_req_serial_no() == null ? other.getHost_req_serial_no() == null : this.getHost_req_serial_no().equals(other.getHost_req_serial_no()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()))
            && (this.getExt4() == null ? other.getExt4() == null : this.getExt4().equals(other.getExt4()))
            && (this.getExt5() == null ? other.getExt5() == null : this.getExt5().equals(other.getExt5()));
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
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getOut_amt() == null) ? 0 : getOut_amt().hashCode());
        result = prime * result + ((getOpen_branch() == null) ? 0 : getOpen_branch().hashCode());
        result = prime * result + ((getWithdraw_account() == null) ? 0 : getWithdraw_account().hashCode());
        result = prime * result + ((getPayee_name() == null) ? 0 : getPayee_name().hashCode());
        result = prime * result + ((getClient_property() == null) ? 0 : getClient_property().hashCode());
        result = prime * result + ((getCity_code() == null) ? 0 : getCity_code().hashCode());
        result = prime * result + ((getBank_id() == null) ? 0 : getBank_id().hashCode());
        result = prime * result + ((getBrabank_name() == null) ? 0 : getBrabank_name().hashCode());
        result = prime * result + ((getIs_advance() == null) ? 0 : getIs_advance().hashCode());
        result = prime * result + ((getPay_status() == null) ? 0 : getPay_status().hashCode());
        result = prime * result + ((getAcct_state() == null) ? 0 : getAcct_state().hashCode());
        result = prime * result + ((getWithdraw_no() == null) ? 0 : getWithdraw_no().hashCode());
        result = prime * result + ((getPay_time() == null) ? 0 : getPay_time().hashCode());
        result = prime * result + ((getFinish_time() == null) ? 0 : getFinish_time().hashCode());
        result = prime * result + ((getHost_req_serial_no() == null) ? 0 : getHost_req_serial_no().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        result = prime * result + ((getExt4() == null) ? 0 : getExt4().hashCode());
        result = prime * result + ((getExt5() == null) ? 0 : getExt5().hashCode());
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
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", platcust=").append(platcust);
        sb.append(", out_amt=").append(out_amt);
        sb.append(", open_branch=").append(open_branch);
        sb.append(", withdraw_account=").append(withdraw_account);
        sb.append(", payee_name=").append(payee_name);
        sb.append(", client_property=").append(client_property);
        sb.append(", city_code=").append(city_code);
        sb.append(", bank_id=").append(bank_id);
        sb.append(", brabank_name=").append(brabank_name);
        sb.append(", is_advance=").append(is_advance);
        sb.append(", pay_status=").append(pay_status);
        sb.append(", acct_state=").append(acct_state);
        sb.append(", withdraw_no=").append(withdraw_no);
        sb.append(", pay_time=").append(pay_time);
        sb.append(", finish_time=").append(finish_time);
        sb.append(", host_req_serial_no=").append(host_req_serial_no);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", ext3=").append(ext3);
        sb.append(", ext4=").append(ext4);
        sb.append(", ext5=").append(ext5);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}