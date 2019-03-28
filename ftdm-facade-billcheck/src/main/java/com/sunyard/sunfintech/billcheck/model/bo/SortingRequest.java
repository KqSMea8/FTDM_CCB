package com.sunyard.sunfintech.billcheck.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by Lid on 2017/6/22.
 */
public class SortingRequest implements Serializable {
    /*
     *集团商户号
     */
    @NotBlank
    private String mall_no;

    /*
     *平台编号
     */
//    @NotBlank
//    private String mer_no;

    /*
     *账期（YYYYMMDD）
     */
    @NotBlank
    private String clear_date;

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

//    public String getMer_no() {
//        return mer_no;
//    }
//
//    public void setMer_no(String mer_no) {
//        this.mer_no = mer_no;
//    }

    public String getClear_date() {
        return clear_date;
    }

    public void setClear_date(String clear_date) {
        this.clear_date = clear_date;
    }
}
