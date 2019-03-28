package com.sunyard.sunfintech.search.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

/**
 * @author RaoYL
 * @version 20180126
 */
public class SearchCompanyAccountBalanceResponse extends BaseResponse{
    private String data;

    @JSONField(serialize = false)
    private SearchCompanyAccountBalanceResponseDetail data_detail;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SearchCompanyAccountBalanceResponseDetail getData_detail() {
        return data_detail;
    }

    public void setData_detail(SearchCompanyAccountBalanceResponseDetail data_detail) {
        this.data_detail = data_detail;
        setData(JSON.toJSONString(data_detail, GlobalConfig.serializerFeature));
    }

    @Override
    public String toString() {
        return "PlatBalanceResponse{" +
                "data='" + data + '\'' +
                ", data_detail=" + data_detail +
                '}';
    }
}
