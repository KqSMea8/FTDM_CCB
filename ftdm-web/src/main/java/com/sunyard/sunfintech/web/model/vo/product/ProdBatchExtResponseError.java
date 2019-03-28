package com.sunyard.sunfintech.web.model.vo.product;

/**
 * @author Zz
 *2017年5月3日
 *批量投标 返回失败信息
 */
public class ProdBatchExtResponseError {

	
	 private String detail_no;
	    private String error_no;
	    private String error_info;

	    public String getDetail_no() {
	        return detail_no;
	    }

	    public void setDetail_no(String detail_no) {
	        this.detail_no = detail_no;
	    }

	    public String getError_no() {
	        return error_no;
	    }

	    public void setError_no(String error_no) {
	        this.error_no = error_no;
	    }

	    public String getError_info() {
	        return error_info;
	    }

	    public void setError_info(String error_info) {
	        this.error_info = error_info;
	    }
}
