package com.sunyard.sunfintech.account.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;


/**
 * Created by my on 2018/5/2.
 */
public class EaccountPordListRequest extends BaseRequest {

    private  String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
