package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TransTransreq implements Serializable {
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
    private String trans_serial;

    /**
     * 
     */
    private String plat_no;

    /**
     * 
     */
    private String platcust;

    /**
     * 
     */
    private String trans_code;

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
    private String return_code;

    /**
     * 
     */
    private String return_msg;

    /**
     * 
     */
    private String status;

    /**
     * 批量订单号
     */
    private String batch_order_no;

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
     * 通知地址
     */
    private String notify_url;

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
     * 原订单号
     */
    private String origin_order_no;

    /**
     * 模块名称
     */
    private String service_name;

    /**
     * trans_transreq
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
     * @return return_code 
     */
    public String getReturn_code() {
        return return_code;
    }

    /**
     * 
     * @param return_code 
     */
    public void setReturn_code(String return_code) {
        this.return_code = return_code == null ? null : return_code.trim();
    }

    /**
     * 
     * @return return_msg 
     */
    public String getReturn_msg() {
        return return_msg;
    }

    /**
     * 
     * @param return_msg 
     */
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg == null ? null : return_msg.trim();
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
     * 批量订单号
     * @return batch_order_no 批量订单号
     */
    public String getBatch_order_no() {
        return batch_order_no;
    }

    /**
     * 批量订单号
     * @param batch_order_no 批量订单号
     */
    public void setBatch_order_no(String batch_order_no) {
        this.batch_order_no = batch_order_no == null ? null : batch_order_no.trim();
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
     * 通知地址
     * @return notify_url 通知地址
     */
    public String getNotify_url() {
        return notify_url;
    }

    /**
     * 通知地址
     * @param notify_url 通知地址
     */
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url == null ? null : notify_url.trim();
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
     * 原订单号
     * @return origin_order_no 原订单号
     */
    public String getOrigin_order_no() {
        return origin_order_no;
    }

    /**
     * 原订单号
     * @param origin_order_no 原订单号
     */
    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no == null ? null : origin_order_no.trim();
    }

    /**
     * 模块名称
     * @return service_name 模块名称
     */
    public String getService_name() {
        return service_name;
    }

    /**
     * 模块名称
     * @param service_name 模块名称
     */
    public void setService_name(String service_name) {
        this.service_name = service_name == null ? null : service_name.trim();
    }

    /**
     *
     * @mbggenerated 2018-01-23
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
        TransTransreq other = (TransTransreq) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrder_no() == null ? other.getOrder_no() == null : this.getOrder_no().equals(other.getOrder_no()))
            && (this.getTrans_serial() == null ? other.getTrans_serial() == null : this.getTrans_serial().equals(other.getTrans_serial()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPlatcust() == null ? other.getPlatcust() == null : this.getPlatcust().equals(other.getPlatcust()))
            && (this.getTrans_code() == null ? other.getTrans_code() == null : this.getTrans_code().equals(other.getTrans_code()))
            && (this.getTrans_name() == null ? other.getTrans_name() == null : this.getTrans_name().equals(other.getTrans_name()))
            && (this.getTrans_date() == null ? other.getTrans_date() == null : this.getTrans_date().equals(other.getTrans_date()))
            && (this.getTrans_time() == null ? other.getTrans_time() == null : this.getTrans_time().equals(other.getTrans_time()))
            && (this.getReturn_code() == null ? other.getReturn_code() == null : this.getReturn_code().equals(other.getReturn_code()))
            && (this.getReturn_msg() == null ? other.getReturn_msg() == null : this.getReturn_msg().equals(other.getReturn_msg()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBatch_order_no() == null ? other.getBatch_order_no() == null : this.getBatch_order_no().equals(other.getBatch_order_no()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getNotify_url() == null ? other.getNotify_url() == null : this.getNotify_url().equals(other.getNotify_url()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()))
            && (this.getExt4() == null ? other.getExt4() == null : this.getExt4().equals(other.getExt4()))
            && (this.getExt5() == null ? other.getExt5() == null : this.getExt5().equals(other.getExt5()))
            && (this.getOrigin_order_no() == null ? other.getOrigin_order_no() == null : this.getOrigin_order_no().equals(other.getOrigin_order_no()))
            && (this.getService_name() == null ? other.getService_name() == null : this.getService_name().equals(other.getService_name()));
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrder_no() == null) ? 0 : getOrder_no().hashCode());
        result = prime * result + ((getTrans_serial() == null) ? 0 : getTrans_serial().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPlatcust() == null) ? 0 : getPlatcust().hashCode());
        result = prime * result + ((getTrans_code() == null) ? 0 : getTrans_code().hashCode());
        result = prime * result + ((getTrans_name() == null) ? 0 : getTrans_name().hashCode());
        result = prime * result + ((getTrans_date() == null) ? 0 : getTrans_date().hashCode());
        result = prime * result + ((getTrans_time() == null) ? 0 : getTrans_time().hashCode());
        result = prime * result + ((getReturn_code() == null) ? 0 : getReturn_code().hashCode());
        result = prime * result + ((getReturn_msg() == null) ? 0 : getReturn_msg().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBatch_order_no() == null) ? 0 : getBatch_order_no().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getNotify_url() == null) ? 0 : getNotify_url().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        result = prime * result + ((getExt4() == null) ? 0 : getExt4().hashCode());
        result = prime * result + ((getExt5() == null) ? 0 : getExt5().hashCode());
        result = prime * result + ((getOrigin_order_no() == null) ? 0 : getOrigin_order_no().hashCode());
        result = prime * result + ((getService_name() == null) ? 0 : getService_name().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", order_no=").append(order_no);
        sb.append(", trans_serial=").append(trans_serial);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", platcust=").append(platcust);
        sb.append(", trans_code=").append(trans_code);
        sb.append(", trans_name=").append(trans_name);
        sb.append(", trans_date=").append(trans_date);
        sb.append(", trans_time=").append(trans_time);
        sb.append(", return_code=").append(return_code);
        sb.append(", return_msg=").append(return_msg);
        sb.append(", status=").append(status);
        sb.append(", batch_order_no=").append(batch_order_no);
        sb.append(", enabled=").append(enabled);
        sb.append(", remark=").append(remark);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", notify_url=").append(notify_url);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", ext3=").append(ext3);
        sb.append(", ext4=").append(ext4);
        sb.append(", ext5=").append(ext5);
        sb.append(", origin_order_no=").append(origin_order_no);
        sb.append(", service_name=").append(service_name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}