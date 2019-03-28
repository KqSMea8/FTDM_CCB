package com.sunyard.sunfintech.search.model;

import com.sunyard.sunfintech.core.base.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by dingjy on 2017/5/24.
 */
public class SearchCompanyAccountBalanceData extends BaseRequest {

    /**
     * 卡类型：
     * 01-存管汇总账户
     * 02-自有资金账户
     * 03-资金清算账户
     * 04-风险准备金账户
     */
    @NotBlank
    private String card_type;

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }
}
