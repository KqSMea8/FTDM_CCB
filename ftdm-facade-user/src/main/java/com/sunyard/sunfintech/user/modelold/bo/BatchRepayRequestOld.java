package com.sunyard.sunfintech.user.modelold.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.user.model.bo.BatchRepayRequestDetail;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by PengZY on 2018/3/5.
 */
public class BatchRepayRequestOld extends BaseRequest {

    /**
     * 批量明细数据
     */
    @NotBlank
    private String data;

    private List<BatchRepayRequestDetailOld> batchRepayRequestDetailList;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        if(StringUtils.isNoneBlank(data)){
            batchRepayRequestDetailList = JSON.parseArray(data,BatchRepayRequestDetailOld.class);
        }
    }

    public List<BatchRepayRequestDetailOld> getBatchRepayRequestDetailList() {
        return batchRepayRequestDetailList;
    }

    public void setBatchRepayRequestDetailList(List<BatchRepayRequestDetailOld> batchRepayRequestDetailList) {
        this.batchRepayRequestDetailList = batchRepayRequestDetailList;
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
