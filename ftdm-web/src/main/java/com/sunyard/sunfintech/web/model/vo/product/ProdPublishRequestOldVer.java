package com.sunyard.sunfintech.web.model.vo.product;


import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.dao.entity.ProdCompensationList;
import com.sunyard.sunfintech.prod.model.bo.EaccFinancinfoDetail;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author Zz
 *2017年4月18日
 */
@SuppressWarnings("serial")
public class ProdPublishRequestOldVer extends BaseRequest {
	
	/*
	 *产品编号
	 */
	@NotBlank
	private String prod_id; 
	
	/*
	 *产品名称 
	 */
	@NotBlank
	private String prod_name; 
	
	/*
	 *产品类型 
	 */
	@NotBlank
	private String prod_type; 
	
	/*
	 * 发行总额
	 */
	@NotNull
	private BigDecimal total_limit; 
	
	/*
	 * 产品起息方式
	 */
	@NotBlank
	private String value_type; 
	
	/*
	 * 成立方式
	 */
	@NotBlank
	private String create_type; 
	
	/*
	 * 产品发行日
	 */
	private String sell_date; 
	/*
	 * 起息日
	 */
	private String value_date; 
	
	/*
	 * 到期日
	 */
	private String expire_date; 
	
	/*
	 * 周期
	 */
	@NotNull
	private Integer cycle;
	
	/*
	 * 周期单位
	 */
	@NotBlank
	private String cycle_unit;
	
	/*
	 * 年华收益率
	 */
	@NotNull
	private BigDecimal ist_year;
	
	/*
	 * 还款方式
	 */
	@NotBlank
	private String repay_type;
	
	/*
	 * 融资信息列表
	 */
	
	private String financing_info_list; 
	private List<EaccFinancinfoDetail> eaccFinancinfo;
	
	/*
	 * 风险等级
	 */
	private String risk_lvl;
	
	/*
	 * 产品介绍
	 */
	private String prod_info;
	
	/*
	 * 限制购买人数
	 */
	private Integer buyer_num_limit;
	
	/*
	 * 出账标示
	 */
	@NotBlank
	private String chargeOff_auto; 
	
	/*
	 * 超额限制
	 */
	@NotBlank
	private String overLimit; 
	
	/*
	 * 超额限制总额度
	 */
	@NotNull
	private BigDecimal over_total_limit;
	
	/*
	 * 受托报备
	 */
	private String remark;
	
	/*
	 * 代偿账户列表
	 */
	private String compensation;
	private List<ProdCompensationList> compensationList;

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getProd_type() {
		return prod_type;
	}

	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}

	public BigDecimal getTotal_limit() {
		return total_limit;
	}

	public void setTotal_limit(BigDecimal total_limit) {
		this.total_limit = total_limit;
	}

	public String getValue_type() {
		return value_type;
	}

	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}

	public String getCreate_type() {
		return create_type;
	}

	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}

	public String getSell_date() {
		return sell_date;
	}

	public void setSell_date(String sell_date) {
		this.sell_date = sell_date;
	}

	public String getValue_date() {
		return value_date;
	}

	public void setValue_date(String value_date) {
		this.value_date = value_date;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public BigDecimal getIst_year() {
		return ist_year;
	}

	public void setIst_year(BigDecimal ist_year) {
		this.ist_year = ist_year;
	}

	public String getRepay_type() {
		return repay_type;
	}

	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type;
	}

	public String getFinancing_info_list() {
		return financing_info_list;
	}

	public void setFinancing_info_list(String financing_info_list) {
		this.financing_info_list = financing_info_list;
		 if(StringUtils.isNoneBlank(financing_info_list)) {
	            this.eaccFinancinfo = JSON.parseArray(financing_info_list,EaccFinancinfoDetail.class );
	        }
	}

	public String getRisk_lvl() {
		return risk_lvl;
	}

	public void setRisk_lvl(String risk_lvl) {
		this.risk_lvl = risk_lvl;
	}

	public String getProd_info() {
		return prod_info;
	}

	public void setProd_info(String prod_info) {
		this.prod_info = prod_info;
	}

	public Integer getBuyer_num_limit() {
		return buyer_num_limit;
	}

	public void setBuyer_num_limit(Integer buyer_num_limit) {
		this.buyer_num_limit = buyer_num_limit;
	}

	public String getChargeOff_auto() {
		return chargeOff_auto;
	}

	public void setChargeOff_auto(String chargeOff_auto) {
		this.chargeOff_auto = chargeOff_auto;
	}

	public String getOverLimit() {
		return overLimit;
	}

	public void setOverLimit(String overLimit) {
		this.overLimit = overLimit;
	}

	public BigDecimal getOver_total_limit() {
		return over_total_limit;
	}

	public void setOver_total_limit(BigDecimal over_total_limit) {
		this.over_total_limit = over_total_limit;
	}

	public String getCycle_unit() {
		return cycle_unit;
	}

	public void setCycle_unit(String cycle_unit) {
		this.cycle_unit = cycle_unit;
	}
	
	
	public List<EaccFinancinfoDetail> getEaccFinancinfo() {
		return eaccFinancinfo;
	}

	public void setEaccFinancinfo(List<EaccFinancinfoDetail> eaccFinancinfo) {
		this.eaccFinancinfo = eaccFinancinfo;
	}
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompensation() {
		return compensation;
	}

	public void setCompensation(String compensation) {
		this.compensation = compensation;
		setCompensationList(JSON.parseArray(compensation,ProdCompensationList.class));
	}

	public List<ProdCompensationList> getCompensationList() {
		return compensationList;
	}

	public void setCompensationList(List<ProdCompensationList> compensationList) {
		this.compensationList = compensationList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProdPublishRequestOldVer that = (ProdPublishRequestOldVer) o;

		if (prod_id != null ? !prod_id.equals(that.prod_id) : that.prod_id != null) return false;
		if (prod_name != null ? !prod_name.equals(that.prod_name) : that.prod_name != null) return false;
		if (prod_type != null ? !prod_type.equals(that.prod_type) : that.prod_type != null) return false;
		if (total_limit != null ? !total_limit.equals(that.total_limit) : that.total_limit != null) return false;
		if (value_type != null ? !value_type.equals(that.value_type) : that.value_type != null) return false;
		if (create_type != null ? !create_type.equals(that.create_type) : that.create_type != null) return false;
		if (sell_date != null ? !sell_date.equals(that.sell_date) : that.sell_date != null) return false;
		if (value_date != null ? !value_date.equals(that.value_date) : that.value_date != null) return false;
		if (expire_date != null ? !expire_date.equals(that.expire_date) : that.expire_date != null) return false;
		if (cycle != null ? !cycle.equals(that.cycle) : that.cycle != null) return false;
		if (cycle_unit != null ? !cycle_unit.equals(that.cycle_unit) : that.cycle_unit != null) return false;
		if (ist_year != null ? !ist_year.equals(that.ist_year) : that.ist_year != null) return false;
		if (repay_type != null ? !repay_type.equals(that.repay_type) : that.repay_type != null) return false;
		if (financing_info_list != null ? !financing_info_list.equals(that.financing_info_list) : that.financing_info_list != null)
			return false;
		if (eaccFinancinfo != null ? !eaccFinancinfo.equals(that.eaccFinancinfo) : that.eaccFinancinfo != null)
			return false;
		if (risk_lvl != null ? !risk_lvl.equals(that.risk_lvl) : that.risk_lvl != null) return false;
		if (prod_info != null ? !prod_info.equals(that.prod_info) : that.prod_info != null) return false;
		if (buyer_num_limit != null ? !buyer_num_limit.equals(that.buyer_num_limit) : that.buyer_num_limit != null)
			return false;
		if (chargeOff_auto != null ? !chargeOff_auto.equals(that.chargeOff_auto) : that.chargeOff_auto != null)
			return false;
		if (overLimit != null ? !overLimit.equals(that.overLimit) : that.overLimit != null) return false;
		if (over_total_limit != null ? !over_total_limit.equals(that.over_total_limit) : that.over_total_limit != null)
			return false;
		if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
		if (compensation != null ? !compensation.equals(that.compensation) : that.compensation != null) return false;
		return compensationList != null ? compensationList.equals(that.compensationList) : that.compensationList == null;
	}

	@Override
	public int hashCode() {
		int result = prod_id != null ? prod_id.hashCode() : 0;
		result = 31 * result + (prod_name != null ? prod_name.hashCode() : 0);
		result = 31 * result + (prod_type != null ? prod_type.hashCode() : 0);
		result = 31 * result + (total_limit != null ? total_limit.hashCode() : 0);
		result = 31 * result + (value_type != null ? value_type.hashCode() : 0);
		result = 31 * result + (create_type != null ? create_type.hashCode() : 0);
		result = 31 * result + (sell_date != null ? sell_date.hashCode() : 0);
		result = 31 * result + (value_date != null ? value_date.hashCode() : 0);
		result = 31 * result + (expire_date != null ? expire_date.hashCode() : 0);
		result = 31 * result + (cycle != null ? cycle.hashCode() : 0);
		result = 31 * result + (cycle_unit != null ? cycle_unit.hashCode() : 0);
		result = 31 * result + (ist_year != null ? ist_year.hashCode() : 0);
		result = 31 * result + (repay_type != null ? repay_type.hashCode() : 0);
		result = 31 * result + (financing_info_list != null ? financing_info_list.hashCode() : 0);
		result = 31 * result + (eaccFinancinfo != null ? eaccFinancinfo.hashCode() : 0);
		result = 31 * result + (risk_lvl != null ? risk_lvl.hashCode() : 0);
		result = 31 * result + (prod_info != null ? prod_info.hashCode() : 0);
		result = 31 * result + (buyer_num_limit != null ? buyer_num_limit.hashCode() : 0);
		result = 31 * result + (chargeOff_auto != null ? chargeOff_auto.hashCode() : 0);
		result = 31 * result + (overLimit != null ? overLimit.hashCode() : 0);
		result = 31 * result + (over_total_limit != null ? over_total_limit.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (compensation != null ? compensation.hashCode() : 0);
		result = 31 * result + (compensationList != null ? compensationList.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ProdPublishRequestOldVer{" +
				super.toString()+", "+
				"prod_id='" + prod_id + '\'' +
				", prod_name='" + prod_name + '\'' +
				", prod_type='" + prod_type + '\'' +
				", total_limit=" + total_limit +
				", value_type='" + value_type + '\'' +
				", create_type='" + create_type + '\'' +
				", sell_date='" + sell_date + '\'' +
				", value_date='" + value_date + '\'' +
				", expire_date='" + expire_date + '\'' +
				", cycle=" + cycle +
				", cycle_unit='" + cycle_unit + '\'' +
				", ist_year=" + ist_year +
				", repay_type='" + repay_type + '\'' +
				", financing_info_list='" + financing_info_list + '\'' +
				", eaccFinancinfo=" + eaccFinancinfo +
				", risk_lvl='" + risk_lvl + '\'' +
				", prod_info='" + prod_info + '\'' +
				", buyer_num_limit=" + buyer_num_limit +
				", chargeOff_auto='" + chargeOff_auto + '\'' +
				", overLimit='" + overLimit + '\'' +
				", over_total_limit=" + over_total_limit +
				", remark='" + remark + '\'' +
				", compensation='" + compensation + '\'' +
				", compensationList=" + compensationList +
				'}';
	}
}
