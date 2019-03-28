package com.sunyard.sunfintech.user.model.bo;

import java.util.List;

/**
 * Created by terry on 2017/7/13.
 */
public class BatchPayResponse extends YunPayBaseErrorResponse {
    private List<BatchPayResponseData> data;

    public List<BatchPayResponseData> getData() {
        return data;
    }

    public void setData(List<BatchPayResponseData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else {
            return "BatchPayResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}
