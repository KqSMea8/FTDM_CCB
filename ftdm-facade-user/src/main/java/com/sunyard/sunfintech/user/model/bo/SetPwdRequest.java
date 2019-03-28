package com.sunyard.sunfintech.user.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SetPwdRequest extends ModifyPwdRequest {
    //跳转设置/修改密码成功后的反显类型
    private String typeStr;

    //角色（1：出借人、2：借款人、3：垫资人、4：担保人）
    @NotBlank
    private String role;

    /**
     * 授权类型（1、出借，2、还款，3、缴费，4、全部）
     */
    @NotNull
    private String authed_type;

    //授权金额
    @NotNull
    @DecimalMin(value = "0.001")
    private BigDecimal authed_amount;

    //授权期限 YYYYMMDD
    @NotBlank
    private String authed_limittime;

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAuthed_type() {
        return authed_type;
    }

    public void setAuthed_type(String authed_type) {
        this.authed_type = authed_type;
    }

    public BigDecimal getAuthed_amount() {
        return authed_amount;
    }

    public void setAuthed_amount(BigDecimal authed_amount) {
        this.authed_amount = authed_amount;
    }

    public String getAuthed_limittime() {
        return authed_limittime;
    }

    public void setAuthed_limittime(String authed_limittime) {
        this.authed_limittime = authed_limittime;
    }
}
