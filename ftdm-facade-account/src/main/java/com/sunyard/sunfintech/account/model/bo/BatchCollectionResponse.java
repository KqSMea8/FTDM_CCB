package com.sunyard.sunfintech.account.model.bo;

import java.util.List;

/**
 * Created by terry on 2017/7/16.
 */
public class BatchCollectionResponse extends YunPayBaseErrorResponse {

    private List<BatchCollectionResponseData> data;

    public List<BatchCollectionResponseData> getData() {
        return data;
    }

    public void setData(List<BatchCollectionResponseData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else{
            return "BatchCollectionResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}
