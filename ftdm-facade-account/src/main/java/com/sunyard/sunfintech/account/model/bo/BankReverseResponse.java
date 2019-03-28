package com.sunyard.sunfintech.account.model.bo;

import java.util.List;

/**
 * Created by terry on 2017/9/13.
 */
public class BankReverseResponse extends YunPayBaseErrorResponse {

    private List<BankReverseResponseDetail> data;

    public List<BankReverseResponseDetail> getData() {
        return data;
    }

    public void setData(List<BankReverseResponseDetail> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else {
            return "BankReverseResponse{" +
                    "data=" + data +
                    '}';
        }
    }
}
