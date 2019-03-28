package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class PlatPlatinfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String mall_no;

    /**
     * 
     */
    private String mall_name;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String plat_name;

    /**
     * 
     */
    private String plat_type;

    /**
     * 
     */
    private String plat_pwd;

    /**
     * 
     */
    private String company_attr;

    /**
     * 
     */
    private String company_name;

    /**
     * 
     */
    private String company_capital;

    /**
     * 
     */
    private String company_license;

    /**
     * 
     */
    private String legal_name;

    /**
     * 
     */
    private String legal_license_type;

    /**
     * 
     */
    private String legal_license;

    /**
     * 
     */
    private String tax_license;

    /**
     * 
     */
    private String tax_number;

    /**
     * 
     */
    private String org_license;

    /**
     * 
     */
    private String plat_state;

    /**
     * 
     */
    private Date company_establish_date;

    /**
     * 
     */
    private Date plat_line_date;

    /**
     * 
     */
    private String plat_address;

    /**
     * 
     */
    private String plat_busi_type;

    /**
     * 
     */
    private String plat_business;

    /**
     * 
     */
    private String payment_plat_no;

    /**
     * 
     */
    private String actual_capital;

    /**
     * 
     */
    private String plat_domain;

    /**
     * 
     */
    private String info_security;

    /**
     * 
     */
    private String check_company;

    /**
     * 
     */
    private String check_time;

    /**
     * 
     */
    private String account_verification_channel;

    /**
     * 
     */
    private String social_credit_code;

    /**
     * 
     */
    private String add_notify_url;

    /**
     * 
     */
    private String daily_cut_url;

    /**
     * 
     */
    private String payment_account;

    /**
     * 
     */
    private String liquidation_url;

    /**
     * 
     */
    private String enabled;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private String create_by;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private String update_by;

    /**
     * 
     */
    private Date update_time;

    /**
     * 
     */
    private String cert;

    /**
     * plat_platinfo
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return mall_no 
     */
    public String getMall_no() {
        return mall_no;
    }

    /**
     * 
     * @param mall_no 
     */
    public void setMall_no(String mall_no) {
        this.mall_no = mall_no == null ? null : mall_no.trim();
    }

    /**
     * 
     * @return mall_name 
     */
    public String getMall_name() {
        return mall_name;
    }

    /**
     * 
     * @param mall_name 
     */
    public void setMall_name(String mall_name) {
        this.mall_name = mall_name == null ? null : mall_name.trim();
    }

    /**
     * 
     * @return plat_no 
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 
     * @param plat_no 
     */
    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no == null ? null : plat_no.trim();
    }

    /**
     * 
     * @return plat_name 
     */
    public String getPlat_name() {
        return plat_name;
    }

    /**
     * 
     * @param plat_name 
     */
    public void setPlat_name(String plat_name) {
        this.plat_name = plat_name == null ? null : plat_name.trim();
    }

    /**
     * 
     * @return plat_type 
     */
    public String getPlat_type() {
        return plat_type;
    }

    /**
     * 
     * @param plat_type 
     */
    public void setPlat_type(String plat_type) {
        this.plat_type = plat_type == null ? null : plat_type.trim();
    }

    /**
     * 
     * @return plat_pwd 
     */
    public String getPlat_pwd() {
        return plat_pwd;
    }

    /**
     * 
     * @param plat_pwd 
     */
    public void setPlat_pwd(String plat_pwd) {
        this.plat_pwd = plat_pwd == null ? null : plat_pwd.trim();
    }

    /**
     * 
     * @return company_attr 
     */
    public String getCompany_attr() {
        return company_attr;
    }

    /**
     * 
     * @param company_attr 
     */
    public void setCompany_attr(String company_attr) {
        this.company_attr = company_attr == null ? null : company_attr.trim();
    }

    /**
     * 
     * @return company_name 
     */
    public String getCompany_name() {
        return company_name;
    }

    /**
     * 
     * @param company_name 
     */
    public void setCompany_name(String company_name) {
        this.company_name = company_name == null ? null : company_name.trim();
    }

    /**
     * 
     * @return company_capital 
     */
    public String getCompany_capital() {
        return company_capital;
    }

    /**
     * 
     * @param company_capital 
     */
    public void setCompany_capital(String company_capital) {
        this.company_capital = company_capital == null ? null : company_capital.trim();
    }

    /**
     * 
     * @return company_license 
     */
    public String getCompany_license() {
        return company_license;
    }

    /**
     * 
     * @param company_license 
     */
    public void setCompany_license(String company_license) {
        this.company_license = company_license == null ? null : company_license.trim();
    }

    /**
     * 
     * @return legal_name 
     */
    public String getLegal_name() {
        return legal_name;
    }

    /**
     * 
     * @param legal_name 
     */
    public void setLegal_name(String legal_name) {
        this.legal_name = legal_name == null ? null : legal_name.trim();
    }

    /**
     * 
     * @return legal_license_type 
     */
    public String getLegal_license_type() {
        return legal_license_type;
    }

    /**
     * 
     * @param legal_license_type 
     */
    public void setLegal_license_type(String legal_license_type) {
        this.legal_license_type = legal_license_type == null ? null : legal_license_type.trim();
    }

    /**
     * 
     * @return legal_license 
     */
    public String getLegal_license() {
        return legal_license;
    }

    /**
     * 
     * @param legal_license 
     */
    public void setLegal_license(String legal_license) {
        this.legal_license = legal_license == null ? null : legal_license.trim();
    }

    /**
     * 
     * @return tax_license 
     */
    public String getTax_license() {
        return tax_license;
    }

    /**
     * 
     * @param tax_license 
     */
    public void setTax_license(String tax_license) {
        this.tax_license = tax_license == null ? null : tax_license.trim();
    }

    /**
     * 
     * @return tax_number 
     */
    public String getTax_number() {
        return tax_number;
    }

    /**
     * 
     * @param tax_number 
     */
    public void setTax_number(String tax_number) {
        this.tax_number = tax_number == null ? null : tax_number.trim();
    }

    /**
     * 
     * @return org_license 
     */
    public String getOrg_license() {
        return org_license;
    }

    /**
     * 
     * @param org_license 
     */
    public void setOrg_license(String org_license) {
        this.org_license = org_license == null ? null : org_license.trim();
    }

    /**
     * 
     * @return plat_state 
     */
    public String getPlat_state() {
        return plat_state;
    }

    /**
     * 
     * @param plat_state 
     */
    public void setPlat_state(String plat_state) {
        this.plat_state = plat_state == null ? null : plat_state.trim();
    }

    /**
     * 
     * @return company_establish_date 
     */
    public Date getCompany_establish_date() {
        return company_establish_date;
    }

    /**
     * 
     * @param company_establish_date 
     */
    public void setCompany_establish_date(Date company_establish_date) {
        this.company_establish_date = company_establish_date;
    }

    /**
     * 
     * @return plat_line_date 
     */
    public Date getPlat_line_date() {
        return plat_line_date;
    }

    /**
     * 
     * @param plat_line_date 
     */
    public void setPlat_line_date(Date plat_line_date) {
        this.plat_line_date = plat_line_date;
    }

    /**
     * 
     * @return plat_address 
     */
    public String getPlat_address() {
        return plat_address;
    }

    /**
     * 
     * @param plat_address 
     */
    public void setPlat_address(String plat_address) {
        this.plat_address = plat_address == null ? null : plat_address.trim();
    }

    /**
     * 
     * @return plat_busi_type 
     */
    public String getPlat_busi_type() {
        return plat_busi_type;
    }

    /**
     * 
     * @param plat_busi_type 
     */
    public void setPlat_busi_type(String plat_busi_type) {
        this.plat_busi_type = plat_busi_type == null ? null : plat_busi_type.trim();
    }

    /**
     * 
     * @return plat_business 
     */
    public String getPlat_business() {
        return plat_business;
    }

    /**
     * 
     * @param plat_business 
     */
    public void setPlat_business(String plat_business) {
        this.plat_business = plat_business == null ? null : plat_business.trim();
    }

    /**
     * 
     * @return payment_plat_no 
     */
    public String getPayment_plat_no() {
        return payment_plat_no;
    }

    /**
     * 
     * @param payment_plat_no 
     */
    public void setPayment_plat_no(String payment_plat_no) {
        this.payment_plat_no = payment_plat_no == null ? null : payment_plat_no.trim();
    }

    /**
     * 
     * @return actual_capital 
     */
    public String getActual_capital() {
        return actual_capital;
    }

    /**
     * 
     * @param actual_capital 
     */
    public void setActual_capital(String actual_capital) {
        this.actual_capital = actual_capital == null ? null : actual_capital.trim();
    }

    /**
     * 
     * @return plat_domain 
     */
    public String getPlat_domain() {
        return plat_domain;
    }

    /**
     * 
     * @param plat_domain 
     */
    public void setPlat_domain(String plat_domain) {
        this.plat_domain = plat_domain == null ? null : plat_domain.trim();
    }

    /**
     * 
     * @return info_security 
     */
    public String getInfo_security() {
        return info_security;
    }

    /**
     * 
     * @param info_security 
     */
    public void setInfo_security(String info_security) {
        this.info_security = info_security == null ? null : info_security.trim();
    }

    /**
     * 
     * @return check_company 
     */
    public String getCheck_company() {
        return check_company;
    }

    /**
     * 
     * @param check_company 
     */
    public void setCheck_company(String check_company) {
        this.check_company = check_company == null ? null : check_company.trim();
    }

    /**
     * 
     * @return check_time 
     */
    public String getCheck_time() {
        return check_time;
    }

    /**
     * 
     * @param check_time 
     */
    public void setCheck_time(String check_time) {
        this.check_time = check_time == null ? null : check_time.trim();
    }

    /**
     * 
     * @return account_verification_channel 
     */
    public String getAccount_verification_channel() {
        return account_verification_channel;
    }

    /**
     * 
     * @param account_verification_channel 
     */
    public void setAccount_verification_channel(String account_verification_channel) {
        this.account_verification_channel = account_verification_channel == null ? null : account_verification_channel.trim();
    }

    /**
     * 
     * @return social_credit_code 
     */
    public String getSocial_credit_code() {
        return social_credit_code;
    }

    /**
     * 
     * @param social_credit_code 
     */
    public void setSocial_credit_code(String social_credit_code) {
        this.social_credit_code = social_credit_code == null ? null : social_credit_code.trim();
    }

    /**
     * 
     * @return add_notify_url 
     */
    public String getAdd_notify_url() {
        return add_notify_url;
    }

    /**
     * 
     * @param add_notify_url 
     */
    public void setAdd_notify_url(String add_notify_url) {
        this.add_notify_url = add_notify_url == null ? null : add_notify_url.trim();
    }

    /**
     * 
     * @return daily_cut_url 
     */
    public String getDaily_cut_url() {
        return daily_cut_url;
    }

    /**
     * 
     * @param daily_cut_url 
     */
    public void setDaily_cut_url(String daily_cut_url) {
        this.daily_cut_url = daily_cut_url == null ? null : daily_cut_url.trim();
    }

    /**
     * 
     * @return payment_account 
     */
    public String getPayment_account() {
        return payment_account;
    }

    /**
     * 
     * @param payment_account 
     */
    public void setPayment_account(String payment_account) {
        this.payment_account = payment_account == null ? null : payment_account.trim();
    }

    /**
     * 
     * @return liquidation_url 
     */
    public String getLiquidation_url() {
        return liquidation_url;
    }

    /**
     * 
     * @param liquidation_url 
     */
    public void setLiquidation_url(String liquidation_url) {
        this.liquidation_url = liquidation_url == null ? null : liquidation_url.trim();
    }

    /**
     * 
     * @return enabled 
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled 
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    /**
     * 
     * @return remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 
     * @return create_by 
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 
     * @param create_by 
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_by 
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 
     * @param update_by 
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 
     * @return cert 
     */
    public String getCert() {
        return cert;
    }

    /**
     * 
     * @param cert 
     */
    public void setCert(String cert) {
        this.cert = cert == null ? null : cert.trim();
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PlatPlatinfo other = (PlatPlatinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getMall_name() == null ? other.getMall_name() == null : this.getMall_name().equals(other.getMall_name()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlat_name() == null ? other.getPlat_name() == null : this.getPlat_name().equals(other.getPlat_name()))
            && (this.getPlat_type() == null ? other.getPlat_type() == null : this.getPlat_type().equals(other.getPlat_type()))
            && (this.getPlat_pwd() == null ? other.getPlat_pwd() == null : this.getPlat_pwd().equals(other.getPlat_pwd()))
            && (this.getCompany_attr() == null ? other.getCompany_attr() == null : this.getCompany_attr().equals(other.getCompany_attr()))
            && (this.getCompany_name() == null ? other.getCompany_name() == null : this.getCompany_name().equals(other.getCompany_name()))
            && (this.getCompany_capital() == null ? other.getCompany_capital() == null : this.getCompany_capital().equals(other.getCompany_capital()))
            && (this.getCompany_license() == null ? other.getCompany_license() == null : this.getCompany_license().equals(other.getCompany_license()))
            && (this.getLegal_name() == null ? other.getLegal_name() == null : this.getLegal_name().equals(other.getLegal_name()))
            && (this.getLegal_license_type() == null ? other.getLegal_license_type() == null : this.getLegal_license_type().equals(other.getLegal_license_type()))
            && (this.getLegal_license() == null ? other.getLegal_license() == null : this.getLegal_license().equals(other.getLegal_license()))
            && (this.getTax_license() == null ? other.getTax_license() == null : this.getTax_license().equals(other.getTax_license()))
            && (this.getTax_number() == null ? other.getTax_number() == null : this.getTax_number().equals(other.getTax_number()))
            && (this.getOrg_license() == null ? other.getOrg_license() == null : this.getOrg_license().equals(other.getOrg_license()))
            && (this.getPlat_state() == null ? other.getPlat_state() == null : this.getPlat_state().equals(other.getPlat_state()))
            && (this.getCompany_establish_date() == null ? other.getCompany_establish_date() == null : this.getCompany_establish_date().equals(other.getCompany_establish_date()))
            && (this.getPlat_line_date() == null ? other.getPlat_line_date() == null : this.getPlat_line_date().equals(other.getPlat_line_date()))
            && (this.getPlat_address() == null ? other.getPlat_address() == null : this.getPlat_address().equals(other.getPlat_address()))
            && (this.getPlat_busi_type() == null ? other.getPlat_busi_type() == null : this.getPlat_busi_type().equals(other.getPlat_busi_type()))
            && (this.getPlat_business() == null ? other.getPlat_business() == null : this.getPlat_business().equals(other.getPlat_business()))
            && (this.getPayment_plat_no() == null ? other.getPayment_plat_no() == null : this.getPayment_plat_no().equals(other.getPayment_plat_no()))
            && (this.getActual_capital() == null ? other.getActual_capital() == null : this.getActual_capital().equals(other.getActual_capital()))
            && (this.getPlat_domain() == null ? other.getPlat_domain() == null : this.getPlat_domain().equals(other.getPlat_domain()))
            && (this.getInfo_security() == null ? other.getInfo_security() == null : this.getInfo_security().equals(other.getInfo_security()))
            && (this.getCheck_company() == null ? other.getCheck_company() == null : this.getCheck_company().equals(other.getCheck_company()))
            && (this.getCheck_time() == null ? other.getCheck_time() == null : this.getCheck_time().equals(other.getCheck_time()))
            && (this.getAccount_verification_channel() == null ? other.getAccount_verification_channel() == null : this.getAccount_verification_channel().equals(other.getAccount_verification_channel()))
            && (this.getSocial_credit_code() == null ? other.getSocial_credit_code() == null : this.getSocial_credit_code().equals(other.getSocial_credit_code()))
            && (this.getAdd_notify_url() == null ? other.getAdd_notify_url() == null : this.getAdd_notify_url().equals(other.getAdd_notify_url()))
            && (this.getDaily_cut_url() == null ? other.getDaily_cut_url() == null : this.getDaily_cut_url().equals(other.getDaily_cut_url()))
            && (this.getPayment_account() == null ? other.getPayment_account() == null : this.getPayment_account().equals(other.getPayment_account()))
            && (this.getLiquidation_url() == null ? other.getLiquidation_url() == null : this.getLiquidation_url().equals(other.getLiquidation_url()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getCert() == null ? other.getCert() == null : this.getCert().equals(other.getCert()));
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getMall_name() == null) ? 0 : getMall_name().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlat_name() == null) ? 0 : getPlat_name().hashCode());
        result = prime * result + ((getPlat_type() == null) ? 0 : getPlat_type().hashCode());
        result = prime * result + ((getPlat_pwd() == null) ? 0 : getPlat_pwd().hashCode());
        result = prime * result + ((getCompany_attr() == null) ? 0 : getCompany_attr().hashCode());
        result = prime * result + ((getCompany_name() == null) ? 0 : getCompany_name().hashCode());
        result = prime * result + ((getCompany_capital() == null) ? 0 : getCompany_capital().hashCode());
        result = prime * result + ((getCompany_license() == null) ? 0 : getCompany_license().hashCode());
        result = prime * result + ((getLegal_name() == null) ? 0 : getLegal_name().hashCode());
        result = prime * result + ((getLegal_license_type() == null) ? 0 : getLegal_license_type().hashCode());
        result = prime * result + ((getLegal_license() == null) ? 0 : getLegal_license().hashCode());
        result = prime * result + ((getTax_license() == null) ? 0 : getTax_license().hashCode());
        result = prime * result + ((getTax_number() == null) ? 0 : getTax_number().hashCode());
        result = prime * result + ((getOrg_license() == null) ? 0 : getOrg_license().hashCode());
        result = prime * result + ((getPlat_state() == null) ? 0 : getPlat_state().hashCode());
        result = prime * result + ((getCompany_establish_date() == null) ? 0 : getCompany_establish_date().hashCode());
        result = prime * result + ((getPlat_line_date() == null) ? 0 : getPlat_line_date().hashCode());
        result = prime * result + ((getPlat_address() == null) ? 0 : getPlat_address().hashCode());
        result = prime * result + ((getPlat_busi_type() == null) ? 0 : getPlat_busi_type().hashCode());
        result = prime * result + ((getPlat_business() == null) ? 0 : getPlat_business().hashCode());
        result = prime * result + ((getPayment_plat_no() == null) ? 0 : getPayment_plat_no().hashCode());
        result = prime * result + ((getActual_capital() == null) ? 0 : getActual_capital().hashCode());
        result = prime * result + ((getPlat_domain() == null) ? 0 : getPlat_domain().hashCode());
        result = prime * result + ((getInfo_security() == null) ? 0 : getInfo_security().hashCode());
        result = prime * result + ((getCheck_company() == null) ? 0 : getCheck_company().hashCode());
        result = prime * result + ((getCheck_time() == null) ? 0 : getCheck_time().hashCode());
        result = prime * result + ((getAccount_verification_channel() == null) ? 0 : getAccount_verification_channel().hashCode());
        result = prime * result + ((getSocial_credit_code() == null) ? 0 : getSocial_credit_code().hashCode());
        result = prime * result + ((getAdd_notify_url() == null) ? 0 : getAdd_notify_url().hashCode());
        result = prime * result + ((getDaily_cut_url() == null) ? 0 : getDaily_cut_url().hashCode());
        result = prime * result + ((getPayment_account() == null) ? 0 : getPayment_account().hashCode());
        result = prime * result + ((getLiquidation_url() == null) ? 0 : getLiquidation_url().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getCert() == null) ? 0 : getCert().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mall_no=").append(mall_no);
        sb.append(", mall_name=").append(mall_name);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", plat_name=").append(plat_name);
        sb.append(", plat_type=").append(plat_type);
        sb.append(", plat_pwd=").append(plat_pwd);
        sb.append(", company_attr=").append(company_attr);
        sb.append(", company_name=").append(company_name);
        sb.append(", company_capital=").append(company_capital);
        sb.append(", company_license=").append(company_license);
        sb.append(", legal_name=").append(legal_name);
        sb.append(", legal_license_type=").append(legal_license_type);
        sb.append(", legal_license=").append(legal_license);
        sb.append(", tax_license=").append(tax_license);
        sb.append(", tax_number=").append(tax_number);
        sb.append(", org_license=").append(org_license);
        sb.append(", plat_state=").append(plat_state);
        sb.append(", company_establish_date=").append(company_establish_date);
        sb.append(", plat_line_date=").append(plat_line_date);
        sb.append(", plat_address=").append(plat_address);
        sb.append(", plat_busi_type=").append(plat_busi_type);
        sb.append(", plat_business=").append(plat_business);
        sb.append(", payment_plat_no=").append(payment_plat_no);
        sb.append(", actual_capital=").append(actual_capital);
        sb.append(", plat_domain=").append(plat_domain);
        sb.append(", info_security=").append(info_security);
        sb.append(", check_company=").append(check_company);
        sb.append(", check_time=").append(check_time);
        sb.append(", account_verification_channel=").append(account_verification_channel);
        sb.append(", social_credit_code=").append(social_credit_code);
        sb.append(", add_notify_url=").append(add_notify_url);
        sb.append(", daily_cut_url=").append(daily_cut_url);
        sb.append(", payment_account=").append(payment_account);
        sb.append(", liquidation_url=").append(liquidation_url);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", cert=").append(cert);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}