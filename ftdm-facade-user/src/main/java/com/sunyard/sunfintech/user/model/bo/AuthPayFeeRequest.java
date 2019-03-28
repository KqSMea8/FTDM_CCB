package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.annotation.SerialNoDetail;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.util.StringUtils;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/1.
 */
@Data
public class AuthPayFeeRequest extends BaseRequest {

    @NotBlank
    private String data;
    private String notify_url;
    @SerialNoDetail
    private List<AuthPayFeeRequestDetail>  authPayFeeRequestDetails;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        if(StringUtils.isNoneBlank(data)){
            authPayFeeRequestDetails = JSON.parseArray(data,AuthPayFeeRequestDetail.class);
        }
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        super.setBase_serial_notify_url(notify_url);
        this.notify_url = notify_url;
    }

    public List<AuthPayFeeRequestDetail> getAuthPayFeeRequestDetails() {
        return authPayFeeRequestDetails;
    }

    public void setAuthPayFeeRequestDetails(List<AuthPayFeeRequestDetail> authPayFeeRequestDetails) {
        this.authPayFeeRequestDetails = authPayFeeRequestDetails;
    }
}
