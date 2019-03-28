package com.sunyard.sunfintech.web.model.vo.product;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Zz
 *2017年5月3日
 *批量投标
 */
public class ProdBatchExtRequestDetail implements Serializable {

	/*
	 * 手续费金额
	 */
	private String  payout_amt;
	
	/*
	 *入账的平台账户
	 */
	private String  payout_plat_type;

	public String getPayout_amt() {
		return payout_amt;
	}

	public void setPayout_amt(String payout_amt) {
		this.payout_amt = payout_amt;
	}

	public String getPayout_plat_type() {
		return payout_plat_type;
	}

	public void setPayout_plat_type(String payout_plat_type) {
		this.payout_plat_type = payout_plat_type;
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
