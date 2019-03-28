package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;

/**
 * 代收返回data
 * Created by bob
 */
public class CollectionDataBaseResponse extends BaseModel {
    //订单状态0:已接受, 1:处理中,2:处理成功,3:处理失败
    private String order_status;
    //系统处理日期(yyyyMMddHHmmss))
    private String process_date;
    //平台流水号
    private String query_id;

    private String error_no;

    private String error_info;

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

    public String getError_no() {
        return error_no;
    }

    public void setError_no(String error_no) {
        this.error_no = error_no;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }
}
