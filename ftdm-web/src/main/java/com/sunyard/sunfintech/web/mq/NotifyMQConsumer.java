package com.sunyard.sunfintech.web.mq;

import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.web.business.NotifyBusiness;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by terry on 2017/8/17.
 */
public class NotifyMQConsumer implements ChannelAwareMessageListener {
    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "notifyBusiness")
    private NotifyBusiness notifyBusiness;

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            NotifyData notifyData = MQUtils.getObject(message, NotifyData.class);
            notifyBusiness.sendNotify(notifyData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
