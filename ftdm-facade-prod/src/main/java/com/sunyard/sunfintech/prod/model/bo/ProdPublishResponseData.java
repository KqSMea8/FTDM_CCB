package com.sunyard.sunfintech.prod.model.bo;

import java.io.Serializable;

/**
 * Created by terry on 2017/5/5.
 */
public class ProdPublishResponseData implements Serializable{
    private String prod_id;

    private String prod_account;

    public String getProd_account() {
        return prod_account;
    }

    public void setProd_account(String prod_account) {
        this.prod_account = prod_account;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }
}
