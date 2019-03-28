package com.sunyard.sunfintech.user.model.bo;


import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.sunyard.sunfintech.dao.entity.ProdCompensationList;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author pzy
 * 2017年4月18日
 */
public class ProdPublishRequest extends BaseRequest {
	
	/*
	 * 产品编号
	 */
	@NotBlank
	private String prod_id;
	
	/*
	 * 产品名称
	 */
	@NotBlank
	private String prod_name; 
	
	/*
	 * 出账类型（0周期性出账、1不定期出账）
	 */
	@NotBlank
	private String prod_type;

	/*
	 * 自动投标标示 0-非自动投标 1-自动投标
	 */
	@NotBlank
	private String auto_flg;

	/*
	 * 发行总额
	 */
	@NotNull
	@DecimalMin(value="0.001")
	private BigDecimal total_limit;
	
	/*
	 * 产品起息方式 (0-满额起息 1-成立起息2投标起息 3-成立审核后起息)
	 */
	@NotBlank
	private String value_type; 
	
	/*
	 * 成立方式 0-满额成立 1-成立日成立 2-活期方式
	 */
	@NotBlank
	private String create_type; 
	
	/*
	 * 产品发行日 (YYYY-MM-DD HH:mm:ss)
	 */
	private String sell_date;

	/*
	 * 起息日 如起息方式为成立起息，为必选项
	 */
	private String value_date; 
	
	/*
	 * 到期日 (YYYY-MM-DD HH:mm:ss)
	 */
	private String expire_date; 
	
	/*
	 * 周期 例如：3个月的标的传 3
	 */
	@NotNull
	private Integer cycle;
	
	/*
	 * 周期单位 1日 2周 3月 4季 5年
	 */
	@NotBlank
	private String cycle_unit;
	
	/*
	 * 年华收益率 例如：8.9% 传 0.089
	 */
	@NotNull
	private BigDecimal ist_year;
	
	/*
	 * 还款方式 0-一次性还款  1-分期还款
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
	@DecimalMin(value="0")
	private BigDecimal over_total_limit;
	
	/*
	 * 受托报备
	 */
	private String remark;
	
	/*
	 * 代偿账户列表
	 */
	private String compensation;
	private List<ProdPublicCompensation> compensationList;

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
		setEaccFinancinfo(JSON.parseArray(financing_info_list,EaccFinancinfoDetail.class));
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
		setCompensationList(JSON.parseArray(compensation,ProdPublicCompensation.class));
	}

	public List<ProdPublicCompensation> getCompensationList() {
		return compensationList;
	}

	public void setCompensationList(List<ProdPublicCompensation> compensationList) {
		this.compensationList = compensationList;
	}

	public String getAuto_flg() {
		return auto_flg;
	}

	public void setAuto_flg(String auto_flg) {
		this.auto_flg = auto_flg;
	}
}
