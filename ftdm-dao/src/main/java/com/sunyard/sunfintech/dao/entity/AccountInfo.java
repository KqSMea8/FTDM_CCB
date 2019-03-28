package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountInfo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 账号
     */
    private String platcust;

    /**
     * 集团客户号
     */
    private String mall_platcust;

    /**
     * 01-用户账户             02-平台账户             03-标的账户             04-用户电子账户
     */
    private String account_type;

    /**
     * 账户名称(平台账户、标的账户、个人账户)
     */
    private String account_name;

    /**
     * 总金额
     */
    private BigDecimal t_balance;

    /**
     * 可用金额
     */
    private BigDecimal n_balance;

    /**
     * 冻结金额
     */
    private BigDecimal f_balance;

    /**
     * 昨日余额
     */
    private BigDecimal yester_balance;

    /**
     * 账户修改日期
     */
    private Date modify_time;

    /**
     * 清算日期
     */
    private Date clear_date;

    /**
     * 状态
     */
    private String status;

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
    private String ext1;

    /**
     * 
     */
    private String ext2;

    /**
     * 
     */
    private String ext3;

    /**
     * 
     */
    private String ext4;

    /**
     * 
     */
    private String ext5;

    /**
     * account_info
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 平台编号
     * @return plat_no 平台编号
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 平台编号
     * @param plat_no 平台编号
     */
    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no == null ? null : plat_no.trim();
    }

    /**
     * 账号
     * @return platcust 账号
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 账号
     * @param platcust 账号
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 集团客户号
     * @return mall_platcust 集团客户号
     */
    public String getMall_platcust() {
        return mall_platcust;
    }

    /**
     * 集团客户号
     * @param mall_platcust 集团客户号
     */
    public void setMall_platcust(String mall_platcust) {
        this.mall_platcust = mall_platcust == null ? null : mall_platcust.trim();
    }

    /**
     * 01-用户账户             02-平台账户             03-标的账户             04-用户电子账户
     * @return account_type 01-用户账户             02-平台账户             03-标的账户             04-用户电子账户
     */
    public String getAccount_type() {
        return account_type;
    }

    /**
     * 01-用户账户             02-平台账户             03-标的账户             04-用户电子账户
     * @param account_type 01-用户账户             02-平台账户             03-标的账户             04-用户电子账户
     */
    public void setAccount_type(String account_type) {
        this.account_type = account_type == null ? null : account_type.trim();
    }

    /**
     * 账户名称(平台账户、标的账户、个人账户)
     * @return account_name 账户名称(平台账户、标的账户、个人账户)
     */
    public String getAccount_name() {
        return account_name;
    }

    /**
     * 账户名称(平台账户、标的账户、个人账户)
     * @param account_name 账户名称(平台账户、标的账户、个人账户)
     */
    public void setAccount_name(String account_name) {
        this.account_name = account_name == null ? null : account_name.trim();
    }

    /**
     * 总金额
     * @return t_balance 总金额
     */
    public BigDecimal getT_balance() {
        return t_balance;
    }

    /**
     * 总金额
     * @param t_balance 总金额
     */
    public void setT_balance(BigDecimal t_balance) {
        this.t_balance = t_balance;
    }

    /**
     * 可用金额
     * @return n_balance 可用金额
     */
    public BigDecimal getN_balance() {
        return n_balance;
    }

    /**
     * 可用金额
     * @param n_balance 可用金额
     */
    public void setN_balance(BigDecimal n_balance) {
        this.n_balance = n_balance;
    }

    /**
     * 冻结金额
     * @return f_balance 冻结金额
     */
    public BigDecimal getF_balance() {
        return f_balance;
    }

    /**
     * 冻结金额
     * @param f_balance 冻结金额
     */
    public void setF_balance(BigDecimal f_balance) {
        this.f_balance = f_balance;
    }

    /**
     * 昨日余额
     * @return yester_balance 昨日余额
     */
    public BigDecimal getYester_balance() {
        return yester_balance;
    }

    /**
     * 昨日余额
     * @param yester_balance 昨日余额
     */
    public void setYester_balance(BigDecimal yester_balance) {
        this.yester_balance = yester_balance;
    }

    /**
     * 账户修改日期
     * @return modify_time 账户修改日期
     */
    public Date getModify_time() {
        return modify_time;
    }

    /**
     * 账户修改日期
     * @param modify_time 账户修改日期
     */
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    /**
     * 清算日期
     * @return clear_date 清算日期
     */
    public Date getClear_date() {
        return clear_date;
    }

    /**
     * 清算日期
     * @param clear_date 清算日期
     */
    public void setClear_date(Date clear_date) {
        this.clear_date = clear_date;
    }

    /**
     * 状态
     * @return status 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
     * @return ext1 
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 
     * @param ext1 
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 
     * @return ext2 
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 
     * @param ext2 
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * 
     * @return ext3 
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * 
     * @param ext3 
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    /**
     * 
     * @return ext4 
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * 
     * @param ext4 
     */
    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    /**
     * 
     * @return ext5 
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * 
     * @param ext5 
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
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
        AccountInfo other = (AccountInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getMall_platcust() == null ? other.getMall_platcust() == null : this.getMall_platcust().equals(other.getMall_platcust()))
            && (this.getAccount_type() == null ? other.getAccount_type() == null : this.getAccount_type().equals(other.getAccount_type()))
            && (this.getAccount_name() == null ? other.getAccount_name() == null : this.getAccount_name().equals(other.getAccount_name()))
            && (this.getT_balance() == null ? other.getT_balance() == null : this.getT_balance().equals(other.getT_balance()))
            && (this.getN_balance() == null ? other.getN_balance() == null : this.getN_balance().equals(other.getN_balance()))
            && (this.getF_balance() == null ? other.getF_balance() == null : this.getF_balance().equals(other.getF_balance()))
            && (this.getYester_balance() == null ? other.getYester_balance() == null : this.getYester_balance().equals(other.getYester_balance()))
            && (this.getModify_time() == null ? other.getModify_time() == null : this.getModify_time().equals(other.getModify_time()))
            && (this.getClear_date() == null ? other.getClear_date() == null : this.getClear_date().equals(other.getClear_date()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()))
            && (this.getExt4() == null ? other.getExt4() == null : this.getExt4().equals(other.getExt4()))
            && (this.getExt5() == null ? other.getExt5() == null : this.getExt5().equals(other.getExt5()));
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
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getMall_platcust() == null) ? 0 : getMall_platcust().hashCode());
        result = prime * result + ((getAccount_type() == null) ? 0 : getAccount_type().hashCode());
        result = prime * result + ((getAccount_name() == null) ? 0 : getAccount_name().hashCode());
        result = prime * result + ((getT_balance() == null) ? 0 : getT_balance().hashCode());
        result = prime * result + ((getN_balance() == null) ? 0 : getN_balance().hashCode());
        result = prime * result + ((getF_balance() == null) ? 0 : getF_balance().hashCode());
        result = prime * result + ((getYester_balance() == null) ? 0 : getYester_balance().hashCode());
        result = prime * result + ((getModify_time() == null) ? 0 : getModify_time().hashCode());
        result = prime * result + ((getClear_date() == null) ? 0 : getClear_date().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        result = prime * result + ((getExt4() == null) ? 0 : getExt4().hashCode());
        result = prime * result + ((getExt5() == null) ? 0 : getExt5().hashCode());
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
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", mall_platcust=").append(mall_platcust);
        sb.append(", account_type=").append(account_type);
        sb.append(", account_name=").append(account_name);
        sb.append(", t_balance=").append(t_balance);
        sb.append(", n_balance=").append(n_balance);
        sb.append(", f_balance=").append(f_balance);
        sb.append(", yester_balance=").append(yester_balance);
        sb.append(", modify_time=").append(modify_time);
        sb.append(", clear_date=").append(clear_date);
        sb.append(", status=").append(status);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", ext3=").append(ext3);
        sb.append(", ext4=").append(ext4);
        sb.append(", ext5=").append(ext5);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}