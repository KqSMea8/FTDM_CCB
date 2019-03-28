package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;

import java.io.Serializable;

/**
 * Created by terry on 2017/7/28
 */
public class BorrowData implements Serializable {

    private BaseRequest baseRequest;
    private BatchRepayRequestDetail batchRepayRequestDetail;
    private String notifyURL;
    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }
}
