package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class ApplyAuthOperaRequest extends BaseRequest{
    /**
     * 平台客户编号
     */
    @NotBlank
    private String platcust;

    /**
     * 授权类型（1、出借，2、还款，3、缴费，4、全部）
     */
    @NotBlank
    private String authed_type;

    /**
     * 授权金额
     */
    @DecimalMin(value = "0.001")
    private BigDecimal authed_amount;

    /**
     * 授权期限 YYYYMMDD
     */
    @NotBlank
    private String authed_limittime;

    private String is_page;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
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

    public String getIs_page() {
        return is_page;
    }

    public void setIs_page(String is_page) {
        this.is_page = is_page;
    }

    @Override
    public String toString() {
        return "ApplyAuthOperaRequest{" +
                "platcust='" + platcust + '\'' +
                ", authed_type='" + authed_type + '\'' +
                ", authed_amount=" + authed_amount +
                ", authed_limittime='" + authed_limittime + '\'' +
                ", is_page='" + is_page + '\'' +
                '}';
    }
}
