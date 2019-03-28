package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;

import java.io.Serializable;

/**
 * Created by PengZY on 2018/1/30.
 */
public class BatchRwWithdrawMQEntity implements Serializable {

    private BaseRequest baseRequest;

    private DateDetail dateDetail;

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public DateDetail getDateDetail() {
        return dateDetail;
    }

    public void setDateDetail(DateDetail dateDetail) {
        this.dateDetail = dateDetail;
    }
}
