package com.sunyard.sunfintech.web.business;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.dic.ServiceName;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 获取系统参数工具类
 * @author heroy
 * @version 2018/1/2
 */
@Service("sysBusiness")
public class SysBusiness {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private ISysParameterService sysParameterService;

    public void modifySysParams(final KeyValueUtil keyValueUtil) {
        logger.info("【往SysParamsSetTopic发消息】" + keyValueUtil);
        redisTemplate.convertAndSend("sys_parameter",keyValueUtil);
    }

    /**
     * 获取全部模块的系统参数
     * @return 系统参数列表
     */
    public List<Map<String, Object>> getSysParams(){
        return null;
    }

    /**
     * 修改系统参数，先修改数据库
     * @param param
     */
    public void modifySysParams (Map<String, Object> param){
        if(null == param) return;
        KeyValueUtil keyValueUtil =  new KeyValueUtil();
        StringBuilder keySb = new StringBuilder();
        keySb.append(MapUtils.getString(param, "mall_no", "mall_no")).append(Constants.DATA_SPLIT_WITH )
                .append(MapUtils.getString(param, "mer_no", "mer_no")).append(Constants.DATA_SPLIT_WITH )
                .append(MapUtils.getString(param, "parameter_key", "parameter_key"));
        keyValueUtil.setKey(keySb.toString());
        keyValueUtil.setValue(MapUtils.getString(param, "parameter_value"));
        this.modifySysParams(keyValueUtil);
    }

    /**
     * 获取系统参数
     * @param mall_no 集团号
     * @param mer_no 平台号
     * @param key KEY
     * @return 参数内容，如果不存在返回空字符
     */
    public String querySysParameter(String mall_no, String mer_no, String key){
        return this.querySysParameter(ServiceName.USER.getName(),mall_no, mer_no, key);
    }

    /**
     * 获取集团参数或FTDM参数
     * @param mall_no
     * @param key
     * @return
     */
    public String querySysParameter(String mall_no, String key){
        return this.querySysParameter(mall_no, mall_no, key);
    }

    /**
     * 获取系统参数
     * @param serviceName 服务名称
     * @param mall_no 集团号
     * @param mer_no 平台号
     * @param key 参数数据库中的KEY
     * @return 参数值
     */
    public String querySysParameter(String serviceName, String mall_no, String mer_no, String key){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(mall_no))
            return "";
        String redisKey = Constants.getSysParamterKey(serviceName,
                mall_no + Constants.DATA_SPLIT_WITH + mer_no + Constants.DATA_SPLIT_WITH + key);
        String value = LocalCacheUtil.getCache(redisKey) == null?"":LocalCacheUtil.getCache(redisKey).toString();
        logger.info("【获取系统参数】本地值：" + value);
        if(StringUtils.isBlank(value)){

            Object redisObject = CacheUtil.getCache().get(redisKey);
            String redisValue = redisObject == null?"":redisObject.toString();
            logger.info("【获取系统参数】redis值：" + redisValue);
            if(StringUtils.isBlank(redisValue)){
                //TODO 去远程获取系统参数
                try {
                    value = sysParameterService.querySysParameter(mall_no, mer_no, key);
                    logger.info("【获取系统参数】远程值：" + value);
                    LocalCacheUtil.setCache(redisKey, value);
                    return value;
                }catch (Exception e){
                    logger.error("【获取系统参数失败】：" + e.getMessage());
                }
                return "";
            }else{
                LocalCacheUtil.setCache(redisKey, redisValue);
                value = (String) LocalCacheUtil.getCache(redisKey);
            }
        }
        return value;
    }
}
