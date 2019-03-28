package com.sunyard.sunfintech.account.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 资金变动明细查询请求参数
 * @author RaoYL
 * @version 20180118
 */
public class FundChangeRequest extends BaseRequest{
    //平台客户号
    @NotBlank
    private String platcust ;

    //查询起始时间
    @DateTimeFormat(pattern="yyyyMMdd" )
    private Date start_date;

    //查询结束时间
    @DateTimeFormat(pattern="yyyyMMdd")
    private Date end_date;

    //交易名称(提现、标的出账、债权转让、标的成立、标的废弃、标的出账、标的还款、借款人还款、充值、投融资转换、转账)
    private String trans_name;

    //分页大小
    private Integer pagesize;

    //分页页码：从1开始
    private Integer pagenum;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    @Override
    public int hashCode(){return HashCodeBuilder.reflectionHashCode(this);}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
