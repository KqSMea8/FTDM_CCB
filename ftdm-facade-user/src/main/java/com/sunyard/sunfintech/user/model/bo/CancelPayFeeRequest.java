package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
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

public class CancelPayFeeRequest extends BaseRequest {
    @NotBlank
    private  String ori_order_no;

    public String getOri_order_no() {

        return ori_order_no;
    }

    public void setOri_order_no(String ori_order_no) {
        super.setBase_serial_ori_order_no(ori_order_no);
        this.ori_order_no = ori_order_no;
    }
}
