package com.sunyard.sunfintech.account.model.bo;

import java.util.List;

/**
 * Created by terry on 2017/6/27.
 */
public class EpayAgentPayRealResponse extends YunPayBaseErrorResponse {

    private List<EpayAgentPayRealResponseDetail> data;

    public List<EpayAgentPayRealResponseDetail> getData() {
        return data;
    }

    public void setData(List<EpayAgentPayRealResponseDetail> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else {
            return "EpayAgentPayRealResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}
