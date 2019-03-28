package com.sunyard.sunfintech.prod.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.prod.model.mq.ProdRfundData;
import com.sunyard.sunfintech.prod.provider.IProdMQOptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * Created by terry on 2017/7/28.
 */
public class BatchProdIdRefundQueue implements ChannelAwareMessageListener {

    @Autowired
    private IProdMQOptionService prodMQOptionService;

    protected Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("【标的还款队列消费】队列消费者接收到消息");
            ProdRfundData prodRfundData = MQUtils.getObject(message, ProdRfundData.class);
            logger.info("【mq标的还款队列消费】准备消费："+ JSON.toJSON(prodRfundData));
            prodMQOptionService.doProdRepay(prodRfundData.getBaseRequest(), prodRfundData.getBatchCustRepay(), prodRfundData.getNotifyURL());
        } catch (Exception e){
            logger.error("【mq标的还款队列消费】消费失败：",e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
