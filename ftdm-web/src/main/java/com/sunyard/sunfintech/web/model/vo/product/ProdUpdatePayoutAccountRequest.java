package com.sunyard.sunfintech.web.model.vo.product;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdUpdatePayoutAccountRequest extends BaseRequest {

    /**
     * 标的编号
     */
    @NotBlank
    private String prod_id;

    /**
     * 产品收款人开户行
     */
    private String open_branch;

    /**
     * 产品收款人银行卡号
     */
    private String withdraw_account;

    /**
     * 卡类型
     */
    private String account_type;

    /**
     * 产品收款人姓名
     */
    private String payee_name;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getOpen_branch() {
        return open_branch;
    }

    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch;
    }

    public String getWithdraw_account() {
        return withdraw_account;
    }

    public void setWithdraw_account(String withdraw_account) {
        this.withdraw_account = withdraw_account;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getPayee_name() {
        return payee_name;
    }

    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name;
    }
}
