package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2018/2/26.
 */
public class AccountInfoResponseDetailAuthInfo implements Serializable {


    //授权金额
    private BigDecimal authed_amount;

    //授权期限
    private String authed_limittime;

    //授权类型
    private String authed_type;

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

    public String getAuthed_type() {
        return authed_type;
    }

    public void setAuthed_type(String authed_type) {
        this.authed_type = authed_type;
    }
}
