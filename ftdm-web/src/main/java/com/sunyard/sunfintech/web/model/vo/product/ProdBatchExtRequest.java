package com.sunyard.sunfintech.web.model.vo.product;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;

/**
 * @author Zz
 *2017年5月3日
 *批量投标
 */
public class ProdBatchExtRequest  extends BaseRequest{

	/*
	 * 商户平台在资金账户管理平台注册的平台编号
	 */
	private String  plat_no;
	
	/*
	 * 订单号
	 */
	private String oder_no;
	
	/*
	 * 商户交易日期
	 */
	private String partner_trans_date;
	
	/*
	 * 商户交易时间
	 */
	private String partner_trans_time;
	
	/*
	 * 总数量
	 */
	private String total_num;
	
	/*
	 * 产品编号
	 */
	private String prod_id;
	
	/*
	 * 明细编号
	 */
	private String detail_no;
	
	/*
	 * 交易金额
	 */
	private String trans_amt;
	
	/*
	 * 投资人编号
	 */
	private String platcust;
	
	/*
	 * 体验金金额
	 */
	private String experience_amt;
	
	/*
	 * 抵用券金额
	 */
	private String  coupon_amt;
	
	/*
	 * 自费金额
	 */
	private String self_amt;
	
	/*
	 * 加息
	 */
	 private String in_interest;
	 
	 /*
	  * 科目优先级0-可提优先1可投优先
	  */
	 private String subject_priority;
	 
	 /*
	  * 手续费说明
	  */
	 private String commission;
	 private List<ProdBatchExtRequestDetail> commissionObject;
	
	 /*
	  * 备注
	  */
	 private String remark;
	 
	 /*
	  * 签名数据
	  */
	 private String signdata;

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getOder_no() {
		return oder_no;
	}

	public void setOder_no(String oder_no) {
		this.oder_no = oder_no;
	}

	public String getPartner_trans_date() {
		return partner_trans_date;
	}

	public void setPartner_trans_date(String partner_trans_date) {
		this.partner_trans_date = partner_trans_date;
	}

	public String getPartner_trans_time() {
		return partner_trans_time;
	}

	public void setPartner_trans_time(String partner_trans_time) {
		this.partner_trans_time = partner_trans_time;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getTrans_amt() {
		return trans_amt;
	}

	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getExperience_amt() {
		return experience_amt;
	}

	public void setExperience_amt(String experience_amt) {
		this.experience_amt = experience_amt;
	}

	public String getCoupon_amt() {
		return coupon_amt;
	}

	public void setCoupon_amt(String coupon_amt) {
		this.coupon_amt = coupon_amt;
	}

	public String getSelf_amt() {
		return self_amt;
	}

	public void setSelf_amt(String self_amt) {
		this.self_amt = self_amt;
	}

	public String getIn_interest() {
		return in_interest;
	}

	public void setIn_interest(String in_interest) {
		this.in_interest = in_interest;
	}

	public String getSubject_priority() {
		return subject_priority;
	}

	public void setSubject_priority(String subject_priority) {
		this.subject_priority = subject_priority;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
		if(StringUtils.isNoneBlank(commission)) {
            this.commissionObject = JSON.parseArray(commission,ProdBatchExtRequestDetail.class );
        }
	}

	public List<ProdBatchExtRequestDetail> getCommissionObject() {
		return commissionObject;
	}

	public void setCommissionObject(List<ProdBatchExtRequestDetail> commissionObject) {
		this.commissionObject = commissionObject;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSigndata() {
		return signdata;
	}

	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}
	 

	//TODO hashcode  toString equals
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
