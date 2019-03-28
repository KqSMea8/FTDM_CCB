package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class BatchCollectionRequest extends BaseRequest {
    @NotBlank
    private String pay_code;
    @NotBlank
    private String notify_url;
    @Min(1)
    private int total_num;
    @DecimalMin("0.001")
    private BigDecimal total_balance;
    @NotNull
    private String  data;

    private List<CollectionDetail> collectionDetailList;

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public BigDecimal getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(BigDecimal total_balance) {
        this.total_balance = total_balance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        if(StringUtils.isNoneBlank(data)){
            collectionDetailList = JSON.parseArray(data,CollectionDetail.class);
        }
    }

    public List<CollectionDetail> getCollectionDetailList() {
        return collectionDetailList;
    }

    public void setCollectionDetailList(List<CollectionDetail> collectionDetailList) {
        this.collectionDetailList = collectionDetailList;
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
