package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 三要素绑卡（仅支持个人，绑定卡仅用于提现）
 * Create by Raoyulu on 2017/9/4
 */
public class PersonBindCardRequest extends BaseRequest{

    @NotBlank
    private String platcust;//用户在资金账户管理平台的子账户

    @NotBlank
    private String id_type;//证件类型（1：身份证）

    @NotBlank
    private String id_code;//证件号码

    @NotBlank
    private String name;//姓名

    @NotBlank
    private String card_no;//卡号

    @NotBlank
    private String card_type;//卡类型(2:信用卡)

    @NotBlank
    private String pay_code;//支付通道（可选银行智能绑卡通道）

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }
}
