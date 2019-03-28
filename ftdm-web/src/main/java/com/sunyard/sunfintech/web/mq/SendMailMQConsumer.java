package com.sunyard.sunfintech.web.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.MailUtil;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.web.business.OutsideBusiness;
import com.sunyard.sunfintech.pub.model.MailModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by terry on 2017/8/17.
 */
public class SendMailMQConsumer implements ChannelAwareMessageListener {
    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "outsideBusiness")
    private OutsideBusiness outsideBusiness;

    @Autowired
    private ISysParameterService sysParameterService;

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            MailModel mailModel = MQUtils.getObject(message, MailModel.class);
            logger.info("【发送邮件】发送消息开始，内容："+ JSON.toJSON(mailModel));
            if(mailModel!=null){
                MailUtil.sendMail(mailModel.getSubject(), mailModel.getMail_content());
                logger.info("【发送邮件】发送消息成功");
            }
        } catch (Exception e) {
            logger.error("【发送邮件】失败：",e);
            e.printStackTrace();
        }finally{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}

