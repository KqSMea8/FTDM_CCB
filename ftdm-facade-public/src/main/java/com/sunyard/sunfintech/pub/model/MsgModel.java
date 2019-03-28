package com.sunyard.sunfintech.pub.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by terry on 2017/6/19.
 */
public class MsgModel implements Serializable {

    /**
     * 订单号
     */
    private String order_no;

    /**
     * 平台号
     */
    private String plat_no;

    /**
     * 交易编号
     */
    private String trans_code;

    /**
     * 短信内容
     */
    private String msgContent;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 模板类型
     */
    private String msg_type;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 验证码
     */
    private String verify_info;

    /**
     * 流水号
     */
    private String trans_serial;

    /**
     * 平台客户号
     */
    private String platcust;

    /**
     * 集团号
     */
    private String mall_no;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getVerify_info() {
        return verify_info;
    }

    public void setVerify_info(String verify_info) {
        this.verify_info = verify_info;
    }

    public String getTrans_serial() {
        return trans_serial;
    }

    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    @Override
    public String toString() {
        return "MsgModel{" +
                "order_no='" + order_no + '\'' +
                ", plat_no='" + plat_no + '\'' +
                ", trans_code='" + trans_code + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", mobile='" + mobile + '\'' +
                ", msg_type='" + msg_type + '\'' +
                ", amount=" + amount +
                ", verify_info='" + verify_info + '\'' +
                ", trans_serial='" + trans_serial + '\'' +
                ", platcust='" + platcust + '\'' +
                ", mall_no='" + mall_no + '\'' +
                '}';
    }
}
