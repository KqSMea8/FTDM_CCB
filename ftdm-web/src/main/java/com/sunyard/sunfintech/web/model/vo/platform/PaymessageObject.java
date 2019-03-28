package com.sunyard.sunfintech.web.model.vo.platform;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by wubin on 2017/5/5.
 */
public class PaymessageObject extends BaseModel{

    /**
     * 用户编号
     */
    @NotBlank
    private String user_no;

    /**
     * 用户类型
     */
    @NotBlank
    private String user_type;

    /**
     * 外部用户编号
     */
    @NotBlank
    private String out_user_no;

    /**
     * 用户账号
     */
    @NotBlank
    private String acct_no;

    /**
     * 币种
     */
    @NotBlank
    private String curr;

    /**
     * 交易金额
     */
    @NotNull
    private BigDecimal trans_amt;

    /**
     * 产品类型
     */
    private String product_type;

    /**
     * 产品编号
     */
    private String product_no;

    /**
     * 产品名称
     */
    private String product_name;

    /**
     * 产品展示地址
     */
    private String product_url;

    /**
     * 付款方式
     */
    private String pay_type;

    /**
     * 银行编号
     */
    private String bank_no;

    /**
     * 支付银行帐号
     */
    private String bank_card_no;

    /**
     * 资金通道
     */
    private String pay_code;

    /**
     * 摘要
     */
    private String memo;

    /**
     * 扩展字段1
     */
    private String extfld1;

    /**
     * 扩展字段2
     */
    private String extfld2;

    /**
     * 扩展字段3
     */
    private String extfld3;

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getOut_user_no() {
        return out_user_no;
    }

    public void setOut_user_no(String out_user_no) {
        this.out_user_no = out_user_no;
    }

    public String getAcct_no() {
        return acct_no;
    }

    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getBank_card_no() {
        return bank_card_no;
    }

    public void setBank_card_no(String bank_card_no) {
        this.bank_card_no = bank_card_no;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getExtfld1() {
        return extfld1;
    }

    public void setExtfld1(String extfld1) {
        this.extfld1 = extfld1;
    }

    public String getExtfld2() {
        return extfld2;
    }

    public void setExtfld2(String extfld2) {
        this.extfld2 = extfld2;
    }

    public String getExtfld3() {
        return extfld3;
    }

    public void setExtfld3(String extfld3) {
        this.extfld3 = extfld3;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
