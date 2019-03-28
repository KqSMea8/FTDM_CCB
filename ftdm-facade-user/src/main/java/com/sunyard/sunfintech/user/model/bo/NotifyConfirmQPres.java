package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseModel;

/**
 * Created by dingjy on 2017/6/4.
 */
public class NotifyConfirmQPres extends BaseModel {
    //处理状态
    private Boolean status;
    //返回数据
    private String data;
    //异步通知商户地址
    private String URL;

    private String mall_no;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    @Override
    public String toString() {
        return "NotifyConfirmQPres{" +
                "status=" + status +
                ", data='" + data + '\'' +
                ", URL='" + URL + '\'' +
                ", mall_no='" + mall_no + '\'' +
                '}';
    }
}
