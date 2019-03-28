package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

/**
 * @author RaoYL
 * @version 20180126
 */
public class CompanyAccountBalanceResponse extends BaseResponse{
    private String data;

    @JSONField(serialize = false)
    private CompanyAccountBalanceResponseDetail data_detail;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CompanyAccountBalanceResponseDetail getData_detail() {
        return data_detail;
    }

    public void setData_detail(CompanyAccountBalanceResponseDetail data_detail) {
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
