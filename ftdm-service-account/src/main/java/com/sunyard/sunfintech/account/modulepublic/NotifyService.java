package com.sunyard.sunfintech.account.modulepublic;

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
@CacheConfig(cacheNames="notifyService")
@org.springframework.stereotype.Service
public class NotifyService implements INotifyService {
    protected Logger logger = LogManager.getLogger(getClass());

//    @Resource(name = "jmsQueueTemplate")
//    private JmsTemplate jmsQueueTemplate;
    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;
    @Override
    public void addNotify(NotifyData notifyData) throws BusinessException {
        if(notifyData!=null){
            logger.info("【异步通知队列】通知内容："+ notifyData.toString());
            try {
                MQUtils.send(amqpTemplate, "ftdm.account.direct.exchange", "NotifyQueue", notifyData);
            }catch (Exception e){
                logger.info("【异步通知】发送失败：" + e.getMessage());
            }
           /* String destination="NotifyQueue";
            jmsQueueTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    logger.info("【异步通知队列】============发送消息");
                    Message message=jmsQueueTemplate.getMessageConverter().toMessage(notifyData, session);
                    logger.info("【异步通知队列】============消息发送成功！");
                    return message;
                }
            });*/
        }
    }
}
