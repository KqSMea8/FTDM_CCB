package com.sunyard.sunfintech.core.base;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 基础请求参数
 * Created by heroy on 2017/4/6.
 */
public class BaseRequest extends BaseSerialNoRequest{
    @NotBlank
    private String sign;
    @NotBlank
    private String order_no;
    @NotBlank
    private String partner_trans_date;
    @NotBlank
    private String partner_trans_time;
    @NotBlank
    private String version;
    @NotBlank
    private String mall_no;

    private String mall_name;
    @NotBlank
    private String mer_no;

    private String mer_name;

    private String remark;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        super.setBase_serial_order_no(order_no);
        this.order_no = order_no;
    }

    @Override
    public BaseRequest clone() {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setMer_no(this.mer_no);
        baseRequest.setMer_name(this.mer_name);
        baseRequest.setMall_no(this.mall_no);
        baseRequest.setMall_name(this.mall_name);
        baseRequest.setOrder_no(this.order_no);
        baseRequest.setSign(this.sign);
        baseRequest.setPartner_trans_date(this.partner_trans_date);
        baseRequest.setPartner_trans_time(this.partner_trans_time);
        baseRequest.setRemark(this.remark);
        baseRequest.setVersion(this.version);
        baseRequest.setBase_serial_ori_order_no(this.getBase_serial_ori_order_no());
        baseRequest.setBase_serial_notify_url(this.getBase_serial_notify_url());
        return baseRequest;
    }

    public String getPartner_trans_date() {
		return partner_trans_date;
	}

	public void setPartner_trans_date(String partner_trans_date) {
		this.partner_trans_date = partner_trans_date;
	}

	public String getPartner_trans_time() {
        return partner_trans_time;
    }

    public void setPartner_trans_time(String partner_trans_time) {
        this.partner_trans_time = partner_trans_time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public String getMall_name() {
        return mall_name;
    }

    public void setMall_name(String mall_name) {
        this.mall_name = mall_name;
    }

    public String getMer_no() {
        return mer_no;
    }

    public void setMer_no(String mer_no) {
        this.mer_no = mer_no;
    }

    public String getMer_name() {
        return mer_name;
    }

    public void setMer_name(String mer_name) {
        this.mer_name = mer_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
