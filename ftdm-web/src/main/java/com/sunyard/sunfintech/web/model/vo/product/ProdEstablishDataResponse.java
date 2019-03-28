package com.sunyard.sunfintech.web.model.vo.product;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


/**
 * @author Ycv
 */
public class ProdEstablishDataResponse implements Serializable{
	
    /**
     * 订单状态0:已接受, 1:处理中,2:处理成功,3:处理失败
     */
	@NotBlank
    private String order_status;

    /**
     * 系统处理日期
     */
	@NotBlank
    private String process_date;

    /**
     * 平台流水号
     */
	@NotBlank
    private String query_id;

   

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
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
