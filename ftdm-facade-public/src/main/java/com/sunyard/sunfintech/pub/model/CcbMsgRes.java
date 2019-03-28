package com.sunyard.sunfintech.pub.model;

import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by dingjy on 2017/6/27.
 * ccb支付组短信接口返回信息
 */
public class CcbMsgRes extends BaseModel {
    private String partner_id;//合作商编号
    private String channelId;//通道ID
    private String third_batch_no;//流水号
    private String recode;//返回代码
    private String remsg;//返回信息

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getThird_batch_no() {
        return third_batch_no;
    }

    public void setThird_batch_no(String third_batch_no) {
        this.third_batch_no = third_batch_no;
    }

    public String getRecode() {
        return recode;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public String getRemsg() {
        return remsg;
    }

    public void setRemsg(String remsg) {
        this.remsg = remsg;
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
