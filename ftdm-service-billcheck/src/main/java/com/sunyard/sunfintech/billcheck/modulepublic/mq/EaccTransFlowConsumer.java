package com.sunyard.sunfintech.billcheck.modulepublic.mq;

import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import com.sunyard.sunfintech.pub.provider.IEaccTransTransreqService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;

/**
 * Created by terry on 2017/7/18.
 */
public class EaccTransFlowConsumer  implements ChannelAwareMessageListener {

    @Autowired
    private IEaccTransTransreqService eaccTransTransreqService;

//    @Override
//    public void onMessage(Message message) {
//        try {
//            System.out.println("电子账户交易流水队列消费者接收到消息");
//            if(message instanceof ObjectMessage){
//                ObjectMessage objectMessage=(ObjectMessage) message;
//                Object object=objectMessage.getObject();
//                EaccTransTransreqWithBLOBs transTransReq = (EaccTransTransreqWithBLOBs)object;
//                eaccTransTransreqService.addFlow(transTransReq);
//                message.acknowledge();
//            }
//
//        } catch (MessageConversionException | JMSException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        EaccTransTransreqWithBLOBs transTransReq = MQUtils.getObject(message, EaccTransTransreqWithBLOBs.class);
        eaccTransTransreqService.addFlow(transTransReq);
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
