package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RwWithdraw implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 流水号
     */
    private String trans_serial;

    /**
     * 订单号
     */
    private String order_no;

    /**
     * 交易日期
     */
    private String trans_date;

    /**
     * 交易时间
     */
    private String trans_time;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 产品编号
     */
    private String prod_id;

    /**
     * 平台客户号
     */
    private String platcust;

    /**
     * 交易代码
     */
    private String trans_code;

    /**
     * 交易名称
     */
    private String trans_name;

    /**
     * 交易金额
     */
    private BigDecimal out_amt;

    /**
     * 出账科目
     */
    private String subject;

    /**
     * 
     */
    private String sub_subject;

    /**
     * 支付编号
     */
    private String pay_code;

    /**
     * 手续费金额
     */
    private BigDecimal fee_amt;

    /**
     * advance_amt
     */
    private BigDecimal advance_amt;

    /**
     * 收款人账户
     */
    private String oppo_account;

    /**
     * 收款人开户行
     */
    private String open_branch;

    /**
     * 收款人户名
     */
    private String payee_name;

    /**
     * 公私标示
     */
    private String client_property;

    /**
     * 城市编号
     */
    private String city_code;

    /**
     * 银行编号
     */
    private String bank_id;

    /**
     * 开户支行名称
     */
    private String brabank_name;

    /**
     * 是否垫付
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
     * 完成时间
     */
    private String finish_time;

    /**
     * 通知地址
     */
    private String notify_url;

    /**
     * 第三方流水号
     */
    private String host_req_serial_no;

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
    private Integer query_no_num;

    /**
     * 
     */
    private String paymessage;

    /**
     * 
     */
    private String province_code;

    /**
     * 三方状态，支付公司状态，银行状态
     */
    private String payment_status;

    /**
     * 三方账期 YYYYMMDD
     */
    private String payment_date;

    /**
     * 卡类型：信用卡，借记卡
     */
    private String card_type;

    /**
     * 原提现申请订单号
     */
    private String origin_order_no;

    /**
     * rw_withdraw
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
     * 订单号
     * @return order_no 订单号
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 订单号
     * @param order_no 订单号
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
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
     * 交易金额
     * @return out_amt 交易金额
     */
    public BigDecimal getOut_amt() {
        return out_amt;
    }

    /**
     * 交易金额
     * @param out_amt 交易金额
     */
    public void setOut_amt(BigDecimal out_amt) {
        this.out_amt = out_amt;
    }

    /**
     * 出账科目
     * @return subject 出账科目
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 出账科目
     * @param subject 出账科目
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
     * 支付编号
     * @return pay_code 支付编号
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 支付编号
     * @param pay_code 支付编号
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
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
     * advance_amt
     * @return advance_amt advance_amt
     */
    public BigDecimal getAdvance_amt() {
        return advance_amt;
    }

    /**
     * advance_amt
     * @param advance_amt advance_amt
     */
    public void setAdvance_amt(BigDecimal advance_amt) {
        this.advance_amt = advance_amt;
    }

    /**
     * 收款人账户
     * @return oppo_account 收款人账户
     */
    public String getOppo_account() {
        return oppo_account;
    }

    /**
     * 收款人账户
     * @param oppo_account 收款人账户
     */
    public void setOppo_account(String oppo_account) {
        this.oppo_account = oppo_account == null ? null : oppo_account.trim();
    }

    /**
     * 收款人开户行
     * @return open_branch 收款人开户行
     */
    public String getOpen_branch() {
        return open_branch;
    }

    /**
     * 收款人开户行
     * @param open_branch 收款人开户行
     */
    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch == null ? null : open_branch.trim();
    }

    /**
     * 收款人户名
     * @return payee_name 收款人户名
     */
    public String getPayee_name() {
        return payee_name;
    }

    /**
     * 收款人户名
     * @param payee_name 收款人户名
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
     * 城市编号
     * @return city_code 城市编号
     */
    public String getCity_code() {
        return city_code;
    }

    /**
     * 城市编号
     * @param city_code 城市编号
     */
    public void setCity_code(String city_code) {
        this.city_code = city_code == null ? null : city_code.trim();
    }

    /**
     * 银行编号
     * @return bank_id 银行编号
     */
    public String getBank_id() {
        return bank_id;
    }

    /**
     * 银行编号
     * @param bank_id 银行编号
     */
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id == null ? null : bank_id.trim();
    }

    /**
     * 开户支行名称
     * @return brabank_name 开户支行名称
     */
    public String getBrabank_name() {
        return brabank_name;
    }

    /**
     * 开户支行名称
     * @param brabank_name 开户支行名称
     */
    public void setBrabank_name(String brabank_name) {
        this.brabank_name = brabank_name == null ? null : brabank_name.trim();
    }

    /**
     * 是否垫付
     * @return is_advance 是否垫付
     */
    public String getIs_advance() {
        return is_advance;
    }

    /**
     * 是否垫付
     * @param is_advance 是否垫付
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
     * 完成时间
     * @return finish_time 完成时间
     */
    public String getFinish_time() {
        return finish_time;
    }

    /**
     * 完成时间
     * @param finish_time 完成时间
     */
    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time == null ? null : finish_time.trim();
    }

    /**
     * 通知地址
     * @return notify_url 通知地址
     */
    public String getNotify_url() {
        return notify_url;
    }

    /**
     * 通知地址
     * @param notify_url 通知地址
     */
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url == null ? null : notify_url.trim();
    }

    /**
     * 第三方流水号
     * @return host_req_serial_no 第三方流水号
     */
    public String getHost_req_serial_no() {
        return host_req_serial_no;
    }

    /**
     * 第三方流水号
     * @param host_req_serial_no 第三方流水号
     */
    public void setHost_req_serial_no(String host_req_serial_no) {
        this.host_req_serial_no = host_req_serial_no == null ? null : host_req_serial_no.trim();
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
     * @return query_no_num 
     */
    public Integer getQuery_no_num() {
        return query_no_num;
    }

    /**
     * 
     * @param query_no_num 
     */
    public void setQuery_no_num(Integer query_no_num) {
        this.query_no_num = query_no_num;
    }

    /**
     * 
     * @return paymessage 
     */
    public String getPaymessage() {
        return paymessage;
    }

    /**
     * 
     * @param paymessage 
     */
    public void setPaymessage(String paymessage) {
        this.paymessage = paymessage == null ? null : paymessage.trim();
    }

    /**
     * 
     * @return province_code 
     */
    public String getProvince_code() {
        return province_code;
    }

    /**
     * 
     * @param province_code 
     */
    public void setProvince_code(String province_code) {
        this.province_code = province_code == null ? null : province_code.trim();
    }

    /**
     * 三方状态，支付公司状态，银行状态
     * @return payment_status 三方状态，支付公司状态，银行状态
     */
    public String getPayment_status() {
        return payment_status;
    }

    /**
     * 三方状态，支付公司状态，银行状态
     * @param payment_status 三方状态，支付公司状态，银行状态
     */
    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status == null ? null : payment_status.trim();
    }

    /**
     * 三方账期 YYYYMMDD
     * @return payment_date 三方账期 YYYYMMDD
     */
    public String getPayment_date() {
        return payment_date;
    }

    /**
     * 三方账期 YYYYMMDD
     * @param payment_date 三方账期 YYYYMMDD
     */
    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date == null ? null : payment_date.trim();
    }

    /**
     * 卡类型：信用卡，借记卡
     * @return card_type 卡类型：信用卡，借记卡
     */
    public String getCard_type() {
        return card_type;
    }

    /**
     * 卡类型：信用卡，借记卡
     * @param card_type 卡类型：信用卡，借记卡
     */
    public void setCard_type(String card_type) {
        this.card_type = card_type == null ? null : card_type.trim();
    }

    /**
     * 原提现申请订单号
     * @return origin_order_no 原提现申请订单号
     */
    public String getOrigin_order_no() {
        return origin_order_no;
    }

    /**
     * 原提现申请订单号
     * @param origin_order_no 原提现申请订单号
     */
    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no == null ? null : origin_order_no.trim();
    }

    /**
     *
     * @mbggenerated 2018-01-29
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
        RwWithdraw other = (RwWithdraw) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()))
            && (this.getTrans_name() == null ? other.getTrans_name() == null : this.getTrans_name().equals(other.getTrans_name()))
            && (this.getOut_amt() == null ? other.getOut_amt() == null : this.getOut_amt().equals(other.getOut_amt()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getSub_subject() == null ? other.getSub_subject() == null : this.getSub_subject().equals(other.getSub_subject()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getFee_amt() == null ? other.getFee_amt() == null : this.getFee_amt().equals(other.getFee_amt()))
            && (this.getAdvance_amt() == null ? other.getAdvance_amt() == null : this.getAdvance_amt().equals(other.getAdvance_amt()))
            && (this.getOppo_account() == null ? other.getOppo_account() == null : this.getOppo_account().equals(other.getOppo_account()))
            && (this.getOpen_branch() == null ? other.getOpen_branch() == null : this.getOpen_branch().equals(other.getOpen_branch()))
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
            && (this.getNotify_url() == null ? other.getNotify_url() == null : this.getNotify_url().equals(other.getNotify_url()))
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
            && (this.getQuery_no_num() == null ? other.getQuery_no_num() == null : this.getQuery_no_num().equals(other.getQuery_no_num()))
            && (this.getPaymessage() == null ? other.getPaymessage() == null : this.getPaymessage().equals(other.getPaymessage()))
            && (this.getProvince_code() == null ? other.getProvince_code() == null : this.getProvince_code().equals(other.getProvince_code()))
            && (this.getPayment_status() == null ? other.getPayment_status() == null : this.getPayment_status().equals(other.getPayment_status()))
            && (this.getPayment_date() == null ? other.getPayment_date() == null : this.getPayment_date().equals(other.getPayment_date()))
            && (this.getCard_type() == null ? other.getCard_type() == null : this.getCard_type().equals(other.getCard_type()))
            && (this.getOrigin_order_no() == null ? other.getOrigin_order_no() == null : this.getOrigin_order_no().equals(other.getOrigin_order_no()));
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        result = prime * result + ((getTrans_name() == null) ? 0 : getTrans_name().hashCode());
        result = prime * result + ((getOut_amt() == null) ? 0 : getOut_amt().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getSub_subject() == null) ? 0 : getSub_subject().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getFee_amt() == null) ? 0 : getFee_amt().hashCode());
        result = prime * result + ((getAdvance_amt() == null) ? 0 : getAdvance_amt().hashCode());
        result = prime * result + ((getOppo_account() == null) ? 0 : getOppo_account().hashCode());
        result = prime * result + ((getOpen_branch() == null) ? 0 : getOpen_branch().hashCode());
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
        result = prime * result + ((getNotify_url() == null) ? 0 : getNotify_url().hashCode());
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
        result = prime * result + ((getQuery_no_num() == null) ? 0 : getQuery_no_num().hashCode());
        result = prime * result + ((getPaymessage() == null) ? 0 : getPaymessage().hashCode());
        result = prime * result + ((getProvince_code() == null) ? 0 : getProvince_code().hashCode());
        result = prime * result + ((getPayment_status() == null) ? 0 : getPayment_status().hashCode());
        result = prime * result + ((getPayment_date() == null) ? 0 : getPayment_date().hashCode());
        result = prime * result + ((getCard_type() == null) ? 0 : getCard_type().hashCode());
        result = prime * result + ((getOrigin_order_no() == null) ? 0 : getOrigin_order_no().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", order_no=").append(order_no);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", platcust=").append(platcust);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", trans_name=").append(trans_name);
        sb.append(", out_amt=").append(out_amt);
        sb.append(", subject=").append(subject);
        sb.append(", sub_subject=").append(sub_subject);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", fee_amt=").append(fee_amt);
        sb.append(", advance_amt=").append(advance_amt);
        sb.append(", oppo_account=").append(oppo_account);
        sb.append(", open_branch=").append(open_branch);
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
        sb.append(", notify_url=").append(notify_url);
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
        sb.append(", query_no_num=").append(query_no_num);
        sb.append(", paymessage=").append(paymessage);
        sb.append(", province_code=").append(province_code);
        sb.append(", payment_status=").append(payment_status);
        sb.append(", payment_date=").append(payment_date);
        sb.append(", card_type=").append(card_type);
        sb.append(", origin_order_no=").append(origin_order_no);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}