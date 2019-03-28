package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by terry on 2017/5/3.
 */
public class ProdBatchTransferRequestBo extends BaseRequest {

    @NotBlank
    private String notify_url;
    @NotBlank
    private String platcust;
    @NotBlank
    private String prod_id;
    @NotBlank
    private String data;
    private List<ProdBatchTransferRequestDataBoTb> dataArray;

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        setDataArray(JSON.parseArray(data,ProdBatchTransferRequestDataBoTb.class));
    }

    public List<ProdBatchTransferRequestDataBoTb> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<ProdBatchTransferRequestDataBoTb> dataArray) {
        this.dataArray = dataArray;
    }
}
