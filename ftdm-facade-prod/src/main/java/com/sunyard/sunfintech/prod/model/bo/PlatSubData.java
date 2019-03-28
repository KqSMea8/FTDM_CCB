package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;

import java.math.BigDecimal;

/**
 * Created by wubin on 2017/9/13.
 */
public class PlatSubData extends BaseModel{

    private BigDecimal amt;

    private String type;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatSubData that = (PlatSubData) o;

        if (amt != null ? !amt.equals(that.amt) : that.amt != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = amt != null ? amt.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PlatSubData{" +
                "amt=" + amt +
                ", type='" + type + '\'' +
                '}';
    }
}
