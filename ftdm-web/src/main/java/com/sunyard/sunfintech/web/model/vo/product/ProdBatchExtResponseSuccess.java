package com.sunyard.sunfintech.web.model.vo.product;

import com.sunyard.sunfintech.prod.model.bo.Amtlist;

/**
 * @author Zz
 *2017年5月3日
 *批量投标返回 成功信息
 */
public class ProdBatchExtResponseSuccess {
	
	/*
	 * 资金处理记录
	 */
	private Amtlist amtlist;
	
	/*
	 * 明细编号
	 */
	private  String  detail_no;
	
	/*
	 *产品编号
	 */
	private String prod_id;
	
	
	/*
	 * 交易金额
	 */
	private String trans_amt;

	public Amtlist getAmtlist() {
		return amtlist;
	}

	public void setAmtlist(Amtlist amtlist) {
		this.amtlist = amtlist;
	}

	public String getDetail_no() {
		return detail_no;
	}


	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}


	public String getProd_id() {
		return prod_id;
	}


	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}


	public String getTrans_amt() {
		return trans_amt;
	}


	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}
	
	
}
