package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by PengZY on 2018/3/12.
 */
public class ProdTruncationResquest extends BaseRequest {

    @NotBlank
    private String prod_id;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    @Override
    public String toString() {
        return "TruncationResquest{" +
                "prod_id='" + prod_id + '\'' +
                '}';
    }
}
