package com.sunyard.sunfintech.web.model.vo.product;

import com.sunyard.sunfintech.core.base.BaseRequest;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdAbortInvestmentRequest extends BaseRequest {

    private String prod_id;

    private String trans_order_no;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getTrans_order_no() {
        return trans_order_no;
    }

    public void setTrans_order_no(String trans_order_no) {
        this.trans_order_no = trans_order_no;
    }
}
