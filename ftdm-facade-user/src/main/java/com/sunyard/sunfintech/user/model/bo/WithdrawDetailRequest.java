package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by PengZY on 2018/1/15.
 */
public class WithdrawDetailRequest extends BaseRequest {

    //明细编号
    @NotBlank
    private String detail_no;

    //账户编号
    @NotBlank
    private String platcust;

    //提现金额
    @NotNull
    private BigDecimal amt;

    //是否垫资:(1-不垫资， 2-垫资，3-行内垫资)；默认不垫付。
    private String is_advance;

    //垫资账户。如果需要垫资可以从垫资人自有账户垫资
    private String advance_platcust;

    //支付通道
    @NotBlank
    private String pay_code;

    //手续费金额（独立于提现金额）
    private BigDecimal fee_amt;

    //提现的账户类型：01投资账户  02融资账户
    private String withdraw_type;

    //公私标示，0-个人 1-公司； 默认个人
    private String client_property;

    //城市编码（富友必填）
    private String city_code;

    //异步通知地址
    @NotBlank
    private String notify_url;

    //卡号 多卡必填
    private String card_no;

    //姓名，如果多卡，则必填
    private String name;

    //开户行号（宝付对公必填）
    private String bank_id;

    //开户行号名称
    private String open_branch;

    //卡类型(1:借记卡，2:信用卡)；默认为1
    private String card_type;

    //交易密码
    @NotBlank
    private String trans_pwd;

    //交易密码随机数
    @NotBlank
    private String random_key;

    //开户行所在省编码(宝付对公必填)
    private String province_code;

    //开户支行名称(宝付对公必填)
    private String brabank_name;

    //备注
    private String remark;

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen_branch() {
        return open_branch;
    }

    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getBrabank_name() {
        return brabank_name;
    }

    public void setBrabank_name(String brabank_name) {
        this.brabank_name = brabank_name;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public BigDecimal getAmt() {
        return amt;
    }
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getIs_advance() {
        return is_advance;
    }

    public void setIs_advance(String is_advance) {
        this.is_advance = is_advance;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public BigDecimal getFee_amt() {
        return fee_amt;
    }
    public void setFee_amt(BigDecimal fee_amt) {
        this.fee_amt = fee_amt;
    }

    public String getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(String withdraw_type) {
        this.withdraw_type = withdraw_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getClient_property() {
        return client_property;
    }

    public void setClient_property(String client_property) {
        this.client_property = client_property;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdvance_platcust() {
        return advance_platcust;
    }

    public void setAdvance_platcust(String advance_platcust) {
        this.advance_platcust = advance_platcust;
    }

    public String getTrans_pwd() {
        return trans_pwd;
    }

    public void setTrans_pwd(String trans_pwd) {
        this.trans_pwd = trans_pwd;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }
}
