package com.sunyard.sunfintech.prod.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.prod.model.mq.InvestData;
import com.sunyard.sunfintech.prod.provider.IProdMQOptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;


/**
 * Created by terry on 2017/7/28.
 */
public class BatchInvestConsumer implements ChannelAwareMessageListener {

    @Autowired
    private IProdMQOptionService prodMQOptionService;

    protected Logger logger = LogManager.getLogger(getClass());


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("批量投标队列消费者接收到消息");
           List<InvestData> investList = MQUtils.getArrayObject(message, InvestData.class);
            logger.info("【mq批量投标队列消费】准备消费："+ JSON.toJSON(investList));
            if (investList!=null&&investList.size()>0) {
                for (InvestData invest:investList) {
                    prodMQOptionService.doInvest(invest.getBaseRequest(), invest.getProdInvestData(), invest.getNotifyURL(),invest.getAuthValid());
                }
            }
        } catch (Exception e){
            logger.error("【mq批量投标队列消费】消费失败：",e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
