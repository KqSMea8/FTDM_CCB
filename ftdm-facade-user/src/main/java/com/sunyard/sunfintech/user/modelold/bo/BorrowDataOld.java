package com.sunyard.sunfintech.user.modelold.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;

import java.io.Serializable;

/**
 * Created by PengZY on 2018/3/5.
 */
public class BorrowDataOld implements Serializable {

    private BaseRequest baseRequest;
    private ProdProductinfo prodProductinfo;
    private BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn;
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

    public BatchRepayRequestDetailAsyn getBatchRepayRequestDetailAsyn() {
        return batchRepayRequestDetailAsyn;
    }

    public void setBatchRepayRequestDetailAsyn(BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn) {
        this.batchRepayRequestDetailAsyn = batchRepayRequestDetailAsyn;
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
