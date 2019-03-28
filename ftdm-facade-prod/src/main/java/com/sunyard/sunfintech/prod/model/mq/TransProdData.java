package com.sunyard.sunfintech.prod.model.mq;

import com.sunyard.sunfintech.prod.model.bo.ProdTransferDebtRequestBo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by terry on 2017/7/28.
 */
public class TransProdData implements Serializable {

    private List<ProdTransferDebtRequestBo> prodTransferDebtRequestBoList;
    private String notifyURL;
    private String time;

    public List<ProdTransferDebtRequestBo> getProdTransferDebtRequestBoList() {
        return prodTransferDebtRequestBoList;
    }

    public void setProdTransferDebtRequestBoList(List<ProdTransferDebtRequestBo> prodTransferDebtRequestBoList) {
        this.prodTransferDebtRequestBoList = prodTransferDebtRequestBoList;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
