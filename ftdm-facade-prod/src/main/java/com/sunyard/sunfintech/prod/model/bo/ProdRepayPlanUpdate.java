package com.sunyard.sunfintech.prod.model.bo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 借款人还款计划更新请求参数
 * @author dingjy
 *
 */
public class ProdRepayPlanUpdate extends BaseModel{

	/**
	 * 产品编号
	 */
	 @NotBlank
	private String prod_id;
	
	private List<RepayPlanListObject> repayPlanListObjects;
    
	
	public List<RepayPlanListObject> getRepayPlanListObjects() {
		return repayPlanListObjects;
	}

	public void setRepayPlanListObjects(List<RepayPlanListObject> repayPlanListObjects) {
		this.repayPlanListObjects = repayPlanListObjects;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	 
}
