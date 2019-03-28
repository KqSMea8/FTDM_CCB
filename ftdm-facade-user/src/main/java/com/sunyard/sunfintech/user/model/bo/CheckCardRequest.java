package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by terry on 2018/1/10.
 */
public class CheckCardRequest extends BaseRequest {

    @NotBlank
    private String platcust;

    @NotBlank
    private String client_property;

    @NotBlank
    private String name;

    @NotBlank
    private String pid;

    @NotBlank
    private String card_no;

    private String pre_mobile;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    public String getClient_property() {
        return client_property;
    }

    public void setClient_property(String client_property) {
        this.client_property = client_property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
