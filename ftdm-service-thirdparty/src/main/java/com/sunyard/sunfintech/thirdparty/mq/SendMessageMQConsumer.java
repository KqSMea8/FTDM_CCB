package com.sunyard.sunfintech.thirdparty.mq;

import com.alibaba.fastjson.JSON;
import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.thirdparty.model.OutsideResponse;
import com.sunyard.sunfintech.thirdparty.model.ReqMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by terry on 2017/8/17.
 */
public class SendMessageMQConsumer implements ChannelAwareMessageListener {
    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    private static final String ACCOUNT = "p2p_cg";
    private static final String PASS = "123456";
    private static final String SMS_IP = "192.168.204.78";
    private static final int SMS_PORT = 8090;

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
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            MsgModel msgModel = MQUtils.getObject(message, MsgModel.class);
            logger.info("【发送短信】发送消息开始，内容："+ JSON.toJSON(msgModel));
            sendMsg(msgModel);
            logger.info("【发送短信】发送消息成功");

        } catch (Exception e) {
            logger.error("【发送短信】失败:" ,e);
            e.printStackTrace();
        }
    }

    private void sendMsg(MsgModel msgModel) {
        logger.info("【短信通知队列】=========开始发送短信");
        logger.info("【短信通知队列】=============手机号:" + msgModel.getMobile() + ",内容:" + msgModel.getMsgContent() + ",订单号:" + msgModel.getOrder_no());
        try {

            ReqMessage reqMessage=new ReqMessage();
            reqMessage.setMobile(msgModel.getMobile());
            reqMessage.setPlat_no(msgModel.getPlat_no());
            reqMessage.setChannel_code("099");
            reqMessage.setOrder_no(msgModel.getOrder_no());
            reqMessage.setSend_info(msgModel.getMsgContent());
            reqMessage.setTrans_code(msgModel.getTrans_code());
            reqMessage.setSend_time(DateUtils.getNow());
            reqMessage.setEnabled(Constants.ENABLED);
            try{
                //TODO 发送短信
//                String mobileNo = msgModel.getMobile();
//                String message =new String(msgModel.getMsgContent().getBytes("utf-8"), "utf-8");
//                String message2 =new String(msgModel.getMsgContent().getBytes("gbk"), "utf-8");
//                Account ac = new Account(ACCOUNT, PASS);
//                PostMsg pm = new PostMsg();
//                pm.getCmHost().setHost(SMS_IP, SMS_PORT);
//                pm.getWsHost().setHost(SMS_IP, SMS_PORT);
//                MTPack pack = new MTPack();
//                pack.setBatchID(UUID.randomUUID());
//                pack.setBatchName(DateUtil.getDateFormat(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
//                pack.setMsgType(MTPack.MsgType.SMS);
//                pack.setBizType(0);
//                pack.setDistinctFlag(false);
//                ArrayList<MessageData> msgs = new ArrayList();
//                pack.setSendType(MTPack.SendType.MASS);
//                msgs.add(new MessageData(mobileNo, message));
//                msgs.add(new MessageData(mobileNo,message2));
//                pack.setMsgs(msgs);
//                GsmsResponse resp = pm.post(ac, pack);
//                Pattern p = Pattern.compile("<string>(.?)</string>");
//                Matcher m = p.matcher(resp.getAttributes());
//
//                String success_num;
//                for(success_num = null; m.find(); success_num = m.group(1)) {
//                    ;
//                }
//
//                System.out.println(success_num);

//                OutsideResponse response = new OutsideResponse();
//                if (response.getResult()) {
//                    logger.info("短信发送成功！");
//                } else {
//                    logger.info("短信发送失败！");
//                }

                Account ac = new Account(ACCOUNT, PASS);//
                PostMsg pm = new PostMsg();
                pm.getCmHost().setHost(SMS_IP, SMS_PORT);//设置网关的IP和port，用于发送信息
                pm.getWsHost().setHost(SMS_IP, SMS_PORT);//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等

                GsmsResponse gsmsResponse=doSendSms(pm, ac,msgModel.getMobile(),msgModel.getMsgContent()); //短信下行

                reqMessage.setRemark("发送成功："+JSON.toJSONString(gsmsResponse));
                logger.info("短信发送成功："+JSON.toJSONString(gsmsResponse));
            }catch (Exception e){
                reqMessage.setException_info("error:"+e.getMessage());
                logger.info("短信发送失败：",e);
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



    /**
     * 短信下发范例
     * @param pm
     * @param ac
     */
    private GsmsResponse doSendSms(PostMsg pm, Account ac, String mobile, String content) throws Exception{
        MTPack pack = new MTPack();
        pack.setBatchID(UUID.randomUUID());
        pack.setBatchName("晋商存管短信发送");
        pack.setMsgType(MTPack.MsgType.SMS);
        pack.setBizType(0);
        pack.setDistinctFlag(false);
        ArrayList<MessageData> msgs = new ArrayList<MessageData>();

//		/** 单发，一号码一内容 */
        pack.setSendType(MTPack.SendType.MASS);
		msgs.add(new MessageData(mobile, content));
		pack.setMsgs(msgs);

        /** 群发，多号码一内容 */
//        pack.setSendType(MTPack.SendType.MASS);
//        String content = "test";
//        msgs.add(new MessageData("13430258111", content));
//        msgs.add(new MessageData("13430258222", content));
//        msgs.add(new MessageData("13430258333", content));
//        pack.setMsgs(msgs);

//		/** 组发，多号码多内容 */
//		pack.setSendType(SendType.GROUP);
//		msgs.add(new MessageData("13430258111", "短信组发测试111"));
//		msgs.add(new MessageData("13430258222", "短信组发测试222"));
//		msgs.add(new MessageData("13430258333", "短信组发测试333"));
//		pack.setMsgs(msgs);

        GsmsResponse resp = pm.post(ac, pack);
        System.out.println(resp);
        return resp;
    }
}

