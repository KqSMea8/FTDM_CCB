package com.sunyard.sunfintech.prod.model.mq;

import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;

import java.io.Serializable;
import java.util.List;

/**
 * Created by terry on 2017/8/7.
 */
public class ProdRefundData implements Serializable {

    private List<BatchCustRepay> custRepayList;
    private ProdProductinfo productinfo;
    private String time;

    public List<BatchCustRepay> getCustRepayList() {
        return custRepayList;
    }

    public void setCustRepayList(List<BatchCustRepay> custRepayList) {
        this.custRepayList = custRepayList;
    }

    public ProdProductinfo getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(ProdProductinfo productinfo) {
        this.productinfo = productinfo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ProdRefundData{" +
                "custRepayList=" + custRepayList +
                ", productinfo=" + productinfo +
                ", time='" + time + '\'' +
                '}';
    }
}
