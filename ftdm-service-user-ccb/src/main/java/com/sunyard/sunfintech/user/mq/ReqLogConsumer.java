package com.sunyard.sunfintech.user.mq;

import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.ReqLogWithBLOBs;
import com.sunyard.sunfintech.dao.mapper.ReqLogMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 日志记录
 */
public class ReqLogConsumer implements ChannelAwareMessageListener {

    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private ReqLogMapper reqLogMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            ReqLogWithBLOBs reqLogWithBLOBs = MQUtils.getObject(message, ReqLogWithBLOBs.class);
            if (null != reqLogWithBLOBs) {
//                if ("begin".equals(reqLogWithBLOBs.getRemark())) {
//                    reqLogMapper.insert(reqLogWithBLOBs);
//                }
//                if ("return".equals(reqLogWithBLOBs.getRemark())) {
//                        ReqLogExample example = new ReqLogExample();
//                        example.createCriteria().andOrder_noEqualTo(reqLogWithBLOBs.getOrder_no());
//                        reqLogMapper.updateByExampleSelective(reqLogWithBLOBs, example);
                    reqLogMapper.insert(reqLogWithBLOBs);
//                }
            }
        } catch (Exception e){
            logger.error("【日志记录】消费失败：",e);
        }finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

    }
}
