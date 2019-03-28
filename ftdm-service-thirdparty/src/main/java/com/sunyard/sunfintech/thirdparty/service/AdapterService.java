package com.sunyard.sunfintech.thirdparty.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 单方适配器
 *
 * @author heroy
 * @version 2018/1/2
 */
@Service(interfaceClass = IAdapterService.class)
@CacheConfig(cacheNames = "adapterService")
@org.springframework.stereotype.Service
public class AdapterService implements IAdapterService {

    private IAdapterService selector;

    @Value("${deploy.environment}")
    private String deployEnvironment;

    public static final String DEFAULT_BANK_SET = "DEV";

    public static final Map<String, String> BANK_PAYMEN_SET = new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        {
            put("DEV", "devPayService"); //开发环境，挡板环境
            put("TEST", "testPayService"); //测试环境
            put("BOB", "bobPayService"); //北京银行支付
            put("CCB", "ccbPayService"); //建行支付
            //...
        }
    };

    private void setStrategy(String bank) {
        String paymentChannel = BANK_PAYMEN_SET.get(deployEnvironment);
        if (null == paymentChannel || "".equals(paymentChannel.trim())) {
            throw new BusinessException("999999", "【三方模块】错误的银行通道配置");
        }
        //利用反射byName创建bean
        selector = SpringContextHolder.getBean(paymentChannel);
    }

    @Override
    public Map<String, Object> apply4ElementsAuth(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.apply4ElementsAuth(params);
    }

    @Override
    public Map<String, Object> confirm4ElementsAuth(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.confirm4ElementsAuth(params);
    }

    @Override
    public Map<String, Object> apply4ElementsBindCard(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.apply4ElementsBindCard(params);
    }

    @Override
    public Map<String, Object> confirm4ElementsBindCard(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.confirm4ElementsBindCard(params);
    }

    @Override
    public Map<String, Object> threeElementsAuth(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.threeElementsAuth(params);
    }

    @Override
    public Map<String, Object> threeElementsBindCard(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.threeElementsBindCard(params);
    }

    @Override
    public Map<String, Object> twoElementsBindCard(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.twoElementsBindCard(params);
    }

    @Override
    public Map<String, Object> applyQuickPay(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.applyQuickPay(params);
    }

    @Override
    public Map<String, Object> confirmyQuickPay(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.confirmyQuickPay(params);
    }

    @Override
    public Map<String, Object> applyGatewayPay(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.applyGatewayPay(params);
    }

    @Override
    public Map<String, Object> payFromCompanyToUser(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.payFromCompanyToUser(params);
    }

    @Override
    public Map<String, Object> payFromUserToCompany(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.payFromUserToCompany(params);
    }

    @Override
    public Map<String, Object> withdrawInBatch(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.withdrawInBatch(params);
    }

    @Override
    public Map<String, Object> queryWithdraw(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryWithdraw(params);
    }

    @Override
    public Map<String, Object> transferOfAccountInBank(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.transferOfAccountInBank(params);
    }

    @Override
    public Map<String, Object> queryAccountOfCompany(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryAccountOfCompany(params);
    }

    @Override
    public Map<String, Object> queryListOfCompanyTransfer(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryListOfCompanyTransfer(params);
    }

    @Override
    public Map<String, Object> queryPayStatus(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryPayStatus(params);
    }

    @Override
    public Map<String, Object> unbindCard(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.unbindCard(params);
    }

    @Override
    public Map<String, Object> getRandomKey(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.getRandomKey(params);
    }

    @Override
    public Map<String, Object> checkPassowrd(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.checkPassowrd(params);
    }

    @Override
    public Map<String, Object> setOrResetPassword(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.setOrResetPassword(params);
    }

    @Override
    public Map<String, Object> isicBindCard(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.isicBindCard(params);
    }

    @Override
    public Map<String, Object> isicCardUnbing(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.isicCardUnbing(params);
    }

    @Override
    public Map<String, Object> isicPhoneChange(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.isicPhoneChange(params);
    }

    @Override
    public Map<String, Object> isicCustomerChange(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.isicCustomerChange(params);
    }

    @Override
    public Map<String, Object> isicCustomerFreeze(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.isicCustomerFreeze(params);
    }

    @Override
    public Map<String, Object> isicCustomerUnfreeze(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.isicCustomerUnfreeze(params);
    }

    @Override
    public Map<String, Object> isicCustomerCancel(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.isicCustomerCancel(params);
    }

    @Override
    public Map<String, Object> epayBankFourInOneQuery(Map<String,Object> params){
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.epayBankFourInOneQuery(params);
    }

    @Override
    public String getAndSaveCheckFile(Map<String, Object> params, String fileName, String clearDate) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.getAndSaveCheckFile(params, fileName, clearDate);
    }

    @Override
    public Map<String, Object> refundPay(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.refundPay(params);
    }

    @Override
    public Map<String, Object> registerEaccount(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.registerEaccount(params);
    }

    @Override
    public Map<String, Object> registerEaccountOld(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.registerEaccountOld(params);
    }

    @Override
    public Map<String, Object> queryRealEaccountBalance(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryRealEaccountBalance(params);
    }

    @Override
    public Map<String, Object> modifyMobile(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.modifyMobile(params);
    }

    @Override
    public Map<String, Object> bindAccountVerify(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.bindAccountVerify(params);
    }

    @Override
    public Map<String, Object> autoAddValue(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.autoAddValue(params);
    }

    @Override
    public Map<String, Object> queryAutoAddValue(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryAutoAddValue(params);
    }

    @Override
    public Map<String, Object> batchPayQueryTo(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.batchPayQueryTo(params);
    }

    @Override
    public Map<String, Object> queryContractApp(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryContractApp(params);
    }

    @Override
    public Map<String, Object> queryContractAppTo(Map<String, Object> params) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.queryContractAppTo(params);
    }

    /*@Override
    public Map<String, Object> queryContractApp(Map<String, Object> params, String url) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.batchPayQueryTo(params,null,url);
    }

    @Override
    public Map<String, Object> queryContractAppTo(Map<String, Object> params, String url) throws BusinessException {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.batchPayQueryTo(params,null,url);
    }*/

    @Override
    public Map<String, Object> agrPayApply(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.agrPayApply(params);
    }

    @Override
    public Map<String, Object> agrPayConfirm(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.agrPayConfirm(params);
    }

    @Override
    public Map<String, Object> batchCollection(Map<String, Object> params) {
        this.setStrategy(MapUtils.getString(params, "bank_payment_set", DEFAULT_BANK_SET));
        return selector.batchCollection(params);
    }
}
