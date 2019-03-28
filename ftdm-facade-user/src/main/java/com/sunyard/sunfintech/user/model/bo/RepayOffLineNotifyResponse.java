package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author bobguo
 *         2017年5月31日
 */
public class RepayOffLineNotifyResponse implements Serializable {
    /*
     * 平台编号
	 */
    private String plat_no;
    /*
     * 订单号
     */
    private String order_no;
    /**
     * 异步通知URL
     */
    @JSONField(serialize = false)
    private String notifyUrl;
    /**
     * 总金额
     */
    private String amt;
    /**
     * 平台客户入账列表
     */
    private String platcustList;
    /**
     * 平台客户入账列表对象
     */
    @JSONField(serialize = false)
    private List<PlatcustListDetail> platcustListDetail;
    /**
     * 1-入账成功  2-入账失败
     */
    private String code;
    /**
     * 签名数据
     */
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getPlatcustList() {
        return platcustList;
    }

    public void setPlatcustList(String platcustList) {
        this.platcustList = platcustList;
    }


    public List<PlatcustListDetail> getPlatcustListDetail() {
        return platcustListDetail;
    }

    public void setPlatcustListDetail(List<PlatcustListDetail> platcustListDetail) {
        this.platcustListDetail = platcustListDetail;
        setPlatcustList(JSON.toJSONString(platcustListDetail));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}