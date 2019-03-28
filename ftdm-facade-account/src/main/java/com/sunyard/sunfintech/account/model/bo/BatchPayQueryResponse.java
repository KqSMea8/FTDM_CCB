package com.sunyard.sunfintech.account.model.bo;

import java.util.List;

/**
 * Created by terry on 2017/7/19.
 */
public class BatchPayQueryResponse extends YunPayBaseErrorResponse {

    private List<BatchPayQueryResponseData> data;

    public List<BatchPayQueryResponseData> getData() {
        return data;
    }

    public void setData(List<BatchPayQueryResponseData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data ==null){
            return super.toString(this.getClass().getSimpleName());
        }else{
            return "BatchPayQueryResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}
