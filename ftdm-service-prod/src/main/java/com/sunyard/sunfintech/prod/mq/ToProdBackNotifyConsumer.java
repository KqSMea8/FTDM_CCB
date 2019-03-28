package com.sunyard.sunfintech.prod.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.provider.IProdServiceStatusQueryService;
import com.sunyard.sunfintech.prod.service.ProdMQOptionService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by terry on 2018/1/30.
 */
public class ToProdBackNotifyConsumer extends BaseServiceSimple implements ChannelAwareMessageListener {

    @Autowired
    private IProdServiceStatusQueryService prodServiceStatusQueryService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("【Prod异步通知接收】接收到发送到prod模块的消息");
            TransTransreq transTransreq  = MQUtils.getObject(message, TransTransreq.class);
            if(transTransreq!=null){
                if(!prodServiceStatusQueryService.transCodeRouter(transTransreq)){
                    logger.info("【Prod异步通知接收】未匹配到trans_code，丢弃消息|trans_code:%s|order_no:%s|trans_serial:%s",
                            transTransreq.getTrans_code(),transTransreq.getOrder_no(),transTransreq.getTrans_serial());
                }
            }
        } catch (Exception e) {
            logger.error("【Prod异步通知接收】消费异常：",e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
