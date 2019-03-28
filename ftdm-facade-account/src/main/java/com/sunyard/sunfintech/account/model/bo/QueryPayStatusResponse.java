package com.sunyard.sunfintech.account.model.bo;

import java.util.List;

/**
 * Created by terry on 2017/9/8.
 */
public class QueryPayStatusResponse extends YunPayBaseErrorResponse {

    private List<QueryPayStatusResponseDetail> data;

    public List<QueryPayStatusResponseDetail> getData() {
        return data;
    }

    public void setData(List<QueryPayStatusResponseDetail> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else {
            return "QueryPayStatusResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}
