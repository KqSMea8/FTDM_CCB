package com.sunyard.sunfintech.user.modelold.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.user.modelold.bo.AccountDetailTran;

import java.util.List;

public class AccountingDetailResponse extends BaseResponse{
	private String data;

	@JSONField(serialize = false)
	private List<AccountDetailTran> data_detail;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<AccountDetailTran> getData_detail() {
		return data_detail;
	}

	public void setData_detail(List<AccountDetailTran> data_detail) {
		this.data_detail = data_detail;
		setData(JSON.toJSONString(data_detail, GlobalConfig.serializerFeature));
	}

	@Override
	public String toString() {
		return "AccountingDetailResponse{" +
				"data='" + data + '\'' +
				", data_detail=" + data_detail +
				'}';
	}
}
