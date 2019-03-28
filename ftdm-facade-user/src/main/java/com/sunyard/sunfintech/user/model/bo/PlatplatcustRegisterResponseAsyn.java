package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

import java.math.BigDecimal;

//import com.sunyard.sunfintech.user.model.bo.PlatplatcustRegisterResponseData;

/**
 * Created by dany on 2017/6/1.
 */
public class PlatplatcustRegisterResponseAsyn extends BaseResponse{
    private BigDecimal authed_amount;//	M	N(19,2)	授权金额

    //@NotBlank
    private String authed_limittime;//	M	C(8)	授权期限 YYYYMMDD
    //返回数据
    private String platcust;

    private String mall_no;

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

    public BigDecimal getAuthed_amount() {
        return authed_amount;
    }

    public void setAuthed_amount(BigDecimal authed_amount) {
        this.authed_amount = authed_amount;
    }

    public String getAuthed_limittime() {
        return authed_limittime;
    }

    public void setAuthed_limittime(String authed_limittime) {
        this.authed_limittime = authed_limittime;
    }

    @Override
    public String toString() {
        return "PlatplatcustRegisterResponseAsyn{" +
                "platcust='" + platcust + '\'' +
                ", mall_no='" + mall_no + '\'' +
                '}';
    }
}
