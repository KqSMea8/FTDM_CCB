package com.sunyard.sunfintech.core.dic;

public enum SeqCodeType {

/*
 *	ACCO_FLOW_ID:账户类流水;
 *	REPEAL_FLOW_ID:冲正流水;
 *	TRANS_FLOW_ID:交易类流水;
 *	TRANS_FRONEN_ID:冻结质押类流水;
 *	WITHDRAW_SERIAL_ID：提现流水;
 *	FINANCING_ID:融资信息编号;
 *	ABNOMALCAPTIAL_ID:资金异常流水;
 *	SHARE_ID:产品份额流水;
 *	CHARGE_SERIAL_ID：充值流水;
 *	FUND_REQUEST_ID:资金请求流水;
*/
    ACCO_FLOW_ID("ACCO_FLOW_ID"),REPEAL_FLOW_ID("REPEAL_FLOW_ID"),
    TRANS_FLOW_ID("TRANS_FLOW_ID"),TRANS_FRONEN_ID("TRANS_FRONEN_ID"),
    P2P_CHANGE_ID("P2P_CHANGE_ID"),ACC_AMT_ID("ACC_AMT_ID"),EACC_NO_ID("EACC_NO_ID"),
    WITHDRAW_SERIAL_ID("WITHDRAW_SERIAL_ID"),EACC_AMT_FLOW_ID("EACC_AMT_FLOW_ID"),CUST_ID("CUST_ID"),
    PLAT_CUST("PLAT_CUST"),FINANCING_ID("FINANCING_ID"),ABNOMALCAPTIAL_ID("ABNOMALCAPTIAL_ID"),SHARE_ID("SHARE_ID"),CHARGE_SERIAL_ID("CHARGE_SERIAL_ID"),FUND_REQUEST_ID("FUND_REQUEST_ID");
    
    private String name;
    
    private SeqCodeType(String code){
        name = code;
    }

    public String getCode(){
        return name;
    }
}
