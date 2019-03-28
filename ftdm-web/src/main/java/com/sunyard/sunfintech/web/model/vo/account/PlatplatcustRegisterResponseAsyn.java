package com.sunyard.sunfintech.web.model.vo.account;

import com.sunyard.sunfintech.core.base.BaseResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

//import com.sunyard.sunfintech.user.model.bo.PlatplatcustRegisterResponseData;

/**
 * Created by dany on 2017/6/1.
 */
public class PlatplatcustRegisterResponseAsyn extends BaseResponse{
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

    @Override
    public String toString() {
        return "PlatplatcustRegisterResponseAsyn{" +
                "platcust='" + platcust + '\'' +
                ", mall_no='" + mall_no + '\'' +
                '}';
    }
}
