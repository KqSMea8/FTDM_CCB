package com.sunyard.sunfintech.billcheck.model.bo;

import com.sunyard.sunfintech.core.base.BaseMessage;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Lid on 2017/6/23.
 */
public class SortingResponse extends BaseMessage{
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

    private String type;

    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
