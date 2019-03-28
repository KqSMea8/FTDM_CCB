package com.sunyard.sunfintech.prod.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.prod.model.mq.BorrowData;
import com.sunyard.sunfintech.prod.model.mq.TransProdData;
import com.sunyard.sunfintech.prod.provider.IProdMQOptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;

import java.io.IOException;


/**
 * Created by terry on 2017/7/28.
 */
public class BatchProdConsumer implements ChannelAwareMessageListener {

    @Autowired
    private IProdMQOptionService prodMQOptionService;
    protected Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("【批量债转队列消费】队列消费者接收到消息");
            TransProdData transProd = MQUtils.getObject(message, TransProdData.class);
            logger.info("【mq批量债转队列消费】准备消费："+ JSON.toJSON(transProd));
            prodMQOptionService.doTransProd(transProd.getProdTransferDebtRequestBoList(), transProd.getNotifyURL());
        } catch (Exception e){
            logger.error("【mq批量债转队列消费】消费失败：",e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
