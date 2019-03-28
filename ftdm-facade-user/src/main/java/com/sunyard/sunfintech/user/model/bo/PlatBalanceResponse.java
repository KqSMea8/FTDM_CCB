package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

/**
 * Created by wubin on 2017/7/27.
 */
public class PlatBalanceResponse extends BaseResponse {

    private String data;

    @JSONField(serialize = false)
    private CompanyAccountDetailData data_detail;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CompanyAccountDetailData getData_detail() {
        return data_detail;
    }

    public void setData_detail(CompanyAccountDetailData data_detail) {
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
