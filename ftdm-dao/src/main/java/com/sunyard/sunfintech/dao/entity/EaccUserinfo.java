package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class EaccUserinfo implements Serializable {
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
    private String mallcust;

    /**
     * 
     */
    private String eaccount_flg;

    /**
     * 
     */
    private String eaccount;

    /**
     * 
     */
    private String cus_type;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String id_type;

    /**
     * 
     */
    private String id_code;

    /**
     * 
     */
    private String mobile;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String sex;

    /**
     * 
     */
    private String trans_pwd;

    /**
     * 
     */
    private Date reg_time;

    /**
     * 
     */
    private String is_card_bind;

    /**
     * 
     */
    private String org_name;

    /**
     * 
     */
    private String bank_license;

    /**
     * 
     */
    private String org_no;

    /**
     * 
     */
    private String business_license;

    /**
     * 
     */
    private String tax_no;

    /**
     * 
     */
    private String default_card_no;

    /**
     * 
     */
    private String default_mobile;

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
     * 投资人
     */
    private Byte invester;

    /**
     * 融资人
     */
    private Byte financier;

    /**
     * 垫资人
     */
    private Byte advancer;

    /**
     * 担保人
     */
    private Byte guarantor;

    /**
     * 扩展角色1
     */
    private Byte ext_role1;

    /**
     * 扩展角色2
     */
    private Byte ext_role2;

    /**
     * 是否设置密码，0：未设置，1：已设置
     */
    private String pwd_flg;

    /**
     * eacc_userinfo
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
     * @return mallcust 
     */
    public String getMallcust() {
        return mallcust;
    }

    /**
     * 
     * @param mallcust 
     */
    public void setMallcust(String mallcust) {
        this.mallcust = mallcust == null ? null : mallcust.trim();
    }

    /**
     * 
     * @return eaccount_flg 
     */
    public String getEaccount_flg() {
        return eaccount_flg;
    }

    /**
     * 
     * @param eaccount_flg 
     */
    public void setEaccount_flg(String eaccount_flg) {
        this.eaccount_flg = eaccount_flg == null ? null : eaccount_flg.trim();
    }

    /**
     * 
     * @return eaccount 
     */
    public String getEaccount() {
        return eaccount;
    }

    /**
     * 
     * @param eaccount 
     */
    public void setEaccount(String eaccount) {
        this.eaccount = eaccount == null ? null : eaccount.trim();
    }

    /**
     * 
     * @return cus_type 
     */
    public String getCus_type() {
        return cus_type;
    }

    /**
     * 
     * @param cus_type 
     */
    public void setCus_type(String cus_type) {
        this.cus_type = cus_type == null ? null : cus_type.trim();
    }

    /**
     * 
     * @return name 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return id_type 
     */
    public String getId_type() {
        return id_type;
    }

    /**
     * 
     * @param id_type 
     */
    public void setId_type(String id_type) {
        this.id_type = id_type == null ? null : id_type.trim();
    }

    /**
     * 
     * @return id_code 
     */
    public String getId_code() {
        return id_code;
    }

    /**
     * 
     * @param id_code 
     */
    public void setId_code(String id_code) {
        this.id_code = id_code == null ? null : id_code.trim();
    }

    /**
     * 
     * @return mobile 
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 
     * @return email 
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 
     * @return sex 
     */
    public String getSex() {
        return sex;
    }

    /**
     * 
     * @param sex 
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 
     * @return trans_pwd 
     */
    public String getTrans_pwd() {
        return trans_pwd;
    }

    /**
     * 
     * @param trans_pwd 
     */
    public void setTrans_pwd(String trans_pwd) {
        this.trans_pwd = trans_pwd == null ? null : trans_pwd.trim();
    }

    /**
     * 
     * @return reg_time 
     */
    public Date getReg_time() {
        return reg_time;
    }

    /**
     * 
     * @param reg_time 
     */
    public void setReg_time(Date reg_time) {
        this.reg_time = reg_time;
    }

    /**
     * 
     * @return is_card_bind 
     */
    public String getIs_card_bind() {
        return is_card_bind;
    }

    /**
     * 
     * @param is_card_bind 
     */
    public void setIs_card_bind(String is_card_bind) {
        this.is_card_bind = is_card_bind == null ? null : is_card_bind.trim();
    }

    /**
     * 
     * @return org_name 
     */
    public String getOrg_name() {
        return org_name;
    }

    /**
     * 
     * @param org_name 
     */
    public void setOrg_name(String org_name) {
        this.org_name = org_name == null ? null : org_name.trim();
    }

    /**
     * 
     * @return bank_license 
     */
    public String getBank_license() {
        return bank_license;
    }

    /**
     * 
     * @param bank_license 
     */
    public void setBank_license(String bank_license) {
        this.bank_license = bank_license == null ? null : bank_license.trim();
    }

    /**
     * 
     * @return org_no 
     */
    public String getOrg_no() {
        return org_no;
    }

    /**
     * 
     * @param org_no 
     */
    public void setOrg_no(String org_no) {
        this.org_no = org_no == null ? null : org_no.trim();
    }

    /**
     * 
     * @return business_license 
     */
    public String getBusiness_license() {
        return business_license;
    }

    /**
     * 
     * @param business_license 
     */
    public void setBusiness_license(String business_license) {
        this.business_license = business_license == null ? null : business_license.trim();
    }

    /**
     * 
     * @return tax_no 
     */
    public String getTax_no() {
        return tax_no;
    }

    /**
     * 
     * @param tax_no 
     */
    public void setTax_no(String tax_no) {
        this.tax_no = tax_no == null ? null : tax_no.trim();
    }

    /**
     * 
     * @return default_card_no 
     */
    public String getDefault_card_no() {
        return default_card_no;
    }

    /**
     * 
     * @param default_card_no 
     */
    public void setDefault_card_no(String default_card_no) {
        this.default_card_no = default_card_no == null ? null : default_card_no.trim();
    }

    /**
     * 
     * @return default_mobile 
     */
    public String getDefault_mobile() {
        return default_mobile;
    }

    /**
     * 
     * @param default_mobile 
     */
    public void setDefault_mobile(String default_mobile) {
        this.default_mobile = default_mobile == null ? null : default_mobile.trim();
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
     * 投资人
     * @return invester 投资人
     */
    public Byte getInvester() {
        return invester;
    }

    /**
     * 投资人
     * @param invester 投资人
     */
    public void setInvester(Byte invester) {
        this.invester = invester;
    }

    /**
     * 融资人
     * @return financier 融资人
     */
    public Byte getFinancier() {
        return financier;
    }

    /**
     * 融资人
     * @param financier 融资人
     */
    public void setFinancier(Byte financier) {
        this.financier = financier;
    }

    /**
     * 垫资人
     * @return advancer 垫资人
     */
    public Byte getAdvancer() {
        return advancer;
    }

    /**
     * 垫资人
     * @param advancer 垫资人
     */
    public void setAdvancer(Byte advancer) {
        this.advancer = advancer;
    }

    /**
     * 担保人
     * @return guarantor 担保人
     */
    public Byte getGuarantor() {
        return guarantor;
    }

    /**
     * 担保人
     * @param guarantor 担保人
     */
    public void setGuarantor(Byte guarantor) {
        this.guarantor = guarantor;
    }

    /**
     * 扩展角色1
     * @return ext_role1 扩展角色1
     */
    public Byte getExt_role1() {
        return ext_role1;
    }

    /**
     * 扩展角色1
     * @param ext_role1 扩展角色1
     */
    public void setExt_role1(Byte ext_role1) {
        this.ext_role1 = ext_role1;
    }

    /**
     * 扩展角色2
     * @return ext_role2 扩展角色2
     */
    public Byte getExt_role2() {
        return ext_role2;
    }

    /**
     * 扩展角色2
     * @param ext_role2 扩展角色2
     */
    public void setExt_role2(Byte ext_role2) {
        this.ext_role2 = ext_role2;
    }

    /**
     * 是否设置密码，0：未设置，1：已设置
     * @return pwd_flg 是否设置密码，0：未设置，1：已设置
     */
    public String getPwd_flg() {
        return pwd_flg;
    }

    /**
     * 是否设置密码，0：未设置，1：已设置
     * @param pwd_flg 是否设置密码，0：未设置，1：已设置
     */
    public void setPwd_flg(String pwd_flg) {
        this.pwd_flg = pwd_flg == null ? null : pwd_flg.trim();
    }

    /**
     *
     * @mbggenerated 2018-02-24
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
        EaccUserinfo other = (EaccUserinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getMallcust() == null ? other.getMallcust() == null : this.getMallcust().equals(other.getMallcust()))
            && (this.getEaccount_flg() == null ? other.getEaccount_flg() == null : this.getEaccount_flg().equals(other.getEaccount_flg()))
            && (this.getEaccount() == null ? other.getEaccount() == null : this.getEaccount().equals(other.getEaccount()))
            && (this.getCus_type() == null ? other.getCus_type() == null : this.getCus_type().equals(other.getCus_type()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getId_type() == null ? other.getId_type() == null : this.getId_type().equals(other.getId_type()))
            && (this.getId_code() == null ? other.getId_code() == null : this.getId_code().equals(other.getId_code()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getTrans_pwd() == null ? other.getTrans_pwd() == null : this.getTrans_pwd().equals(other.getTrans_pwd()))
            && (this.getReg_time() == null ? other.getReg_time() == null : this.getReg_time().equals(other.getReg_time()))
            && (this.getIs_card_bind() == null ? other.getIs_card_bind() == null : this.getIs_card_bind().equals(other.getIs_card_bind()))
            && (this.getOrg_name() == null ? other.getOrg_name() == null : this.getOrg_name().equals(other.getOrg_name()))
            && (this.getBank_license() == null ? other.getBank_license() == null : this.getBank_license().equals(other.getBank_license()))
            && (this.getOrg_no() == null ? other.getOrg_no() == null : this.getOrg_no().equals(other.getOrg_no()))
            && (this.getBusiness_license() == null ? other.getBusiness_license() == null : this.getBusiness_license().equals(other.getBusiness_license()))
            && (this.getTax_no() == null ? other.getTax_no() == null : this.getTax_no().equals(other.getTax_no()))
            && (this.getDefault_card_no() == null ? other.getDefault_card_no() == null : this.getDefault_card_no().equals(other.getDefault_card_no()))
            && (this.getDefault_mobile() == null ? other.getDefault_mobile() == null : this.getDefault_mobile().equals(other.getDefault_mobile()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getInvester() == null ? other.getInvester() == null : this.getInvester().equals(other.getInvester()))
            && (this.getFinancier() == null ? other.getFinancier() == null : this.getFinancier().equals(other.getFinancier()))
            && (this.getAdvancer() == null ? other.getAdvancer() == null : this.getAdvancer().equals(other.getAdvancer()))
            && (this.getGuarantor() == null ? other.getGuarantor() == null : this.getGuarantor().equals(other.getGuarantor()))
            && (this.getExt_role1() == null ? other.getExt_role1() == null : this.getExt_role1().equals(other.getExt_role1()))
            && (this.getExt_role2() == null ? other.getExt_role2() == null : this.getExt_role2().equals(other.getExt_role2()))
            && (this.getPwd_flg() == null ? other.getPwd_flg() == null : this.getPwd_flg().equals(other.getPwd_flg()));
    }

    /**
     *
     * @mbggenerated 2018-02-24
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMall_no() == null) ? 0 : getMall_no().hashCode());
        result = prime * result + ((getMallcust() == null) ? 0 : getMallcust().hashCode());
        result = prime * result + ((getEaccount_flg() == null) ? 0 : getEaccount_flg().hashCode());
        result = prime * result + ((getEaccount() == null) ? 0 : getEaccount().hashCode());
        result = prime * result + ((getCus_type() == null) ? 0 : getCus_type().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getId_type() == null) ? 0 : getId_type().hashCode());
        result = prime * result + ((getId_code() == null) ? 0 : getId_code().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getTrans_pwd() == null) ? 0 : getTrans_pwd().hashCode());
        result = prime * result + ((getReg_time() == null) ? 0 : getReg_time().hashCode());
        result = prime * result + ((getIs_card_bind() == null) ? 0 : getIs_card_bind().hashCode());
        result = prime * result + ((getOrg_name() == null) ? 0 : getOrg_name().hashCode());
        result = prime * result + ((getBank_license() == null) ? 0 : getBank_license().hashCode());
        result = prime * result + ((getOrg_no() == null) ? 0 : getOrg_no().hashCode());
        result = prime * result + ((getBusiness_license() == null) ? 0 : getBusiness_license().hashCode());
        result = prime * result + ((getTax_no() == null) ? 0 : getTax_no().hashCode());
        result = prime * result + ((getDefault_card_no() == null) ? 0 : getDefault_card_no().hashCode());
        result = prime * result + ((getDefault_mobile() == null) ? 0 : getDefault_mobile().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getInvester() == null) ? 0 : getInvester().hashCode());
        result = prime * result + ((getFinancier() == null) ? 0 : getFinancier().hashCode());
        result = prime * result + ((getAdvancer() == null) ? 0 : getAdvancer().hashCode());
        result = prime * result + ((getGuarantor() == null) ? 0 : getGuarantor().hashCode());
        result = prime * result + ((getExt_role1() == null) ? 0 : getExt_role1().hashCode());
        result = prime * result + ((getExt_role2() == null) ? 0 : getExt_role2().hashCode());
        result = prime * result + ((getPwd_flg() == null) ? 0 : getPwd_flg().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-02-24
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mall_no=").append(mall_no);
        sb.append(", mallcust=").append(mallcust);
        sb.append(", eaccount_flg=").append(eaccount_flg);
        sb.append(", eaccount=").append(eaccount);
        sb.append(", cus_type=").append(cus_type);
        sb.append(", name=").append(name);
        sb.append(", id_type=").append(id_type);
        sb.append(", id_code=").append(id_code);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", trans_pwd=").append(trans_pwd);
        sb.append(", reg_time=").append(reg_time);
        sb.append(", is_card_bind=").append(is_card_bind);
        sb.append(", org_name=").append(org_name);
        sb.append(", bank_license=").append(bank_license);
        sb.append(", org_no=").append(org_no);
        sb.append(", business_license=").append(business_license);
        sb.append(", tax_no=").append(tax_no);
        sb.append(", default_card_no=").append(default_card_no);
        sb.append(", default_mobile=").append(default_mobile);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", invester=").append(invester);
        sb.append(", financier=").append(financier);
        sb.append(", advancer=").append(advancer);
        sb.append(", guarantor=").append(guarantor);
        sb.append(", ext_role1=").append(ext_role1);
        sb.append(", ext_role2=").append(ext_role2);
        sb.append(", pwd_flg=").append(pwd_flg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}