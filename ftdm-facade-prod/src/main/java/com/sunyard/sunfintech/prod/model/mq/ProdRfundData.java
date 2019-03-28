package com.sunyard.sunfintech.prod.model.mq;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;

import java.io.Serializable;

/**
 * Created by terry on 2017/7/28.
 */
public class ProdRfundData implements Serializable {

    private BaseRequest baseRequest;
    private ProdProductinfo prodProductinfo;
    private BatchCustRepay batchCustRepay;
    private String notifyURL;
    private String time;

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public ProdProductinfo getProdProductinfo() {
        return prodProductinfo;
    }

    public void setProdProductinfo(ProdProductinfo prodProductinfo) {
        this.prodProductinfo = prodProductinfo;
    }

    public BatchCustRepay getBatchCustRepay() {
        return batchCustRepay;
    }

    public void setBatchCustRepay(BatchCustRepay batchCustRepay) {
        this.batchCustRepay = batchCustRepay;
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
