package com.sunyard.sunfintech.core.base;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.support.validator.BeanValidators;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Map;

/**
 * 业务逻辑层基类<br/>
 * 继承基类后必须配置CacheConfig(cacheNames=""), 用于缓存记录
 * @author heroy
 * @version 20170401
 */
public abstract class BaseServiceSimple implements ApplicationContextAware {

	@Value("${service.name}")
	protected String serviceName;

	@Value("${deploy.environment}")
    protected String deployEnvironment;

	protected Logger logger = LogManager.getLogger(getClass());

	protected ApplicationContext applicationContext;

	@Autowired
	private Validator validator;

    protected void sleep(int millis) {
        try {
            Thread.sleep(RandomUtils.nextLong(10, millis));
        } catch (InterruptedException e) {
            logger.error("【线程等待失败】：", e);
        }
    }

	/**
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：抛出异常
	 */
	protected boolean validate(Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			logger.debug(ex.getMessage());
			Map<String, String> map = BeanValidators.extractPropertyAndMessage(ex);
			throw new BusinessException(BusinessMsg.PARAMETER_LACK, JSON.toJSONString(map));
		}
		return true;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/** 获取缓存键值 */
	protected String getCacheKey(Object id) {
		String cacheName = getCacheKey();
		return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":").append(id).toString();
	}

	/** 获取缓存键值 */
	protected String getLockKey(Object id) {
		String cacheName = getCacheKey();
		return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":LOCK:").append(id).toString();
	}

	/**
	 * 获取缓存键
	 * @return 键KEY
	 */
	private String getCacheKey() {
		Class<?> cls = getClass();
		String cacheName = Constants.CACHEKEYMAP.get(cls);
		if (StringUtils.isBlank(cacheName)) {
			CacheConfig cacheConfig = cls.getAnnotation(CacheConfig.class);
			if (cacheConfig == null || cacheConfig.cacheNames() == null || cacheConfig.cacheNames().length < 1) {
				cacheName = getClass().getName();
			} else {
				cacheName = cacheConfig.cacheNames()[0];
			}
			Constants.CACHEKEYMAP.put(cls, cacheName);
		}
		return cacheName;
	}

    /**
     * 消息发送
     * @param jmsTemplate 消息模板类
     * @param msgName 消息名称
     * @param object 消息体
     */
//    public void sendMQ(final JmsTemplate jmsTemplate, final String msgName, final Object object){
//        try {
//            jmsTemplate.send(msgName, new MessageCreator() {
//                @Override
//                public Message createMessage(Session session) throws JMSException {
//                    Message message = jmsTemplate.getMessageConverter().toMessage(object, session);
//                    return message;
//                }
//            });
//        }catch (Exception e){
//            logger.error("【消息发送失败】：" + e.getMessage());
//        }
//    }
}
