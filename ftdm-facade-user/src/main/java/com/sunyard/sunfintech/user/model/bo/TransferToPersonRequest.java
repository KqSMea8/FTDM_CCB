package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 平台转帐个人
 * Created by wubin on 2017/5/5.
 */
public class TransferToPersonRequest extends BaseRequest {

    /**
     * 平台账户
     * 11-自有子账户
     */
    @NotBlank
    private String plat_account;

    /**
     * 金额
     */
    @DecimalMin(value = "0.001")
    private BigDecimal amount;

    /**
     * 平台客户号
     */
    @NotBlank
    private String platcust;

    /**
     * 账户类型(01-投资，02-融资，默认投资)
     */
    private String account_type;

    /**
     * 原因类型
     * 1：平台赠送；
     * 2：平台补贴；
     * 3：活动派发；
     * 4：抵扣券未使用派发；
     * 5：迁移存管账户；
     * 6：加息补贴；
     * 7：体验金奖励；
     * 8：佣金奖励
     */
    @NotBlank
    private String cause_type;

    /**
     * 摘要
     */
    private String tips;

    public String getPlat_account() {
        return plat_account;
    }

    public void setPlat_account(String plat_account) {
        this.plat_account = plat_account;
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

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
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

        TransferToPersonRequest that = (TransferToPersonRequest) o;

        if (plat_account != null ? !plat_account.equals(that.plat_account) : that.plat_account != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
        if (account_type != null ? !account_type.equals(that.account_type) : that.account_type != null) return false;
        if (cause_type != null ? !cause_type.equals(that.cause_type) : that.cause_type != null) return false;
        return tips != null ? tips.equals(that.tips) : that.tips == null;
    }

    @Override
    public int hashCode() {
        int result = plat_account != null ? plat_account.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (platcust != null ? platcust.hashCode() : 0);
        result = 31 * result + (account_type != null ? account_type.hashCode() : 0);
        result = 31 * result + (cause_type != null ? cause_type.hashCode() : 0);
        result = 31 * result + (tips != null ? tips.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferToPersonRequest{" +
                "plat_account='" + plat_account + '\'' +
                ", amount=" + amount +
                ", platcust='" + platcust + '\'' +
                ", account_type='" + account_type + '\'' +
                ", cause_type='" + cause_type + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
