package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ClearHoliday implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 节假日期
     */
    private String date;

    /**
     * 1-放假2-上班第一天
     */
    private String type;

    /**
     * 放假天数
     */
    private Integer date_count;

    /**
     * 备注
     */
    private String remark;

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
     * clear_holiday
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
     * 节假日期
     * @return date 节假日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 节假日期
     * @param date 节假日期
     */
    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    /**
     * 1-放假2-上班第一天
     * @return type 1-放假2-上班第一天
     */
    public String getType() {
        return type;
    }

    /**
     * 1-放假2-上班第一天
     * @param type 1-放假2-上班第一天
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 放假天数
     * @return date_count 放假天数
     */
    public Integer getDate_count() {
        return date_count;
    }

    /**
     * 放假天数
     * @param date_count 放假天数
     */
    public void setDate_count(Integer date_count) {
        this.date_count = date_count;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
     * @mbggenerated 2018-06-29
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
        ClearHoliday other = (ClearHoliday) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getDate_count() == null ? other.getDate_count() == null : this.getDate_count().equals(other.getDate_count()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getExt1() == null ? other.getExt1() == null : this.getExt1().equals(other.getExt1()))
            && (this.getExt2() == null ? other.getExt2() == null : this.getExt2().equals(other.getExt2()))
            && (this.getExt3() == null ? other.getExt3() == null : this.getExt3().equals(other.getExt3()));
    }

    /**
     *
     * @mbggenerated 2018-06-29
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDate_count() == null) ? 0 : getDate_count().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getExt1() == null) ? 0 : getExt1().hashCode());
        result = prime * result + ((getExt2() == null) ? 0 : getExt2().hashCode());
        result = prime * result + ((getExt3() == null) ? 0 : getExt3().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-06-29
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", type=").append(type);
        sb.append(", date_count=").append(date_count);
        sb.append(", remark=").append(remark);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", ext3=").append(ext3);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}