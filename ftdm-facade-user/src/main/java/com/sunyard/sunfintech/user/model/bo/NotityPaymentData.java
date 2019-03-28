package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

public class NotityPaymentData  implements Serializable{
	private String partner_id;
	private String partner_serial_no;
	private String hsepay_order_no;
	private String order_balance;
	private String pay_finish_date;
	private String pay_finish_time;
	private String real_pay_balance;
	private String pay_status;
	private String host_req_serial_no;
	private String cert_sign;
	private String remark;
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getPartner_serial_no() {
		return partner_serial_no;
	}
	public void setPartner_serial_no(String partner_serial_no) {
		this.partner_serial_no = partner_serial_no;
	}
	public String getHsepay_order_no() {
		return hsepay_order_no;
	}
	public void setHsepay_order_no(String hsepay_order_no) {
		this.hsepay_order_no = hsepay_order_no;
	}
	public String getOrder_balance() {
		return order_balance;
	}
	public void setOrder_balance(String order_balance) {
		this.order_balance = order_balance;
	}
	public String getPay_finish_date() {
		return pay_finish_date;
	}
	public void setPay_finish_date(String pay_finish_date) {
		this.pay_finish_date = pay_finish_date;
	}
	public String getPay_finish_time() {
		return pay_finish_time;
	}
	public void setPay_finish_time(String pay_finish_time) {
		this.pay_finish_time = pay_finish_time;
	}
	public String getReal_pay_balance() {
		return real_pay_balance;
	}
	public void setReal_pay_balance(String real_pay_balance) {
		this.real_pay_balance = real_pay_balance;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getHost_req_serial_no() {
		return host_req_serial_no;
	}
	public void setHost_req_serial_no(String host_req_serial_no) {
		this.host_req_serial_no = host_req_serial_no;
	}
	public String getCert_sign() {
		return cert_sign;
	}
	public void setCert_sign(String cert_sign) {
		this.cert_sign = cert_sign;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
