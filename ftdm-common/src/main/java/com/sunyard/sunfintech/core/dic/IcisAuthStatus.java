/********************************************
 * 文件名称: ACCTSTA <br/>
 * 功能说明: 到账状态
 *********************************************/

package com.sunyard.sunfintech.core.dic;
/*
认证状态	auth_status
0	成功
1	待认证
2	处理中
3   解绑成功
7	认证失败
9	失败
 */

public enum IcisAuthStatus {
	Success("0"),Processing("2"),UNBINDSUCCESS("3"),UNBINDFAIL("7"),Fail("9");

	private String code;

	private IcisAuthStatus(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
