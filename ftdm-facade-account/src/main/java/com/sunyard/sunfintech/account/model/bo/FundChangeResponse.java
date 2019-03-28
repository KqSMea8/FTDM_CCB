package com.sunyard.sunfintech.account.model.bo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 *
 * 资金变动明细查询   ---响应modal---
 * @author RaoYL
 * @version 20180118
 */
public class FundChangeResponse extends BaseResponse{

    //返回的业务数据
    private String data;

    @JSONField(serialize=false)
    private List<FundChangeResponseDetail> data_detail;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<FundChangeResponseDetail> getData_detail() {
        return data_detail;
    }

    public void setData_detail(List<FundChangeResponseDetail> data_detail) {
        this.data_detail = data_detail;
        setData(JSON.toJSONString(data_detail, GlobalConfig.serializerFeature));
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
