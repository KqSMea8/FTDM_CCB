package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdUpdateRepaymentPlanRequest extends BaseRequest{

    @NotBlank
    private String prod_id;

    @NotBlank
    private String repay_plan_list;

    private List<ProdUpdateRepaymentPlanRequestRepayPlan> repay_plan_list_array;

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
        setRepay_plan_list_array(JSON.parseArray(repay_plan_list, ProdUpdateRepaymentPlanRequestRepayPlan.class));
    }

    public List<ProdUpdateRepaymentPlanRequestRepayPlan> getRepay_plan_list_array() {
        return repay_plan_list_array;
    }

    public void setRepay_plan_list_array(List<ProdUpdateRepaymentPlanRequestRepayPlan> repay_plan_list_array) {
        this.repay_plan_list_array = repay_plan_list_array;
    }
}
