package com.sunyard.sunfintech.billcheck.modulepublic.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;

import java.io.IOException;

/**
 * Created by terry on 2017/5/11.
 */
public class TransTransreqConsumer extends BaseServiceSimple implements ChannelAwareMessageListener {

    @Autowired
    private ITransReqService transReqService;

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            logger.info("业务流水消费者接收到消息");
            TransTransreq transTransReq  = MQUtils.getObject(message, TransTransreq.class);
            if(transTransReq!=null){
                logger.info("接收到消息内容："+ JSON.toJSONString(transTransReq));
                transReqService.addFlow(transTransReq);
            }else{
                String msg = new String(message.getBody(),"UTF-8");
                logger.info("接收到消息内容："+ msg+"反序列化失败");
            }
        } catch (MessageConversionException | IOException e) {
            e.printStackTrace();
            logger.error("TransTransreqConsumer异常",e);
        }finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }



}
