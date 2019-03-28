package com.sunyard.sunfintech.prod.model.bo;

import java.math.BigDecimal;

/**
 * Created by terry on 2018/9/20.
 */
public class ProdRefundWaitPay {

    private String trans_serial;
    private String mall_no;
    private String plat_no;
    private Integer total_num;
    private BigDecimal total_balance;
    private String third_no;

    public String getTrans_serial() {
        return trans_serial;
    }

    public void setTrans_serial(String trans_serial) {
        this.trans_serial = trans_serial;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
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

    public String getThird_no() {
        return third_no;
    }

    public void setThird_no(String third_no) {
        this.third_no = third_no;
    }

    @Override
    public String toString() {
        return "ProdRefundWaitPay{" +
                "trans_serial='" + trans_serial + '\'' +
                ", mall_no='" + mall_no + '\'' +
                ", plat_no='" + plat_no + '\'' +
                ", total_num=" + total_num +
                ", total_balance=" + total_balance +
                ", third_no='" + third_no + '\'' +
                '}';
    }
}
