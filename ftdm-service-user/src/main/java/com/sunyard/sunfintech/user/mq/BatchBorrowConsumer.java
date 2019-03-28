package com.sunyard.sunfintech.user.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.user.model.bo.BatchRepayAsynMQEntity;
import com.sunyard.sunfintech.user.model.bo.BorrowData;
import com.sunyard.sunfintech.user.provider.IProdRepayMQOptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * Created by terry on 2017/7/28.
 */
public class BatchBorrowConsumer implements ChannelAwareMessageListener {

    @Autowired
    private IProdRepayMQOptionService prodMQOptionService;

    protected Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("【借款人还款申请】队列消费者接收到消息");
            BatchRepayAsynMQEntity batchRepayAsynMQEntity = MQUtils.getObject(message, BatchRepayAsynMQEntity.class);
            if(batchRepayAsynMQEntity!=null){
                logger.info("【mq借款人还款申请】准备消费："+ JSON.toJSON(batchRepayAsynMQEntity));
                prodMQOptionService.doBatchRepayAsyn(batchRepayAsynMQEntity);
            }else{
                logger.info("【mq借款人还款申请】反序列化异常");
            }
        } catch (Exception e){
            logger.error("【借款人还款申请】消费失败：",e);
        }finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
