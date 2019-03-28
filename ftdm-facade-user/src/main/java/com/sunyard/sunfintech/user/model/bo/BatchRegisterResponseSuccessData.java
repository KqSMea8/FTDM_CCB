package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BatchRegisterResponseSuccessData implements Serializable{
	//明细编号
		private String detail_no;
		
		//用户手机号
		private String mobile;
		
		//平台客户编号
		private String platcust;

		public String getDetail_no() {
			return detail_no;
		}

		public void setDetail_no(String detail_no) {
			this.detail_no = detail_no;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getPlatcust() {
			return platcust;
		}

		public void setPlatcust(String platcust) {
			this.platcust = platcust;
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
