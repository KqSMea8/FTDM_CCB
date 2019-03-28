package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseResponse;

import java.io.Serializable;
import java.util.List;


public class BatchRegisterReturnOldData extends BaseResponse implements Serializable {

	private List<BatchRegisterResponseSuccessData> success_data_detail;

	private List<BaseErrorData> error_data_detail;

	public List<BatchRegisterResponseSuccessData> getSuccess_data_detail() {
		return success_data_detail;
	}

	public void setSuccess_data_detail(List<BatchRegisterResponseSuccessData> success_data_detail) {
		this.success_data_detail = success_data_detail;
	}

	public List<BaseErrorData> getError_data_detail() {
		return error_data_detail;
	}

	public void setError_data_detail(List<BaseErrorData> error_data_detail) {
		this.error_data_detail = error_data_detail;
	}
}
