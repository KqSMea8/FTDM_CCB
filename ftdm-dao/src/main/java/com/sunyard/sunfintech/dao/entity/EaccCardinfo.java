package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class EaccCardinfo implements Serializable {
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
    private String pay_channel;

    /**
     * 
     */
    private String card_no;

    /**
     * 
     */
    private String bank_no;

    /**
     * 
     */
    private String open_branch;

    /**
     * 
     */
    private String card_type;

    /**
     * 
     */
    private String mobile;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String bindId;

    /**
     * 
     */
    private String acct_name;

    /**
     * 
     */
    private String acct_no;

    /**
     * 
     */
    private String org_no;

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
     * eacc_cardinfo
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
     * @return pay_channel 
     */
    public String getPay_channel() {
        return pay_channel;
    }

    /**
     * 
     * @param pay_channel 
     */
    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel == null ? null : pay_channel.trim();
    }

    /**
     * 
     * @return card_no 
     */
    public String getCard_no() {
        return card_no;
    }

    /**
     * 
     * @param card_no 
     */
    public void setCard_no(String card_no) {
        this.card_no = card_no == null ? null : card_no.trim();
    }

    /**
     * 
     * @return bank_no 
     */
    public String getBank_no() {
        return bank_no;
    }

    /**
     * 
     * @param bank_no 
     */
    public void setBank_no(String bank_no) {
        this.bank_no = bank_no == null ? null : bank_no.trim();
    }

    /**
     * 
     * @return open_branch 
     */
    public String getOpen_branch() {
        return open_branch;
    }

    /**
     * 
     * @param open_branch 
     */
    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch == null ? null : open_branch.trim();
    }

    /**
     * 
     * @return card_type 
     */
    public String getCard_type() {
        return card_type;
    }

    /**
     * 
     * @param card_type 
     */
    public void setCard_type(String card_type) {
        this.card_type = card_type == null ? null : card_type.trim();
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
     * @return status 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * @return bindId 
     */
    public String getBindId() {
        return bindId;
    }

    /**
     * 
     * @param bindId 
     */
    public void setBindId(String bindId) {
        this.bindId = bindId == null ? null : bindId.trim();
    }

    /**
     * 
     * @return acct_name 
     */
    public String getAcct_name() {
        return acct_name;
    }

    /**
     * 
     * @param acct_name 
     */
    public void setAcct_name(String acct_name) {
        this.acct_name = acct_name == null ? null : acct_name.trim();
    }

    /**
     * 
     * @return acct_no 
     */
    public String getAcct_no() {
        return acct_no;
    }

    /**
     * 
     * @param acct_no 
     */
    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no == null ? null : acct_no.trim();
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
        EaccCardinfo other = (EaccCardinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMall_no() == null ? other.getMall_no() == null : this.getMall_no().equals(other.getMall_no()))
            && (this.getMallcust() == null ? other.getMallcust() == null : this.getMallcust().equals(other.getMallcust()))
            && (this.getPay_channel() == null ? other.getPay_channel() == null : this.getPay_channel().equals(other.getPay_channel()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getBank_no() == null ? other.getBank_no() == null : this.getBank_no().equals(other.getBank_no()))
            && (this.getOpen_branch() == null ? other.getOpen_branch() == null : this.getOpen_branch().equals(other.getOpen_branch()))
            && (this.getCard_type() == null ? other.getCard_type() == null : this.getCard_type().equals(other.getCard_type()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBindId() == null ? other.getBindId() == null : this.getBindId().equals(other.getBindId()))
            && (this.getAcct_name() == null ? other.getAcct_name() == null : this.getAcct_name().equals(other.getAcct_name()))
            && (this.getAcct_no() == null ? other.getAcct_no() == null : this.getAcct_no().equals(other.getAcct_no()))
            && (this.getOrg_no() == null ? other.getOrg_no() == null : this.getOrg_no().equals(other.getOrg_no()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
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
        result = prime * result + ((getMallcust() == null) ? 0 : getMallcust().hashCode());
        result = prime * result + ((getPay_channel() == null) ? 0 : getPay_channel().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getBank_no() == null) ? 0 : getBank_no().hashCode());
        result = prime * result + ((getOpen_branch() == null) ? 0 : getOpen_branch().hashCode());
        result = prime * result + ((getCard_type() == null) ? 0 : getCard_type().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBindId() == null) ? 0 : getBindId().hashCode());
        result = prime * result + ((getAcct_name() == null) ? 0 : getAcct_name().hashCode());
        result = prime * result + ((getAcct_no() == null) ? 0 : getAcct_no().hashCode());
        result = prime * result + ((getOrg_no() == null) ? 0 : getOrg_no().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
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
        sb.append(", mallcust=").append(mallcust);
        sb.append(", pay_channel=").append(pay_channel);
        sb.append(", card_no=").append(card_no);
        sb.append(", bank_no=").append(bank_no);
        sb.append(", open_branch=").append(open_branch);
        sb.append(", card_type=").append(card_type);
        sb.append(", mobile=").append(mobile);
        sb.append(", status=").append(status);
        sb.append(", bindId=").append(bindId);
        sb.append(", acct_name=").append(acct_name);
        sb.append(", acct_no=").append(acct_no);
        sb.append(", org_no=").append(org_no);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}