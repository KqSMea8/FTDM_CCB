package com.sunyard.sunfintech.web.mq;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.dic.ServiceName;
import com.sunyard.sunfintech.core.util.KeyValueUtil;
import com.sunyard.sunfintech.core.util.LocalCacheUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;


/**
 * Created by terry on 2017/8/17.
 */
public class SysParamMQConsumer implements MessageListener {
    protected Logger logger = LogManager.getLogger(getClass());


    @Autowired
    RedisTemplate redisTemplate;

    RedisSerializer serializer;

    @Override
    public void onMessage(Message message, byte[] pattern){
        try {
            serializer = redisTemplate.getValueSerializer();
            KeyValueUtil keyValueUtil = (KeyValueUtil) serializer.deserialize(message.getBody());
            logger.info("【修改系统参数】" + keyValueUtil.toString());
            String localKey = Constants.getSysParamterKey(ServiceName.USER.getName(), keyValueUtil.getKey());
            Object localValue = LocalCacheUtil.getCache(localKey);
            if(null == localValue || StringUtils.isBlank(localValue.toString())){
                logger.info("【修改系统参数】:失败，不存在相同key的系统参数");
            }
            LocalCacheUtil.setCache(localKey, keyValueUtil.getValue());
        } catch (Exception e) {
            logger.info("【修改系统参数】:失败" + e.getMessage());
        }
    }

}
