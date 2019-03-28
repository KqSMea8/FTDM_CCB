package com.sunyard.sunfintech.web.model.vo.platform;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 账户流水单据下载
 * Created by wubin on 2017/5/5.
 */
public class DownloadAccountListRequest extends BaseRequest {

    /**
     * 平台客户号
     */
    @NotBlank
    private String platcust;

    /**
     * 开始时间 (yyyyMMdd)
     */
    @NotNull
    private String start_date;

    /**
     * 结束日期(yyyyMMdd)
     */
    @NotNull
    private String end_date;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "DownloadAccountListRequest{" +
                "platcust='" + platcust + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                '}';
    }
}
