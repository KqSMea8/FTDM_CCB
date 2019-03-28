package com.sunyard.sunfintech.pub.model;

import com.sunyard.sunfintech.core.util.SeqUtil;

import java.io.Serializable;

/**
 * 发送异步通知实体类
 * modifid by yanlgei 新增id
 * Created by terry on 2017/8/17.
 */
public class NotifyData implements Serializable {

    private String notifyUrl;
    private String notifyContent;
    private String mall_no;
    /**
     * 唯一标识
     * 默认获取一个序列号
     */
    private String id = SeqUtil.getSeqNum();

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getMall_no() {
        return mall_no;
    }

    public void setMall_no(String mall_no) {
        this.mall_no = mall_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NotifyData{" +
                "notifyUrl='" + notifyUrl + '\'' +
                ", notifyContent='" + notifyContent + '\'' +
                ", mall_no='" + mall_no + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
