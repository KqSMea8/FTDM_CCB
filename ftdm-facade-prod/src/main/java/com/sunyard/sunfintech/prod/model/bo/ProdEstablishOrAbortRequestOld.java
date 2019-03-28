package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public class ProdEstablishOrAbortRequestOld extends BaseRequest {
    /**
     * 产品编号
     */
    @NotBlank
    private String prod_id;

    /**
     * 成标、废标标记    2 成标 3废标
     */
    @NotBlank
    private String flag;

    /**
     * 成标时  分佣说明
     */
    private String funddata;
    private FunddataObject funddataObject;

    /**
     * 分账成标
     */
    private String subdata;
    private List<SubdataObject> SubdataObjectList;

    /**
     *  成标时  还款计划
     */
    private String repay_plan_list;
    private List<RepayPlanListObject> repayPlanListObject;

    private String plat_subdata;
    private List<PlatSubData> platSubDataObject;

    public String getPlat_subdata() {
        return plat_subdata;
    }

    public void setPlat_subdata(String plat_subdata) {
        this.plat_subdata = plat_subdata;
        setPlatSubDataObject(JSONArray.parseArray(plat_subdata,PlatSubData.class));
    }

    public List<PlatSubData> getPlatSubDataObject() {
        return platSubDataObject;
    }

    public void setPlatSubDataObject(List<PlatSubData> platSubDataObject) {
        this.platSubDataObject = platSubDataObject;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFunddata() {
        return funddata;
    }

    public void setFunddata(String funddata) {
        this.funddata = funddata;
        setFunddataObject(JSON.parseObject(funddata,FunddataObject.class));
    }

    public FunddataObject getFunddataObject() {
        return funddataObject;
    }

    public void setFunddataObject(FunddataObject funddataObject) {
        this.funddataObject = funddataObject;
    }

    public String getSubdata() {
        return subdata;
    }

    public void setSubdata(String subdata) {
        this.subdata = subdata;
        setSubdataObjectList(JSONArray.parseArray(subdata,SubdataObject.class));
    }

    public List<SubdataObject> getSubdataObjectList() {
        return SubdataObjectList;
    }

    public void setSubdataObjectList(List<SubdataObject> subdataObjectList) {
        SubdataObjectList = subdataObjectList;
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

        ProdEstablishOrAbortRequestOld that = (ProdEstablishOrAbortRequestOld) o;

        if (prod_id != null ? !prod_id.equals(that.prod_id) : that.prod_id != null) return false;
        if (flag != null ? !flag.equals(that.flag) : that.flag != null) return false;
        if (funddata != null ? !funddata.equals(that.funddata) : that.funddata != null) return false;
        if (funddataObject != null ? !funddataObject.equals(that.funddataObject) : that.funddataObject != null)
            return false;
        if (subdata != null ? !subdata.equals(that.subdata) : that.subdata != null) return false;
        if (SubdataObjectList != null ? !SubdataObjectList.equals(that.SubdataObjectList) : that.SubdataObjectList != null)
            return false;
        if (repay_plan_list != null ? !repay_plan_list.equals(that.repay_plan_list) : that.repay_plan_list != null)
            return false;
        if (repayPlanListObject != null ? !repayPlanListObject.equals(that.repayPlanListObject) : that.repayPlanListObject != null)
            return false;
        if (plat_subdata != null ? !plat_subdata.equals(that.plat_subdata) : that.plat_subdata != null) return false;
        return platSubDataObject != null ? platSubDataObject.equals(that.platSubDataObject) : that.platSubDataObject == null;
    }

    @Override
    public int hashCode() {
        int result = prod_id != null ? prod_id.hashCode() : 0;
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        result = 31 * result + (funddata != null ? funddata.hashCode() : 0);
        result = 31 * result + (funddataObject != null ? funddataObject.hashCode() : 0);
        result = 31 * result + (subdata != null ? subdata.hashCode() : 0);
        result = 31 * result + (SubdataObjectList != null ? SubdataObjectList.hashCode() : 0);
        result = 31 * result + (repay_plan_list != null ? repay_plan_list.hashCode() : 0);
        result = 31 * result + (repayPlanListObject != null ? repayPlanListObject.hashCode() : 0);
        result = 31 * result + (plat_subdata != null ? plat_subdata.hashCode() : 0);
        result = 31 * result + (platSubDataObject != null ? platSubDataObject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProdEstablishOrAbortRequest{" +
                "prod_id='" + prod_id + '\'' +
                ", flag='" + flag + '\'' +
                ", funddata='" + funddata + '\'' +
                ", funddataObject=" + funddataObject +
                ", subdata='" + subdata + '\'' +
                ", SubdataObjectList=" + SubdataObjectList +
                ", repay_plan_list='" + repay_plan_list + '\'' +
                ", repayPlanListObject=" + repayPlanListObject +
                ", plat_subdata='" + plat_subdata + '\'' +
                ", platSubDataObject=" + platSubDataObject +
                '}';
    }
}
