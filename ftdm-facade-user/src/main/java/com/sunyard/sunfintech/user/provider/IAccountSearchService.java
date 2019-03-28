package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.user.model.bo.*;

import java.util.List;

/**
 * Created by Zz on 2017/5/24.
 */
public interface IAccountSearchService {

    /**
     *  pzy
     *  电子账户查询
     */
    public EaccountInfoResponse queryEaccountInfo(EaccountInfoRequest eaccountInfoRequest)throws BusinessException;

    /**
     *  pzy
     *  电子账户余额查询
     */
    public EaccountBalanceResponse queryEaccountInfoBalance(EaccountBalanceRequest eaccountBalanceRequest)throws BusinessException;

	/**
	 * 查询电子账户信息
	 * @author Lid
	 * @param realEaccountRequest
	 * @return RealEaccountResponse
	 * @throws BusinessException
	 */
	public RealEaccountResponse queryEaccount(RealEaccountRequest realEaccountRequest) throws BusinessException;

	//根据平台号查询集团号   映射表
	public String queryMallNoByPlatNo(String plat_no)throws BusinessException;

	//根据平台号查询集团号   映射表 不走缓存
	public String queryNoCacheMallNoByPlatNo(String plat_no)throws BusinessException;

	//根据集团号查询平台号   映射表
	public List<String> queryPlatNoByMallNo(String mall_no)throws BusinessException;

	//根据集团号查询平台号   映射表 不走缓存
	public List<String> queryNoCachePlatNoByMallNo(String mall_no)throws BusinessException;

	public AccountInfoResponse queryAccountInfo(EaccountInfoRequest eaccountInfoRequest) throws BusinessException;

	//查询用户充值信息
	public RwRecharge queryRwRecharge(String plat_no, String order_no) throws BusinessException;

	//检查用户是否绑卡
	public EaccUserinfo checkUserinfo(ApplyQuickPayRequest applyQuickPayRequest) throws BusinessException;

	//查询平台客户绑卡信息
	public EaccCardinfo queryEaccCardInfoByEaccAccountInfo(String mall_no, String plat_no, String platcust, String card_no,String pay_channel)throws BusinessException;

	//查询用户信息
	public EaccUserinfo queryEaccUserInfoByEaccAccountInfo(String mall_no, String plat_no, String platcust)throws BusinessException;

	//查询支付平台映射信息
	public PlatPaycode queryPlatPayCode(String plat_no, String pay_code) throws BusinessException;

	//查询用户绑卡信息
	public List<EaccCardinfo> queryEaccCardInfo(String mall_no, String platcust, String card_no, Object status)throws BusinessException;

	//查询用户绑卡信息
	public List<EaccCardinfo> queryEaccCardInfo(String mall_no, String platcust, String card_no, String mobile, Object status)throws BusinessException;

	//根据流水号查询用户充值信息
	public RwRecharge queryRwRechargeBySerial(String Trans_serial, String Host_req_serial_no, String Hsepay_order_no);

	//查询用户账户平台映射关系
	public List<EaccAccountinfo> queryEaccAccountInfo(String mallcust,String mall_no,String plat_no)throws BusinessException;

	/**
	 * 查询授权信息
	 * @param platcust
	 * @param plat_no
	 * @param mall_no
	 * @return
	 * @throws BusinessException
	 */
	public List<EaccUserauth> queryEaccUserAuth(String platcust,String plat_no,String mall_no,String... status)throws BusinessException;

    EaccUserinfo checkUserinfoWithoutMobile(CollectionRequest collectionRequest) throws BusinessException;

	/**
	 * 真实电子账号余额查询
	 * @param realEaccountBalance
	 * @return RealEaccountBalanceResponseDetail
	 * @throws BusinessException
	 */
    public RealEaccountBalanceResponseDetail queryRealEaccountBalance(RealEaccountBalance realEaccountBalance) throws BusinessException;

	EaccUserinfo checkUserinfoWithoutMobile(CollectionDetail data_detail_item);
}
