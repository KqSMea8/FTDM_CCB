package com.sunyard.sunfintech.account.model.bo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by terry on 2017/7/19.
 */
public class BatchPayQueryResponseData implements Serializable {

    private String partner_serial_no;
    private String original_serial_no;
    private Integer total_num;
    private BigDecimal total_balance;
    private String cert_sign;
    private List<BatchPayQueryResponseDataDetail> detail;

    public String getPartner_serial_no() {
        return partner_serial_no;
    }

    public void setPartner_serial_no(String partner_serial_no) {
        this.partner_serial_no = partner_serial_no;
    }

    public String getOriginal_serial_no() {
        return original_serial_no;
    }

    public void setOriginal_serial_no(String original_serial_no) {
        this.original_serial_no = original_serial_no;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public BigDecimal getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(BigDecimal total_balance) {
        this.total_balance = total_balance;
    }

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    public List<BatchPayQueryResponseDataDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<BatchPayQueryResponseDataDetail> detail) {
        this.detail = detail;
    }
}
