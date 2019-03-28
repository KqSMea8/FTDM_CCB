package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.annotation.SerialNo;
import com.sunyard.sunfintech.core.annotation.SerialNoDetail;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestNoSynResquest extends BaseRequest {

    @NotNull
    private Integer total_num;

    @NotBlank
    private String notify_url;

    @NotBlank
    private String data;
    @SerialNoDetail(isBatch = true)
    private List<ProdInvestData> dataArray;

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        super.setBase_serial_notify_url(notify_url);
        this.notify_url = notify_url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        setDataArray(JSON.parseArray(data,ProdInvestData.class));
    }

    public List<ProdInvestData> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<ProdInvestData> dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public String toString() {
        return "ProdInvestNoSynResquest{" +
                "total_num=" + total_num +
                ", notify_url='" + notify_url + '\'' +
                ", data='" + data + '\'' +
                ", dataArray=" + dataArray +
                '}';
    }
}
