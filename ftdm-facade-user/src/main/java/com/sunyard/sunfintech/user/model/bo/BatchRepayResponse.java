package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 借款人批量还款  --响应model--
 * Created by wubin on 2017/5/3.
 */
public class BatchRepayResponse extends BaseResponse {

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 处理完成时间
     */
    private String finish_datetime;

    /**
     * 订单状态
     */
    private String order_status;

    /**
     * 订单处理信息
     */
    private String order_info;

    /**
     * 订单请求数量
     */
    private Integer total_num;

    /**
     * 成功数量
     */
    private Integer success_num;

    /**
     * 成功信息
     */
    private String success_data;

    @JSONField(serialize=false)
    private List<BatchRepaySuccessData> successDataList;

    /**
     * 失败信息
     */
    private String error_data;

    @JSONField(serialize=false)
    private List<BatchRepayErrorData> errorDataList;

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

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public Integer getSuccess_num() {
        return success_num;
    }

    public void setSuccess_num(Integer success_num) {
        this.success_num = success_num;
    }

    public String getSuccess_data() {
        return success_data;
    }

    public void setSuccess_data(String success_data) {
        this.success_data = success_data;
    }

    public List<BatchRepaySuccessData> getSuccessDataList() {
        return successDataList;
    }

    public void setSuccessDataList(List<BatchRepaySuccessData> successDataList) {
        this.successDataList = successDataList;
        if(successDataList!=null){
            this.success_data = JSON.toJSONString(successDataList, GlobalConfig.serializerFeature);
        }
    }

    public String getError_data() {
        return error_data;
    }

    public void setError_data(String error_data) {
        this.error_data = error_data;
    }

    public List<BatchRepayErrorData> getErrorDataList() {
        return errorDataList;
    }

    public void setErrorDataList(List<BatchRepayErrorData> errorDataList) {
        this.errorDataList = errorDataList;
        if(errorDataList!=null){
            this.error_data = JSON.toJSONString(errorDataList,GlobalConfig.serializerFeature);
        }
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
