package com.sunyard.sunfintech.web.model.vo.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.prod.model.bo.FunddataObject;
import com.sunyard.sunfintech.prod.model.bo.PlatSubData;
import com.sunyard.sunfintech.prod.model.bo.RepayPlanListObject;
import com.sunyard.sunfintech.prod.model.bo.SubdataObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProdEstablishOrAbortRequest extends BaseRequest{
	
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProdEstablishOrAbortRequest that = (ProdEstablishOrAbortRequest) o;

		if (prod_id != null ? !prod_id.equals(that.prod_id) : that.prod_id != null) return false;
		if (repay_plan_list != null ? !repay_plan_list.equals(that.repay_plan_list) : that.repay_plan_list != null)
			return false;
		return repayPlanListObject != null ? !repayPlanListObject.equals(that.repayPlanListObject) : that.repayPlanListObject != null;
	}

	@Override
	public int hashCode() {
		int result = prod_id != null ? prod_id.hashCode() : 0;
		result = 31 * result + (repay_plan_list != null ? repay_plan_list.hashCode() : 0);
		result = 31 * result + (repayPlanListObject != null ? repayPlanListObject.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ProdEstablishOrAbortRequest{" +
				"prod_id='" + prod_id + '\'' +
				", repay_plan_list='" + repay_plan_list + '\'' +
				", repayPlanListObject=" + repayPlanListObject +
				'}';
	}
}