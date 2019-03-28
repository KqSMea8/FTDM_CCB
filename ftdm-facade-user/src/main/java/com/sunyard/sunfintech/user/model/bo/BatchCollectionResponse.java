package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BatchCollectionResponse extends BaseResponse{
    private List<Data> data;

    public List<Data> getData() {
        if(null==this.data){
            data=new ArrayList<Data>();
        }
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data implements Serializable{
        private String detail_no;
        private String status;
        private BigDecimal amt;
        private String error_no;
        private String error_info;

        public String getError_no() {
            return error_no;
        }

        public void setError_no(String error_no) {
            this.error_no = error_no;
        }

        public String getError_info() {
            return error_info;
        }

        public void setError_info(String error_info) {
            this.error_info = error_info;
        }

        public String getDetail_no() {
            return detail_no;
        }

        public void setDetail_no(String detail_no) {
            this.detail_no = detail_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public BigDecimal getAmt() {
            return amt;
        }

        public void setAmt(BigDecimal amt) {
            this.amt = amt;
        }
    }
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
