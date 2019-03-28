package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * Created by PengZY on 2017/9/21.
 */
@CacheConfig(cacheNames="batchTransferToPersonRequest")
@org.springframework.stereotype.Service
public class BatchTransferToPersonRequest extends BaseRequest {

    @NotBlank
    private String plat_account;//平台账号

    @NotBlank
    private String notify_url;

    @NotBlank
    private String data;

    private List<BatchTransferToPersonRequestDeatil> batchTransferToPersonRequestDeatils;

    public String getPlat_account() {
        return plat_account;
    }

    public void setPlat_account(String plat_account) {
        this.plat_account = plat_account;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        setBatchTransferToPersonRequestDeatils(JSON.parseArray(data,BatchTransferToPersonRequestDeatil.class));
    }

    public List<BatchTransferToPersonRequestDeatil> getBatchTransferToPersonRequestDeatils() {
        return batchTransferToPersonRequestDeatils;
    }

    public void setBatchTransferToPersonRequestDeatils(List<BatchTransferToPersonRequestDeatil> batchTransferToPersonRequestDeatils) {
        this.batchTransferToPersonRequestDeatils = batchTransferToPersonRequestDeatils;
    }


}
