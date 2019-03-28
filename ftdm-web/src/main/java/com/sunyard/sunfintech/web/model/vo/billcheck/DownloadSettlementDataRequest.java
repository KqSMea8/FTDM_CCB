package com.sunyard.sunfintech.web.model.vo.billcheck;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 清算数据下载
 * Created by wubin on 2017/5/5.
 */
public class DownloadSettlementDataRequest extends BaseRequest {

    /**
     * 商户平台在资金账户管理平台注册的平台编号
     */
    @NotBlank
    private String plat_no;

    /**
     * 清算日期(yyyyMMdd)
     */
    @NotNull
    private Date clear_date;

    /**
     * 签名数据
     */
    @NotBlank
    private String signdata;

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public Date getClear_date() {
        return clear_date;
    }

    public void setClear_date(Date clear_date) {
        this.clear_date = clear_date;
    }

    public String getSigndata() {
        return signdata;
    }

    public void setSigndata(String signdata) {
        this.signdata = signdata;
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
