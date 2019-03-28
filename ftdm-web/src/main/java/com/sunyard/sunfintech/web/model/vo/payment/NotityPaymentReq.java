package com.sunyard.sunfintech.web.model.vo.payment;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Zz
 *2017年5月4日
 *4.5.2. 充值回调通知
 */
public class NotityPaymentReq extends BaseRequest {

	/*
	 * 集团编号
	 */
	private String mall_no;

	/*
	 * 平台编号
	 */
	@NotBlank
	private String plat_no;
	
	
	/*
	 * 电子账户
	 */
	@NotBlank
	private String platcust;
	
	/*
	 * 充值类型（1-用户充值 ）
	 */
	@NotBlank
	private String type ;
	
	/*
	 * 订单金额
	 */
	@NotNull
	private BigDecimal  order_amt;
	
	/*
	 * 订单日期
	 */
	@NotBlank
	private String trans_date;
	
	/*
	 * 订单时间
	 */
	@NotBlank
	private String trans_time;
	
	/*
	 * 支付订单号
	 */
	@NotBlank
	private String pay_order_no;
	
	/*
	 * 支付完成日期
	 */
	@NotBlank
	private String pay_finish_date;
	
	/*
	 * 支付完成时间
	 */
	@NotBlank
	private String pay_finish_time;
	
	/*
	 * 订单状态0:已接受, 1:处理中,2:处理成功,3:处理失败
	 */
	@NotBlank
	private String order_status;
	
	/*
	 * 支付金额
	 */
	@NotNull
	private BigDecimal pay_amt;
	/*
	 * 失败原因
	 */
	private String  error_info;
	/*
	 * 失败编码
	 */
	private String error_no;
	/*
	 * 签名数据
	 */
	@NotBlank
	private String signdata ;
	/*
	 * 支付通道流水号
	 */
	private String host_req_serial_no ;

	public String getMall_no() {
		return mall_no;
	}

	public void setMall_no(String mall_no) {
		this.mall_no = mall_no;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	
	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getOrder_amt() {
		return order_amt;
	}

	public void setOrder_amt(BigDecimal order_amt) {
		this.order_amt = order_amt;
	}

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}

	public String getPay_order_no() {
		return pay_order_no;
	}

	public void setPay_order_no(String pay_order_no) {
		this.pay_order_no = pay_order_no;
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

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public BigDecimal getPay_amt() {
		return pay_amt;
	}

	public void setPay_amt(BigDecimal pay_amt) {
		this.pay_amt = pay_amt;
	}

	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

	public String getError_no() {
		return error_no;
	}

	public void setError_no(String error_no) {
		this.error_no = error_no;
	}

	public String getSigndata() {
		return signdata;
	}

	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}

	public String getHost_req_serial_no() {
		return host_req_serial_no;
	}

	public void setHost_req_serial_no(String host_req_serial_no) {
		this.host_req_serial_no = host_req_serial_no;
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
