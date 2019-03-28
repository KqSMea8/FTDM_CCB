package com.sunyard.sunfintech.account.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.account.model.bo.QueryPayStatus;
import com.sunyard.sunfintech.account.model.bo.QueryPayStatusResponse;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by terry on 2017/9/8.
 */
public class QueryPayStatusConsumer implements ChannelAwareMessageListener
{
    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private IAccountTransferThirdParty accountTransferThirdParty;
    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            logger.info("业务流水消费者接收到消息");
            QueryPayStatus queryPayStatus  = MQUtils.getObject(message, QueryPayStatus.class);
            if(queryPayStatus!=null){

                logger.info("接收到消息内容："+ JSON.toJSONString(queryPayStatus));


                Map<String,Object> params=new HashMap<>();
                params.put("partner_id","10000001");
                params.put("partner_serial_no", SeqUtil.getSeqNum());
                params.put("original_serial_no",queryPayStatus.getTrans_serial());
                params.put("occur_balance",queryPayStatus.getTrans_amt());
                try{
                    QueryPayStatusResponse queryPayStatusResponse=accountTransferThirdParty.queryPayStatusQuery(params,queryPayStatus.getMall_no());
                    if(queryPayStatusResponse==null || "2".equals(queryPayStatusResponse.getData().get(0).getPay_status())){
                        logger.info("订单号"+queryPayStatus.getTrans_serial()+"查询到处理中，加入队列，继续查询。");
                        accountTransferThirdParty.addQueryToQueue(queryPayStatus);
                    }
                }catch (Exception e){
                    //重新加入队列
                    logger.error("查询异常：",e);
                    accountTransferThirdParty.addQueryToQueue(queryPayStatus);
                }

            }else{
                String msg = new String(message.getBody(),"UTF-8");
                logger.info("接收到消息内容："+ msg+"反序列化失败");
            }
        } catch (MessageConversionException | IOException e) {
            e.printStackTrace();
            logger.error("QueryPayStatusConsumer",e);
        }  finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
//    @Override
//    public void onMessage(Message message) {
//        try {
//            System.out.println("接收到消息");
//            if(message instanceof ObjectMessage){
//                System.out.println("消息为ObjectMessage");
//                ObjectMessage objectMessage=(ObjectMessage) message;
//                Object object=objectMessage.getObject();
//                QueryPayStatus queryPayStatus = (QueryPayStatus)object;
//
//                message.acknowledge();
//            }
//
//        } catch (MessageConversionException | JMSException e) {
//            e.printStackTrace();
//        }
  //  }
}
