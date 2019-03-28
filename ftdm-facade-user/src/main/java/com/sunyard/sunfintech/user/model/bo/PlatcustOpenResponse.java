package com.sunyard.sunfintech.user.model.bo;

import java.util.List;

/**
 * Created by dany on 2017/6/2.
 */
public class PlatcustOpenResponse extends YunPayBaseErrorResponse {
    private List<PlatcustOpenData> data;

    public List<PlatcustOpenData> getData() {
        return data;
    }

    public void setData(List<PlatcustOpenData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else {
            return "PlatcustOpenResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}
