package com.sunyard.sunfintech.web.model.vo.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.prod.model.bo.ProdBatchTransferSuccessData;

import java.util.List;

/**
 * Created by terry on 2017/5/3.
 */
public class ProdBatchTransferResponse extends BaseResponse {

    private String plat_no;
    private String finish_datetime;
    private String order_status;
    private String order_info;
    private String total_num;
    private String success_num;
    private String success_data;
    @JSONField(serialize=false)
    private List<ProdBatchTransferSuccessData> success_data_obj;
    private String error_data;
    @JSONField(serialize=false)
    private List<BaseErrorData> error_data_obj;

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getFinish_datetime() {
        return finish_datetime;
    }

    public void setFinish_datetime(String finish_datetime) {
        this.finish_datetime = finish_datetime;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_info() {
        return order_info;
    }

    public void setOrder_info(String order_info) {
        this.order_info = order_info;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getSuccess_num() {
        return success_num;
    }

    public void setSuccess_num(String success_num) {
        this.success_num = success_num;
    }

    public String getSuccess_data() {
        return success_data;
    }

    public void setSuccess_data(String success_data) {
        this.success_data = success_data;
    }

    public List<ProdBatchTransferSuccessData> getSuccess_data_obj() {
        return success_data_obj;
    }

    public void setSuccess_data_obj(List<ProdBatchTransferSuccessData> success_data_obj) {
        this.success_data_obj = success_data_obj;
        setSuccess_data(JSON.toJSONString(success_data_obj, GlobalConfig.serializerFeature));
    }

    public String getError_data() {
        return error_data;
    }

    public void setError_data(String error_data) {
        this.error_data = error_data;
    }

    public List<BaseErrorData> getError_data_obj() {
        return error_data_obj;
    }

    public void setError_data_obj(List<BaseErrorData> error_data_obj) {
        this.error_data_obj = error_data_obj;
        setError_data(JSON.toJSONString(error_data_obj, GlobalConfig.serializerFeature));
    }
}
