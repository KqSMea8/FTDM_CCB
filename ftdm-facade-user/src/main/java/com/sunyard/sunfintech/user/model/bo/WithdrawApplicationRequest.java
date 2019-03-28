package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by PengZY on 2018/1/15.
 * 提现申请
 */
public class WithdrawApplicationRequest extends BaseRequest {

    @NotBlank
    private String platcust;//集团客户号

    @NotNull
    private BigDecimal amt;//提现金额

    private String is_advance;//垫资类型

    private String advance_platcust;//垫资账户

    @NotBlank
    private String pay_code;//支付编号

    private BigDecimal fee_amt;//提现手续费

    private String withdraw_type;//提现人类型

    private String client_property;//公私标识

    private String city_code;//城市编码

    @NotBlank
    private String notify_url;//通知地址

    @NotBlank
    private String card_no;//卡号

    @NotBlank
    private String name;//姓名

    private String bank_id;//大额行号

    private String open_branch;//开户行

    private String brabank_name;//开户支行名称

    private String card_type;//卡类型

    @NotBlank
    private String trans_pwd;//交易密码

    @NotBlank
    private String random_key;//交易密码随机数

    private String province_code;

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
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
        this.is_advance = StringUtils.isNotBlank(is_advance) ? is_advance : AdvanceType.NOADVANCE.getCode();
    }

    public String getAdvance_platcust() {
        return advance_platcust;
    }

    public void setAdvance_platcust(String advance_platcust) {
        this.advance_platcust = advance_platcust;
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
        this.fee_amt = fee_amt == null ? new BigDecimal("0") : fee_amt;
    }

    public String getWithdraw_type() {return withdraw_type; }

    public void setWithdraw_type(String withdraw_type) {
        this.withdraw_type = StringUtils.isNotBlank(withdraw_type) ? withdraw_type : Ssubject.INVEST.getCode();
    }

    public String getClient_property() {
        return client_property;
    }

    public void setClient_property(String client_property) {
        this.client_property = StringUtils.isNotBlank(client_property) ? client_property : CusType.PERSONAL.getCode();
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getOpen_branch() {
        return open_branch;
    }

    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = StringUtils.isNotBlank(card_type) ? card_type : CardType.DEBIT.getCode();
    }

    public String getBrabank_name() {
        return brabank_name;
    }

    public void setBrabank_name(String brabank_name) {
        this.brabank_name = brabank_name;
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

    @Override
    public String toString() {
        return "WithdrawApplicationRequest{" +
                "platcust='" + platcust + '\'' +
                ", amt=" + amt +
                ", is_advance='" + is_advance + '\'' +
                ", advance_platcust='" + advance_platcust + '\'' +
                ", pay_code='" + pay_code + '\'' +
                ", fee_amt=" + fee_amt +
                ", withdraw_type='" + withdraw_type + '\'' +
                ", client_property='" + client_property + '\'' +
                ", city_code='" + city_code + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", card_no='" + card_no + '\'' +
                ", name='" + name + '\'' +
                ", bank_id='" + bank_id + '\'' +
                ", open_branch='" + open_branch + '\'' +
                ", brabank_name='" + brabank_name + '\'' +
                ", card_type='" + card_type + '\'' +
                ", trans_pwd='" + trans_pwd + '\'' +
                ", random_key='" + random_key + '\'' +
                '}';
    }
}
