package com.sunyard.sunfintech.prod.model.bo;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by PengZY on 2018/1/24.
 */
public class ProdPublicCompensation implements Serializable {

    @NotBlank
    private String platcust;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }
}
