package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SunbobTaskUnionpay implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String card_no;

    /**
     * 
     */
    private String third_no;

    /**
     * 
     */
    private String third_batch_no;

    /**
     * 
     */
    private BigDecimal amt;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String trans_type;

    /**
     * 
     */
    private String trans_name;

    /**
     * 
     */
    private String trans_date;

    /**
     * 
     */
    private String trans_time;

    /**
     * 
     */
    private String oppo_card;

    /**
     * 
     */
    private String oppo_name;

    /**
     * 
     */
    private Date finish_datetime;

    /**
     * 
     */
    private String result;

    /**
     * 
     */
    private String summary_wn;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private BigDecimal attr1;

    /**
     * 
     */
    private String attr2;

    /**
     * 
     */
    private String attr3;

    /**
     * sunbob_task_unionpay
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
     * @return third_no 
     */
    public String getThird_no() {
        return third_no;
    }

    /**
     * 
     * @param third_no 
     */
    public void setThird_no(String third_no) {
        this.third_no = third_no == null ? null : third_no.trim();
    }

    /**
     * 
     * @return third_batch_no 
     */
    public String getThird_batch_no() {
        return third_batch_no;
    }

    /**
     * 
     * @param third_batch_no 
     */
    public void setThird_batch_no(String third_batch_no) {
        this.third_batch_no = third_batch_no == null ? null : third_batch_no.trim();
    }

    /**
     * 
     * @return amt 
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 
     * @param amt 
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
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
     * @return trans_type 
     */
    public String getTrans_type() {
        return trans_type;
    }

    /**
     * 
     * @param trans_type 
     */
    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type == null ? null : trans_type.trim();
    }

    /**
     * 
     * @return trans_name 
     */
    public String getTrans_name() {
        return trans_name;
    }

    /**
     * 
     * @param trans_name 
     */
    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name == null ? null : trans_name.trim();
    }

    /**
     * 
     * @return trans_date 
     */
    public String getTrans_date() {
        return trans_date;
    }

    /**
     * 
     * @param trans_date 
     */
    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date == null ? null : trans_date.trim();
    }

    /**
     * 
     * @return trans_time 
     */
    public String getTrans_time() {
        return trans_time;
    }

    /**
     * 
     * @param trans_time 
     */
    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time == null ? null : trans_time.trim();
    }

    /**
     * 
     * @return oppo_card 
     */
    public String getOppo_card() {
        return oppo_card;
    }

    /**
     * 
     * @param oppo_card 
     */
    public void setOppo_card(String oppo_card) {
        this.oppo_card = oppo_card == null ? null : oppo_card.trim();
    }

    /**
     * 
     * @return oppo_name 
     */
    public String getOppo_name() {
        return oppo_name;
    }

    /**
     * 
     * @param oppo_name 
     */
    public void setOppo_name(String oppo_name) {
        this.oppo_name = oppo_name == null ? null : oppo_name.trim();
    }

    /**
     * 
     * @return finish_datetime 
     */
    public Date getFinish_datetime() {
        return finish_datetime;
    }

    /**
     * 
     * @param finish_datetime 
     */
    public void setFinish_datetime(Date finish_datetime) {
        this.finish_datetime = finish_datetime;
    }

    /**
     * 
     * @return result 
     */
    public String getResult() {
        return result;
    }

    /**
     * 
     * @param result 
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * 
     * @return summary_wn 
     */
    public String getSummary_wn() {
        return summary_wn;
    }

    /**
     * 
     * @param summary_wn 
     */
    public void setSummary_wn(String summary_wn) {
        this.summary_wn = summary_wn == null ? null : summary_wn.trim();
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
     * @return attr1 
     */
    public BigDecimal getAttr1() {
        return attr1;
    }

    /**
     * 
     * @param attr1 
     */
    public void setAttr1(BigDecimal attr1) {
        this.attr1 = attr1;
    }

    /**
     * 
     * @return attr2 
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * 
     * @param attr2 
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    /**
     * 
     * @return attr3 
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * 
     * @param attr3 
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3 == null ? null : attr3.trim();
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
        SunbobTaskUnionpay other = (SunbobTaskUnionpay) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getThird_no() == null ? other.getThird_no() == null : this.getThird_no().equals(other.getThird_no()))
            && (this.getThird_batch_no() == null ? other.getThird_batch_no() == null : this.getThird_batch_no().equals(other.getThird_batch_no()))
            && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getTrans_type() == null ? other.getTrans_type() == null : this.getTrans_type().equals(other.getTrans_type()))
            && (this.getTrans_name() == null ? other.getTrans_name() == null : this.getTrans_name().equals(other.getTrans_name()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getOppo_card() == null ? other.getOppo_card() == null : this.getOppo_card().equals(other.getOppo_card()))
            && (this.getOppo_name() == null ? other.getOppo_name() == null : this.getOppo_name().equals(other.getOppo_name()))
            && (this.getFinish_datetime() == null ? other.getFinish_datetime() == null : this.getFinish_datetime().equals(other.getFinish_datetime()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getSummary_wn() == null ? other.getSummary_wn() == null : this.getSummary_wn().equals(other.getSummary_wn()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getAttr1() == null ? other.getAttr1() == null : this.getAttr1().equals(other.getAttr1()))
            && (this.getAttr2() == null ? other.getAttr2() == null : this.getAttr2().equals(other.getAttr2()))
            && (this.getAttr3() == null ? other.getAttr3() == null : this.getAttr3().equals(other.getAttr3()));
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
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getThird_no() == null) ? 0 : getThird_no().hashCode());
        result = prime * result + ((getThird_batch_no() == null) ? 0 : getThird_batch_no().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getTrans_type() == null) ? 0 : getTrans_type().hashCode());
        result = prime * result + ((getTrans_name() == null) ? 0 : getTrans_name().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getOppo_card() == null) ? 0 : getOppo_card().hashCode());
        result = prime * result + ((getOppo_name() == null) ? 0 : getOppo_name().hashCode());
        result = prime * result + ((getFinish_datetime() == null) ? 0 : getFinish_datetime().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getSummary_wn() == null) ? 0 : getSummary_wn().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getAttr1() == null) ? 0 : getAttr1().hashCode());
        result = prime * result + ((getAttr2() == null) ? 0 : getAttr2().hashCode());
        result = prime * result + ((getAttr3() == null) ? 0 : getAttr3().hashCode());
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
        sb.append(", card_no=").append(card_no);
        sb.append(", third_no=").append(third_no);
        sb.append(", third_batch_no=").append(third_batch_no);
        sb.append(", amt=").append(amt);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", trans_type=").append(trans_type);
        sb.append(", trans_name=").append(trans_name);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", oppo_card=").append(oppo_card);
        sb.append(", oppo_name=").append(oppo_name);
        sb.append(", finish_datetime=").append(finish_datetime);
        sb.append(", result=").append(result);
        sb.append(", summary_wn=").append(summary_wn);
        sb.append(", status=").append(status);
        sb.append(", attr1=").append(attr1);
        sb.append(", attr2=").append(attr2);
        sb.append(", attr3=").append(attr3);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}