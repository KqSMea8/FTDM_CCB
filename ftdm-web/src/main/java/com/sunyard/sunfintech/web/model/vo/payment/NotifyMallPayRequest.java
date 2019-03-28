package com.sunyard.sunfintech.web.model.vo.payment;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Zz
 *2017年5月4日
 *4.5.6.	商城充值通知
 */
public class NotifyMallPayRequest  extends BaseRequest{
	
	/*
	 * 商户平台在资金账户管理平台注册的平台编号
	 */
	@NotBlank
	private String  plat_no;
	
	
	/*
	 * 平台客户编号
	 */
	@NotBlank
	private String platcust ;
	
	/*
	 * 支付日期
	 */
	@NotBlank
	private String  trans_date;
	
	
	/*
	 * 支付时间
	 */
	@NotBlank
	private String trans_time ;
	
	/*
	 * 订单状态0:已接受, 1:处理中,2:处理成功,3:处理失败
	 */
	@NotBlank
	private String order_status ;
	
	/*
	 * 签名数据
	 */
	@NotBlank
	private String  signdata ;

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

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getSigndata() {
		return signdata;
	}

	public void setSigndata(String signdata) {
		this.signdata = signdata;
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
