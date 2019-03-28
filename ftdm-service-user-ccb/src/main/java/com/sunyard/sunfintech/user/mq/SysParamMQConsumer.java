package com.sunyard.sunfintech.user.mq;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.util.KeyValueUtil;
import com.sunyard.sunfintech.core.util.LocalCacheUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.SysParameter;
import com.sunyard.sunfintech.user.modulepublic.SysParameterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Created by terry on 2017/8/17.
 */
public class SysParamMQConsumer implements org.springframework.data.redis.connection.MessageListener {
    protected Logger logger = LogManager.getLogger(getClass());

    @Value("${service.name}")
    protected String serviceName;

    @Autowired
    private SysParameterService sysParameterService;

    @Autowired
    RedisTemplate redisTemplate;

    RedisSerializer serializer;

    @Override
    public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern){
        try {
            serializer = redisTemplate.getValueSerializer();
            KeyValueUtil keyValueUtil = (KeyValueUtil) serializer.deserialize(message.getBody());
            logger.info("【修改系统参数】" + keyValueUtil.toString());
            String localKey = Constants.getSysParamterKey(this.serviceName, keyValueUtil.getKey());
            Object localValue = LocalCacheUtil.getCache(localKey);
            if(null == localValue || StringUtils.isBlank(localValue.toString())){
                logger.info("【修改系统参数】:失败，不存在相同key的系统参数");
            }
            if(!"mall_no|mer_no|parameter_key".equals(keyValueUtil.getValue())){
                String[] keys =  keyValueUtil.getKey().split("\\|");
                if(keys.length == 3){
                    SysParameter sysParameter = sysParameterService.getSysParameter(keys[0], keys[1], keys[2]);
                    //不判断数据库中是否相等
//                    if(null != sysParameter && (!((String)keyValueUtil.getValue()).equals(sysParameter.getParameter_value()))){
                    logger.info("【修改数据库中系统参数】" + keyValueUtil.getValue());
                    sysParameter.setParameter_value((String)keyValueUtil.getValue());
                    sysParameterService.updateSysParameter(sysParameter);
//                    }
                }
            }
        } catch (Exception e) {
            logger.info("【修改系统参数】:失败" + e.getMessage());
        }
    }
}
