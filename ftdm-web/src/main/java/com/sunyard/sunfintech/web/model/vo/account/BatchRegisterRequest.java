package com.sunyard.sunfintech.web.model.vo.account;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.user.model.bo.BatchRegisterRequestDetail;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量开户(四要素绑卡)
 * 
 * Created by heroy on 2017/4/6.
 */
public class BatchRegisterRequest extends BaseRequest {

	//总数量
    @NotNull
    private Long total_num;

    //JsonArray，批量明细数据
    @NotBlank
    private String data;

    private List<BatchRegisterRequestDetail> dataObject;

    public String getData() {
        return data;
    }

    public Long getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Long total_num) {
        this.total_num = total_num;
    }

    public void setData(String data) {
        this.data = data;
        if(StringUtils.isNoneBlank(data)) {
            this.dataObject = JSON.parseArray(data,BatchRegisterRequestDetail.class );
        }
    }

    public List<BatchRegisterRequestDetail> getDataObject() {
        return dataObject;
    }

    public void setDataObject(List<BatchRegisterRequestDetail> dataObject) {
        this.dataObject = dataObject;
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