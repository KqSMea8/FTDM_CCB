package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class QuartzUnionpay extends QuartzUnionpayKey implements Serializable {
    /**
     * 自动提现模式：1-银联模式  2-行内模式
     */
    private String quartz_tyep;

    /**
     * 提现任务受理开关(0-禁用，1-启用)
     */
    private String receive_status;

    /**
     * 提现指令发送开关(0-禁用，1-启用)
     */
    private String send_status;

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
     * quartz_unionpay
     */
    private static final long serialVersionUID = 1L;

    /**
     * 自动提现模式：1-银联模式  2-行内模式
     * @return quartz_tyep 自动提现模式：1-银联模式  2-行内模式
     */
    public String getQuartz_tyep() {
        return quartz_tyep;
    }

    /**
     * 自动提现模式：1-银联模式  2-行内模式
     * @param quartz_tyep 自动提现模式：1-银联模式  2-行内模式
     */
    public void setQuartz_tyep(String quartz_tyep) {
        this.quartz_tyep = quartz_tyep == null ? null : quartz_tyep.trim();
    }

    /**
     * 提现任务受理开关(0-禁用，1-启用)
     * @return receive_status 提现任务受理开关(0-禁用，1-启用)
     */
    public String getReceive_status() {
        return receive_status;
    }

    /**
     * 提现任务受理开关(0-禁用，1-启用)
     * @param receive_status 提现任务受理开关(0-禁用，1-启用)
     */
    public void setReceive_status(String receive_status) {
        this.receive_status = receive_status == null ? null : receive_status.trim();
    }

    /**
     * 提现指令发送开关(0-禁用，1-启用)
     * @return send_status 提现指令发送开关(0-禁用，1-启用)
     */
    public String getSend_status() {
        return send_status;
    }

    /**
     * 提现指令发送开关(0-禁用，1-启用)
     * @param send_status 提现指令发送开关(0-禁用，1-启用)
     */
    public void setSend_status(String send_status) {
        this.send_status = send_status == null ? null : send_status.trim();
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
     * @mbggenerated 2017-07-07
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
        QuartzUnionpay other = (QuartzUnionpay) that;
        return (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()))
            && (this.getQuartz_tyep() == null ? other.getQuartz_tyep() == null : this.getQuartz_tyep().equals(other.getQuartz_tyep()))
            && (this.getReceive_status() == null ? other.getReceive_status() == null : this.getReceive_status().equals(other.getReceive_status()))
            && (this.getSend_status() == null ? other.getSend_status() == null : this.getSend_status().equals(other.getSend_status()))
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
     * @mbggenerated 2017-07-07
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getPay_code() == null) ? 0 : getPay_code().hashCode());
        result = prime * result + ((getQuartz_tyep() == null) ? 0 : getQuartz_tyep().hashCode());
        result = prime * result + ((getReceive_status() == null) ? 0 : getReceive_status().hashCode());
        result = prime * result + ((getSend_status() == null) ? 0 : getSend_status().hashCode());
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
     * @mbggenerated 2017-07-07
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", quartz_tyep=").append(quartz_tyep);
        sb.append(", receive_status=").append(receive_status);
        sb.append(", send_status=").append(send_status);
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