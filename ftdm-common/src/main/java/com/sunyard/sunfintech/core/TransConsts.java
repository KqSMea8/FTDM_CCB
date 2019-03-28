package com.sunyard.sunfintech.core;


public interface TransConsts {
	/*
	 * 交易常量定义
	 *
	 */

	/****************P00001*****账户类流水*********************/
	//联机注册
	public static final String REGIST_CODE = "600001";//交易代码
	public static final String REGIST_NAME = "联机注册";//交易名称
	//批量注册
	public static final String BATCH_REGIST_CODE = "600002";//交易代码
	public static final String BATCH_REGIST_NAME = "批量注册";//交易名称
	//批量注册实名认证
	public static final String BATCH_CERTIFY_CODE = "600003";//交易代码
	public static final String BATCH_CERTIFY_NAME = "批量实名认证";//交易名
	//电子账户开户
	public static final String PLATCUST_OPEN_CODE = "600004";//交易代码
	public static final String PLATCUST_OPEN_NAME = "电子帐户开户";//交易名
	//更新扩展表
	public static final String UPDATE_CODE="U00001";//交易代码
	public static final String UPDATE_NAME="扩展信息更新";//交易名
	//更新平台客户信息
	public static final String PLAT_UPDATE_CODE="PU00001";//交易代码
	public static final String PLAT_UPDATE_NAME="更新平台客户信息";//交易名
	public static final String BATCH_CHANGE_CARD_CODE="CP00001";//交易代码
	public static final String BATCH_CHANGE_CARD_NAME="批量换卡";//交易名

	//绑卡
	public static final String CARD_BIND_CODE = "610001";//交易代码
	public static final String CARD_BIND_NAME = "绑卡";//交易名称
	//信用卡绑卡
	public static final String CREDIT_CARD_BIND_CODE = "610013";//交易代码
	public static final String CREDIT_CARD_BIND_NAME = "信用卡绑卡";//交易名称
	// 短信绑卡
	public static final String CARD_BIND_APPLY_CODE = "610011";// 交易代码
	public static final String CARD_BIND_APPLY_NAME = "绑卡短信申请";// 交易名称
	public static final String CARD_BIND_CONFIRM_CODE = "610012";// 交易代码
	public static final String CARD_BIND_CONFIRM_NAME = "绑卡短信确认";// 交易名称
	// 换卡
	public static final String CARD_CHANGE_CODE = "610002";//交易代码
	public static final String CARD_CHANGE_NAME = "换卡";//交易名称

	//批量解绑
	public static final String BATCH_CARD_UNBIND_CODE = "610003";//交易代码
	public static final String BATCH_CARD_UNBIND_NAME = "批量解绑";//交易名称

	//批量绑卡
	public static final String BATCH_CARD_BIND_CODE = "620001";//交易代码
	public static final String BATCH_CARD_BIND_NAME = "批量绑卡";//交易名称
	//批量换卡
	public static final String BATCH_CARD_CHANGE_CODE = "620002";//交易代码
	public static final String BATCH_CARD_CHANGE_NAME = "批量换卡";//交易名称

	//短验换卡申请
	public static final String MESSAGE_CARD_CHANGEREQUEST_CODE = "620003";//交易代码
	public static final String MESSAGE_CARD_CHANGEREQUEST_NAME = "短验换卡申请";//交易名称

	//短验换卡确认
	public static final String MESSAGE_CARD_CHANGECONFIRM_CODE = "620004";//交易代码
	public static final String MESSAGE_CARD_CHANGECONFIRM_NAME = "短验换卡确认";//交易名称

	//首笔认证请求
	public static final String FIRST_CARDCERT_CODE = "620005";//交易代码
	public static final String FIRST_CARDCERT_NAME = "首笔认证";//交易名称

	//首笔认证查询
	public static final String QUERY_FIRST_CARDCERT_CODE = "620006";//交易代码
	public static final String QUERY_FIRST_CARDCERT_NAME = "首笔认证查询";//交易名称

	//四要素开户申请
	public static final String APPLY_4ELEMENTS_REGISTER_CODE = "620007";//交易代码
	public static final String APPLY_4ELEMENTS_REGISTER_NAME = "四要素开户申请";//交易名称

	//四要素开户确认
	public static final String CONFIRM_4ELEMENTS_REGISTER_CODE = "620008";//交易代码
	public static final String CONFIRM_4ELEMENTS_REGISTER_NAME = "四要素开户确认";//交易名称

	//三要素开户申请
	public static final String APPLY_3ELEMENTS_REGISTER_CODE = "620009";//交易代码
	public static final String APPLY_3ELEMENTS_REGISTER_NAME = "三要素开户申请";//交易名称

	//企业开户
	public static final String COMPANY_REGISTER_CODE = "620010";//交易代码
	public static final String COMPANY_REGISTER_NAME = "企业开户";//交易名称
	//
	public static  final String OVERSEAS_OPENING_CODE ="620011";//交易代码
	public static  final String OVERSEAS_OPENING_NAME ="境外开户";//交易代码
	//设置支付密码
	public static final String SET_PWD_CODE = "630001";//交易代码
	public static final String SET_PWD_NAME = "设置支付密码";//交易名称
	//修改支付密码
	public static final String UPDATE_PWD_CODE = "630002";//交易代码
	public static final String UPDATE_PWD_NAME = "修改支付密码";//交易名称
	//修改预留手机号
	public static final String UPDATE_PRE_MOBILE_CODE = "630003";//交易代码
	public static final String UPDATE_PRE_MOBILE_NAME = "修改预留手机号";//交易名称

	//客户冻结/解冻
	public static final String FREEZE_ACCOUNT_CODE = "640001";//交易代码
	public static final String FREEZE_ACCOUNT_NAME = "客户冻结解冻";//交易名称

	//客户冻结/解冻
	public static final String UNREG_ACCOUNT_CODE = "640002";//交易代码
	public static final String UNREG_ACCOUNT_NAME = "客户销户";//交易名称

	/*********************业务相关信息*********************/
	//标的发布
	public static final String PUBLISH_CODE = "P00000";
	public static final String PUBLISH_NAME = "标的发布";
	//标的投标
	public static final String BUY_CODE = "P00001";
	public static final String BUY_NAME = "标的投标";

	//提现
	public static final String WITHDRAW_CODE = "W00001";
	public static final String WITHDRAW_NAME = "提现";

	// 提现手续费
	public static final String WITHDRAW_FEE_CODE = "WF0001";
	public static final String WITHDRAW_FEE_NAME = "提现手续费";

	//代偿手续费
	public static final String COMPENSATORYREPAY_FEE_CODE = "WF0002";
	public static final String COMPENSATORYREPAY_FEE_NAME = "代偿手续费";

	//平台提现
	public static final String PLAT_WITHDRAW_CODE = "WP0001";
	public static final String PLAT_WITHDRAW_NAME = "平台提现";

	//标的成立
	public static final String ESTAB_CODE = "P00002";
	public static final String ESTAB_NAME = "标的成立";

	//标的废除
	public static final String ABANDON_CODE = "P00003";
	public static final String ABANDON_NAME = "标的废除";

	//标的出账
	public static final String CHARGEOFF_CODE = "P00004";
	public static final String CHARGEOFF_NAME = "标的出账";

	//标的出账信息修改
	public static final String CHARGEOFF_UPDATE_CODE = "P00004";
	public static final String CHARGEOFF_UPDATE_NAME = "标的出账信息修改";

	//债权转让
	public static final String TRANSFERDEBT_CODE = "P00005";
	public static final String TRANSFERDEBT_NAME = "债权转让";

	//标的还款
	public static final String PRODREPAY_CODE = "P00006";
	public static final String PRODREPAY_NAME = "标的还款";


	//借款人还款
	public static final String BORROWREPAY_CODE = "P00007";
	public static final String BORROWREPAY_NAME = "借款人还款申请";

	//标的代偿还款
	public static final String PRODCOMPENSATORYREPAY_CODE = "P00009";
	public static final String PRODCOMPENSATORYREPAY_NAME = "代偿还款";

	//借款人还款代偿（委托）
	public static final String BORROWREPAY_COMPENSATORYREPAY_CODE = "P00010";
	public static final String BORROWREPAY_COMPENSATORYREPAY_NAME = "借款人还款代偿";

	//投资撤销
	public static final String INVEST_ABORT_CODE = "P00011";
	public static final String INVEST_ABORT_NAME = "投资撤销";

	//投资申请
	public static final String INVEST_APPLY_CODE = "P00012";
	public static final String INVEST_APPLY_NAME = "投资申请";


	//债转申请
	public static final String PRODTRANSFER_APPLY_CODE="P00013";
	public static final String PRODTRANSFER_APPLY_NAME="债转申请";


	// 提前还款
	public static final String BATCH_BORROWREPAY_CODE = "B00001";
	public static final String BATCH_BORROWREPAY_NAME = "提前还款";

	//充值
	public static final String CHARGE_CODE = "C00001";
	public static final String CHARGE_NAME = "充值";
	//平台充值
	public static final String PLAT_CHARGE_CODE = "CP0001";
	public static final String PLAT_CHARGE_NAME = "平台充值";

	//批量提现
	public static final String BATCH_WITHDRAW_CODE = "W10001";
	public static final String BATCH_WITHDRAW_NAME = "批量提现";

	//提现申请
	public static final String WITHDRAW_APPLICATION_CODE = "W10002";
	public static final String WITHDRAW_APPLICATION_NAME = "提现申请";

	//提现确认
	public static final String WITHDRAW_AFFIRM_CODE = "W10003";
	public static final String WITHDRAW_AFFIRM_NAME = "提现确认";

	//回拨退款提现
	String WITHDRAW_TO_PLAT_CODE="W10004";
	String WITHDRAW_TO_PLAT_NAME="回拨退款提现";

	public static final String BATCH_WITHDRAW_CODE_TRUE = "W20002";
	public static final String BATCH_WITHDRAW_NAME_TRUE = "批量提现通知确认";

	//冻结
	public static final String FUND_FREEZE_CODE = "F00001";
	public static final String FUND_FREEZE_NAME = "资金冻结";
	//解冻
	public static final String FUND_UNFROZEN_CODE = "F00002";
	public static final String FUND_UNFROZEN_NAME = "资金解冻";

	//平台内转账
	public static final String TRANSFER_ACCOUNTS_CODE = "TC00001";
	public static final String TRANSFER_ACCOUNTS_NAME = "转账";

	//投融资转账
	public static final String CONVERSE_ACCOUNTS_CODE = "TC00002";
	public static final String CONVERSE_ACCOUNTS_NAME = "投融资转换";

	//平台不同账户互转
	public static final String PLAT_CONVERSE_ACCOUNTS_CODE = "TC00003";
	public static final String PLAT_CONVERSE_ACCOUNTS_NAME = "平台不同账户转换";


	//中间户减款
	public static final String PLAT_MADDLE_ACCOUNTS_CODE = "ZJ00001";
	public static final String PLAT_MADDLE_ACCOUNTS_NAME = "中间户减款";

	//中间户加款
	public static final String PLAT_MADDLE_ACCOUNTS_ADD_CODE = "ZJ00002";
	public static final String PLAT_MADDLE_ACCOUNTS_ADD_NAME = "中间户加款";

	//入账
	public static final String CHARGE_ENTER_CODE = "CE00001";
	public static final String CHARGE_ENTER_NAME = "入账";
	//出账
	public static final String CHARGE_OFF_CODE = "CO00001";
	public static final String CHARGE_OFF_NAME = "出账";

	public static final String CHARGE_OFF_ADVANCE_CODE = "CO00002";
	public static final String CHARGE_OFF_ADVANCE_NAME = "垫付出账";

	//平台向个人转账
	public static final String PLAT_TO_ACCOUNT_CODE = "PTA00001";
	public static final String PLAT_TO_ACCOUNT_NAME = "平台转账个人";

	//平台向个人转账撤销
	public static final String PLAT_TO_ACCOUNT_BACK_CODE = "PTABK001";
	public static final String PLAT_TO_ACCOUNT_BACK_NAME = "平台转账个人撤销";

	//个人向平台转账
	public static final String TO_PLAT_ACCOUNT_CODE = "PTA00002";
	public static final String TO_PLAT_ACCOUNT_NAME = "个人转账平台";

	//平台日，月转账限额参数id
	public static final String PLAT_TRANS_LIMIT = "PTL00001";
	//平台日，月转账限额value
	public static final String PLAT_DAY_LIMIT = "1";
	public static final String PLAT_MONTH_LIMIT = "2";


	public static final String PLAT_YEAE_LIMIT = "3";
	//个人日，月，年转账限额参数id
	public static final String ACCOUNT_TRANS_LIMIT = "ATL00001";
	//个人日，月转账限额value
	public static final String ACCOUNT_DAY_LIMIT = "1";
	public static final String ACCOUNT_MONTH_LIMIT = "2";
	public static final String ACCOUNT_YEAR_LIMIT = "3";

	//快捷充值
	public static final String QUICK_RECHARGE_CODE = "CK0001";
	public static final String QUICK_RECHARGE_NAME = "快捷充值";
	public static final String QUICK_CONFIRM_CODE = "C00011";
	public static final String QUICK_CONFIRM_NAME = "快捷充值确认";

	//代扣
	public static final String COLLECTION_CODE = "C00005";
	public static final String COLLECTION_NAME = "代扣充值";

	//网关充值请求
	public static final String GATEWAY_RECHARGE_CODE = "C00002";
	public static final String GATEWAY_RECHARGE_NAME = "网关充值";

	// 商城充值模式
	public static final String SHOP_RECHARGE_CODE = "C00003";
	public static final String SHOP_RECHARGE_NAME = "商城充值";

	// 还款计划更新
	public static final String REPAY_PLAN_CODE = "H00001";
	public static final String REPAY_PLAN_NAME = "计划还款";

	// 借款人线下还款
	public static final String BORROW_REPAY_CODE = "P00008";
	public static final String BORROW_REPAY_NAME = "借款人线下还款";

	// 借款人分账至受托人
	public static final String BORROW_SUB_ACCOUNT_CODE = "P00012";
	public static final String BORROW_SUB_ACCOUNT_NAME = "借款人分账";

	// 借款人分账至受托人
	public static final String BORROW_SUB_TRUSTACCOUNT_CODE = "P00013";
	public static final String BORROW_SUB_TRUSTACCOUNT_NAME = "借款人分账至受托人";

	// 借款人分账至平台手续费
	public static final String BORROW_SUB_FEEACCOUNT_CODE = "P00014";
	public static final String BORROW_SUB_FEEACCOUNT_NAME = "借款人分账至平台手续费";

	// 借款人分账至平台风险金
	public static final String BORROW_SUB_DEPOSITACCOUNT_CODE = "P00015";
	public static final String BORROW_SUB_DEPOSITACCOUNT_NAME = "借款人分账至平台风险金";

	// 借款人分账至代偿人
	public static final String BORROW_SUB_COMACCOUNT_CODE = "P00016";
	public static final String BORROW_SUB_COMACCOUNT_NAME = "借款人分账至代偿人";


	// 投资人线下充值
	public static final String INVEST_CHANGE_CODE = "C00004";
	public static final String INVEST_CHANGE_NAME = "投资人线下充值";

	//现金账户转账电子账户
	public static final String CASH_TO_EACCOUNT_CODE="TC0004";
	public static final String CASH_TO_EACCOUNT_NAME="现金账户转账电子账户";

	//请求第三方批量订单查询
	public static final String LIST_ORDER_NO_QUERY_CODE = "REQ00001";
	public static final String LIST_ORDER_NO_QUERY_NAME = "请求第三方批量订单查询";

	//请求第三方批量订单查询
	public static final String CLEAR_N_CODE = "S00001";
	public static final String CLEAR_N_NAME = "可用在途清算";

	//请求第三方批量订单查询
	public static final String CLEAR_F_CODE = "S00002";
	public static final String CLEAR_F_NAME = "冻结在途清算";

	//退款
	String REFUND_CODE = "TK0001";
	String REFUND_NAME = "退款";

	//线下加款
	String OFFLINE_CODE = "OF0001";
	String OFFLINE_NAME = "线下加款";

	//管理台内部调账
	String INSIDE_TCVH_CODE = "IT0001";
	String INSIDE_TCVH_NAME = "管理台内部调账";

	//授权申请
	String APPLY_AUTH_OPERA_CODE = "A00001";
	String APPLY_AUTH_OPERA_NAME = "授权申请";

	//授权确认
	String CONFIRM_AUTH_OPERA_CODE = "A00002";
	String CONFIRM_AUTH_OPERA_NAME = "授权确认";

	//授权取消
	String CANCEL_AUTH_OPERA_CODE = "A00003";
	String CANCEL_AUTH_OPERA_NAME = "授权取消";

	//缴费
	String PAY_FEE_CODE = "B00002";
	String PAY_FEE_NAME = "缴费";

	//授权缴费
	String AUTH_PAY_FEE_CODE = "B00003";
	String AUTH_PAY_FEE_NAME = "授权缴费";

	//取消缴费
	String CANCEL_PAY_FEE_CODE = "B00004";
	String CANCEL_PAY_FEE_NAME = "授权取消";

	//系统异常
	String SYSTEM_ERROR_CODE="ERR001";
	String SYSTEM_ERROR_NAME="系统异常";


	//签约申请
	String CONTRACT_PAY_FEE_CODE="CP00001";
	String CONTRACT_PAY_FEE_NAME="签约申请";

	//签约确认
	String CONTRACTT_PAY_TT_CODE="CT00001";
	String CONTRACTT_PAY_FEE_NAME="签约确认";



}
