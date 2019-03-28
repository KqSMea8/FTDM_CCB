package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PengZY on 2017/7/14.
 */
public class CashierBalanceQueryReturn extends YunPayBaseErrorResponse implements Serializable {

    private List<CashierBalanceQueryReturnDetail> data;

    public List<CashierBalanceQueryReturnDetail> getData() {
        return data;
    }

    public void setData(List<CashierBalanceQueryReturnDetail> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(data==null){
            return super.toString(this.getClass().getSimpleName());
        }else {
            return "CashierBalanceQueryReturn{" +
                    "data=" + data +
                    '}';
        }
    }

}
