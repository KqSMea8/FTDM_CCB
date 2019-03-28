package com.sunyard.sunfintech.web.model.vo.payment;

import com.sunyard.sunfintech.core.base.BaseResponse;

public class ApplyQuickPayMallResponse extends BaseResponse {
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
	 * 支付订单号
	 */
	private String pay_order_no;
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
	public String getPay_order_no() {
		return pay_order_no;
	}
	public void setPay_order_no(String pay_order_no) {
		this.pay_order_no = pay_order_no;
	}
	public String getSign() {
		return signdata;
	}
	public void setSign(String sign) {
		this.signdata = sign;
	}
	
	
}
