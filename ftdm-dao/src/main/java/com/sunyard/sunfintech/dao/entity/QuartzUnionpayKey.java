package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class QuartzUnionpayKey implements Serializable {
    /**
     * 平台号
     */
    private String plat_no;

    /**
     * 支付编号
     */
    private String pay_code;

    /**
     * quartz_unionpay
     */
    private static final long serialVersionUID = 1L;

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
     * 支付编号
     * @return pay_code 支付编号
     */
    public String getPay_code() {
        return pay_code;
    }

    /**
     * 支付编号
     * @param pay_code 支付编号
     */
    public void setPay_code(String pay_code) {
        this.pay_code = pay_code == null ? null : pay_code.trim();
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
        QuartzUnionpayKey other = (QuartzUnionpayKey) that;
        return (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getPay_code() == null ? other.getPay_code() == null : this.getPay_code().equals(other.getPay_code()));
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
        sb.append(", plat_no=").append(plat_no);
        sb.append(", pay_code=").append(pay_code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}