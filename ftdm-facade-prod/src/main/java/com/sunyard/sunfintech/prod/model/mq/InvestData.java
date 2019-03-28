package com.sunyard.sunfintech.prod.model.mq;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestData;

import java.io.Serializable;

/**
 * Created by terry on 2017/7/28.
 */
public class InvestData implements Serializable {

    private BaseRequest baseRequest;
    private ProdProductinfo prodProductinfo;
    private ProdInvestData prodInvestData;
    private String notifyURL;
    private String time;

    public boolean getAuthValid() {
        return authValid;
    }

    public void setAuthValid(boolean authValid) {
        this.authValid = authValid;
    }

    private  boolean authValid;
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

    public ProdInvestData getProdInvestData() {
        return prodInvestData;
    }

    public void setProdInvestData(ProdInvestData prodInvestData) {

        this.prodInvestData = prodInvestData;
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
