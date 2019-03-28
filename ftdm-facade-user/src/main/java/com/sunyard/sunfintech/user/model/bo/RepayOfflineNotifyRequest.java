package com.sunyard.sunfintech.user.model.bo;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.base.BaseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;


public class RepayOfflineNotifyRequest extends BaseModel {
    /**
     * 转账类型：行内转账“pay”；跨行转账“paybill”
     */
    @NotNull
    private String tran_type;
    /**
     * 代发编号
     */
    @NotNull
    private String third_no;
    /**
     * 订单号
     */
    @NotNull
    private String third_batch_no;
    /**
     * 回调信息
     */
    @NotBlank
    private String trandata;
    private List<RepayOfflineNotifyData> trandataDetail;
    /**
     * 合作商编号
     */
    private String partner_id;
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 签名串
     */
    private String cert_sign;

    public String getTran_type() {
        return tran_type;
    }

    public void setTran_type(String tran_type) {
        this.tran_type = tran_type;
    }

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

    public String getCert_sign() {
        return cert_sign;
    }

    public void setCert_sign(String cert_sign) {
        this.cert_sign = cert_sign;
    }

    public String getThird_no() {
        return third_no;
    }

    public void setThird_no(String third_no) {
        this.third_no = third_no;
    }

    public String getThird_batch_no() {
        return third_batch_no;
    }

    public void setThird_batch_no(String third_batch_no) {
        this.third_batch_no = third_batch_no;
    }

    public String getTrandata() {
        return trandata;
    }

    public void setTrandata(String trandata) {
        this.trandata = trandata;
        if (StringUtils.isNoneBlank(trandata)) {
            this.trandataDetail = JSON.parseArray(trandata, RepayOfflineNotifyData.class);
        }
    }

    public List<RepayOfflineNotifyData> getTrandataDetail() {
        return trandataDetail;
    }

    public void setTrandataDetail(List<RepayOfflineNotifyData> trandataDetail) {
        this.trandataDetail = trandataDetail;
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
