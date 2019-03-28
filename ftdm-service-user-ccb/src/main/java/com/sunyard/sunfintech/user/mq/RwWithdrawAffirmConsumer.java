package com.sunyard.sunfintech.user.mq;

import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.user.model.bo.RwWithdrawAffirmMQEntity;
import com.sunyard.sunfintech.user.provider.IRwWithdrawMQOptionSerivce;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by PengZY on 2018/1/30.
 */
public class RwWithdrawAffirmConsumer extends BaseServiceSimple implements ChannelAwareMessageListener {

    @Autowired
    private IRwWithdrawMQOptionSerivce rwWithdrawMQOptionSerivce;

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            System.out.println("提现确认队列消费者接收到消息");
            RwWithdrawAffirmMQEntity rwWithdrawAffirmMQEntity  = MQUtils.getObject(message, RwWithdrawAffirmMQEntity.class);
            rwWithdrawMQOptionSerivce.doRwWithdrawAffirm(rwWithdrawAffirmMQEntity);
        } catch (Exception e){
            logger.error("【提现确认】消费失败：",e);
        }finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

    }
}
