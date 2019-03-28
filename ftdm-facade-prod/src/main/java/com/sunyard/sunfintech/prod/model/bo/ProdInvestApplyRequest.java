package com.sunyard.sunfintech.prod.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdInvestApplyRequest extends BaseRequest {

    @NotNull
    private String platcust;

    @NotBlank
    private String trans_pwd;

    @NotBlank
    private String random_key;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getTrans_pwd() {
        return trans_pwd;
    }

    public void setTrans_pwd(String trans_pwd) {
        this.trans_pwd = trans_pwd;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    @Override
    public String toString() {
        return "ProdInvestApplyRequest{" +
                "platcust='" + platcust +
                "'}'";
    }
}
