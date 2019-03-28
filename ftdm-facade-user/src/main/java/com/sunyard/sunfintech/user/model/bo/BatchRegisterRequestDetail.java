package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class BatchRegisterRequestDetail  implements Serializable{
	//明细编号
	@NotBlank
	private String detail_no;

	//用户姓名
	@NotBlank
	private String name;

	//证件类型（1：身份证）
	@NotBlank
	private String id_type;

	//证件号码
	@NotBlank
	private String id_code;

	//手机号码
	@NotBlank
	private String mobile;

	//电子邮箱
	private String email;

	//性别（0:男性，1:女性）
	private String sex;

	//客户类型（1:个人客户），仅限个人
	private String cus_type;

	//出生日期
	private String birthday;

	//开户行
	private String open_branch;

	//卡号
	@NotBlank
	private String card_no;

	//卡类型(1:借记卡，2:信用卡)
	private String card_type;

	//预留手机号
	@NotBlank
	private String pre_mobile;

	//支付通道
	@NotBlank
	private String pay_code;

	//异步通知地址，企业客户必填
	private String notify_url;

	//备注
	private String remark;

	//短信发送标记
	private String message_flag;

	private BigDecimal authed_amount;//	M	N(19,2)	授权金额

	private String authed_limittime;//	M	C(8)	授权期限 YYYYMMDD

	private String authed_type;

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_code() {
		return id_code;
	}

	public void setId_code(String id_code) {
		this.id_code = id_code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCus_type() {
		return cus_type;
	}

	public void setCus_type(String cus_type) {
		this.cus_type = cus_type;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getOpen_branch() {
		return open_branch;
	}

	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getPre_mobile() {
		return pre_mobile;
	}

	public void setPre_mobile(String pre_mobile) {
		this.pre_mobile = pre_mobile;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
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

	public String getMessage_flag() {
		return message_flag;
	}

	public void setMessage_flag(String message_flag) {
		this.message_flag = message_flag;
	}

	public BigDecimal getAuthed_amount() {
		return authed_amount;
	}

	public void setAuthed_amount(BigDecimal authed_amount) {
		this.authed_amount = authed_amount;
	}

	public String getAuthed_limittime() {
		return authed_limittime;
	}

	public void setAuthed_limittime(String authed_limittime) {
		this.authed_limittime = authed_limittime;
	}

	public String getAuthed_type() {
		return authed_type;
	}

	public void setAuthed_type(String authed_type) {
		this.authed_type = authed_type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BatchRegisterRequestDetail that = (BatchRegisterRequestDetail) o;

		if (detail_no != null ? !detail_no.equals(that.detail_no) : that.detail_no != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (id_type != null ? !id_type.equals(that.id_type) : that.id_type != null) return false;
		if (id_code != null ? !id_code.equals(that.id_code) : that.id_code != null) return false;
		if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
		if (cus_type != null ? !cus_type.equals(that.cus_type) : that.cus_type != null) return false;
		if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
		if (open_branch != null ? !open_branch.equals(that.open_branch) : that.open_branch != null) return false;
		if (card_no != null ? !card_no.equals(that.card_no) : that.card_no != null) return false;
		if (card_type != null ? !card_type.equals(that.card_type) : that.card_type != null) return false;
		if (pre_mobile != null ? !pre_mobile.equals(that.pre_mobile) : that.pre_mobile != null) return false;
		if (pay_code != null ? !pay_code.equals(that.pay_code) : that.pay_code != null) return false;
		if (notify_url != null ? !notify_url.equals(that.notify_url) : that.notify_url != null) return false;
		if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
		if (message_flag != null ? !message_flag.equals(that.message_flag) : that.message_flag != null) return false;
		if (authed_amount != null ? !authed_amount.equals(that.authed_amount) : that.authed_amount != null)
			return false;
		if (authed_limittime != null ? !authed_limittime.equals(that.authed_limittime) : that.authed_limittime != null)
			return false;
		return authed_type != null ? authed_type.equals(that.authed_type) : that.authed_type == null;
	}

	@Override
	public int hashCode() {
		int result = detail_no != null ? detail_no.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (id_type != null ? id_type.hashCode() : 0);
		result = 31 * result + (id_code != null ? id_code.hashCode() : 0);
		result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (sex != null ? sex.hashCode() : 0);
		result = 31 * result + (cus_type != null ? cus_type.hashCode() : 0);
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		result = 31 * result + (open_branch != null ? open_branch.hashCode() : 0);
		result = 31 * result + (card_no != null ? card_no.hashCode() : 0);
		result = 31 * result + (card_type != null ? card_type.hashCode() : 0);
		result = 31 * result + (pre_mobile != null ? pre_mobile.hashCode() : 0);
		result = 31 * result + (pay_code != null ? pay_code.hashCode() : 0);
		result = 31 * result + (notify_url != null ? notify_url.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (message_flag != null ? message_flag.hashCode() : 0);
		result = 31 * result + (authed_amount != null ? authed_amount.hashCode() : 0);
		result = 31 * result + (authed_limittime != null ? authed_limittime.hashCode() : 0);
		result = 31 * result + (authed_type != null ? authed_type.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "BatchRegisterRequestDetail{" +
				"detail_no='" + detail_no + '\'' +
				", name='" + name + '\'' +
				", id_type='" + id_type + '\'' +
				", id_code='" + id_code + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", sex='" + sex + '\'' +
				", cus_type='" + cus_type + '\'' +
				", birthday='" + birthday + '\'' +
				", open_branch='" + open_branch + '\'' +
				", card_no='" + card_no + '\'' +
				", card_type='" + card_type + '\'' +
				", pre_mobile='" + pre_mobile + '\'' +
				", pay_code='" + pay_code + '\'' +
				", notify_url='" + notify_url + '\'' +
				", remark='" + remark + '\'' +
				", message_flag='" + message_flag + '\'' +
				", authed_amount=" + authed_amount +
				", authed_limittime='" + authed_limittime + '\'' +
				", authed_type='" + authed_type + '\'' +
				'}';
	}
}
