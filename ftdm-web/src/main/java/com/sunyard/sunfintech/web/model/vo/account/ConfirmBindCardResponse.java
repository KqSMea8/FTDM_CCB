package com.sunyard.sunfintech.web.model.vo.account;

import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * 
 * 短验绑卡（可包含开户）确认  ---响应modal---
 * @author PengZY
 * 
 */
public class ConfirmBindCardResponse extends BaseResponse{

	
	//确认成功时，必填
	private String platcust;
	
	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

}
