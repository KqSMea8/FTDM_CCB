/********************************************
 * 文件名称: AMTTYPE <br/>
 * 系统名称: 直销银行V1.0
 * 模块名称: WEB业务平台帐户类
 * 软件版权: 信雅达系统工程股份有限公司
 * 功能说明: 资金流向
 * 系统版本: 1.0.0.1
 * 开发人员:  Terrance
 * 开发时间: 2014年10月11日 上午11:43:53 <br/> 
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 * V3.0.0.2 20130530-01  XXXX        提示报错 M201305300011
 *********************************************/

package com.sunyard.sunfintech.core.dic;
/*
角色（1：出借人、2：借款人、3：垫资人、4：担保人）
 */

public enum RoleType {
	INVESTOR("1"),BORROWER("2"),ADVANCER("3"),GUARANTOR("4");

	private String code;

	private RoleType(String code){
		this.code= code;
	}
	
	public String getCode(){
		return code;
	}
}
