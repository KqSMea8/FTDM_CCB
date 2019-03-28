package com.sunyard.sunfintech.core.exception;

import com.sunyard.sunfintech.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误信息
 * 
 * @author heroy
 * @version 2017/4/10
 */
public class BusinessMsg {
	/************************ 处理成功 ************************/
	public static final String SUCCESS = "10000";
	public static final String ACCEPTANCE_SUCCESS="10001";//受理成功
	public static final String CHECK_SUCCESS="10002";//校验通过
	/********************* 客户端引起的错误 *********************/
	public static final String PARAMETER_LACK = "20001";// 缺少必要参数
	public static final String PARAMETER_ERROR = "20002";// 参数错误
	public static final String SIGNATURE_ERROR = "20003";// 签名错误
	public static final String APPID_LACK = "20004"; // appid 为空
	public static final String APPID_ERROR = "20005"; // appid 错误
	public static final String PIATFOR_NUMBER_LACK = "20014"; // 平台编号为空
	public static final String PIATFORMUMBER_ERROR = "20015";// 平台编号错误
	public static final String ORDERNUMBER_REPEATED = "20016"; // 订单号重复
	public static final String ORIGINAL_ORDER_NO_ERROR = "20017"; // 原订单号重复
	public static final String ORIGINAL_ORDER_NO_FAILED = "20018";// 原订单号处理未成功
	public static final String ORDER_NOEXISTENT = "20019"; // 订单不存在
	public static final String PARAMETER_LENGTH_LONG = "21000";// 参数长度过长
	public static final String IDCODE_ERROR = "20020";// 身份证号有误
	public static final String MOBILE_ERROR = "20021";// 手机号有误
	public static final String BANKCARD_ERROR = "20022";// 银行卡有误

	public static final String ORIGINAL_ORDER_NO_EMPTY = "20023";//  原申请订单号为空

	/********************* 服务端引起的错误 *********************/
	public static final String UNKNOW_ERROE = "30001";// 未知错误
	public static final String DATEBASE_EXCEPTION = "30002"; // 数据库操作异常
	public static final String NULLPOINTEREXCEPTION = "30003"; // 空指针异常
	public static final String IOEREXCEPTION = "30004"; // io操作异常
	public static final String NUMBER_ERROR = "30005"; // 数学运算异常
	public static final String OUT_OF_ARRAY = "30006"; // 数组越界
	public static final String METHODPARAMETER_ERROR = "30007"; // 方法参数错误
	public static final String CLASSEXCEPTION = "30008"; // 类转换异常
	public static final String SAFE_ERROR = "30009"; // 违背安全原则
	public static final String SQL_ERROR = "30010"; // SQL异常
	public static final String RUNTIME_EXCEPTION = "30011"; // 运行异常
	public static final String UNKNOW_METHODNAME = "30012"; // 方法名不存在
	public static final String SERVER_BUSY = "30013";// 服务器繁忙
	public static final String CALL_REMOTE_ERROR = "31111";// 远程调用错误
	public static final String UNKNOW_REMOTE_STATUS = "32222"; /// 远程未知状况
	public static final String INVALID_PRODUCT_ID = "40001"; // 无效的产品id
	public static final String PASSWORD_ERROR = "40002"; // 用户密码错误
	public static final String PASSWORD_OUT_OFF_DATE = "41002"; // 密钥失效
	public static final String REPEATED_USERNAME = "40003"; // 用户名重复
	public static final String USER_LOCKED = "41003"; // 用户被锁定，用于用户多次输入密码后被锁定账户
	public static final String OPENID_ERROR = "40004"; // 无效的open_id
	public static final String INVALID_INVITATION_CODE = "40005"; // 无效的邀请码
	public static final String CERTIFICATION_ERROR = "40006"; // 实名认证失败
	public static final String INVALID_ACCOUNT = "40007";// 账号无效或者不存在
	public static final String REPEATED_ID = "40008"; // 重复的产品id
	public static final String NVALID_TRANSFER_NO = "40009"; // 无效的转让份额
	public static final String NVALID_PLAT_NO = "40010"; // 无效的平台编号
	public static final String PRODUCT_FULL = "40011"; // 标的已满额
	public static final String BUY_PRODUCT_FORBID = "40012"; // 标的状态不许购买
	public static final String USER_IN_BLACKLIST = "40013"; // 用户在黑名单中
	public static final String ESTABLISH_PRODUCT_FAILED = "40014"; // 标的未成标
	public static final String ACCOUNT_INFO_DIFF= "40015"; // 请求账户与融资人提取账户信息不一致
	public static final String ASSIGNMENT_DISSATISF = "40016"; // 不满足转让条件
	public static final String OUT_OF_RANGE = "40017"; // 不在误差范围内 //out_of_range
	public static final String SUM_NOTENOUGH = "40018"; // 出账金额不足
	public static final String SUBJECT_UNFULL = "40019"; // 标的未满额
	public static final String MONEY_ERROR = "40020"; // 金额有误
	public static final String SUBJECT_SUCCESS = "40021"; // 标的已成标
	public static final String PROD_REJECT_INVEST = "40023"; // 标的不可投标
	public static final String PROD_LIMIT_NOTENOUGH="40022";//标的额度不足
	public static final String REPAYMENT_FAILED = "41000"; // 还款失败
	public static final String REGISTERED = "42000"; // 已注册
	public static final String UNREGISTERED = "42001"; // 未注册
	public static final String TIEDCARD = "46000"; // 已绑卡
	public static final String NO_PERMISSION = "47000"; // 用户无权进行此操作
	public static final String RECHARGE_WRONGFUL = "50001"; // 充值金额不合法
	public static final String PAYMENT_FAILED = "50002"; // 支付失败
	public static final String NETWORK_ERROR = "50003"; // 网络异常
	public static final String TIEDCARDCHANNEL_NO = "50004"; // 用户此渠道下未绑定卡
	public static final String BINDID_ONTFOUND = "50005"; // bindid不存在
	public static final String TIEDCARD_FAILED = "50006"; // 绑卡失败
	public static final String TIEFAILURE = "50007";// 解绑失败
	public static final String GET_VERIFICATION_FAILED = "50008"; // 获取验证码失败
	public static final String NO_CHANNEL_INFORMATION = "50009"; // 没有渠道信息
	public static final String UNKNOW_CHANNEL = "50010"; // 未知渠道
	public static final String REPEAT_BINDING = "50011"; // 重复绑卡
	public static final String AUTHENTICATION_FAILED = "50012"; // 实名认证失败 (重复)
	public static final String VERIFICATION_ERROR="50023";//验证码错误
	public static final String VERIFICATION_OUT_OF_DATE="50024";//验证码已过期
    public static final String VERIFICATION_ERROR_TOO_MUCH="50025";
	public static final String CARD_NUMBER_OUT_LIMIT="50028";//绑卡数量超限制
	public static final String PHONE_NUMBER_ERROR="50029";//预留号码错误
	public static final String UNBIND_CARD_ERROR = "50013"; // 未绑卡
	public static final String BALANCE_LACK = "50014"; // 余额不足
	public static final String WITHDRAW_FAILED = "50015"; // 提现失败
	public static final String QUERY_BALANCE_ERROR = "50016"; // 余额查询失败
	public static final String GET_TIEDCARDMESSAGE_FAILED = "50017"; // 查询绑卡信息失败
	public static final String DOWNLOADFILE_FAILED = "50018"; // 下载对账文件失败
	public static final String REASON_ILLEGAL = "50019"; // 转账原因不合法
	public static final String LIMIT_OUT = "50020"; // 超出转账额度
	public static final String MOUTHLIMIT_OUT = "50021"; // 超出月转账额度
	public static final String CURRENT_NONSUPPORT = "50022";// 活期标暂不支持垫付出账
	public static final String TRANS_FAIL="50026";//转账失败
	public static final String CFCA_AUTH_FAILED="50027";//CFCA验证不通过
	public static final String TRANSFER_FAILED_RETRY="654321";//转账暂时失败，请重试
	public static final String FOUR_VERIFY_FAILED="50030";//鉴权失败
	public static final String IDENTIFYING_CODE_SEND_ERROR="50031";//验证码发送失败
	public static final String DATA_INSERT_FAILED="50032";//数据插入失败
	public static final String ORIGINAL_ORDER_PROCESSED="50033";//原订单已被处理
	public static final String REPEAT_REGISTER="50034";//已开户，请勿重复开户
	public static final String NO_AUTH_DATA="50035";//沒有确认授权数据
	public static final String IDENTIFYING_CODE_SEND_TOO_MUCH="50036";//验证码发送太过频繁
	public static final String AUTH_ERROR="50037";//验证失败，密码验证失败，但不是密码不正确，其他原因
	public static final String UNKNOWN_ACCOUNT_TYPE="50038";//未知账户类型
	public static final String ARRAY_SIZE_IS_TOO_LARGE="50039";//数组长度太长
	public static final String NO_BIND_CARD_DATA="50040";//没有绑卡数据
	public static final String TRANS_SERIAL_INNER_ERR="50041";//记录流水拦截器异常
	public static final String AUTH_CHECK_FAIL="50042";//授权验证未通过
	public static final String SET_PASSWORD_ERROR="50043";//设置交易密码错误
	public static final String URL_UNLAWFUL_REQUEST="50044";//申请与确认参数不一致
	public static final String OVER_DATE="50045";//标的时间超过
	public static final String NOT_FUND_CONFIG="50046";//未找到配置信息
	/**********************E支付组错误码*********************/
	public static final String RECONCILIATION_FILE_NOT_GENERATED="001001";//对账文件未生成
	public static final String E_VERIFICATION_OUT_OF_DATE="001002";//验证码已过期
	public static final String E_VERIFICATION_ERROR="001003";//验证码错误
	public static final String LOSS_CARD="013006";//已挂失卡
	public static final String INVALID_CARD_NUMBER="013004";//无效卡号
	public static final String INVAILD_ACCOUNT="013009";//无此账户
	public static final String E_BALANCE_LACK="013008";//余额不足
	public static final String E_LIMIT_OUT="013013";//超出提款限额
	public static final String RESTRICTIVE_TRADE_CARDHOLDERS="013012";//不允许持卡人进行的交易
	public static final String E_PASSWORD_ERROR="013011";//密码错
	public static final String PAYING_BANK_FAILURE="012012";//付款行故障
	public static final String PAYING_BANK_NOT_OPEN_BUSINESS="012010";//付款行未开通业务
	public static final String SINGLE_TRANSACTION_OUT_OF_UPPER_LIMIT="012017";//单笔交易金额超过单笔金额上限
	public static final String SINGLE_TRANSACTION_OUT_OF_LOWER_LIMIT="012018";//单笔交易金额低于单笔金额下限
	public static final String RECEIVING_BANK_FAILURE="012015";//收款行故障
	public static final String SYSTEM_BUSY="013028";//系统忙，请稍后再提交
	public static final String ORIGINAL_ORDER_NOEXISTENT="013033";//无此原交易
	public static final String ACCOUNT_NAME_MISMATCH="013031";//账号户名不符
	public static final String ACCOUNT_ERROR="013030";//账号错误
	public static final String ORIGINAL_AMT_ERROR="013014";//原始金额不正确
	public static final String ACCOUNT_FROZEN="013017";//账户已冻结
	public static final String ACCOUNT_NAME_ERROR="013065";//户名错
	public static final String ID_OR_IDTAPE_LACK="013063";//证件类型和证件号码未输
	public static final String ACCOUNT_NOT_SUPPORT_COLLECTION="013064";//账户不支持代收付
	public static final String TRANSMISSION_TIMEOUT="027019";//通讯超时
	public static final String DUBBO_TIMEOUT="028028";//dubbotimeout
	public static final String INVAILD_ACCOUNT_DOUJIE="013099";

	/************************* 处理失败 *************************/
	public static final String FAIL = "999999";// 处理失败

	private static final Map<String, String> errorMsg = new HashMap<String, String>() {
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		{
			put(SUCCESS, "处理成功");
			put(BusinessMsg.ACCEPTANCE_SUCCESS,"受理成功");
			put(BusinessMsg.CHECK_SUCCESS,"校验通过");

			/********************* 客户端引起的错误 *********************/
			put(BusinessMsg.PARAMETER_LACK, "缺少必要参数");
			put(BusinessMsg.PARAMETER_ERROR, "参数错误");
			put(BusinessMsg.SIGNATURE_ERROR, "签名错误");
			put(BusinessMsg.APPID_LACK, "appid 为空");
			put(BusinessMsg.APPID_ERROR, "appid 错误");
			put(BusinessMsg.PIATFOR_NUMBER_LACK, "平台编号为空");
			put(BusinessMsg.PIATFORMUMBER_ERROR, "平台编号错误");
			put(BusinessMsg.ORDERNUMBER_REPEATED, "订单号重复");
			put(BusinessMsg.ORIGINAL_ORDER_NO_ERROR, "原订单号重复");
			put(BusinessMsg.ORIGINAL_ORDER_NO_FAILED, "原订单号处理未成功");
			put(BusinessMsg.PARAMETER_LENGTH_LONG, "参数长度过长");
			put(BusinessMsg.IDCODE_ERROR, "身份证号有误");
			put(BusinessMsg.MOBILE_ERROR, "手机号有误");
			put(BusinessMsg.BANKCARD_ERROR, "银行卡号有误");
			put(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY, "原订单号为空");

			/********************* 服务端引起的错误 *********************/
			put(BusinessMsg.UNKNOW_ERROE, "未知错误");
			put(BusinessMsg.DATEBASE_EXCEPTION, "数据库操作异常");
			put(BusinessMsg.NULLPOINTEREXCEPTION, "空指针异常");
			put(BusinessMsg.IOEREXCEPTION, "io操作异常");
			put(BusinessMsg.NUMBER_ERROR, "数学运算异常");
			put(BusinessMsg.OUT_OF_ARRAY, "数组越界");
			put(BusinessMsg.METHODPARAMETER_ERROR, "方法参数错误");
			put(BusinessMsg.CLASSEXCEPTION, "类转换异常");
			put(BusinessMsg.SAFE_ERROR, "违背安全原则");
			put(BusinessMsg.SQL_ERROR, "SQL异常");
			put(BusinessMsg.RUNTIME_EXCEPTION, "运行异常");
			put(BusinessMsg.UNKNOW_METHODNAME, "方法名不存在");
			put(BusinessMsg.SERVER_BUSY, "服务器繁忙");
			put(BusinessMsg.CALL_REMOTE_ERROR, "远程调用错误");
			put(BusinessMsg.UNKNOW_REMOTE_STATUS, "远程未知状况");
			put(BusinessMsg.INVALID_PRODUCT_ID, "无效的产品id");
			put(BusinessMsg.PROD_LIMIT_NOTENOUGH,"标的剩余额度不足");
			put(BusinessMsg.PROD_REJECT_INVEST,"标的不可投标");

			put(BusinessMsg.PASSWORD_ERROR, "用户密码错误");
			put(BusinessMsg.URL_UNLAWFUL_REQUEST,"申请与确认参数不一致");
			put(BusinessMsg.PASSWORD_OUT_OFF_DATE, "密钥失效");
			put(BusinessMsg.ORDER_NOEXISTENT, "订单不存在");
			put(BusinessMsg.REPEATED_USERNAME, "用户名重复");
			put(BusinessMsg.USER_LOCKED, "账户被锁定");
			put(BusinessMsg.OPENID_ERROR, "无效的open_id");
			put(BusinessMsg.INVALID_INVITATION_CODE, "无效的邀请码");
			put(BusinessMsg.CERTIFICATION_ERROR, "实名认证失败");
			put(BusinessMsg.INVALID_ACCOUNT, "账号无效或者不存在");
			put(BusinessMsg.REPEATED_ID, "重复的产品id");
			put(BusinessMsg.NVALID_TRANSFER_NO, "无效的转让份额");
			put(BusinessMsg.NVALID_PLAT_NO, "无效的平台编号");
			put(BusinessMsg.PRODUCT_FULL, "标的已满额");
			put(BusinessMsg.BUY_PRODUCT_FORBID, "标的状态不许购买");
			put(BusinessMsg.USER_IN_BLACKLIST, "用户在黑名单中");
			put(BusinessMsg.ESTABLISH_PRODUCT_FAILED, "标的未成标");
			put(BusinessMsg.ACCOUNT_INFO_DIFF, "请求账户与融资人提取账户信息不一致");
			put(BusinessMsg.ASSIGNMENT_DISSATISF, "不满足转让条件");
			put(BusinessMsg.OUT_OF_RANGE, "不在误差范围内");
			put(BusinessMsg.SUM_NOTENOUGH, "出账金额不足");
			put(BusinessMsg.SUBJECT_UNFULL, "标的未满额");
			put(BusinessMsg.MONEY_ERROR, "金额有误");
			put(BusinessMsg.SUBJECT_SUCCESS, "标的已成标");
			put(BusinessMsg.REPAYMENT_FAILED, "还款失败");
			put(BusinessMsg.REGISTERED, "已注册");
			put(BusinessMsg.UNREGISTERED, "未注册");
			put(BusinessMsg.TIEDCARD, "已绑卡");
			put(BusinessMsg.NO_PERMISSION, "用户无权进行此操作");
			put(BusinessMsg.RECHARGE_WRONGFUL, "充值金额不合法");
			put(BusinessMsg.PAYMENT_FAILED, "支付失败");
			put(BusinessMsg.NETWORK_ERROR, "网络异常");
			put(BusinessMsg.TIEDCARDCHANNEL_NO, "用户此渠道下未绑定卡");
			put(BusinessMsg.BINDID_ONTFOUND, "");
			put(BusinessMsg.BINDID_ONTFOUND, "bindid不存在");
			put(BusinessMsg.TIEDCARD_FAILED, "绑卡失败");
			put(BusinessMsg.TIEFAILURE, "解绑失败");
			put(BusinessMsg.GET_VERIFICATION_FAILED, "获取验证码失败");
			put(BusinessMsg.NO_CHANNEL_INFORMATION, "没有渠道信息");
			put(BusinessMsg.UNKNOW_CHANNEL, "未知渠道");
			put(BusinessMsg.REPEAT_BINDING, "重复绑卡");
			put(BusinessMsg.AUTHENTICATION_FAILED, "实名认证失败");
			put(BusinessMsg.UNBIND_CARD_ERROR, "未绑卡");
			put(BusinessMsg.BALANCE_LACK, "余额不足");
			put(BusinessMsg.WITHDRAW_FAILED, "提现失败");
			put(BusinessMsg.QUERY_BALANCE_ERROR, "余额查询失败");
			put(BusinessMsg.GET_TIEDCARDMESSAGE_FAILED, "查询绑卡信息失败");
			put(BusinessMsg.DOWNLOADFILE_FAILED, "下载对账文件失败");
			put(BusinessMsg.REASON_ILLEGAL, "转账原因不合法");
			put(BusinessMsg.LIMIT_OUT, "超出转账额度");
			put(BusinessMsg.MOUTHLIMIT_OUT, "超出月转账额度");
			put(BusinessMsg.CURRENT_NONSUPPORT, "活期标暂不支持垫付出账");
            put(BusinessMsg.VERIFICATION_ERROR, "验证码错误");
            put(BusinessMsg.VERIFICATION_OUT_OF_DATE, "验证码已过期");
			put(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH, "验证码失败次数过多");
			put(BusinessMsg.TRANS_FAIL,"转账失败");
			put(BusinessMsg.CFCA_AUTH_FAILED,"CFCA验证不通过");
			put(BusinessMsg.TRANSFER_FAILED_RETRY,"转账暂时失败，请重试");
			put(BusinessMsg.CARD_NUMBER_OUT_LIMIT,"绑卡数量超限");
			put(BusinessMsg.PHONE_NUMBER_ERROR,"预留号码错误");
			put(BusinessMsg.IDENTIFYING_CODE_SEND_ERROR,"验证码发送失败");
			put(BusinessMsg.DATA_INSERT_FAILED,"数据插入失败");
			put(BusinessMsg.ORIGINAL_ORDER_PROCESSED,"原订单已被处理");
			put(BusinessMsg.REPEAT_REGISTER,"重复开户");
			put(BusinessMsg.NO_AUTH_DATA,"没有确认授权数据");
			put(BusinessMsg.IDENTIFYING_CODE_SEND_TOO_MUCH,"验证码发送太过频繁");
			put(BusinessMsg.AUTH_ERROR,"验权失败");
			put(BusinessMsg.UNKNOWN_ACCOUNT_TYPE,"未知账户类型");
			put(BusinessMsg.ARRAY_SIZE_IS_TOO_LARGE,"数组长度超出最大限制");
			put(BusinessMsg.NO_BIND_CARD_DATA,"没有绑卡数据，请先绑卡");
			put(BusinessMsg.TRANS_SERIAL_INNER_ERR,"流水拦截器记录内部异常");
			put(BusinessMsg.AUTH_CHECK_FAIL,"授权验证未通过");
			put(BusinessMsg.SET_PASSWORD_ERROR,"设置交易密码错误");
			put(BusinessMsg.NOT_FUND_CONFIG,"未找到配置信息");

			/**********************E支付组错误码*********************/
			put(BusinessMsg.RECONCILIATION_FILE_NOT_GENERATED,"对账文件未生成");
			put(BusinessMsg.E_VERIFICATION_OUT_OF_DATE,"验证码已过期");
			put(BusinessMsg.E_VERIFICATION_ERROR,"验证码错误");
			put(BusinessMsg.LOSS_CARD,"已挂失卡");
			put(BusinessMsg.INVALID_CARD_NUMBER,"无效卡号");
			put(BusinessMsg.INVAILD_ACCOUNT,"无此账户");
			put(BusinessMsg.E_BALANCE_LACK,"余额不足");
			put(BusinessMsg.E_LIMIT_OUT,"超出提款限额");
			put(BusinessMsg.RESTRICTIVE_TRADE_CARDHOLDERS,"不允许持卡人进行的交易");
			put(BusinessMsg.E_PASSWORD_ERROR,"密码错");
			put(BusinessMsg.PAYING_BANK_FAILURE,"付款行故障");
			put(BusinessMsg.PAYING_BANK_NOT_OPEN_BUSINESS,"//付款行未开通业务");
			put(BusinessMsg.SINGLE_TRANSACTION_OUT_OF_UPPER_LIMIT,"单笔交易金额超过单笔金额上限");
			put(BusinessMsg.SINGLE_TRANSACTION_OUT_OF_LOWER_LIMIT,"单笔交易金额低于单笔金额下限");
			put(BusinessMsg.RECEIVING_BANK_FAILURE,"收款行故障");
			put(BusinessMsg.SYSTEM_BUSY,"系统忙，请稍后再提交");
			put(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,"无此原交易");
			put(BusinessMsg.ACCOUNT_NAME_MISMATCH,"账号户名不符");
			put(BusinessMsg.ACCOUNT_ERROR,"账号错误");
			put(BusinessMsg.ORIGINAL_AMT_ERROR,"原始金额不正确");
			put(BusinessMsg.ACCOUNT_FROZEN,"账户已冻结");
			put(BusinessMsg.ACCOUNT_NAME_ERROR,"户名错");
			put(BusinessMsg.ID_OR_IDTAPE_LACK,"证件类型和证件号码未输");
			put(BusinessMsg.ACCOUNT_NOT_SUPPORT_COLLECTION,"账户不支持代收付");
			put(BusinessMsg.TRANSMISSION_TIMEOUT,"通讯超时");
			put(BusinessMsg.DUBBO_TIMEOUT,"dubbo连接超时");

			put(BusinessMsg.FOUR_VERIFY_FAILED,"鉴权失败");
			put(BusinessMsg.OVER_DATE,"超过标的募集时间");
			/************************* 处理失败 *************************/
			put(BusinessMsg.FAIL, "处理失败");
			put(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY, "原订单号为空");
			put(BusinessMsg.INVAILD_ACCOUNT_DOUJIE,"账户不存在，用已冻结");

		}
	};

	/**
	 * 获取错误信息
	 * 
	 * @param errorCode
	 *            错误代码
	 * @return 错误信息
	 */
	public static String getMsg(String errorCode) {
		String msg = errorMsg.get(errorCode);
		if (StringUtils.isBlank(msg)) {
			return "未知错误";
		}
		return msg;
	}
}
