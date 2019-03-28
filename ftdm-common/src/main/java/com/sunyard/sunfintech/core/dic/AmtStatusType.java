package com.sunyard.sunfintech.core.dic;

public enum AmtStatusType {
	ADD("0"),REDUCE("1");

	private String code;

	private AmtStatusType(String code){
		this.code= code;
	}

	public String getCode(){
		return code;
	}
}
