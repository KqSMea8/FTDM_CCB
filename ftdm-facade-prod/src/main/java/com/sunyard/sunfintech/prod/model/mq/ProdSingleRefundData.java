package com.sunyard.sunfintech.prod.model.mq;

import com.sunyard.sunfintech.dao.entity.EaccUserinfo;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;

import java.io.Serializable;

/**
 * Created by terry on 2017/8/7.
 */
public class ProdSingleRefundData implements Serializable {

    private BatchCustRepay custRepay;
    private ProdProductinfo productinfo;
    private EaccUserinfo eaccUserinfo;
    private String time;

    public BatchCustRepay getCustRepay() {
        return custRepay;
    }

    public void setCustRepay(BatchCustRepay custRepay) {
        this.custRepay = custRepay;
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

    public EaccUserinfo getEaccUserinfo() {
        return eaccUserinfo;
    }

    public void setEaccUserinfo(EaccUserinfo eaccUserinfo) {
        this.eaccUserinfo = eaccUserinfo;
    }
}
