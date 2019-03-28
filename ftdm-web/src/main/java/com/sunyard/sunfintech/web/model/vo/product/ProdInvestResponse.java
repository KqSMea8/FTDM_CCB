package com.sunyard.sunfintech.web.model.vo.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestSuccessData;

import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestResponse extends BaseResponse {

    private String order_status;

    private String order_info;

    private String trans_date;

    @Override
    public String getOrder_status() {
        return order_status;
    }

    @Override
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_info() {
        return order_info;
    }

    public void setOrder_info(String order_info) {
        this.order_info = order_info;
    }

    @Override
    public String getTrans_date() {
        return trans_date;
    }

    @Override
    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }
}
