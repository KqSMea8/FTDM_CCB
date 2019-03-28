package com.sunyard.sunfintech.thirdparty.model;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * Created by my on 2018/5/21.
 */
public class ContractConfirmRequest extends BaseRequest {

    private String origin_order_no;
    private String  identifying_code;

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public String getIdentifying_code() {
        return identifying_code;
    }

    public void setIdentifying_code(String identifying_code) {
        this.identifying_code = identifying_code;
    }
}
