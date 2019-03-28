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
public class ProdBatchRepayOldRequest extends BaseRequest {

    /**
     * 标的编号
     */
    @NotBlank
    private String prod_id;

    /**
     * 还款期数，如果一次性还款，repay_num为1
     */
    @NotNull
    private Integer repay_num;

    /**
     * 是否整个标的还清：0-是，1-否；
     */
    @NotBlank
    private String is_payoff;

    /**
     * 交易金额（所有实际还款金额之和）
     */
    @NotNull
    private BigDecimal trans_amt;

    /**
     * 本期已还清：0-是，1-否
     */
    @NotBlank
    private String repay_flag;

    /**
     * 资金数据，json格式记录还款金额
     */
    @NotBlank
    private String funddata;
    private List<BatchCustRepayOld> custRepayList;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public Integer getRepay_num() {
        return repay_num;
    }

    public void setRepay_num(Integer repay_num) {
        this.repay_num = repay_num;
    }

    public String getIs_payoff() {
        return is_payoff;
    }

    public void setIs_payoff(String is_payoff) {
        this.is_payoff = is_payoff;
    }

    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    public String getRepay_flag() {
        return repay_flag;
    }

    public void setRepay_flag(String repay_flag) {
        this.repay_flag = repay_flag;
    }

    public String getFunddata() {
        return funddata;
    }

    public void setFunddata(String funddata) {
        this.funddata = funddata;
        setCustRepayList(JSON.parseArray(funddata,BatchCustRepayOld.class));
    }

    public List<BatchCustRepayOld> getCustRepayList() {
        return custRepayList;
    }

    public void setCustRepayList(List<BatchCustRepayOld> custRepayList) {
        this.custRepayList = custRepayList;
    }

    @Override
    public String toString() {
        return "ProdBatchRepayRequest{" +
                super.toString()+","+
                "prod_id='" + prod_id + '\'' +
                ", repay_num=" + repay_num +
                ", is_payoff='" + is_payoff + '\'' +
                ", trans_amt=" + trans_amt +
                ", repay_flag='" + repay_flag + '\'' +
                ", funddata='" + funddata + '\'' +
                ", custRepayList=" + custRepayList +
                '}';
    }
}
