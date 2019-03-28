package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by terry on 2017/5/3.
 */
public class ProdBatchTransferAsynRequestBo extends BaseRequest {

    @NotBlank
    private String notify_url;
    @NotBlank
    private String data;
    private List<ProdBatchTransferRequestDataBo> dataArray;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        setDataArray(JSON.parseArray(data,ProdBatchTransferRequestDataBo.class));
    }

    public List<ProdBatchTransferRequestDataBo> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<ProdBatchTransferRequestDataBo> dataArray) {
        this.dataArray = dataArray;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdBatchTransferAsynRequestBo that = (ProdBatchTransferAsynRequestBo) o;

        if (notify_url != null ? !notify_url.equals(that.notify_url) : that.notify_url != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return dataArray != null ? dataArray.equals(that.dataArray) : that.dataArray == null;
    }

    @Override
    public int hashCode() {
        int result = notify_url != null ? notify_url.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (dataArray != null ? dataArray.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProdBatchTransferAsynRequestBo{" +
                "notify_url='" + notify_url + '\'' +
                ", data='" + data + '\'' +
                ", dataArray=" + dataArray +
                '}';
    }
}
