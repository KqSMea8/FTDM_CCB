package com.sunyard.sunfintech.core.base;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 基础请求参数
 * Created by heroy on 2017/4/6.
 */
@Data
public class BaseSerialNoRequest extends BaseModel{

    private String base_serial_order_no;
    private String link_trans_serial;

    /**
     * 必须
     */
    private String base_serial_ori_order_no;




    /**

     * 必须
     */

    private String base_serial_plutcust;
    private String base_serial_notify_url;

    public String getBase_serial_order_no() {
        return base_serial_order_no;
    }

    public void setBase_serial_order_no(String base_serial_order_no) {
        this.base_serial_order_no = base_serial_order_no;
    }

    public String getLink_trans_serial() {
        return link_trans_serial;
    }

    public void setLink_trans_serial(String link_trans_serial) {
        this.link_trans_serial = link_trans_serial;
    }

    public String getBase_serial_ori_order_no() {
        return base_serial_ori_order_no;
    }

    public void setBase_serial_ori_order_no(String base_serial_ori_order_no) {
        this.base_serial_ori_order_no = base_serial_ori_order_no;
    }

    public String getBase_serial_plutcust() {
        return base_serial_plutcust;
    }

    public void setBase_serial_plutcust(String base_serial_plutcust) {
        this.base_serial_plutcust = base_serial_plutcust;
    }

    public String getBase_serial_notify_url() {
        return base_serial_notify_url;
    }

    public void setBase_serial_notify_url(String base_serial_notify_url) {
        this.base_serial_notify_url = base_serial_notify_url;
    }
}
