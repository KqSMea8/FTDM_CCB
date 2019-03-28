package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by PengZY on 2017/7/14.
 */
public class RealEaccountBalanceResponseDetail extends BaseResponse {

    //户名
    private String client_name;

    //电子账号
    private String eaccount;

    //开户行
    private String open_bank;

    private String data;

    @JSONField(serialize = false)
    private List<SubAcctBulkPackage> subAcctBulkPackages;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<SubAcctBulkPackage> getSubAcctBulkPackages() {
        return subAcctBulkPackages;
    }

    public void setSubAcctBulkPackages(List<SubAcctBulkPackage> subAcctBulkPackages) {
        this.subAcctBulkPackages = subAcctBulkPackages;
        setData(JSON.toJSONString(subAcctBulkPackages, GlobalConfig.serializerFeature));
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getEaccount() {
        return eaccount;
    }

    public void setEaccount(String eaccount) {
        this.eaccount = eaccount;
    }

    public String getOpen_bank() {
        return open_bank;
    }

    public void setOpen_bank(String open_bank) {
        this.open_bank = open_bank;
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
