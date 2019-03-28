package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountInfoResponseDetail implements Serializable {

	//平台客户号
	private String platcust;

	//账户姓名
	private String name;

	//账户姓名
	private String org_name;

	//证件号
	private String pid;

	//电子账户开户标记  0-未开通 1-已开通
	private String eflg;

	//注册手机号
	private String mobile;

	//客户类型（1:个人客户，2:企业客户)
	private String cus_type;

	//营业执照编号（企业客户，营业执照编号、社会信用代码证要二选一）
	private String business_license;

	//社会信用代码证（企业客户，营业执照编号、社会信用代码证要二选一）
	private String bank_license;

	//是否设置交易密码 0-未设置  1-已设置
	private String pwd_flg;

	//授权信息
	private String auth_info;

	//是否冻结  0-未冻结  1-已冻结
	private String freeze_flg;

	//是否销户  0-未销户  1-已销户
	private String cancel_flg;

	private String cardinfo;

	@JSONField(serialize = false)
	private List<CardInfoDetail> cardInfoDetail;

	public String getPlatcust() {return platcust;}

	public void setPlatcust(String platcust) {this.platcust = platcust;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getEflg() {return eflg;}

	public void setEflg(String eflg) {this.eflg = eflg;}

	public  String getMobile() {
		return mobile;
	}

	public void   setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCus_type() {
		return cus_type;
	}

	public void   setCus_type(String cus_type) {
		this.cus_type = cus_type;
	}

	public String getBusiness_license() {
		return business_license;
	}

	public void   setBusiness_license(String business_license) {
		this.business_license = business_license;
	}

	public String getBank_license() {
		return bank_license;
	}

	public void   setBank_license(String bank_license) {
		this.bank_license = bank_license;
	}

	public String getPwd_flg() {
		return pwd_flg;
	}

	public void setPwd_flg(String pwd_flg) {
		this.pwd_flg = pwd_flg;
	}

	public String getFreeze_flg() {
		return freeze_flg;
	}

	public void setFreeze_flg(String freeze_flg) {
		this.freeze_flg = freeze_flg;
	}

	public String getCancel_flg() {
		return cancel_flg;
	}

	public void setCancel_flg(String cancel_flg) {
		this.cancel_flg = cancel_flg;
	}

	public String getCardinfo() {
		return cardinfo;
	}

	public void   setCardinfo(String cardinfo) {
		this.cardinfo = cardinfo;
	}

	public List<CardInfoDetail> getCardInfoDetail() {
		return cardInfoDetail;
	}

	public void setCardInfoDetail(List<CardInfoDetail> cardInfoDetail) {
		this.cardInfoDetail = cardInfoDetail;
		setCardinfo(JSON.toJSONString(cardInfoDetail, GlobalConfig.serializerFeature));
	}

	public String getAuth_info() {
		return auth_info;
	}

	public void setAuth_info(String auth_info) {
		this.auth_info = auth_info;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AccountInfoResponseDetail that = (AccountInfoResponseDetail) o;

		if (platcust != null ? !platcust.equals(that.platcust) : that.platcust != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (org_name != null ? !org_name.equals(that.org_name) : that.org_name != null) return false;
		if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
		if (eflg != null ? !eflg.equals(that.eflg) : that.eflg != null) return false;
		if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
		if (cus_type != null ? !cus_type.equals(that.cus_type) : that.cus_type != null) return false;
		if (business_license != null ? !business_license.equals(that.business_license) : that.business_license != null)
			return false;
		if (bank_license != null ? !bank_license.equals(that.bank_license) : that.bank_license != null) return false;
		if (pwd_flg != null ? !pwd_flg.equals(that.pwd_flg) : that.pwd_flg != null) return false;
		if (auth_info != null ? !auth_info.equals(that.auth_info) : that.auth_info != null) return false;
		if (freeze_flg != null ? !freeze_flg.equals(that.freeze_flg) : that.freeze_flg != null) return false;
		if (cancel_flg != null ? !cancel_flg.equals(that.cancel_flg) : that.cancel_flg != null) return false;
		if (cardinfo != null ? !cardinfo.equals(that.cardinfo) : that.cardinfo != null) return false;
		return cardInfoDetail != null ? cardInfoDetail.equals(that.cardInfoDetail) : that.cardInfoDetail == null;
	}

	@Override
	public int hashCode() {
		int result = platcust != null ? platcust.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (org_name != null ? org_name.hashCode() : 0);
		result = 31 * result + (pid != null ? pid.hashCode() : 0);
		result = 31 * result + (eflg != null ? eflg.hashCode() : 0);
		result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
		result = 31 * result + (cus_type != null ? cus_type.hashCode() : 0);
		result = 31 * result + (business_license != null ? business_license.hashCode() : 0);
		result = 31 * result + (bank_license != null ? bank_license.hashCode() : 0);
		result = 31 * result + (pwd_flg != null ? pwd_flg.hashCode() : 0);
		result = 31 * result + (auth_info != null ? auth_info.hashCode() : 0);
		result = 31 * result + (freeze_flg != null ? freeze_flg.hashCode() : 0);
		result = 31 * result + (cancel_flg != null ? cancel_flg.hashCode() : 0);
		result = 31 * result + (cardinfo != null ? cardinfo.hashCode() : 0);
		result = 31 * result + (cardInfoDetail != null ? cardInfoDetail.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "AccountInfoResponseDetail{" +
				"platcust='" + platcust + '\'' +
				", name='" + name + '\'' +
				", org_name='" + org_name + '\'' +
				", pid='" + pid + '\'' +
				", eflg='" + eflg + '\'' +
				", mobile='" + mobile + '\'' +
				", cus_type='" + cus_type + '\'' +
				", business_license='" + business_license + '\'' +
				", bank_license='" + bank_license + '\'' +
				", pwd_flg='" + pwd_flg + '\'' +
				", auth_info='" + auth_info + '\'' +
				", freeze_flg='" + freeze_flg + '\'' +
				", cancel_flg='" + cancel_flg + '\'' +
				", cardinfo='" + cardinfo + '\'' +
				", cardInfoDetail=" + cardInfoDetail +
				'}';
	}
}
