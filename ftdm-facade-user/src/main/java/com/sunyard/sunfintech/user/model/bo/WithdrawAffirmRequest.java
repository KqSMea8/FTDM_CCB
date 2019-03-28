package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by PengZY on 2018/1/16.
 * 提现确认
 */
public class WithdrawAffirmRequest extends BaseRequest {

    /*
     * 总数量
     */
    @NotNull
    private Integer total_num;

    /*
     * JsonArray明细数据
     */
    @NotBlank
    private String data;

    @JSONField(serialize=false)
    private List<DateDetailAffirm> datedetail;

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.datedetail = JSON.parseArray(data,DateDetailAffirm.class);
    }

    public List<DateDetailAffirm> getDatedetail() {
        return datedetail;
    }

    public void setDatedetail(List<DateDetailAffirm> datedetail) {
        this.datedetail = datedetail;
        setData(JSON.toJSONString(datedetail, GlobalConfig.serializerFeature));
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
