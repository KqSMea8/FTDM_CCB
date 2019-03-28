package com.sunyard.sunfintech.web.model.vo.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 平台非存管账户出金    --响应model--
 * Created by wubin on 2017/5/5.
 */
public class WithdrawNoDepositoryResponse extends BaseResponse {

    /**
     * 返回业务数据
     */
    @NotBlank
    private String data;

    @JSONField(serialize = false)
    private WithdrawNoDepository withdrawNoDepository;
    /**
     * 系统处理日期(yyyyMMddHHmmss)
     */
    @NotBlank
    private String process_date;

    public WithdrawNoDepository getWithdrawNoDepository() {
        return withdrawNoDepository;
    }

    public void setWithdrawNoDepository(WithdrawNoDepository withdrawNoDepository) {
        this.withdrawNoDepository = withdrawNoDepository;
        if(withdrawNoDepository!=null){
            this.data = JSON.toJSONString(withdrawNoDepository, GlobalConfig.serializerFeature);
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
