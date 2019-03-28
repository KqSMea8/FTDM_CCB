package com.sunyard.sunfintech.user.mq;

import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.ReqMessage;
import com.sunyard.sunfintech.dao.mapper.ReqMessageMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 日志记录
 */
public class MsgResultToDBConsumer implements ChannelAwareMessageListener {

    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private ReqMessageMapper reqMessageMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            logger.info("短信入库队列接收到消息");
            ReqMessage reqMessage = MQUtils.getObject(message, ReqMessage.class);
            if (null != reqMessage) {
                reqMessageMapper.insert(reqMessage);
            }
        }catch (Exception e) {
            logger.error("短信发送结果入库失败：",e);
        }finally{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

    }
}
