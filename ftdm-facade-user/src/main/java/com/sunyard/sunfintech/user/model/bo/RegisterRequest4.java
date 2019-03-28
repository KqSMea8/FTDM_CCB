package com.sunyard.sunfintech.user.model.bo;

import org.hibernate.validator.constraints.NotBlank;

public class RegisterRequest4 extends RegisterRequest3 {
    //预留手机号
    @NotBlank
    private String pre_mobile;

    private String platcust;

    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    @Override
    public String getPlatcust() {
        return platcust;
    }

    @Override
    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }
}
