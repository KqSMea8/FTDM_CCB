package com.sunyard.sunfintech.prod.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 标的还款
 * @author Lid
 *
 */
public class ProdRepayData  implements Serializable{

	/**
	 * 平台编号
	 */
	private String plat_no;
	/**
     * 订单号
     */
    private String order_no;
    /**
	 * 商户交易日期
	 */
    private String partner_trans_date;
    /**
	 * 商户交易时间
	 */
    private String partner_trans_time;
    /**
	 * 标的编号
	 */
    private String prod_id;
    /**
	 * 还款期数
	 */
    private Integer repay_num;
    /**
	 * 是否整个标的还清：0-是，1-否； 
	 */
    private Integer is_payoff;
    /**
	 * 交易金额
	 */
    private BigDecimal trans_amt;
	public String getPlat_no() {
		return plat_no;
	}
	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public Integer getRepay_num() {
		return repay_num;
	}
	public void setRepay_num(Integer repay_num) {
		this.repay_num = repay_num;
	}
	public Integer getIs_payoff() {
		return is_payoff;
	}
	public void setIs_payoff(Integer is_payoff) {
		this.is_payoff = is_payoff;
	}
	public BigDecimal getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(BigDecimal trans_amt) {
		this.trans_amt = trans_amt;
	}
    
}
