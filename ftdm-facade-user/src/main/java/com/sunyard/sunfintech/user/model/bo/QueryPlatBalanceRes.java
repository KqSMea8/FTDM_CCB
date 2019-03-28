package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * Created by dingjy on 2017/6/4.
 */
public class QueryPlatBalanceRes extends BaseResponse {
    private PlatBalanceData data;

    public PlatBalanceData getData() {
        return data;
    }

    public void setData(PlatBalanceData data) {
        this.data = data;
    }
}
