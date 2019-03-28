package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;
import com.sunyard.sunfintech.core.base.BaseSerialNoRequest;
import com.sunyard.sunfintech.prod.model.bo.Amtlist;

import java.io.Serializable;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestSuccessData extends BaseSerialNoRequest{

    /**
     * 明细编号
     */
    private String detail_no;
    /**
     * 平台客户号
     */
    private String platcust;
    /**
     * 产品ID
     */
    private String prod_id;
    /**
     * 交易金额
     */
    private String trans_amt;

    private Amtlist amtlist;

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        super.setBase_serial_order_no(detail_no);
        this.detail_no = detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getTrans_amt() {
        return trans_amt;
    }

    public void setTrans_amt(String trans_amt) {
        this.trans_amt = trans_amt;
    }

    public Amtlist getAmtlist() {
        return amtlist;
    }

    public void setAmtlist(Amtlist amtlist) {
        this.amtlist = amtlist;
    }

    @Override
    public String toString() {
        return "ProdInvestSuccessData{" +
                "detail_no='" + detail_no + '\'' +
                ", platcust='" + platcust + '\'' +
                ", prod_id='" + prod_id + '\'' +
                ", trans_amt='" + trans_amt + '\'' +
                ", amtlist=" + amtlist +
                '}';
    }
}
