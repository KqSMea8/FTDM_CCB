package com.sunyard.sunfintech.web.model.vo.account;

import java.util.List;

import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.user.model.bo.UnBindCardSuccess;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.util.Strings;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

/**
 * 
 * 批量换卡（解绑+换卡申请）  ---响应modal---
 * @author PengZY
 * 
 */
public class UnBindCardResponse extends BaseResponse{
	
	//平台编号（批量订单受理成功时，则必填）
	private String plat_no;
	
	//处理完成时间(批量订单受理成功时，则必填)
	private String finish_datetime;
	
	//订单状态(批量订单受理成功时，则必填)
	private String order_status;
	
	//订单处理信息(批量订单受理成功时，则必填)
	private String order_info;
	
	//订单请求数量(批量订单受理成功时，则必填)
	private String total_num;
	
	//成功数量(批量订单受理成功时，则必填)
	private String success_num;
	
	//成功信息（有成功处理的数据时，则为必填参数；其中detail_no、platcust为必填信息）
	private String success_data;
	
	@JSONField(serialize=false)
	private List<UnBindCardSuccess> success_data_detail;
	
	//失败信息
	private String error_data;
	
	@JSONField(serialize=false)
	private List<BaseErrorData> error_data_detail;

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getFinish_datetime() {
		return finish_datetime;
	}

	public void setFinish_datetime(String finish_datetime) {
		this.finish_datetime = finish_datetime;
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
		if(Strings.isBlank(success_data)){
			this.success_data_detail = JSON.parseArray(success_data, UnBindCardSuccess.class);
		}
	}

	public List<UnBindCardSuccess> getSuccess_data_detail() {
		return success_data_detail;
	}

	public void setSuccess_data_detail(List<UnBindCardSuccess> success_data_detail) {
		this.success_data_detail = success_data_detail;
		if(success_data_detail != null && !success_data_detail.isEmpty()){
			this.success_data = JSON.toJSONString(success_data_detail,GlobalConfig.serializerFeature);
		}
	}

	public String getError_data() {
		return error_data;
	}

	public void setError_data(String error_data) {
		this.error_data = error_data;
		if(Strings.isBlank(error_data)){
			this.error_data_detail = JSON.parseArray(error_data, BaseErrorData.class);
		}
	}

	public List<BaseErrorData> getError_data_detail() {
		return error_data_detail;
	}

	public void setError_data_detail(List<BaseErrorData> error_data_detail) {
		this.error_data_detail = error_data_detail;
		if(error_data_detail != null && !error_data_detail.isEmpty()){
			this.error_data = JSON.toJSONString(error_data_detail,GlobalConfig.serializerFeature);
		}
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
