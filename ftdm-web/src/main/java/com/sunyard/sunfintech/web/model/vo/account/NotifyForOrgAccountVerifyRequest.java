package com.sunyard.sunfintech.web.model.vo.account;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by terry on 2017/6/21.
 */
public class NotifyForOrgAccountVerifyRequest {

    /**
     * 订单号
     */
    @NotBlank
    private String order_no;

    /**
     * 绑卡状态
     */
    @NotBlank
    private String card_status;

    /**
     * 通知地址
     */
    @NotBlank
    private String notify_url;

    /**
     * 审核原因
     */
    @NotBlank
    private String cause;

    @NotBlank
    private String mall_no;

    private String plat_no;

    private String platcust;

    private String trans_date;

    private String trans_time;

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCard_status() {
        return card_status;
    }

    public void setCard_status(String card_status) {
        this.card_status = card_status;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    @Override
    public String toString() {
        return "NotifyForOrgAccountVerifyRequest{" +
                "order_no='" + order_no + '\'' +
                ", card_status='" + card_status + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", cause='" + cause + '\'' +
                ", mall_no='" + mall_no + '\'' +
                '}';
    }
}

