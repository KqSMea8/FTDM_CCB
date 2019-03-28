package com.sunyard.sunfintech.user.model.bo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


/**
 * 融资人分账
 * @author wuml
 */
public class BorrowerSubAccountRequest extends BaseRequest {
	
	//标的编号
	@NotBlank
	private String prod_id;

	@NotNull
	@DecimalMin(value="0")
	private BigDecimal amt;


	/**
	 * 手续费分佣
	 */
	private String funddata;
	private FunddataObject funddataObject;

	/**
	 * 分账客户分佣
	 */

	private String subdata;
	private List<SubdataObject> SubdataObjectList;

	/**
	 * 风险金分佣
	 */
	private String plat_subdata;
	private List<PlatSubData> platSubDataObject;

	public String getPlat_subdata() {
		return plat_subdata;
	}

	public void setPlat_subdata(String plat_subdata) {
		this.plat_subdata = plat_subdata;
		setPlatSubDataObject(JSONArray.parseArray(plat_subdata,PlatSubData.class));
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public List<PlatSubData> getPlatSubDataObject() {
		return platSubDataObject;
	}

	public void setPlatSubDataObject(List<PlatSubData> platSubDataObject) {
		this.platSubDataObject = platSubDataObject;
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

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
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
