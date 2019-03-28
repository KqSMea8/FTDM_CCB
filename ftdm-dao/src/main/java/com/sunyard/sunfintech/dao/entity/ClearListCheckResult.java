package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ClearListCheckResult implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String order_no;

    /**
     * 
     */
    private String ser_no;

    /**
     * 
     */
    private String trans_code;

    /**
     * 
     */
    private Date check_time;

    /**
     * 
     */
    private String check_content_amt;

    /**
     * 
     */
    private String check_content_req;

    /**
     * 
     */
    private String chenk_result;

    /**
     * 
     */
    private String suggestion;

    /**
     * 
     */
    private String recheck;

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
    private String type;

    /**
     * clear_list_check_result
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
     * @return ser_no 
     */
    public String getSer_no() {
        return ser_no;
    }

    /**
     * 
     * @param ser_no 
     */
    public void setSer_no(String ser_no) {
        this.ser_no = ser_no == null ? null : ser_no.trim();
    }

    /**
     * 
     * @return trans_code 
     */
    public String getTrans_code() {
        return trans_code;
    }

    /**
     * 
     * @param trans_code 
     */
    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code == null ? null : trans_code.trim();
    }

    /**
     * 
     * @return check_time 
     */
    public Date getCheck_time() {
        return check_time;
    }

    /**
     * 
     * @param check_time 
     */
    public void setCheck_time(Date check_time) {
        this.check_time = check_time;
    }

    /**
     * 
     * @return check_content_amt 
     */
    public String getCheck_content_amt() {
        return check_content_amt;
    }

    /**
     * 
     * @param check_content_amt 
     */
    public void setCheck_content_amt(String check_content_amt) {
        this.check_content_amt = check_content_amt == null ? null : check_content_amt.trim();
    }

    /**
     * 
     * @return check_content_req 
     */
    public String getCheck_content_req() {
        return check_content_req;
    }

    /**
     * 
     * @param check_content_req 
     */
    public void setCheck_content_req(String check_content_req) {
        this.check_content_req = check_content_req == null ? null : check_content_req.trim();
    }

    /**
     * 
     * @return chenk_result 
     */
    public String getChenk_result() {
        return chenk_result;
    }

    /**
     * 
     * @param chenk_result 
     */
    public void setChenk_result(String chenk_result) {
        this.chenk_result = chenk_result == null ? null : chenk_result.trim();
    }

    /**
     * 
     * @return suggestion 
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * 
     * @param suggestion 
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion == null ? null : suggestion.trim();
    }

    /**
     * 
     * @return recheck 
     */
    public String getRecheck() {
        return recheck;
    }

    /**
     * 
     * @param recheck 
     */
    public void setRecheck(String recheck) {
        this.recheck = recheck == null ? null : recheck.trim();
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
     * @return type 
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     *
     * @mbggenerated 2018-06-21
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
        ClearListCheckResult other = (ClearListCheckResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getSer_no() == null ? other.getSer_no() == null : this.getSer_no().equals(other.getSer_no()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()))
            && (this.getCheck_time() == null ? other.getCheck_time() == null : this.getCheck_time().equals(other.getCheck_time()))
            && (this.getCheck_content_amt() == null ? other.getCheck_content_amt() == null : this.getCheck_content_amt().equals(other.getCheck_content_amt()))
            && (this.getCheck_content_req() == null ? other.getCheck_content_req() == null : this.getCheck_content_req().equals(other.getCheck_content_req()))
            && (this.getChenk_result() == null ? other.getChenk_result() == null : this.getChenk_result().equals(other.getChenk_result()))
            && (this.getSuggestion() == null ? other.getSuggestion() == null : this.getSuggestion().equals(other.getSuggestion()))
            && (this.getRecheck() == null ? other.getRecheck() == null : this.getRecheck().equals(other.getRecheck()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getSer_no() == null) ? 0 : getSer_no().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        result = prime * result + ((getCheck_time() == null) ? 0 : getCheck_time().hashCode());
        result = prime * result + ((getCheck_content_amt() == null) ? 0 : getCheck_content_amt().hashCode());
        result = prime * result + ((getCheck_content_req() == null) ? 0 : getCheck_content_req().hashCode());
        result = prime * result + ((getChenk_result() == null) ? 0 : getChenk_result().hashCode());
        result = prime * result + ((getSuggestion() == null) ? 0 : getSuggestion().hashCode());
        result = prime * result + ((getRecheck() == null) ? 0 : getRecheck().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", order_no=").append(order_no);
        sb.append(", ser_no=").append(ser_no);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", check_time=").append(check_time);
        sb.append(", check_content_amt=").append(check_content_amt);
        sb.append(", check_content_req=").append(check_content_req);
        sb.append(", chenk_result=").append(chenk_result);
        sb.append(", suggestion=").append(suggestion);
        sb.append(", recheck=").append(recheck);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}