package com.sunyard.sunfintech.thirdparty.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by terry on 2017/6/2.
 */
public class AccountVerifyResponse extends YunPayBaseErrorResponse implements Serializable {

    //成功数据
    private List<AccountVerifyResponseData> data;

    public List<AccountVerifyResponseData> getData() {
        return data;
    }

    public void setData(List<AccountVerifyResponseData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else {
            return "AccountVerifyResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}

