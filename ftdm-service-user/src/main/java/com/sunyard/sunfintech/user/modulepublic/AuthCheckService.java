package com.sunyard.sunfintech.user.modulepublic;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AuthStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.EaccUserauth;
import com.sunyard.sunfintech.dao.entity.EaccUserauthExample;
import com.sunyard.sunfintech.dao.mapper.EaccUserauthMapper;
import com.sunyard.sunfintech.pub.provider.IAuthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by terry on 2018/2/14.
 */
@CacheConfig(cacheNames = "authCheckService")
@Service
public class AuthCheckService extends BaseServiceSimple implements IAuthCheckService {

    @Autowired
    private EaccUserauthMapper eaccUserauthMapper;

    @Override
    public boolean checkAuth(String plat_no, String mall_no, String platcust, String authType, BigDecimal authAmt) throws BusinessException {
        if(StringUtils.isBlank(plat_no) || StringUtils.isBlank(mall_no) || StringUtils.isBlank(platcust) || StringUtils.isBlank(authType)){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"|检查授权信息参数不全，请程序员检查代码");
        }
        String cacheKey=Constants.CHECK_AUTH_CACHE_KEY+mall_no+plat_no+platcust+authType;
        Object cacheObj=CacheUtil.getCache().get(cacheKey);
        if(cacheObj!=null){
            if(cacheObj instanceof EaccUserauth){
                EaccUserauth cacheUserAuth=(EaccUserauth)cacheObj;
                return checkAuth(cacheUserAuth,authAmt);
            }else{
                CacheUtil.getCache().del(cacheKey);
            }
        }
        EaccUserauthExample example=new EaccUserauthExample();
        EaccUserauthExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andMall_noEqualTo(mall_no);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(AuthStatus.ALREADY_AUTH.getCode());
        criteria.andAuthed_typeEqualTo(authType);
        List<EaccUserauth> eaccUserauthList=eaccUserauthMapper.selectByExample(example);
        if(eaccUserauthList.size()==1){
            //为了防止频繁查库，设置一小时缓存
            EaccUserauth eaccUserauth=eaccUserauthList.get(0);
            CacheUtil.getCache().set(cacheKey,JSON.toJSONString(eaccUserauth),3600);
            return checkAuth(eaccUserauth,authAmt);
        }else if(eaccUserauthList.size()>1){
            throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION,
                    String.format(BusinessMsg.RUNTIME_EXCEPTION+"|授权数据异常|platcust:%s|auth_type:%s",platcust,authType));
        }
        return false;
    }

    @Override
    public List<EaccUserauth> queryAuthInfo(String mall_no, String mer_no, String platcust) {
        List<EaccUserauth> authInfos =null;
        if(StringUtils.isBlank(platcust) || StringUtils.isBlank(mer_no)||StringUtils.isBlank(mall_no)){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"|查询授权信息参数不全，请程序员检查代码");
        }
        String cacheKey="authInfo:"+mer_no+platcust;
        Object cacheObj=CacheUtil.getCache().get(cacheKey);
        if(cacheObj!=null){
            authInfos = JSON.parseArray(cacheObj.toString(),EaccUserauth.class);
            return authInfos;
        }
        EaccUserauthExample example=new EaccUserauthExample();
        EaccUserauthExample.Criteria criteria=example.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andPlat_noEqualTo(mer_no);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andAuthed_statusEqualTo(AuthStatus.ALREADY_AUTH.getCode());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        example.setOrderByClause("authed_type");
        List<EaccUserauth> eaccUserauthList=eaccUserauthMapper.selectByExample(example);
        if(eaccUserauthList.size()>0){
            //为了防止频繁查库，设置一小时缓存
            CacheUtil.getCache().set(cacheKey, JSON.toJSONString(eaccUserauthList),3600);
        }
        return eaccUserauthList;
    }

    @Override
    public List<EaccUserauth> queryAuthInfoAllStatus(String mall_no, String mer_no, String platcust) {
        if(StringUtils.isBlank(platcust) || StringUtils.isBlank(mer_no)||StringUtils.isBlank(mall_no)){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"|查询授权信息参数不全，请程序员检查代码");
        }
        EaccUserauthExample example=new EaccUserauthExample();
        EaccUserauthExample.Criteria criteria=example.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andPlat_noEqualTo(mer_no);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        example.setOrderByClause("authed_type");
        List<EaccUserauth> eaccUserauthList=eaccUserauthMapper.selectByExample(example);
        return eaccUserauthList;
    }

    private boolean checkAuth(EaccUserauth eaccUserauth, BigDecimal authAmt){
        logger.info("授权金额：{}|授权期限：{}|交易金额：{}|当前日期：{}",
                eaccUserauth.getAuthed_amount(),eaccUserauth.getAuthed_limittime(),authAmt,DateUtils.getyyyyMMddDate());
        if(eaccUserauth.getAuthed_amount().compareTo(authAmt)<0){
            logger.info("交易金额过大|授权金额：{}|交易金额：{}",eaccUserauth.getAuthed_amount(),authAmt);
            return false;
        }
        if(eaccUserauth.getAuthed_limittime().compareTo(DateUtils.getyyyyMMddDate())<0){
            logger.info("授权信息已过期|授权期限：{}|当前日期：{}",eaccUserauth.getAuthed_limittime(),DateUtils.getyyyyMMddDate());
            return false;
        }
        return true;
    }

    public static void main(String args){

    }
}
