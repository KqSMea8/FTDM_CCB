package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 平台不同账户转账
 * Created by wubin on 2017/5/5.
 */
public class TransferToPlatformRequest extends BaseRequest {

    /**
     * 来源账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户)
     */
    @NotBlank
    private String source_account;

    /**
     * 转账金额
     */
    @NotNull
    private BigDecimal amt;

    /**
     * 目标账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户)
     */
    @NotBlank
    private String dest_account;

    public String getSource_account() {
        return source_account;
    }

    public void setSource_account(String source_account) {
        this.source_account = source_account;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getDest_account() {
        return dest_account;
    }

    public void setDest_account(String dest_account) {
        this.dest_account = dest_account;
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
