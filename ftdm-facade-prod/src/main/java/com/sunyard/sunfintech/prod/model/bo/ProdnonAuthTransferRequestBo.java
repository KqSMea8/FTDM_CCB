package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProdnonAuthTransferRequestBo extends BaseRequest {
    @NotBlank
    private String notify_url;
    @NotBlank
    private String data;
    private List<ProdnonAuthTransferRequestDataBo> dataArray;

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ProdnonAuthTransferRequestDataBo> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<ProdnonAuthTransferRequestDataBo> dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdnonAuthTransferRequestBo that = (ProdnonAuthTransferRequestBo) o;
        return Objects.equals(notify_url, that.notify_url) &&
                Objects.equals(data, that.data) &&
                Objects.equals(dataArray, that.dataArray);
    }
    @Override
    public String toString() {
        return "ProdnonAuthTransferRequestBo{" +
                "notify_url='" + notify_url + '\'' +
                ", data='" + data + '\'' +
                ", dataArray=" + dataArray +
                '}';
    }
}
