package com.sunyard.sunfintech.core;

import com.sunyard.sunfintech.core.util.BeanUtil;

import java.net.InetAddress;
import java.util.Map;

/**
 * 系统常量
 * modified by yanglei 20180111
 * @author heroy
 * @version 20170401 愚人节
 *
 */
public abstract class Constants {

	/**
	 * 新旧版本接口判断
	 * 新/旧
	 */
	public static final String NEW_INTERFACE="1";
	public static final String OLD_INTERFACE="0";


	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	/**
	 * 时间格式
	 */
	public static final String TIMEFORMAT = "yyyy-MM-dd hh:mm:ss";

	/**
	 * 编码方式
	 */
	public static final String CHARACTERENCODING = "UTF-8";

	/**
	 * 上传文件基础虚拟路径
	 */
//	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	public static final String EXCEPTION_HEAD = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
	
	public static final String CACHE_NAMESPACE = "ftdm:";

//	public static final String CHCHE_JOB_PRE = CACHE_NAMESPACE + "job:";

	public static final String DATA_SPLIT_WITH = "|";


	/** 
	 * 缓存键KEY
	 * 不同的类都有一个键 
	 * */
	public static final Map<Class<?>, String> CACHEKEYMAP = BeanUtil.newHashMap();
	
	/**
	 * 有效的1 无效的0
	 */
	public static final String ENABLED = "1";
	public static final String DISABLED = "0";

	public static final String GLOBAL_PARAMETER = "";

	public static final String RW_WITHDRAW_REDIS_ORIGIN_ORDER_NO_KEY = CACHE_NAMESPACE + "user:rw_withdraw:origin_order_no:";

//	public static final String PLATFROM_TO_PERSON_REDIS_KEY = CACHE_NAMESPACE + "platfrom_to_person_redis_key:";
//
//	public static final String RW_WITHDRAW_REDIS_QUERY_KEY = CACHE_NAMESPACE + "rw_withdraw_redis_query_key:";
//
//	public static final String RW_WITHDRAW_KEY_TRANS = CACHE_NAMESPACE + "rw_withdraw_trans:";

	public static final String REDISKEY_SYS_PARAMETER = CACHE_NAMESPACE + "billcheck:clear:";

	public static final String TRANSREQ_ORDER_KEY=CACHE_NAMESPACE+"order:";

	public static final String TRANSREQ_ORDER_LOCK_KEY=CACHE_NAMESPACE+"order:lock:";

	public static final String OPEN_ACCOUNT_PARAMS_KEY=CACHE_NAMESPACE+"user:eacc_userinfo:trans_serial:";

	public static final String AUTH_APPLAY_KEY=CACHE_NAMESPACE+"user:eacc_auth:trans_serial:";

	public static final String OPEN_ACCOUNT_IDENTIFYING_CODE_KEY=CACHE_NAMESPACE+"user:eacc_userinfo:identifying_code:";

	public static final String AUTH_APPLAY_IDENTIFYING_CODE_KEY =CACHE_NAMESPACE+"user:eacc_auth:identifying_code:";

	public static final String IDENTIFYING_CODE_TIME_OUT="identifying_code_time_out";

	public static final String IDENTIFYING_CODE_ERROR_NUM="identifying_code_error_num";

	public static final String SET_PWD_PARAMS_KEY=CACHE_NAMESPACE+"user:setPwd:trans_serial:";

	public static final String NOTIFY_SEDNDING_COUNT=CACHE_NAMESPACE+"nofiydata:";

	public static final String PROD_INVEST_APPLY_LOCK_PREFIX = "prodInvestApplyLock:";

	public static final String AUTHPAYFEE_ORDER_KEY=CACHE_NAMESPACE+"authPayfeeNotify_";

	public static final String PROD_TRANS_NOTIFY_DATA="prod:notify:data:";

	public static final String APPLY_AUTH_REPAY=CACHE_NAMESPACE+"user:applyAuthRepay:notify:data:";

	public static final String CHECK_AUTH_CACHE_KEY=CACHE_NAMESPACE+"user:authCheck:cache:";

	public static final String PROD_INVEST_NOTIFY_DATA="prod:investasync_callback:data:";

	public static final String BIND_CARD_CACHE_KEY=CACHE_NAMESPACE+"user:bindCard:cache:";

	public static final String ORDER_NO_IN_WHITELIST_KEY=CACHE_NAMESPACE+"web:whitelist:order_no:";

	public static final String TOKEN_IN_WHITELIST_KEY=CACHE_NAMESPACE+"web:whitelist:token:";

	public static final String TOKEN_STATIC_STR="token:order_no:";

	public static final String WITHDRAW_AUTO_ADD_VALUE_LOCK_KEY =CACHE_NAMESPACE+"user:withdraw:auto_add_value:";

	public static final String WITHDRAW_SEND_VALUE_LOCK_KEY =CACHE_NAMESPACE+"user:withdraw:send_value:";

	public static final String WITHDRAW_RETRY_NUMBER_KEY =CACHE_NAMESPACE+"user:withdraw:retry_num_key:";

	public static final String RW_WITHDRAW_REDIS_KEY = CACHE_NAMESPACE + "rw_withdraw_redis_key";

	public static final String PROD_INVEST_LOCK_KEY =CACHE_NAMESPACE+"prod:invest:lock_key:";

	public static final String REGISTER_LOCK_KEY =CACHE_NAMESPACE+"user:register:lock_key:";

	public static final String APP_LOCK_KEY=CACHE_NAMESPACE+"user:contractApp:";

	public static final String PLAT_WITHDRAW_DAY_LIMIT_KEY=CACHE_NAMESPACE+"plat:withdraw:day_limit_key:";

	public static final String PAY_FEE_DAY_LIMIT_KEY=CACHE_NAMESPACE+"user:pay_fee:day_limit_key:";

	public static final String PLAT_WITHDRAW_READY_AMT_KEY=CACHE_NAMESPACE+"user:withdraw:ready_amt_key:";

	public static final String SKIP_CHECK_PWD_KEY=CACHE_NAMESPACE+"web:skio_check_pwd:";



//	/**
//	 * 标的还款锁
//	 */
//	public final static String PROD_REFUND_LOCK_PREFIX = "prod:lock:prod_refund:";
//	public static String getProdRefundLockPrefix(final String plat_not, final String prod_id){
//		StringBuilder sb = new StringBuilder(PROD_REFUND_LOCK_PREFIX)
//				.append(plat_not).append(DATA_SPLIT_WITH).append(prod_id);
//		return sb.toString();
//	}
//
//	/**
//	 * 标的发布锁
//	 * @param plat_not 平台号
//	 * @param prod_id 标的号
//	 */
//	public static String PROD_PUBLISH_ABORT_LOCK_PREFIX = "prod:lock:prod_publish_abort:";
//	public static String getProdPublisAboortLock(final String plat_not, final String prod_id){
//		StringBuilder sb = new StringBuilder(PROD_PUBLISH_ABORT_LOCK_PREFIX)
//				.append(plat_not).append(DATA_SPLIT_WITH).append(prod_id);
//		return sb.toString();
//	}

	/**
	 * 获取系统参数key
	 */
	public static final String SYS_PARAMETER_KEY_PREFIX = "sys_parameter:parameter_value:";
	public static String getSysParamterKey(final String serviceName, final String dataKey){
		StringBuilder sb = new StringBuilder(serviceName).append(":").append(SYS_PARAMETER_KEY_PREFIX)
				.append(dataKey);
		return sb.toString();
	}

	/**
	 * 获取短信验证码key
	 */
	public static final String MESSAFGE_SWITHES_KEY_PREFIX = "message_swithes:permission:";
	public static String getMessageSwithesKey(final String serviceName, final String dataKey){
		StringBuilder sb = new StringBuilder(serviceName).append(":").append(MESSAFGE_SWITHES_KEY_PREFIX)
				.append(dataKey);
		return sb.toString();
	}

	public static String IP = "127.0.0.1";
	public static String HostName = "localhost";

	static{
		InetAddress addr=null;
		try{
			addr = InetAddress.getLocalHost();
			IP = addr.getHostAddress(); //获得机器IP　　
			HostName = addr.getHostName(); //获得机器名称
			System.out.println("主机名:" + HostName + ",IP地址:" + IP);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
