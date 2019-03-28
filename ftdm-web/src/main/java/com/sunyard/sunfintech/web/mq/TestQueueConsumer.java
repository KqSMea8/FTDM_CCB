package com.sunyard.sunfintech.web.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;


/**
 * Created by terry on 2017/8/17.
 */
public class TestQueueConsumer implements MessageListener {
    protected Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onMessage(Message message) {
//        try {
//            logger.info("【测试队列】=========接收到消息");
//            if(message instanceof TextMessage){
//                logger.info("【测试队列】=========消息内容："+((TextMessage) message).getText());
//                message.acknowledge();
//            }
//
//        } catch (MessageConversionException | JMSException e) {
//            e.printStackTrace();
//        }
    }
}
