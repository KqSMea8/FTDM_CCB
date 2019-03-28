package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProdEstablishOrAbort extends BaseRequest{

	/**
	 * 产品编号
	 */
	@NotBlank
	private String prod_id;

	/**
	 *  成标时  还款计划
	 */
	@NotBlank
	private String repay_plan_list;
	private List<RepayPlanListObject> repayPlanListObject;

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getRepay_plan_list() {
		return repay_plan_list;
	}

	public void setRepay_plan_list(String repay_plan_list) {
		this.repay_plan_list = repay_plan_list;
		setRepayPlanListObject(JSONArray.parseArray(repay_plan_list,RepayPlanListObject.class));
	}

	public List<RepayPlanListObject> getRepayPlanListObject() {
		return repayPlanListObject;
	}

	public void setRepayPlanListObject(List<RepayPlanListObject> repayPlanListObject) {
		this.repayPlanListObject = repayPlanListObject;
	}

	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}