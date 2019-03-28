package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;

import java.math.BigDecimal;

/**
 * Created by PengZY on 2018/3/12.
 */
public class ProdTruncationResponse extends BaseResponse {

    private String is_finished;

    private String prod_state;

    private BigDecimal remain_vol;

    public String getIs_finished() {
        return is_finished;
    }

    public void setIs_finished(String is_finished) {
        this.is_finished = is_finished;
    }

    public String getProd_state() {
        return prod_state;
    }

    public void setProd_state(String prod_state) {
        this.prod_state = prod_state;
    }

    public BigDecimal getRemain_vol() {
        return remain_vol;
    }

    public void setRemain_vol(BigDecimal remain_vol) {
        this.remain_vol = remain_vol;
    }

    @Override
    public String toString() {
        return "ProdTruncationResponse{" +
                "is_finished='" + is_finished + '\'' +
                ", prod_state='" + prod_state + '\'' +
                ", remain_vol=" + remain_vol +
                '}';
    }
}
