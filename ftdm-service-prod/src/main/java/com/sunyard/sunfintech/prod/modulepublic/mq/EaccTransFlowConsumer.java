package com.sunyard.sunfintech.prod.modulepublic.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import com.sunyard.sunfintech.pub.provider.IEaccTransTransreqService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * Created by terry on 2017/7/18.
 */
public class EaccTransFlowConsumer implements ChannelAwareMessageListener {

    @Autowired
    private IEaccTransTransreqService eaccTransTransreqService;

    protected Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("电子账户交易流水队列消费者接收到消息");
            EaccTransTransreqWithBLOBs transTransReq = MQUtils.getObject(message, EaccTransTransreqWithBLOBs.class);
            logger.info("【mq交易流水队列消费】准备消费："+ JSON.toJSON(transTransReq));
            eaccTransTransreqService.addFlow(transTransReq);
        }  finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
