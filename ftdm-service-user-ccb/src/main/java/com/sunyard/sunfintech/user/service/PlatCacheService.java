package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.dic.PayStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.user.provider.IPlatCacheService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lid on 2017/6/22.
 */
@Service(interfaceClass = IPlatCacheService.class)
@CacheConfig(cacheNames="platCacheService")
@org.springframework.stereotype.Service("platCacheService")
public class PlatCacheService extends BaseServiceSimple implements IPlatCacheService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final String REDISKEY_PLAT_CARDINFO = Constants.CACHE_NAMESPACE + "plat_cardinfo:";

    @Autowired
    private PlatCardinfoMapper platCardinfoMapper;

    @Autowired
    private PlatPlatinfoMapper platPlatinfoMapper;

    @Autowired
    private PlatPaycodeMapper platPaycodeMapper;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    /**
     * 平台卡信息
     * @param plat_no
     * @param card_type
     * @return value
     */
    @Override
    public String queryCardInfoToCache(String plat_no,String card_type){
        String key = REDISKEY_PLAT_CARDINFO + plat_no + "-" + card_type;
        logger.info("【平台卡信息】-->开始查缓存数据");
        //判断缓存中存在该key对应的数据
        if(null == CacheUtil.getCache().get(key)){
            logger.info("【平台卡信息】-->reids中没有key所对应的系统参数，重新set");
            PlatCardinfoExample examplepl = new PlatCardinfoExample();
            PlatCardinfoExample.Criteria criteriapl = examplepl.createCriteria();
            criteriapl.andPlat_noEqualTo(plat_no);
            criteriapl.andCard_typeEqualTo(card_type);
            criteriapl.andEnabledEqualTo(Constants.ENABLED);
            List<PlatCardinfo> platCardinfoList = platCardinfoMapper.selectByExample(examplepl);//获得卡号、户名
            if(platCardinfoList!=null || platCardinfoList.size()==1){
                logger.info("【平台卡信息】-->查询出platCardinfo信息");
                Map<String,Object> value = new HashMap<String,Object>();
                value.put("pay_no",platCardinfoList.get(0).getPay_no());
                value.put("card_no",platCardinfoList.get(0).getCard_no());
                value.put("card_name",platCardinfoList.get(0).getCard_name());
                String json = JSON.toJSONString(value);
                logger.info("把"+key+":"+json+"系统参数设置到reids中");
                //重新设置参数到缓存中
                CacheUtil.getCache().set(key, json);
                return json;
            }else{
                logger.info("【平台卡信息】-->数据库中没有该数据");
                throw new BusinessException(BusinessMsg.FAIL, "数据库中没有该数据");
            }
        }
        return CacheUtil.getCache().get(key).toString();
    }

    /**
     * 查询所有的平台信息
     * @return
     */
    @Override
    public List<PlatPlatinfo> queryAllPlatInfo() {
        List<PlatPlatinfo> platPlatinfoList;
        try{
            PlatPlatinfoExample example = new PlatPlatinfoExample();
            platPlatinfoList = platPlatinfoMapper.selectByExample(example);
        }catch (Exception e){
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return platPlatinfoList;
    }

    /**
     * 根据平台号查询所有交易code
     * @param plat_no
     * @return
     */
    @Override
    public List<PlatPaycode> queryTransactionCode(String plat_no) {
        List<PlatPaycode> platPaycodeList;
        try {
            PlatPaycodeExample example = new PlatPaycodeExample();
            PlatPaycodeExample.Criteria criteria = example.createCriteria();
            criteria.andIs_transactionEqualTo("1");//交易渠道
            criteria.andPlat_noEqualTo(plat_no);
            platPaycodeList = platPaycodeMapper.selectByExample(example);
        }catch (Exception e){
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return platPaycodeList;
    }

    @Override
    public List<RwRecharge> queryPlatRecharge(String plat_no, String paymentDate) {
        List<RwRecharge> rwRechargeList;
        try {
            logger.info("【查询平台充值记录】");
            RwRechargeExample example = new RwRechargeExample();
            RwRechargeExample.Criteria criteria = example.createCriteria();
            criteria.andPayment_dateEqualTo(paymentDate);
            criteria.andPlat_noEqualTo(plat_no);
            criteria.andTrans_codeEqualTo(TransConsts.PLAT_CHARGE_CODE);
            criteria.andStatusEqualTo(OrderStatus.SUCCESS.getCode());
            rwRechargeList = rwRechargeMapper.selectByExample(example);
        }catch (Exception e){
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return rwRechargeList;
    }

    @Override
    public List<RwWithdraw> queryPlatWithdraw(String plat_no, String paymentDate) {
        List<RwWithdraw> withdrawList;
        try {
            logger.info("【查询平台提现记录】");
            RwWithdrawExample example = new RwWithdrawExample();
            RwWithdrawExample.Criteria criteria = example.createCriteria();
            criteria.andPayment_dateEqualTo(paymentDate);
            criteria.andPlat_noEqualTo(plat_no);
            criteria.andTrans_codeEqualTo(TransConsts.PLAT_WITHDRAW_CODE);
            criteria.andPay_statusEqualTo(PayStatus.SUCCESS.getCode());
            withdrawList = rwWithdrawMapper.selectByExample(example);
        }catch (Exception e){
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return withdrawList;
    }

}
