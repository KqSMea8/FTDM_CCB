package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;

import java.io.Serializable;

/**
 * Created by PengZY on 2018/3/12.
 */
public class BatchRepayAsynMQEntity implements Serializable {

    private BaseRequest baseRequest;

    private BatchRepayRequestDetail batchRepayRequestDetail;

    private String notify;

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public BatchRepayRequestDetail getBatchRepayRequestDetail() {
        return batchRepayRequestDetail;
    }

    public void setBatchRepayRequestDetail(BatchRepayRequestDetail batchRepayRequestDetail) {
        this.batchRepayRequestDetail = batchRepayRequestDetail;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    @Override
    public String toString() {
        return "BatchRepayAsynMQEntity{" +
                "baseRequest=" + baseRequest +
                ", batchRepayRequestDetail=" + batchRepayRequestDetail +
                ", notify='" + notify + '\'' +
                '}';
    }
}
