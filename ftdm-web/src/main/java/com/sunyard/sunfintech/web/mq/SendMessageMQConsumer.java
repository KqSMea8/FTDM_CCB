package com.sunyard.sunfintech.web.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.dao.entity.MessageLog;
import com.sunyard.sunfintech.dao.entity.ReqMessage;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.web.business.OutsideBusiness;
import com.sunyard.sunfintech.web.model.modulepublic.MsgModel;
import com.sunyard.sunfintech.web.model.modulepublic.OutsideResponse;
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
public class SendMessageMQConsumer implements ChannelAwareMessageListener {
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
//            System.out.println("接收到消息");
//            if(message instanceof ObjectMessage){
//                System.out.println("消息为ObjectMessage");
//                ObjectMessage objectMessage=(ObjectMessage) message;
//                Object object=objectMessage.getObject();
//                MsgModel msgModel = (MsgModel) object;
//                sendMsg(msgModel);
//                message.acknowledge();
//            }
            MsgModel msgModel = MQUtils.getObject(message, MsgModel.class);
            logger.info("【发送短信】发送消息开始，内容："+ JSON.toJSON(msgModel));
            sendMsg(msgModel);
            logger.info("【发送短信】发送消息成功");

        } catch (Exception e) {
            logger.error("【发送短信】失败" + e.getMessage());
            e.printStackTrace();
        }finally{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    private void sendMsg(MsgModel msgModel) {
        logger.info("【短信通知队列】=========开始发送短信");
        logger.info("【短信通知队列】=============手机号:" + msgModel.getMobile() + ",内容:" + msgModel.getMsgContent() + ",订单号:" + msgModel.getOrder_no());
        try {

            String msg_channel_code = sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.MSG_CHANNEL_CODE);

            ReqMessage reqMessage=new ReqMessage();
            reqMessage.setMobile(msgModel.getMobile());
            reqMessage.setPlat_no(msgModel.getPlat_no());
            reqMessage.setChannel_code(msg_channel_code);
            reqMessage.setOrder_no(msgModel.getOrder_no());
            reqMessage.setSend_info(msgModel.getMsgContent());
            reqMessage.setTrans_code(msgModel.getTrans_code());
            reqMessage.setSend_time(DateUtils.getNow());
            reqMessage.setEnabled(Constants.ENABLED);
            try{
                OutsideResponse response = outsideBusiness.sendMsg(msgModel.getTrans_serial(), msgModel.getMobile(),
                        msgModel.getMsgContent(), msg_channel_code, msgModel.getPlat_no());
                if (response.getResult()) {
                    logger.info("短信发送成功！");
                } else {
                    logger.info("短信发送失败！");
                }
                reqMessage.setRemark("发送成功："+response.getReturn_data());
            }catch (Exception e){
                reqMessage.setException_info("error:"+e.getMessage());
            }
            reqMessage.setCreate_by(msgModel.getPlat_no());
            reqMessage.setCreate_time(DateUtils.getNow());
            //发送消息到DB处理队列
            boolean isSucc= MQUtils.send(amqpTemplate, "ftdm.web.direct.exchange", "MsgResultToBDQueue", reqMessage);
            if (!isSucc){
                logger.info("短信发送结果丢进MQ失败|order_no:{}|reqMessage:{}",msgModel.getOrder_no(),JSON.toJSONString(reqMessage));
            }
        } catch (BusinessException e) {
            logger.info("短信发送失败：",e);
        }
    }
}

