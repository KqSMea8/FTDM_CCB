package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

import java.util.List;

/**
 *@author RaoYL
 * 平台真实账务往来响应参数
 */
public class PlatAccountDetailResponse extends BaseResponse{
    private String data;


    @JSONField(serialize = false)
    private List<PlatAccountDetailResponseList> data_detail;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<PlatAccountDetailResponseList> getData_detail() {
        return data_detail;
    }

    public void setData_detail(List<PlatAccountDetailResponseList> data_detail) {
        this.data_detail = data_detail;
        setData(JSON.toJSONString(data_detail, GlobalConfig.serializerFeature));
    }

    @Override
    public String toString() {
        return "AccountingDetailHxResponse{" +
                "data='" + data + '\'' +
                ", data_detail=" + data_detail +
                '}';
    }
}
