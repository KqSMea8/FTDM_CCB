package com.sunyard.sunfintech.dao.entity;

import java.io.Serializable;

public class ComParam implements Serializable {
    /**
     * 平台编号
     */
    private String plat_no;

    /**
     * 参数类型
     */
    private String param_type;

    /**
     * 
     */
    private String prod_type;

    /**
     * 
     */
    private String prod_id;

    /**
     * 参数编号
     */
    private String param_code;

    /**
     * 参数值
     */
    private String param_value;

    /**
     * 参数名称
     */
    private String param_name;

    /**
     * 参数描述
     */
    private String param_desc;

    /**
     * com_param
     */
    private static final long serialVersionUID = 1L;

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
     * 参数类型
     * @return param_type 参数类型
     */
    public String getParam_type() {
        return param_type;
    }

    /**
     * 参数类型
     * @param param_type 参数类型
     */
    public void setParam_type(String param_type) {
        this.param_type = param_type == null ? null : param_type.trim();
    }

    /**
     * 
     * @return prod_type 
     */
    public String getProd_type() {
        return prod_type;
    }

    /**
     * 
     * @param prod_type 
     */
    public void setProd_type(String prod_type) {
        this.prod_type = prod_type == null ? null : prod_type.trim();
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
     * 参数编号
     * @return param_code 参数编号
     */
    public String getParam_code() {
        return param_code;
    }

    /**
     * 参数编号
     * @param param_code 参数编号
     */
    public void setParam_code(String param_code) {
        this.param_code = param_code == null ? null : param_code.trim();
    }

    /**
     * 参数值
     * @return param_value 参数值
     */
    public String getParam_value() {
        return param_value;
    }

    /**
     * 参数值
     * @param param_value 参数值
     */
    public void setParam_value(String param_value) {
        this.param_value = param_value == null ? null : param_value.trim();
    }

    /**
     * 参数名称
     * @return param_name 参数名称
     */
    public String getParam_name() {
        return param_name;
    }

    /**
     * 参数名称
     * @param param_name 参数名称
     */
    public void setParam_name(String param_name) {
        this.param_name = param_name == null ? null : param_name.trim();
    }

    /**
     * 参数描述
     * @return param_desc 参数描述
     */
    public String getParam_desc() {
        return param_desc;
    }

    /**
     * 参数描述
     * @param param_desc 参数描述
     */
    public void setParam_desc(String param_desc) {
        this.param_desc = param_desc == null ? null : param_desc.trim();
    }

    /**
     *
     * @mbggenerated 2017-10-27
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
        ComParam other = (ComParam) that;
        return (this.getPlat_no() == null ? other.getPlat_no() == null : this.getPlat_no().equals(other.getPlat_no()))
            && (this.getParam_type() == null ? other.getParam_type() == null : this.getParam_type().equals(other.getParam_type()))
            && (this.getProd_type() == null ? other.getProd_type() == null : this.getProd_type().equals(other.getProd_type()))
            && (this.getProd_id() == null ? other.getProd_id() == null : this.getProd_id().equals(other.getProd_id()))
            && (this.getParam_code() == null ? other.getParam_code() == null : this.getParam_code().equals(other.getParam_code()))
            && (this.getParam_value() == null ? other.getParam_value() == null : this.getParam_value().equals(other.getParam_value()))
            && (this.getParam_name() == null ? other.getParam_name() == null : this.getParam_name().equals(other.getParam_name()))
            && (this.getParam_desc() == null ? other.getParam_desc() == null : this.getParam_desc().equals(other.getParam_desc()));
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPlat_no() == null) ? 0 : getPlat_no().hashCode());
        result = prime * result + ((getParam_type() == null) ? 0 : getParam_type().hashCode());
        result = prime * result + ((getProd_type() == null) ? 0 : getProd_type().hashCode());
        result = prime * result + ((getProd_id() == null) ? 0 : getProd_id().hashCode());
        result = prime * result + ((getParam_code() == null) ? 0 : getParam_code().hashCode());
        result = prime * result + ((getParam_value() == null) ? 0 : getParam_value().hashCode());
        result = prime * result + ((getParam_name() == null) ? 0 : getParam_name().hashCode());
        result = prime * result + ((getParam_desc() == null) ? 0 : getParam_desc().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", plat_no=").append(plat_no);
        sb.append(", param_type=").append(param_type);
        sb.append(", prod_type=").append(prod_type);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", param_code=").append(param_code);
        sb.append(", param_value=").append(param_value);
        sb.append(", param_name=").append(param_name);
        sb.append(", param_desc=").append(param_desc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}