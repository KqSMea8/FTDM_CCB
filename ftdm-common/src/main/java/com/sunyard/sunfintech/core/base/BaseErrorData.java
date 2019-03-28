package com.sunyard.sunfintech.core.base;

import java.io.Serializable;

/**
 * 批量处理的错误返回
 * Created by terry on 2017/5/3.
 */
public class BaseErrorData  extends BaseSerialNoRequest {

    /**
     * 明细编号
     */
    private String detail_no;

    /**
     * 错误码
     */
    private String error_no;

    /**
     * 错误信息
     */
    private String error_info;

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        super.setBase_serial_order_no(detail_no);
        this.detail_no = detail_no;
    }

    public String getError_no() {
        return error_no;
    }

    public void setError_no(String error_no) {
        this.error_no = error_no;
    }

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

    @Override
    public String toString() {
        return "BaseErrorData{" +
                "detail_no='" + detail_no + '\'' +
                ", error_no='" + error_no + '\'' +
                ", error_info='" + error_info + '\'' +
                '}';
    }
}
