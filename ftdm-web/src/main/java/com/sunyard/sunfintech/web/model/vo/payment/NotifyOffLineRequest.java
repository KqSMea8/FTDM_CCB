package com.sunyard.sunfintech.web.model.vo.payment;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Zz
 *2017年5月4日
 *4.5.8.	线下充值/还款异步通知
 */
public class NotifyOffLineRequest {

	/*
	 * 平台编号
	 */
	@NotBlank
	private String plat_no ;
	
	/*
	 * 总金额
	 */
	@NotNull
	private BigDecimal amt;
	
	/*
	 * 平台客户入账列表
	 */
	@NotBlank
	@JSONField(serialize=false)
	private String platcustList;
	private List<PlatcustListDetail> platcustlistdetail;
	
	/*
	 * 银行卡
	 */
	@NotBlank
	private String bank_no;
	
	/*
	 * 1-入账成功  2-入账失败
	 */
	@NotBlank
	private String code;
	/*
	 * 签名数据
	 */
	@NotBlank
	private String signdata;
	public String getPlat_no() {
		return plat_no;
	}
	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getPlatcustList() {
		return platcustList;
	}
	public void setPlatcustList(String platcustList) {
		this.platcustList = platcustList;
		if(StringUtils.isNoneBlank(platcustList)) {
			this.platcustlistdetail = JSON.parseArray(platcustList,PlatcustListDetail.class);
		}
	}
	public List<PlatcustListDetail> getPlatcustlistdetail() {
		return platcustlistdetail;
	}
	public void setPlatcustlistdetail(List<PlatcustListDetail> platcustlistdetail) {
		this.platcustlistdetail = platcustlistdetail;
		setPlatcustList(JSON.toJSONString(platcustlistdetail,GlobalConfig.serializerFeature));
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSigndata() {
		return signdata;
	}
	public void setSigndata(String signdata) {
		this.signdata = signdata;
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
