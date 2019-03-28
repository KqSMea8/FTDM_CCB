package com.sunyard.sunfintech.prod.model.bo;


import com.sunyard.sunfintech.core.base.BaseEntity;

public class CertInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 身份证号
	 */
	private String id_code;
	
	/**
	 * 验证通道
	 */
	private String pay_code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId_code() {
		return id_code;
	}

	public void setId_code(String id_code) {
		this.id_code = id_code;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
}
