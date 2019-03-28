package com.sunyard.sunfintech.web.model.vo.product;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;

/**
 * @author Zz
 *2017年5月3日
 *批量投标
 */
public class ProdBatchExtResponse {

	
	private String plat_no;
	
	/*
	 * 订单编号
	 */
	private String order_no;
	
	/*
	 * 处理完成时间
	 */
	private String finish_datetime;
	
	/*
	 * 签名数据
	 */
	private String signdata;
	
	/*
	 * 订单状态
	 */
	private String order_status;
	
	/*
	 * 订单处理信息
	 */
	private String order_info;
	
	/*
	 * 订单请求数量
	 */
	private String total_num;
	
	/*
	 * 成功数量
	 */
	private String success_num;
	
	/*
	 * 成功的信息
	 */

	private String success_data;
	@JSONField(serialize=false)
	private List<ProdBatchExtResponseSuccess> success_data_obj;
	/*
	 * 失败信息
	 */
	private String error_data;
	@JSONField(serialize=false)
	private List<ProdBatchExtResponseError> error_data_obj;
	
	
	
	public String getPlat_no() {
		return plat_no;
	}
	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getFinish_datetime() {
		return finish_datetime;
	}
	public void setFinish_datetime(String finish_datetime) {
		this.finish_datetime = finish_datetime;
	}
	public String getSigndata() {
		return signdata;
	}
	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getOrder_info() {
		return order_info;
	}
	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getSuccess_num() {
		return success_num;
	}
	public void setSuccess_num(String success_num) {
		this.success_num = success_num;
	}

	public String getSuccess_data() {
		return success_data;
	}

	public void setSuccess_data(String success_data) {
		this.success_data = success_data;
	}

	public List<ProdBatchExtResponseSuccess> getSuccess_data_obj() {
		return success_data_obj;
	}

	public void setSuccess_data_obj(List<ProdBatchExtResponseSuccess> success_data_obj) {
		this.success_data_obj = success_data_obj;
		setSuccess_data(JSON.toJSONString(success_data_obj, GlobalConfig.serializerFeature));
	}

	public String getError_data() {
		return error_data;
	}

	public void setError_data(String error_data) {
		this.error_data = error_data;
	}

	public List<ProdBatchExtResponseError> getError_data_obj() {
		return error_data_obj;
	}

	public void setError_data_obj(List<ProdBatchExtResponseError> error_data_obj) {
		this.error_data_obj = error_data_obj;
		setError_data(JSON.toJSONString(error_data_obj,GlobalConfig.serializerFeature));
	}

	//TODO hashcode  toString equals
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
