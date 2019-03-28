package com.sunyard.sunfintech.account.modulepublic.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.account.modulepublic.SysParameterService;
import com.sunyard.sunfintech.core.util.KeyValueUtil;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Created by terry on 2017/5/11.
 */
public class TestDirectReceiver implements ChannelAwareMessageListener {

    @Value("${deploy.environment}")
    private String deployEnvironment;

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private SysParameterService sysParameterService;



    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        KeyValueUtil keyValueUtil = MQUtils.getObject(message, KeyValueUtil.class);
        System.out.println("线程【" + Thread.currentThread().getId() + " 】: " + keyValueUtil );
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        System.out.print(StringUtils.isBlank(
                " "
        ));
    }
}
