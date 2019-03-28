package com.sunyard.sunfintech.user.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.user.provider.IUserServiceStatusQueryService;
import com.sunyard.sunfintech.user.service.callback.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terry on 2018/1/30.
 */
public class ToUserBackNotifyConsumer extends BaseServiceSimple implements ChannelAwareMessageListener {
//    @Autowired
//    private ITransReqService transReqService;
//    @Autowired
//    private INotifyService notifyService;
//    @Autowired
//    private IPlatfromToPersonMQService platfromToPersonMQService;
//    @Autowired
//    private IProdRepayMQOptionService prodRepayMQOptionService;

    @Autowired
    private IUserServiceStatusQueryService userServiceStatusQueryService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("【User异步通知接收】接收到发送到user模块的消息");
            TransTransreq transTransreq = MQUtils.getObject(message, TransTransreq.class);
            userServiceStatusQueryService.callBack(transTransreq);
//            if (transTransreq != null) {
//                if (!transCodeRouter(transTransreq)) {
//                    logger.info("【User异步通知接收】未匹配到trans_code，丢弃消息|trans_code:%s|order_no:%s|trans_serial:%s",
//                            transTransreq.getTrans_code(), transTransreq.getOrder_no(), transTransreq.getTrans_serial());
//                }
//            }
        } catch (Exception e) {
            logger.error("【User异步通知接收】消费异常：", e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
//    private boolean transCodeRouter(TransTransreq transTransreq) throws BusinessException {
//        String trans_code = transTransreq.getTrans_code();
//        logger.info(String.format("【User异步通知接收】处理消息|trans_code:%s|params:%s", trans_code, JSON.toJSONString(transTransreq)));
//        switch (trans_code) {
//            case TransConsts.PLAT_TO_ACCOUNT_CODE: {
//                logger.info("【User异步通知接收】接收到平台转个人消息|trans_serial:%s|status:%s|return_code:%s|return_msg:%",
//                        transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg());
//                platfromToPersonMQService.doPlatfromToPerson(transTransreq);
//                break;
//            }
//            case TransConsts.PAY_FEE_CODE: {
//
//                transReqService.insert(transTransreq);
//                logger.info(String.format("【User异步通知接收-缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。更新成功",
//                        transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg()));
//                notify(transTransreq);
//                break;
//            }
//            case TransConsts.AUTH_PAY_FEE_CODE: {
//
//                transReqService.insert(transTransreq);
//                logger.info(String.format("【User异步通知接收-%s】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。更新成功",
//                        TransConsts.AUTH_PAY_FEE_NAME, transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg()));
//                updateParentTrans(transTransreq);
//                notify(transTransreq);
//                break;
//            }
//            case TransConsts.CANCEL_PAY_FEE_CODE: {
//                transReqService.insert(transTransreq);
//                logger.info(String.format("【User异步通知接收-取消缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。更新成功",
//                        transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg()));
//                break;
//            }
//            case TransConsts.BORROWREPAY_CODE: {
//                transReqService.insert(transTransreq);
//                logger.info(String.format("【User异步通知接收-取消缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。更新成功",
//                        transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg()));
//                prodRepayMQOptionService.afterBatchRepayAsynNotify(transTransreq);
//                break;
//            }
//            default: {
//                return false;
//            }
//        }
//        return true;
//    }


}
