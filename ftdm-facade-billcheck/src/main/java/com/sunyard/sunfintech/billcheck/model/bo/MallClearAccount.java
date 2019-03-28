package com.sunyard.sunfintech.billcheck.model.bo;

import java.math.BigDecimal;

/**
 * 每个账户科目对应一个对象
 * @author djh
 *
 */
public class MallClearAccount extends ClearAccount{

	private String plat_no;

	public MallClearAccount(String plat_no,String platcust, String subject, String sub_subject){
		super(platcust,subject,sub_subject);
		this.plat_no = plat_no;
	}

	public String getPlat_no() {
		return  plat_no;
	}

	@Override
	public String toString() {
		return "{plat_no:"+plat_no+",platcust:"+platcust+",subject:"+subject+",sub_subject:"+sub_subject+",n_balance:"+n_balance+",f_balance:"+f_balance+"}";
	}

}
