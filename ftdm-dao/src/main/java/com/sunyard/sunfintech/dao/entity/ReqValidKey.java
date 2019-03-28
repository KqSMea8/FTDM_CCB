package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ReqValidKey implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 认证类型（0-实名认证，1-银行卡四要素认证）
     */
    private String type;

    /**
     * 平台号
     */
    private String plat_no;

    /**
     * req_valid
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
     * 认证类型（0-实名认证，1-银行卡四要素认证）
     * @return type 认证类型（0-实名认证，1-银行卡四要素认证）
     */
    public String getType() {
        return type;
    }

    /**
     * 认证类型（0-实名认证，1-银行卡四要素认证）
     * @param type 认证类型（0-实名认证，1-银行卡四要素认证）
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 平台号
     * @return plat_no 平台号
     */
    public String getPlat_no() {
        return plat_no;
    }

    /**
     * 平台号
     * @param plat_no 平台号
     */
    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no == null ? null : plat_no.trim();
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
        ReqValidKey other = (ReqValidKey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()));
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
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
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
        sb.append(", type=").append(type);
        sb.append(", plat_no=").append(plat_no);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}