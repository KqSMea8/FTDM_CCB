package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/1.
 */

public class AuthPayFeeRequestDetail extends BaseRequest{
    /**
     * 明细编号
     */
    @NotBlank
    private String detail_no;

    /**
     * 平台客户编号（出资人）
     */
    @NotBlank
    private String platcust;
    /**
     * 产品编号，如果非缴费到平台必填
     */
    @NotBlank
    private String prod_id;

    /**
     * 平台客户编号（收款人），如果非缴费到平台必填
     */
    @NotNull
    private String payee;

    /**
     * 交易密码
     */
    private String trans_pwd;

    /**
     * 交易密码随机数
     */
    private String random_key;

    /**
     * 缴费金额
     */
    @NotNull
    @DecimalMin(value = "0.001")
    private BigDecimal amt;


    public void setDetail_no(String detail_no) {
        super.setOrder_no(detail_no);
        this.detail_no = detail_no;
    }

    /**
     * 摘要
     */

    private String tips;

    public void setPlatcust(String platcust) {
        super.setBase_serial_plutcust(platcust);
        this.platcust = platcust;
    }

    /**
     * 缴费金额
     */

    @NotNull
    private String account_type;

    public String getDetail_no() {
        return detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
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

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }


    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }
}
