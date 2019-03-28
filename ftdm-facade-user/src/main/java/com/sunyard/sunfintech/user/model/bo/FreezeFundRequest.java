package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 资金冻结解冻
 * Created by wubin on 2017/5/5.
 */
public class FreezeFundRequest extends BaseRequest{

    /**
     * 平台客户账户
     */
    @NotBlank
    private String platcust;

    /**
     * 平台客户账户类型
     */
    @NotBlank
    private String account_type;

    /**
     * 金额
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 冻结解冻标示：01、冻结，02、解冻
     */
    @NotBlank
    private String freeze_flg;

    /**
     * 冻结流水号，如果是解冻操作则必填
     */
    private String freeze_order_no;

    /**
     * 冻结解冻备注
     */
    private String remark;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFreeze_flg() {
        return freeze_flg;
    }

    public void setFreeze_flg(String freeze_flg) {
        this.freeze_flg = freeze_flg;
    }

    public String getFreeze_order_no() {
        return freeze_order_no;
    }

    public void setFreeze_order_no(String freeze_order_no) {
        this.freeze_order_no = freeze_order_no;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "FreezeFundRequest{" +
                "platcust='" + platcust + '\'' +
                ", account_type='" + account_type + '\'' +
                ", amount=" + amount +
                ", freeze_flg='" + freeze_flg + '\'' +
                ", freeze_order_no='" + freeze_order_no + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
