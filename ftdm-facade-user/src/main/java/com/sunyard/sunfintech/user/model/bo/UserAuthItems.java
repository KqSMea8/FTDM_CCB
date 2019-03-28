package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;

/**
 * @author heroy
 * @version 2018/1/9
 */
public class UserAuthItems implements Serializable {
    /**
     * 集团编号
     */
    private String mall_no;
    /**
     * 商户编号
     */
    private String mer_no;
    /**
     * 商户客户号
     */
    private String platcust;
    /**
     * 客户姓名
     */
    private String client_name;
    /**
     * 证件类型
     0	身份证
     1	外国护照
     2	营业执照
     3	军官证
     4	社会保障号

     */
    private String id_kind;
    /**
     * 证件号码
     */
    private String id_no;
    /**
     * 交易类型(0:设置密码 1:重置)
     */
    private String trans_flag;
    /**
     * 原密码随机数键【非必填】
     */
    private String ori_random_key;
    /**
     * 原密码随机数键【非必填】
     */
    private String ori_password;
    /**
     * 随机数键
     */
    private String random_key;
    /**
     * 随机数键
     */
    private String random_value;
    /**
     * 交易密码
     */
    private String passwod;

    /**
     * 密码类型(0-PC版，1-SDK版，2-H5版)
     */
    private String password_type;

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public void setMer_no(String mer_no) {
        this.mer_no = mer_no;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public void setId_kind(String id_kind) {
        this.id_kind = id_kind;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public void setTrans_flag(String trans_flag) {
        this.trans_flag = trans_flag;
    }

    public void setOri_random_key(String ori_random_key) {
        this.ori_random_key = ori_random_key;
    }

    public void setOri_password(String ori_password) {
        this.ori_password = ori_password;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    public void setRandom_value(String random_value) {
        this.random_value = random_value;
    }

    public void setPasswod(String passwod) {
        this.passwod = passwod;
    }

    public String getPassword_type() {
        return password_type;
    }

    public void setPassword_type(String password_type) {
        this.password_type = password_type;
    }

    public String getMall_no() {
        return mall_no;
    }

    public String getMer_no() {
        return mer_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getId_kind() {
        return id_kind;
    }

    public String getId_no() {
        return id_no;
    }

    public String getTrans_flag() {
        return trans_flag;
    }

    public String getOri_random_key() {
        return ori_random_key;
    }

    public String getOri_password() {
        return ori_password;
    }

    public String getRandom_key() {
        return random_key;
    }

    public String getRandom_value() {
        return random_value;
    }

    public String getPasswod() {
        return passwod;
    }



    @Override
    public String toString() {
        return "{" +
                "mall_no:'" + mall_no + '\'' +
                ", mer_no:'" + mer_no + '\'' +
                ", platcust:'" + platcust + '\'' +
                ", client_name:'" + client_name + '\'' +
                ", id_kind:'" + id_kind + '\'' +
                ", id_no:'" + id_no + '\'' +
                ", trans_flag:'" + trans_flag + '\'' +
                ", ori_random_key:'" + ori_random_key + '\'' +
                ", ori_password:'" + ori_password + '\'' +
                ", random_key:'" + random_key + '\'' +
                ", passwod:'" + passwod + '\'' +
                ", password_type:'" + password_type + '\'' +
                '}';
    }
}
