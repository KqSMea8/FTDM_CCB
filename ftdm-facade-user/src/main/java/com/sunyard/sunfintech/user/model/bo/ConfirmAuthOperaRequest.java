package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

public class ConfirmAuthOperaRequest extends BaseRequest{
    /**
     * 授权金额
     */
    private BigDecimal authed_amount;
    /**
     * 授权期限 YYYYMMDD
     */
    private String authed_limittime;
    /**
     * 短信验证码
     */
    @NotBlank
    private String identifying_code;

    /**
     * 原申请订单号
     */
    @NotBlank
    private String origin_order_no;

    /**
     * 交易密码
     */
    @NotBlank
    private String trans_pwd;

    @NotBlank
    private String random_key;

    private String  notify_url;

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getIdentifying_code() {
        return identifying_code;
    }

    public void setIdentifying_code(String identifying_code) {
        this.identifying_code = identifying_code;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public String getTrans_pwd() {
        return trans_pwd;
    }

    public void setTrans_pwd(String trans_pwd) {
        this.trans_pwd = trans_pwd;
    }

    public String getRandom_key() {
        return random_key;
    }

    public void setRandom_key(String random_key) {
        this.random_key = random_key;
    }

    public BigDecimal getAuthed_amount() {
        return authed_amount;
    }

    public void setAuthed_amount(BigDecimal authed_amount) {
        this.authed_amount = authed_amount;
    }

    public String getAuthed_limittime() {
        return authed_limittime;
    }

    public void setAuthed_limittime(String authed_limittime) {
        this.authed_limittime = authed_limittime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfirmAuthOperaRequest that = (ConfirmAuthOperaRequest) o;

        if (identifying_code != null ? !identifying_code.equals(that.identifying_code) : that.identifying_code != null)
            return false;
        if (origin_order_no != null ? !origin_order_no.equals(that.origin_order_no) : that.origin_order_no != null)
            return false;
        if (trans_pwd != null ? !trans_pwd.equals(that.trans_pwd) : that.trans_pwd != null) return false;
        return random_key != null ? random_key.equals(that.random_key) : that.random_key == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (identifying_code != null ? identifying_code.hashCode() : 0);
        result = 31 * result + (origin_order_no != null ? origin_order_no.hashCode() : 0);
        result = 31 * result + (trans_pwd != null ? trans_pwd.hashCode() : 0);
        result = 31 * result + (random_key != null ? random_key.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConfirmAuthOperaRequest{" +
                "identifying_code='" + identifying_code + '\'' +
                ", origin_order_no='" + origin_order_no + '\'' +
                ", trans_pwd='" + trans_pwd + '\'' +
                ", random_key='" + random_key + '\'' +
                '}';
    }
}
