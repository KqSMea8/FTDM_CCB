package com.sunyard.sunfintech.thirdparty.provider;

import com.sunyard.sunfintech.account.model.bo.BatchPayQueryResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponse;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponseDetail;

import java.util.List;
import java.util.Map;

/**
 * 三方适配器服务接口
 * @author heroy
 * @version 2018/1/2
 */


/*
* @param params 参数
* params 在原有基础上需增加 host，url，mall_no 这3个参数
* @return 返回值:
* Map<String,Object>   keys:  recode(响应码)  remsg(响应报文)  status（状态码）  以及其他
 */
public interface IAdapterService {
    /* ========================第三方接口======================= */

    /**
     * //四要素鉴权申请【需要验证码】   鉴权暂时不写
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> apply4ElementsAuth(Map<String, Object> params);

    /**
     * //四要素鉴权确认【需要验证码】   鉴权暂时不写
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> confirm4ElementsAuth(Map<String, Object> params);

    /**
     * //四要素绑卡【需要验证码】
     *
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> apply4ElementsBindCard(Map<String, Object> params);

    /**
     * //四要素绑卡确认【需要验证码】
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> confirm4ElementsBindCard(Map<String, Object> params);

    /**
     * //三要素鉴权【信用卡】    鉴权暂时不写
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> threeElementsAuth(Map<String, Object> params);

    /**
     * //三要素绑卡【信用卡】
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> threeElementsBindCard(Map<String, Object> params);

    /**
     * //实名认证【二要素认证】
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> twoElementsBindCard(Map<String, Object> params);

    /**
     *  //快捷充值申请
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> applyQuickPay(Map<String, Object> params);

    /**
     *  //快捷充值确认
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> confirmyQuickPay(Map<String, Object> params);

    /**
     *  //网关充值
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> applyGatewayPay(Map<String, Object> params);

    /**
     * //代发【从平台出金】
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> payFromCompanyToUser(Map<String, Object> params);

    /**
     * //代扣【用户入金】
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> payFromUserToCompany(Map<String, Object> params);

    /**
     *  //批量提现【发往代付】
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> withdrawInBatch(Map<String, Object> params);

    /**
     *  //批量提现【查询处理中】
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> queryWithdraw(Map<String, Object> params);


    /* ========================行内核心接口======================= */

    /**
     *  //行内转账
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> transferOfAccountInBank(Map<String, Object> params);

    /**
     *  //对公平台账户查询
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> queryAccountOfCompany(Map<String, Object> params);

    /**
     * 账户往来明细查询
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> queryListOfCompanyTransfer(Map<String, Object> params);

    //平台充值   统一调用transferOfAccountInBank行内转账 接口

    //平台提现  统一调用transferOfAccountInBank行内转账 接口

    //投资人线下充值  统一调用transferOfAccountInBank行内转账 接口

    //投资人线下充值   统一调用transferOfAccountInBank行内转账 接口

    /**
     * 支付订单状态查询
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> queryPayStatus(Map<String, Object> params);

    /**
     * 解绑
     * @param params 参数
     * @return 返回值
     */
    public Map<String, Object> unbindCard(Map<String, Object> params);

    /**
     * getRandom_key
     * @param params
     * @return
     */
    public Map<String, Object> getRandomKey(Map<String, Object> params);

    /**
     * icis密码验证
     * @param params
     * @return
     */
    public Map<String, Object> checkPassowrd(Map<String, Object> params);

    /**
     * getRandom_key
     * @param params
     * @return
     */
    public Map<String, Object> setOrResetPassword(Map<String, Object> params);

    /**
     * icis绑卡
     */
    public Map<String, Object> isicBindCard(Map<String, Object> params);

    /**
     * icis解绑
     */
    public Map<String, Object> isicCardUnbing(Map<String, Object> params);

    /**
     * icis预留手机号变更
     */
    public Map<String, Object> isicPhoneChange(Map<String, Object> params);

    /**
     * icis客户信息变更
     */
    public Map<String, Object> isicCustomerChange(Map<String, Object> params);

    /**
     * icis客户冻结
     */
    public Map<String, Object> isicCustomerFreeze(Map<String, Object> params);

    /**
     * icis客户解冻
     */
    public Map<String, Object> isicCustomerUnfreeze(Map<String, Object> params);

    /**
     * icis客户销户
     */
    public Map<String, Object> isicCustomerCancel(Map<String, Object> params);

    //平台真实账户往来明细查询（带附言）
    public Map<String, Object> epayBankFourInOneQuery(Map<String,Object> params);

    /**
     * 获取支付对账文件
     * @return
     */
    String getAndSaveCheckFile(Map<String, Object> params, String fileName, String clearDate);

    /**
     * 退款
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String, Object> refundPay(Map<String, Object> params) throws BusinessException;

    /**
     * 电子账户开户
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String, Object> registerEaccount(Map<String,Object> params) throws BusinessException;

    /**
     * 电子账户开户(老接口)
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String, Object> registerEaccountOld(Map<String,Object> params) throws BusinessException;

    /**
     * 真是电子账户余额查询
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String, Object> queryRealEaccountBalance(Map<String,Object> params) throws BusinessException;

    /**
     * 预留手机号变更
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String, Object> modifyMobile(Map<String,Object> params) throws BusinessException;

    /**
     * 绑卡鉴权（短验绑卡，无短验绑卡，四要素绑卡）
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String,Object> bindAccountVerify(Map<String,Object> params) throws BusinessException;

    /**
     * 自动加值
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String,Object> autoAddValue(Map<String,Object> params) throws BusinessException;

    /**
     * 自动加值查询
     * @param params
     * @return
     * @throws BusinessException
     */
    Map<String,Object> queryAutoAddValue(Map<String,Object> params) throws BusinessException;

    /**
     * 代付订单查询
     */
    Map<String,Object> batchPayQueryTo(Map<String,Object> params)throws BusinessException;


    Map<String,Object>queryContractApp(Map<String,Object>params)throws BusinessException;

    Map<String,Object>queryContractAppTo(Map<String,Object>params)throws BusinessException;
    Map<String,Object> agrPayApply(Map<String, Object> sendParams);

    Map<String,Object> agrPayConfirm(Map<String, Object> sendParams);

    Map<String,Object> batchCollection(Map<String, Object> params);
}
