package com.sunyard.sunfintech.web.model.vo.product;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestData;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestDataTb;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestNoSynResquest;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdBatchInvestResquest extends BaseRequest {

    public ProdInvestNoSynResquest getProdInvestNoSynResquest() {
        ProdInvestNoSynResquest prodInvestNoSynResquest = new ProdInvestNoSynResquest();
        BeanUtils.copyProperties(this, prodInvestNoSynResquest);
        List<ProdInvestDataTb> tblist = this.getDataArray();
        List<ProdInvestData> asyncList = new ArrayList<>();
        if (tblist != null && tblist.size() > 0) {
            for (ProdInvestDataTb investData :tblist) {
                ProdInvestData asyncInvestItem = investData.getAsyncData(prod_id);
                asyncList.add(asyncInvestItem);
            }
        }
        prodInvestNoSynResquest.setDataArray(asyncList);
        return prodInvestNoSynResquest;
    }
    @NotNull
    private Integer total_num;

    @NotBlank
    private String prod_id;

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        super.setBase_serial_notify_url(notify_url);
        this.notify_url = notify_url;
    }

    @NotBlank

    private String   notify_url;
    @NotBlank
    private String data;
    private List<ProdInvestDataTb> dataArray;

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        setDataArray(JSON.parseArray(data,ProdInvestDataTb.class));
    }

    public List<ProdInvestDataTb> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<ProdInvestDataTb> dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public String toString() {
        return "ProdBatchInvestResquest{" +
                super.toString()+", "+
                "total_num=" + total_num +
                ", prod_id='" + prod_id + '\'' +
                ", data='" + data + '\'' +
                ", dataArray=" + dataArray +
                '}';
    }
}
