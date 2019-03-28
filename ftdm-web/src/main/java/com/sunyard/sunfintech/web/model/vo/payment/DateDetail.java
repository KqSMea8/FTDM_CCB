package com.sunyard.sunfintech.web.model.vo.payment;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Zz
 *2017年5月4日
 */
public class DateDetail {

		/*
		 * 明细编号
		 */
		@NotBlank
		private String detail_no;
		
		/*
		 * 用户编号
		 */
		@NotBlank
		private String platcust;
		
		/*
		 * 提现金额
		 */
		@NotBlank
		private String amt;
		
		/*
		 * 是否垫资:(1-不垫资， 2-垫资)；默认不垫付。
		 */
		private String is_advance ;
		
		/*
		 * 支付通道
		 */
		@NotBlank
		private String pay_code;
		
		/*
		 * 手续费金额
		 */
		private String fee_amt;
		
		/*
		 * 提现的账户类型：0投资账户1融资账户
		 */
		private String withdraw_type;
		
		/*
		 * 异步通知地址
		 */
		@NotBlank
		private String notify_url;
		/*
		 * 备注
		 */
		private String remark;
		public String getDetail_no() {
			return detail_no;
		}
		public void setDetail_no(String detail_no) {
			this.detail_no = detail_no;
		}
		public String getPlatcust() {
			return platcust;
		}
		public void setPlatcust(String platcust) {
			this.platcust = platcust;
		}
		public String getAmt() {
			return amt;
		}
		public void setAmt(String amt) {
			this.amt = amt;
		}
		public String getIs_advance() {
			return is_advance;
		}
		public void setIs_advance(String is_advance) {
			this.is_advance = is_advance;
		}
		public String getPay_code() {
			return pay_code;
		}
		public void setPay_code(String pay_code) {
			this.pay_code = pay_code;
		}
		public String getFee_amt() {
			return fee_amt;
		}
		public void setFee_amt(String fee_amt) {
			this.fee_amt = fee_amt;
		}
		public String getWithdraw_type() {
			return withdraw_type;
		}
		public void setWithdraw_type(String withdraw_type) {
			this.withdraw_type = withdraw_type;
		}
		public String getNotify_url() {
			return notify_url;
		}
		public void setNotify_url(String notify_url) {
			this.notify_url = notify_url;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
}
