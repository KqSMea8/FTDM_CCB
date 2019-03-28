package com.sunyard.sunfintech.pub.model;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by dingjy on 2017/6/27.
 * 草根短信发送web端请求数据
 */
public class CcbSendMsgReq extends BaseRequest {

    //短信类型
    @NotBlank
    private String msg_type;
    //手机号码
    @NotBlank
    private String mobile_no;
    //金额
    private String amount;
    //短信 验证码
    private String verify_info;

    public String getVerify_info() {
        return verify_info;
    }

    public void setVerify_info(String verify_info) {
        this.verify_info = verify_info;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
