package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdBatchRepayRequest extends BaseRequest {

    /**
     * 交易金额（所有实际还款金额之和）
     */
    @NotNull
    private BigDecimal trans_amt;

    /**
     * 异步通知地址
     */
    @NotBlank
    private String notify_url;

    /**
     * 资金数据，json格式记录还款金额
     */
    @NotBlank
    private String funddata;
    private List<BatchCustRepay> custRepayList;

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public String getFunddata() {
        return funddata;
    }

    public void setFunddata(String funddata) {
        this.funddata = funddata;
        setCustRepayList(JSON.parseArray(funddata,BatchCustRepay.class));
    }

    public List<BatchCustRepay> getCustRepayList() {
        return custRepayList;
    }

    public void setCustRepayList(List<BatchCustRepay> custRepayList) {
        this.custRepayList = custRepayList;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
