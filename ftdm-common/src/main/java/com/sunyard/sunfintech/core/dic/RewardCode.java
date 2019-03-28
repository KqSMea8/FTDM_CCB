package com.sunyard.sunfintech.core.dic;

/*
 * 
 * REGISTER --注册
 * CERTIFY	--实名认证
 * BANKNO	--银行卡绑定
 * BID		--投资
 */
public enum RewardCode {
	
    REGISTER("0"),CERTIFY("1"),BANKNO("2"),BID("3");
	
    private String code;

    private RewardCode(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
