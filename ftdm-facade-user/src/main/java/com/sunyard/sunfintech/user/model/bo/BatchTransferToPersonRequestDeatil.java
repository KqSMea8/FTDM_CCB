package com.sunyard.sunfintech.user.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by PengZY on 2017/9/21.
 */
public class BatchTransferToPersonRequestDeatil implements Serializable {

    @NotBlank
    private String detail_no;//明细订单号

    private String account_type;//账户类型 01-投资 02-融资

    @DecimalMin(value = "0.001")
    private BigDecimal amount;//金额

    @NotBlank
    private String platcust;//平台客户号

    @NotBlank
    private String cause_type;//原因

    private String tips;//备注

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getCause_type() {
        return cause_type;
    }

    public void setCause_type(String cause_type) {
        this.cause_type = cause_type;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchTransferToPersonRequestDeatil that = (BatchTransferToPersonRequestDeatil) o;

        if (detail_no != null ? !detail_no.equals(that.detail_no) : that.detail_no != null) return false;
        if (account_type != null ? !account_type.equals(that.account_type) : that.account_type != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (cause_type != null ? !cause_type.equals(that.cause_type) : that.cause_type != null) return false;
        return tips != null ? tips.equals(that.tips) : that.tips == null;
    }

    @Override
    public int hashCode() {
        int result = detail_no != null ? detail_no.hashCode() : 0;
        result = 31 * result + (account_type != null ? account_type.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
        result = 31 * result + (cause_type != null ? cause_type.hashCode() : 0);
        result = 31 * result + (tips != null ? tips.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BatchTransferToPersonRequestDeatil{" +
                "detail_no='" + detail_no + '\'' +
                ", account_type='" + account_type + '\'' +
                ", amount=" + amount +
                ", platcust='" + platcust + '\'' +
                ", cause_type='" + cause_type + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
