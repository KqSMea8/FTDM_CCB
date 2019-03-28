package com.sunyard.sunfintech.user.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.user.model.bo.PlatfromToPersonData;
import com.sunyard.sunfintech.user.provider.IPlatfromToPersonMQService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by PengZY on 2017/10/16.
 */
public class BatchPlatformToPersonConsumer implements ChannelAwareMessageListener {

    @Autowired
    private IPlatfromToPersonMQService platfromToPersonMQService;

    @Override
    public void onMessage(Message message, Channel channel) {
       /* try {
            System.out.println("平台转个人队列消费者接收到消息");
            PlatfromToPersonData platfromToPersonData  = MQUtils.getObject(message, PlatfromToPersonData.class);
            platfromToPersonMQService.doPlatfromToPerson(platfromToPersonData);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}
