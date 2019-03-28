package com.sunyard.sunfintech.schedule.modulepublic;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.cache.annotation.CacheConfig;

import javax.annotation.Resource;


/**
 * Created by terry on 2017/8/21.
 */
@CacheConfig(cacheNames = "notifyService")
@org.springframework.stereotype.Service
public class NotifyService implements INotifyService {
    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Override
    public void addNotify(NotifyData notifyData) throws BusinessException {

        MQUtils.send(amqpTemplate, "ftdm.schedule.direct.exchange", "NotifyQueue", notifyData);
    }
}
