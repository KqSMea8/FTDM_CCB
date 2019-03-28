package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdAbortInvestmentResponse extends BaseResponse {

    private String data;
    @JSONField(serialize=false)
    private ProdAbortInvestment data_obj;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ProdAbortInvestment getData_obj() {
        return data_obj;
    }

    public void setData_obj(ProdAbortInvestment data_obj) {
        this.data_obj = data_obj;
        setData(JSON.toJSONString(data_obj, GlobalConfig.serializerFeature));
    }
}
