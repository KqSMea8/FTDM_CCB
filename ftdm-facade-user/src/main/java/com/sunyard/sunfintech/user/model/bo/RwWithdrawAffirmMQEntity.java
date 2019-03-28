package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;

import java.io.Serializable;

/**
 * Created by PengZY on 2018/1/30.
 */
public class RwWithdrawAffirmMQEntity implements Serializable {

    private BaseRequest baseRequest;

    private DateDetailAffirm dateDetailAffirm;

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public DateDetailAffirm getDateDetailAffirm() {
        return dateDetailAffirm;
    }

    public void setDateDetailAffirm(DateDetailAffirm dateDetailAffirm) {
        this.dateDetailAffirm = dateDetailAffirm;
    }
}
