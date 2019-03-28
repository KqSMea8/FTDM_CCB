package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

import java.io.Serializable;


public class BatchRegisterReturnData extends BaseResponse implements Serializable {
   
	private String platcust;

	private String origin_order_no;

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getOrigin_order_no() {
		return origin_order_no;
	}

	public void setOrigin_order_no(String origin_order_no) {
		this.origin_order_no = origin_order_no;
	}
}
