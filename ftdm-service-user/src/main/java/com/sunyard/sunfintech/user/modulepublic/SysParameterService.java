package com.sunyard.sunfintech.user.modulepublic;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.LocalCacheUtil;
import com.sunyard.sunfintech.dao.entity.SysParameter;
import com.sunyard.sunfintech.dao.entity.SysParameterExample;
import com.sunyard.sunfintech.dao.mapper.SysParameterMapper;
import com.sunyard.sunfintech.user.provider.IUserSysParameterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author heroy
 * @version 2017/5/25
 */
@CacheConfig(cacheNames="sysParameterService")
@Service("sysParameterService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = IUserSysParameterService.class)
public class SysParameterService extends BaseServiceSimple implements IUserSysParameterService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SysParameterMapper sysParameterMapper;

    /**
     * 获取系统参数
     * @param sysParameter 参数
     * @return 系统参数值
     */
    @Override
    public String querySysParameter(SysParameter sysParameter){
        validate(sysParameter);
        logger.info("【获取系统参数】从本地内存获取系统参数: " + sysParameter);
        String dataKey = new StringBuilder(sysParameter.getMall_no()).append(Constants.DATA_SPLIT_WITH).append(sysParameter.getMer_no())
                .append(Constants.DATA_SPLIT_WITH).append(sysParameter.getParameter_key()).toString();
        String redisKey = Constants.getSysParamterKey(serviceName, dataKey);
        logger.debug("【获取系统参数】redisKey:" + redisKey);

        Object localValue = LocalCacheUtil.getCache(redisKey);
        if(null != localValue){
            logger.info("【获取系统参数】系统参数值:" + localValue.toString());
            return localValue.toString();
        }
        logger.info("【获取系统参数】开始从reids获取系统参数");
        if(null == CacheUtil.getCache().get(redisKey)){
            logger.info("reids中没有key所对应的系统参数");
            SysParameterExample sysParameterExample = new SysParameterExample();
            SysParameterExample.Criteria criteria = sysParameterExample.createCriteria();
            criteria.andMall_noEqualTo(sysParameter.getMall_no());
            criteria.andMer_noEqualTo(sysParameter.getMer_no());
            criteria.andParameter_keyEqualTo(sysParameter.getParameter_key());
            List<SysParameter> sysParameterList = sysParameterMapper.selectByExample(sysParameterExample);
            if(sysParameterList.size() == 1 && null != sysParameterList.get(0)){
                logger.info("把"+redisKey+":"+sysParameterList.get(0).getParameter_value()+"系统参数设置到reids中");
                //重新设置参数到缓存中
                CacheUtil.getCache().set(redisKey, sysParameterList.get(0).getParameter_value());
                //设置本地参数
                LocalCacheUtil.setCache(redisKey, sysParameterList.get(0).getParameter_value());
                return sysParameterList.get(0).getParameter_value();
            }else{
                logger.info("【获取系统参数】获取不到正确的系统参数: " + sysParameter);
                return "";
            }
        }

        LocalCacheUtil.setCache(redisKey, CacheUtil.getCache().get(redisKey).toString());
        logger.info("【获取系统参数】系统参数值:" + LocalCacheUtil.getCache(redisKey).toString());
        return LocalCacheUtil.getCache(redisKey).toString();
    }

    @Override
    public SysParameter getSysParameter(String mall_no, String mer_no, String key) throws BusinessException {
        if(StringUtils.isBlank(mall_no) || StringUtils.isBlank(mer_no) || StringUtils.isBlank(key)){
            return null;
        }
        logger.info("【获取系统参数】key: " + mall_no + Constants.DATA_SPLIT_WITH  + mer_no + Constants.DATA_SPLIT_WITH + key);
        SysParameterExample sysParameterExample = new SysParameterExample();
        SysParameterExample.Criteria criteria = sysParameterExample.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMer_noEqualTo(mer_no);
        criteria.andParameter_keyEqualTo(key);
        List<SysParameter> sysParameterList = sysParameterMapper.selectByExample(sysParameterExample);
        if(sysParameterList.size() == 1 && null != sysParameterList.get(0)){
            return sysParameterList.get(0);
        }else{
            logger.info("【获取系统参数】获取不到正确的系统参数" );
            return null;
        }
    }

    /**
     * 获取系统参数
     * @param mall_no 集团号
     * @param mer_no 平台号
     * @param key 数据库中Parameter_key
     * @return 参数值
     */
    @Override
    public String querySysParameter(String mall_no, String mer_no, String key){
        if(StringUtils.isBlank(mall_no) || StringUtils.isBlank(mer_no) || StringUtils.isBlank(key)){
            return "";
        }
        SysParameter sysParameter = new SysParameter();
        sysParameter.setMall_no(mall_no);
        sysParameter.setMer_no(mer_no);
        sysParameter.setParameter_key(key);
        return this.querySysParameter(sysParameter);
    }

    /**
     * 获取系统参数
     * @param mall_no 集团号
     * @param key 数据库中Parameter_key
     * @return
     */
    @Override
    public String querySysParameter(String mall_no, String key){
        return this.querySysParameter(mall_no, mall_no, key);
    }

    @Transactional
    @Override
    public boolean updateSysParameter(SysParameter sysParameter){
        validate(sysParameter);
        logger.info("【更新系统参数】sysParameter：" + sysParameter);
        String dataKey = new StringBuilder(sysParameter.getMall_no()).append(Constants.DATA_SPLIT_WITH).append(sysParameter.getMer_no())
                .append(Constants.DATA_SPLIT_WITH).append(sysParameter.getParameter_key()).toString();
        String redisKey = Constants.getSysParamterKey(serviceName, dataKey);
        logger.info("【更新系统参数】redisKey：" + redisKey);

        SysParameterExample sysParameterExample = new SysParameterExample();
        SysParameterExample.Criteria criteria = sysParameterExample.createCriteria();
        criteria.andMall_noEqualTo(sysParameter.getMall_no());
        criteria.andMer_noEqualTo(sysParameter.getMer_no());
        criteria.andParameter_keyEqualTo(sysParameter.getParameter_key());
        sysParameterMapper.updateByExample(sysParameter, sysParameterExample);

        CacheUtil.getCache().set(redisKey, sysParameter.getParameter_value());
        LocalCacheUtil.setCache(redisKey, sysParameter.getParameter_value());
        return true;
    }

    @Override
    public boolean insertSysParameter(SysParameter sysParameter) throws BusinessException {
        validate(sysParameter);
        //组装系统参数rediskey
        String dataKey = new StringBuilder(sysParameter.getMall_no()).append(Constants.DATA_SPLIT_WITH).append(sysParameter.getMer_no())
                .append(Constants.DATA_SPLIT_WITH).append(sysParameter.getParameter_key()).toString();
        String redisKey = Constants.getSysParamterKey(serviceName, dataKey);
        logger.info("【更新系统参数】redisKey：" + redisKey);
        //插入数据库
        sysParameterMapper.insert(sysParameter);
        //插入缓存
        CacheUtil.getCache().set(redisKey, sysParameter.getParameter_value());
        LocalCacheUtil.setCache(redisKey, sysParameter.getParameter_value());
        return true;
    }

    @Override
    public boolean attestation(String plain, String sData) throws BusinessException {
        return false;
    }

    @Override
    public List<SysParameter> queryAllUserSysParameters() {
        return null;
    }
}
