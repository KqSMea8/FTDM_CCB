package com.sunyard.sunfintech.web.model.vo.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.config.GlobalConfig;

/**
 * @author Zz 2017年5月4日
 *
 *         快捷充值响应
 */
public class ApplyQuickPayResponse {

	/*
	 * 返回码，10000为成功
	 */
	private String recode;
	/*
	 * 返回结果描述
	 */
	private String remsg;
	/*
	 * 返回业务数据
	 */
	private String data;
	@JSONField(serialize=false)
	private DateDetail datedetail;
	/*
	 * 商户请求订单号
	 */
	private String order_no;

	/*
	 * 订单状态0:已接受, 1:处理中,2:处理成功,3:处理失败
	 */
	private String order_status;

	/*
	 * 系统处理日期(yyyyMMddHHmmss))
	 */
	private String process_date;
	/*
	 * 平台流水号
	 */
	private String query_id;
	/*
	 * 支付通道流水号
	 */
	private String host_req_serial_no;
	/*
	 * 签名数据
	 */
	private String signdata;
	public String getRecode() {
		return recode;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	public String getRemsg() {
		return remsg;
	}
	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
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
	public String getHost_req_serial_no() {
		return host_req_serial_no;
	}
	public void setHost_req_serial_no(String host_req_serial_no) {
		this.host_req_serial_no = host_req_serial_no;
	}
	public String getSigndata() {
		return signdata;
	}
	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}
	public DateDetail getDatedetail() {
		return datedetail;
	}
	public void setDatedetail(DateDetail datedetail) {
		this.datedetail = datedetail;
		setData(JSON.toJSONString(datedetail,GlobalConfig.serializerFeature ));

	}
	
	
}
