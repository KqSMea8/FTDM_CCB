package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdTransferRecord implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String trans_serial;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String ptrans_date;

    /**
     * 
     */
    private String partner_trans_time;

    /**
     * 
     */
    private String order_no;

    /**
     * 
     */
    private String platcust;

    /**
     * 
     */
    private BigDecimal trans_share;

    /**
     * 
     */
    private String prod_id;

    /**
     * 
     */
    private BigDecimal trans_amt;

    /**
     * 
     */
    private BigDecimal deal_amout;

    /**
     * 
     */
    private BigDecimal invest_amt;

    /**
     * 
     */
    private BigDecimal extract_amt;

    /**
     * 
     */
    private String deal_platcust;

    /**
     * 
     */
    private String commission;

    /**
     * 
     */
    private String commission_ext;

    /**
     * 
     */
    private Date publish_date;

    /**
     * 
     */
    private Date deal_date;

    /**
     * 
     */
    private BigDecimal transfer_income;

    /**
     * 平台：01  ；个人：对应platcust
     */
    private String income_acct;

    /**
     * 
     */
    private String related_prod_ids;

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
     * prod_transfer_record
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
     * @return trans_serial 
     */
    public String getTrans_serial() {
        return trans_serial;
    }

    /**
     * 
     * @param trans_serial 
     */
    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial == null ? null : trans_serial.trim();
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
     * @return ptrans_date 
     */
    public String getPtrans_date() {
        return ptrans_date;
    }

    /**
     * 
     * @param ptrans_date 
     */
    public void setPtrans_date(String ptrans_date) {
        this.ptrans_date = ptrans_date == null ? null : ptrans_date.trim();
    }

    /**
     * 
     * @return partner_trans_time 
     */
    public String getPartner_trans_time() {
        return partner_trans_time;
    }

    /**
     * 
     * @param partner_trans_time 
     */
    public void setPartner_trans_time(String partner_trans_time) {
        this.partner_trans_time = partner_trans_time == null ? null : partner_trans_time.trim();
    }

    /**
     * 
     * @return order_no 
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * 
     * @param order_no 
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * 
     * @return platcust 
     */
    public String getPlatcust() {
        return platcust;
    }

    /**
     * 
     * @param platcust 
     */
    public void setPlatcust(String platcust) {
        this.platcust = platcust == null ? null : platcust.trim();
    }

    /**
     * 
     * @return trans_share 
     */
    public BigDecimal getTrans_share() {
        return trans_share;
    }

    /**
     * 
     * @param trans_share 
     */
    public void setTrans_share(BigDecimal trans_share) {
        this.trans_share = trans_share;
    }

    /**
     * 
     * @return prod_id 
     */
    public String getProd_id() {
        return prod_id;
    }

    /**
     * 
     * @param prod_id 
     */
    public void setProd_id(String prod_id) {
        this.prod_id = prod_id == null ? null : prod_id.trim();
    }

    /**
     * 
     * @return trans_amt 
     */
    public BigDecimal getTrans_amt() {
        return trans_amt;
    }

    /**
     * 
     * @param trans_amt 
     */
    public void setTrans_amt(BigDecimal trans_amt) {
        this.trans_amt = trans_amt;
    }

    /**
     * 
     * @return deal_amout 
     */
    public BigDecimal getDeal_amout() {
        return deal_amout;
    }

    /**
     * 
     * @param deal_amout 
     */
    public void setDeal_amout(BigDecimal deal_amout) {
        this.deal_amout = deal_amout;
    }

    /**
     * 
     * @return invest_amt 
     */
    public BigDecimal getInvest_amt() {
        return invest_amt;
    }

    /**
     * 
     * @param invest_amt 
     */
    public void setInvest_amt(BigDecimal invest_amt) {
        this.invest_amt = invest_amt;
    }

    /**
     * 
     * @return extract_amt 
     */
    public BigDecimal getExtract_amt() {
        return extract_amt;
    }

    /**
     * 
     * @param extract_amt 
     */
    public void setExtract_amt(BigDecimal extract_amt) {
        this.extract_amt = extract_amt;
    }

    /**
     * 
     * @return deal_platcust 
     */
    public String getDeal_platcust() {
        return deal_platcust;
    }

    /**
     * 
     * @param deal_platcust 
     */
    public void setDeal_platcust(String deal_platcust) {
        this.deal_platcust = deal_platcust == null ? null : deal_platcust.trim();
    }

    /**
     * 
     * @return commission 
     */
    public String getCommission() {
        return commission;
    }

    /**
     * 
     * @param commission 
     */
    public void setCommission(String commission) {
        this.commission = commission == null ? null : commission.trim();
    }

    /**
     * 
     * @return commission_ext 
     */
    public String getCommission_ext() {
        return commission_ext;
    }

    /**
     * 
     * @param commission_ext 
     */
    public void setCommission_ext(String commission_ext) {
        this.commission_ext = commission_ext == null ? null : commission_ext.trim();
    }

    /**
     * 
     * @return publish_date 
     */
    public Date getPublish_date() {
        return publish_date;
    }

    /**
     * 
     * @param publish_date 
     */
    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    /**
     * 
     * @return deal_date 
     */
    public Date getDeal_date() {
        return deal_date;
    }

    /**
     * 
     * @param deal_date 
     */
    public void setDeal_date(Date deal_date) {
        this.deal_date = deal_date;
    }

    /**
     * 
     * @return transfer_income 
     */
    public BigDecimal getTransfer_income() {
        return transfer_income;
    }

    /**
     * 
     * @param transfer_income 
     */
    public void setTransfer_income(BigDecimal transfer_income) {
        this.transfer_income = transfer_income;
    }

    /**
     * 平台：01  ；个人：对应platcust
     * @return income_acct 平台：01  ；个人：对应platcust
     */
    public String getIncome_acct() {
        return income_acct;
    }

    /**
     * 平台：01  ；个人：对应platcust
     * @param income_acct 平台：01  ；个人：对应platcust
     */
    public void setIncome_acct(String income_acct) {
        this.income_acct = income_acct == null ? null : income_acct.trim();
    }

    /**
     * 
     * @return related_prod_ids 
     */
    public String getRelated_prod_ids() {
        return related_prod_ids;
    }

    /**
     * 
     * @param related_prod_ids 
     */
    public void setRelated_prod_ids(String related_prod_ids) {
        this.related_prod_ids = related_prod_ids == null ? null : related_prod_ids.trim();
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
        ProdTransferRecord other = (ProdTransferRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPtrans_date() == null ? other.getPtrans_date() == null : this.getPtrans_date().equals(other.getPtrans_date()))
            && (this.getPartner_trans_time() == null ? other.getPartner_trans_time() == null : this.getPartner_trans_time().equals(other.getPartner_trans_time()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getTrans_share() == null ? other.getTrans_share() == null : this.getTrans_share().equals(other.getTrans_share()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getTrans_amt() == null ? other.getTrans_amt() == null : this.getTrans_amt().equals(other.getTrans_amt()))
            && (this.getDeal_amout() == null ? other.getDeal_amout() == null : this.getDeal_amout().equals(other.getDeal_amout()))
            && (this.getInvest_amt() == null ? other.getInvest_amt() == null : this.getInvest_amt().equals(other.getInvest_amt()))
            && (this.getExtract_amt() == null ? other.getExtract_amt() == null : this.getExtract_amt().equals(other.getExtract_amt()))
            && (this.getDeal_platcust() == null ? other.getDeal_platcust() == null : this.getDeal_platcust().equals(other.getDeal_platcust()))
            && (this.getCommission() == null ? other.getCommission() == null : this.getCommission().equals(other.getCommission()))
            && (this.getCommission_ext() == null ? other.getCommission_ext() == null : this.getCommission_ext().equals(other.getCommission_ext()))
            && (this.getPublish_date() == null ? other.getPublish_date() == null : this.getPublish_date().equals(other.getPublish_date()))
            && (this.getDeal_date() == null ? other.getDeal_date() == null : this.getDeal_date().equals(other.getDeal_date()))
            && (this.getTransfer_income() == null ? other.getTransfer_income() == null : this.getTransfer_income().equals(other.getTransfer_income()))
            && (this.getIncome_acct() == null ? other.getIncome_acct() == null : this.getIncome_acct().equals(other.getIncome_acct()))
            && (this.getRelated_prod_ids() == null ? other.getRelated_prod_ids() == null : this.getRelated_prod_ids().equals(other.getRelated_prod_ids()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()));
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
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPtrans_date() == null) ? 0 : getPtrans_date().hashCode());
        result = prime * result + ((getPartner_trans_time() == null) ? 0 : getPartner_trans_time().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getTrans_share() == null) ? 0 : getTrans_share().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getTrans_amt() == null) ? 0 : getTrans_amt().hashCode());
        result = prime * result + ((getDeal_amout() == null) ? 0 : getDeal_amout().hashCode());
        result = prime * result + ((getInvest_amt() == null) ? 0 : getInvest_amt().hashCode());
        result = prime * result + ((getExtract_amt() == null) ? 0 : getExtract_amt().hashCode());
        result = prime * result + ((getDeal_platcust() == null) ? 0 : getDeal_platcust().hashCode());
        result = prime * result + ((getCommission() == null) ? 0 : getCommission().hashCode());
        result = prime * result + ((getCommission_ext() == null) ? 0 : getCommission_ext().hashCode());
        result = prime * result + ((getPublish_date() == null) ? 0 : getPublish_date().hashCode());
        result = prime * result + ((getDeal_date() == null) ? 0 : getDeal_date().hashCode());
        result = prime * result + ((getTransfer_income() == null) ? 0 : getTransfer_income().hashCode());
        result = prime * result + ((getIncome_acct() == null) ? 0 : getIncome_acct().hashCode());
        result = prime * result + ((getRelated_prod_ids() == null) ? 0 : getRelated_prod_ids().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
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
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", ptrans_date=").append(ptrans_date);
        sb.append(", partner_trans_time=").append(partner_trans_time);
        sb.append(", order_no=").append(order_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", trans_share=").append(trans_share);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", trans_amt=").append(trans_amt);
        sb.append(", deal_amout=").append(deal_amout);
        sb.append(", invest_amt=").append(invest_amt);
        sb.append(", extract_amt=").append(extract_amt);
        sb.append(", deal_platcust=").append(deal_platcust);
        sb.append(", commission=").append(commission);
        sb.append(", commission_ext=").append(commission_ext);
        sb.append(", publish_date=").append(publish_date);
        sb.append(", deal_date=").append(deal_date);
        sb.append(", transfer_income=").append(transfer_income);
        sb.append(", income_acct=").append(income_acct);
        sb.append(", related_prod_ids=").append(related_prod_ids);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}