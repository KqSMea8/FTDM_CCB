package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * 网关充值请求返回
 * @author Lid
 *
 */
public class GetewayPayResponse extends BaseResponse {

	/**
	 * 支付通道流水号(用来提交给支付公司的)
	 */
	private String channel_serial_no;

	/*
	 * 支付网关页面
	 */

	private String view;

	public String getChannel_serial_no() {
		return channel_serial_no;
	}

	public void setChannel_serial_no(String channel_serial_no) {
		this.channel_serial_no = channel_serial_no;
	}

	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
}
