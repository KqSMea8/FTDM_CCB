package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zz
 *2017年5月4日
 */
public class BatchWithdrawResponse  extends BaseResponse {

	/*
	 * 平台编号(批量订单受理成功时，则必填)
	 */
	private String plat_no;
	/*
	 * 处理完成时间
	 */
	private String finish_datetime;
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
	private Integer total_num;
	/*
	 * 成功数量
	 */
	private Integer success_num;
	/*
	 * 成功信息
	 */
	private String success_data;

	@JSONField(serialize = false)
	private List<SuccessData> success_data_detail;

	/*
	 * 失败信息
	 */
	private String error_data;

	@JSONField(serialize = false)
	private List<ErrorData> error_data_detail;

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
		super.setOrder_status(order_status);
		this.order_status = order_status;
	}

	public String getOrder_info() {
		return order_info;
	}

	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}

	public Integer getSuccess_num() {
		return success_num;
	}

	public void setSuccess_num(Integer success_num) {
		this.success_num = success_num;
	}

	public String getSuccess_data() {
		return success_data;
	}

	public void setSuccess_data(String success_data) {
		this.success_data = success_data;
	}

	public String getError_data() {
		return error_data;
	}

	public void setError_data(String error_data) {
		this.error_data = error_data;
	}

	public List<SuccessData> getSuccess_data_detail() {
		return success_data_detail;
	}

	public void setSuccess_data_detail(List<SuccessData> success_data_detail) {
		this.success_data_detail = success_data_detail;
		setSuccess_data(JSON.toJSONString(success_data_detail, GlobalConfig.serializerFeature));
	}

	public List<ErrorData> getError_data_detail() {
		return error_data_detail;
	}

	public void setError_data_detail(List<ErrorData> error_data_detail) {
		this.error_data_detail = error_data_detail;
		setError_data(JSON.toJSONString(error_data_detail, GlobalConfig.serializerFeature));
	}

	@Override
	public String toString() {
		return "BatchWithdrawResponse{" +
				"plat_no='" + plat_no + '\'' +
				", finish_datetime='" + finish_datetime + '\'' +
				", order_status='" + order_status + '\'' +
				", order_info='" + order_info + '\'' +
				", total_num='" + total_num + '\'' +
				", success_num=" + success_num +
				", success_data='" + success_data + '\'' +
				", success_data_detail=" + success_data_detail +
				", error_data='" + error_data + '\'' +
				", error_data_detail=" + error_data_detail +
				'}';
	}
}
