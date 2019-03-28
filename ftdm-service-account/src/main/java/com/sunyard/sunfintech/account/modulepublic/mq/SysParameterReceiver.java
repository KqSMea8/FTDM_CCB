package com.sunyard.sunfintech.account.modulepublic.mq;

import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.account.modulepublic.SysParameterService;
import com.sunyard.sunfintech.core.util.KeyValueUtil;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by terry on 2017/5/11.
 */
public class SysParameterReceiver implements ChannelAwareMessageListener {

    @Value("${deploy.environment}")
    private String deployEnvironment;

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private SysParameterService sysParameterService;
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        KeyValueUtil keyValueUtil = MQUtils.getObject(message, KeyValueUtil.class);
        System.out.println("######################消息内容#######################");
        System.out.println(keyValueUtil );
        System.out.println("######################消息答应#######################");
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
//    @Override
//    public void onMessage(Message message) {
//        try {
//            System.out.println("######################收到消息#######################");
//            String msg = new String(message.getBody(), "UTF-8");
//            KeyValueUtil keyValueUtil = MQUtils.getObject(message, KeyValueUtil.class);
//            System.out.println("######################消息内容#######################");
//            System.out.println(msg);
//            System.out.println("######################消息答应#######################");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static void main(String args[])
    {
        System.out.print(StringUtils.isBlank(
                " "
        ));
    }
}
